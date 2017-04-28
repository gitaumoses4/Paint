package tabs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import paint.EventHandler;
import widgets.MDividedButton;
import widgets.MButton;
import widgets.ToolBarItem;
import useful.LineSizeChangeListener;

/**
 * @author Moses Muigai Gitau
 */
public class Size extends ToolBarItem {

    private final ImageIcon lineSizeIcon = new ImageIcon(getClass().getResource("images/lineSize.png"));
    private final ImageIcon onePxIcon = new ImageIcon(getClass().getResource("images/1px.png"));
    private final ImageIcon threePxIcon = new ImageIcon(getClass().getResource("images/3px.png"));
    private final ImageIcon fivePxIcon = new ImageIcon(getClass().getResource("images/5px.png"));
    private final ImageIcon eightPxIcon = new ImageIcon(getClass().getResource("images/8px.png"));

    private final MButton lineSize;

    private MButton onePx;
    private MButton threePx;
    private MButton fivePx;
    private MButton eightPx;
    private int lineSizes[] = {1, 3, 5, 8};
    private MButton buttons[] = new MButton[lineSizes.length];

    private JPanel lineBoard;
    private final EventHandler eventHandler;
    private LineSizeChange lineSizeChange = new LineSizeChange();

    private ArrayList<LineSizeChangeListener> listeners = new ArrayList<>();

    public Size(EventHandler eventHandler) {
        super("");
        this.eventHandler = eventHandler;
        lineSize = new MButton(lineSizeIcon, "Size", true);
        lineSize.setIconDimensions(30, 30);

        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(lineSize, constraints);

        addLineSizeMenu();
        this.addLineSizeChangeListener(eventHandler);
    }

    private void addLineSizeMenu() {
        onePx = new MButton(onePxIcon);
        onePx.setIconDimensions(100, 20);
        onePx.addActionListener(lineSizeChange);
        threePx = new MButton(threePxIcon);
        threePx.addActionListener(lineSizeChange);
        threePx.setIconDimensions(100, 23);
        fivePx = new MButton(fivePxIcon);
        fivePx.addActionListener(lineSizeChange);
        fivePx.setIconDimensions(100, 25);
        fivePx.addActionListener(lineSizeChange);
        eightPx = new MButton(eightPxIcon);
        eightPx.setIconDimensions(100, 28);
        eightPx.addActionListener(lineSizeChange);

        lineBoard = new JPanel(new GridLayout(0, 1));

        buttons[0] = onePx;
        buttons[1] = threePx;
        buttons[2] = fivePx;
        buttons[3] = eightPx;
        for (MButton b : buttons) {
            lineBoard.add(b);
        }

        lineSize.addMenuComponent(lineBoard);
    }

    public void addLineSizeChangeListener(LineSizeChangeListener l) {
        this.listeners.add(l);
    }

    public void removeLineSizeChangeListener(LineSizeChangeListener l) {
        this.listeners.remove(l);
    }

    public void fireLineSizeChanged(int size) {
        for (LineSizeChangeListener l : listeners) {
            l.lineSizeChanged(size);
        }
    }

    class LineSizeChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MButton button = (MButton) e.getSource();
            for (int i = 0; i < buttons.length; i++) {
                if (button == buttons[i]) {
                    buttons[i].setActive(true);
                    fireLineSizeChanged(lineSizes[i]);
                }else{
                    buttons[i].setActive(false);
                }
            }
            lineSize.getPopup().setVisible(false);
        }

    }
}
