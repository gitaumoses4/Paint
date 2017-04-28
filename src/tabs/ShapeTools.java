package tabs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import paint.EventHandler;
import useful.Settings;
import useful.ShapeChangeListener;
import widgets.MButton;
import widgets.MenuAction;
import widgets.ToolBarItem;

/**
 * @author Moses Muigai Gitau
 */
public class ShapeTools extends ToolBarItem {

    public static final String NO_OUTLINE = "No Outline";
    public static final String NO_FILL = "No Fill";
    public static final String SOLID_FILL = "Solid Fill";
    public static final String SOLID_COLOR = "Solid Color";
    public static final String CRAYON = "Crayon";
    public static final String MARKER = "Marker";
    public static final String OIL = "Oil";
    public static final String NATURAL_PENCIL = "Natural Pencil";
    public static final String WATER_COLOR = "Water Color";

    private final ImageIcon shapeFillIcon = new ImageIcon(getClass().getResource("images/shape_fill.png"));
    private final ImageIcon shapeOutlineIcon = new ImageIcon(getClass().getResource("images/shape_outline.png"));
    private final ImageIcon noFillIcon = new ImageIcon(getClass().getResource("images/no_fill.png"));
    private final ImageIcon solidFillIcon = new ImageIcon(getClass().getResource("images/solid_fill.png"));
    private final ImageIcon crayonIcon = new ImageIcon(getClass().getResource("images/crayon.png"));
    private final ImageIcon markerIcon = new ImageIcon(getClass().getResource("images/marker.png"));
    private final ImageIcon oilIcon = new ImageIcon(getClass().getResource("images/oilbrush.png"));
    private final ImageIcon naturalPencilIcon = new ImageIcon(getClass().getResource("images/natural_pencil.png"));
    private final ImageIcon waterColorIcon = new ImageIcon(getClass().getResource("images/water_marker.png"));

    private final Shapes shapes;
    private final MButton shapeFill;
    private final MButton shapeOutline;

    private int size = 20;

    private final MenuAction[] noOutline = new MenuAction[2];
    private final MenuAction[] solidFill = new MenuAction[2];
    private final MenuAction[] crayon = new MenuAction[2];
    private final MenuAction[] marker = new MenuAction[2];
    private final MenuAction[] oil = new MenuAction[2];
    private final MenuAction[] naturalPencil = new MenuAction[2];
    private final MenuAction[] waterColor = new MenuAction[2];
    private final EventHandler eventHandler;

    private final ArrayList<MenuAction> fill = new ArrayList();
    private final ArrayList<MenuAction> outline = new ArrayList();

    private final ShapeFillMenuController fillControl = new ShapeFillMenuController();
    private final ShapeOutlineMenuController outlineControl = new ShapeOutlineMenuController();

    private final ArrayList<ShapeChangeListener> listeners = new ArrayList();

    private String fillType = "";
    private String outlineType = "";

