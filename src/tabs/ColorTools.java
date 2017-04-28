package tabs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import paint.EventHandler;
import useful.ColorChangeListener;
import useful.Settings;
import widgets.MColorButton;
import widgets.MButton;
import widgets.Tool;
import widgets.ToolBarItem;
import widgets.Tools;

/**
 * @author Moses Muigai Gitau
 */
public class ColorTools extends ToolBarItem implements ActionListener {

    private final ImageIcon editColorsIcon = new ImageIcon(getClass().getResource("images/edit_colors.png"));
    private final MColorButton color1;
    private final MColorButton color2;
    private final Settings settings;
    private final Color[] defaultColors;
    private final MColorButton[] defaultColorButtons;
    private final MButton editColors;
    private final EventHandler eventHandler;
    private final SelectedColor selectedColor = new SelectedColor();
    private final ArrayList<ColorChangeListener> listeners = new ArrayList<>();

    public ColorTools(EventHandler eventHandler) {
        super("Colors");
        this.eventHandler = eventHandler;
        this.settings = eventHandler.getSettings();
        defaultColors = (Color[]) settings.getProperty(Settings.DEFAULT_PAINT_COLORS);
        defaultColorButtons = new MColorButton[defaultColors.length + 10];

        color1 = new MColorButton(Color.black, "Color 1", MButton.BOTTOM);
        color1.setIconDimensions(30, 30);
        color1.setName(Tools.COLOR_ONE);
        color1.setActive(true);
        color1.addActionListener(this);

        color2 = new MColorButton(Color.white, "Color 2", MButton.BOTTOM);
        color2.setIconDimensions(30, 30);
        color2.setName(Tools.COLOR_TWO);
        color2.addActionListener(this);

        editColors = new MButton(editColorsIcon, "Edit Colors", MButton.BOTTOM, false);
        initDefaultColorButtons();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        addComponent(color1, constraints);
        constraints.gridx = 1;
        addComponent(color2, constraints);
        editColors.setIconDimensions(30, 30);
        constraints.gridx = 13;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        addComponent(editColors, constraints);
    }

    private void initDefaultColorButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                int index = (i * 10) + j;
                Color color;
                boolean enabled;
                if (i == 2) {
                    color = new Color(0, 0, 0, 0);
                    enabled = false;
                } else {
                    enabled = true;
                    color = defaultColors[index];
                }
                constraints.gridx = 2 + j;
                constraints.gridy = i;
                defaultColorButtons[index] = new MColorButton(color);
                defaultColorButtons[index].setIconDimensions(18, 18);
                defaultColorButtons[index].setInset(1);
                defaultColorButtons[index].setEnabled(enabled);
                defaultColorButtons[index].addActionListener(selectedColor);
                addComponent(defaultColorButtons[index], constraints);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tool selected = (Tool) e.getSource();
        Tool unselected = selected.getName().equals(Tools.COLOR_ONE) ? color2 : color1;
        selected.setActive(true);
        unselected.setActive(false);
    }

    class SelectedColor implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MColorButton colorButton = (MColorButton) e.getSource();
            if (colorButton.isEnabled()) {
                Color color = colorButton.getColor();
                if (color1.isActive()) {
                    color1.setColor(color);
                    eventHandler.setColorOne(color);
                } else {
                    color2.setColor(color);
                    eventHandler.setColorTwo(color);
                }
                fireColorChanged(color1.getColor(), color2.getColor());
            }
        }

    }

    public String getActiveColorTool() {
        return color1.isActive() ? Tools.COLOR_ONE : Tools.COLOR_TWO;
    }

    public void setColorOne(Color color) {
        this.color1.setColor(color);
        this.eventHandler.setColorOne(color);
    }

    public void setColorTwo(Color color) {
        this.color2.setColor(color);
        this.eventHandler.setColorTwo(color);
    }

    public void addColorChangeListener(ColorChangeListener l) {
        this.listeners.add(l);
    }

    public void removeColorChangeListener(ColorChangeListener l) {
        this.listeners.remove(l);
    }

    public void fireColorChanged(Color color1, Color color2) {
        for (ColorChangeListener l : listeners) {
            l.colorChanged(color1, color2);
        }
    }
}