    public ShapeTools(EventHandler eventHandler) {
        super("Shapes");
        this.eventHandler = eventHandler;
        shapes = new Shapes(eventHandler);
        shapeFill = new MButton(shapeFillIcon, "Fill", MButton.RIGHT, true);
        shapeFill.setIconDimensions(size - 5, size - 5);
        shapeOutline = new MButton(shapeOutlineIcon, "Outline", MButton.RIGHT, true);
        shapeOutline.setIconDimensions(size - 5, size - 5);
        constraints.gridx = 5;
        constraints.gridy = 0;
        addComponent(shapeOutline, constraints);
        constraints.gridy = 1;
        addComponent(shapeFill, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 3;
        addComponent(shapes, constraints);

        addShapeFillMenu();
        addShapeOutlineMenu();

        setSelectedTools();
    }

    private void addShapeFillMenu() {
        noOutline[0] = new MenuAction(NO_FILL, noFillIcon, 'F', "No Fill", size, size);
        noOutline[0].addActionListener(fillControl);
        solidFill[0] = new MenuAction(SOLID_FILL, solidFillIcon, 'S', "Solid Fill", size, size);
        solidFill[0].addActionListener(fillControl);
        crayon[0] = new MenuAction(CRAYON, crayonIcon, 'C', "Crayon Fill", size, size);
        crayon[0].addActionListener(fillControl);
        marker[0] = new MenuAction(MARKER, markerIcon, 'M', "Marker Fill", size, size);
        marker[0].addActionListener(fillControl);
        oil[0] = new MenuAction(OIL, oilIcon, 'O', "Oil Fill", size, size);
        oil[0].addActionListener(fillControl);
        naturalPencil[0] = new MenuAction(NATURAL_PENCIL, naturalPencilIcon, 'N', "Natural Pencil Fill", size, size);
        naturalPencil[0].addActionListener(fillControl);
        waterColor[0] = new MenuAction(WATER_COLOR, waterColorIcon, 'W', "Watercolor Fill", size, size);
        waterColor[0].addActionListener(fillControl);

        fill.add(noOutline[0]);
        fill.add(solidFill[0]);
        fill.add(crayon[0]);
        fill.add(marker[0]);
        fill.add(oil[0]);
        fill.add(naturalPencil[0]);
        fill.add(waterColor[0]);

        for (MenuAction action : fill) {
            shapeFill.addMenuAction(action);
        }
    }

    private void addShapeOutlineMenu() {
        noOutline[1] = new MenuAction(NO_OUTLINE, noFillIcon, 'O', "No Outline", size, size);
        noOutline[1].addActionListener(outlineControl);
        solidFill[1] = new MenuAction(SOLID_COLOR, solidFillIcon, 'S', "Solid Fill", size, size);
        solidFill[1].addActionListener(outlineControl);
        crayon[1] = new MenuAction(CRAYON, crayonIcon, 'C', "Crayon Fill", size, size);
        crayon[1].addActionListener(outlineControl);
        marker[1] = new MenuAction(MARKER, markerIcon, 'M', "Marker Fill", size, size);
        marker[1].addActionListener(outlineControl);
        oil[1] = new MenuAction(OIL, oilIcon, 'O', "Oil Fill", size, size);
        oil[1].addActionListener(outlineControl);
        naturalPencil[1] = new MenuAction(NATURAL_PENCIL, naturalPencilIcon, 'N', "Natural Pencil Fill", size, size);
        naturalPencil[1].addActionListener(outlineControl);
        waterColor[1] = new MenuAction(WATER_COLOR, waterColorIcon, 'W', "Watercolor Fill", size, size);
        waterColor[1].addActionListener(outlineControl);

        outline.add(noOutline[1]);
        outline.add(solidFill[1]);
        outline.add(crayon[1]);
        outline.add(marker[1]);
        outline.add(oil[1]);
        outline.add(naturalPencil[1]);
        outline.add(waterColor[1]);
        for (MenuAction action : outline) {
            shapeOutline.addMenuAction(action);
        }
    }

    private void setSelectedTools() {
        fillType = (String) eventHandler.getSettings().getProperty(Settings.FILL);
        outlineType = (String) eventHandler.getSettings().getProperty(Settings.OUTLINE);
        for (MenuAction action : this.fill) {
            if (action.getText().equals(fillType)) {
                action.setSelected(true);
            }
        }
        for (MenuAction action : this.outline) {
            if (action.getText().equals(outlineType)) {
                action.setSelected(true);
            }
        }
    }

    public void addShapeChangeListener(ShapeChangeListener l) {
        this.listeners.add(l);
    }

    public void removeShapeChangeListener(ShapeChangeListener l) {
        this.listeners.remove(l);
    }

    public void fireShapeFillChanged(String fill) {
        for (ShapeChangeListener l : listeners) {
            l.shapeFillChanged(fill);
        }
    }

    public void fireShapeOutlineChanged(String outline) {
        for (ShapeChangeListener l : listeners) {
            l.shapeOutlineChanged(outline);
        }
    }

    public String getFill() {
        return fillType;
    }

    public String getOutline() {
        return outlineType;
    }
    public void enableFillButton(boolean value){
        this.shapeFill.setEnabled(value);
    }

    class ShapeFillMenuController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (MenuAction action : fill) {
                if (!action.equals(e.getSource())) {
                    action.setSelected(false);
                } else {
                    fillType = action.getText();
                    action.setSelected(true);
                    fireShapeFillChanged(action.getText());
                }
            }
        }

    }

    class ShapeOutlineMenuController implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (MenuAction action : outline) {
                if (!action.equals(e.getSource())) {
                    action.setSelected(false);
                } else {
                    outlineType = action.getText();
                    action.setSelected(true);
                    fireShapeOutlineChanged(action.getText());
                }
            }
        }
    }
}
