package tabs;

import widgets.Tool;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import paint.EventHandler;
import widgets.MDividedButton;
import widgets.MButton;
import widgets.ToolBarItem;
import widgets.Tools;

/**
 * @author Moses Muigai Gitau
 */
public class BrushTools extends ToolBarItem {

    private final ImageIcon brushIcon = new ImageIcon(getClass().getResource("images/brush.png"));
    private final ImageIcon calligraphBrush1Icon = new ImageIcon(getClass().getResource("images/calligraph_brush1.png"));
    private final ImageIcon calligraphBrush2Icon = new ImageIcon(getClass().getResource("images/calligraph_brush2.png"));
    private final ImageIcon airBrushIcon = new ImageIcon(getClass().getResource("images/airbrush.png"));
    private final ImageIcon oilBrushIcon = new ImageIcon(getClass().getResource("images/oilbrush.png"));
    private final ImageIcon crayonIcon = new ImageIcon(getClass().getResource("images/crayon.png"));
    private final ImageIcon markerIcon = new ImageIcon(getClass().getResource("images/marker.png"));
    private final ImageIcon naturalPencilIcon = new ImageIcon(getClass().getResource("images/natural_pencil.png"));
    private final ImageIcon waterMarkerIcon = new ImageIcon(getClass().getResource("images/water_marker.png"));

    private final MDividedButton brushButton;

    private final MButton brush = new MButton(brushIcon);
    private final MButton calligraphBrush1 = new MButton(calligraphBrush1Icon);
    private final MButton calligraphBrush2 = new MButton(calligraphBrush2Icon);
    private final MButton airBrush = new MButton(airBrushIcon);
    private final MButton oilBrush = new MButton(oilBrushIcon);
    private final MButton crayon = new MButton(crayonIcon);
    private final MButton marker = new MButton(markerIcon);
    private final MButton naturalPencil = new MButton(naturalPencilIcon);
    private final MButton waterMarker = new MButton(waterMarkerIcon);

    private JPanel brushBoard;
    private int size = 35;
    private final EventHandler eventHandler;

    public BrushTools(EventHandler eventHandler) {
        super("");
        this.eventHandler = eventHandler;
        brushButton = new MDividedButton(brushIcon, "Brushes", true);
        brushButton.setName(Tools.BRUSH);
        eventHandler.addTool(brushButton);
        brushButton.setIconDimensions(size, size);
        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(brushButton, constraints);
        addBrushBoard();
    }

    private void addBrushBoard() {
        brushBoard = new JPanel(new GridLayout(0, 4));

        brush.setIconDimensions(size, size);
        brush.setToolTipText("Brush");
        brushBoard.add(brush);

        calligraphBrush1.setIconDimensions(size, size);
        calligraphBrush1.setToolTipText("Calligraph Brush 1");
        brushBoard.add(calligraphBrush1);

        calligraphBrush2.setIconDimensions(size, size);
        calligraphBrush2.setToolTipText("Calligraph Brush 2");
        brushBoard.add(calligraphBrush2);

        airBrush.setIconDimensions(size, size);
        airBrush.setToolTipText("Air Brush");
        brushBoard.add(airBrush);

        oilBrush.setIconDimensions(size, size);
        oilBrush.setToolTipText("Oil Brush");
        brushBoard.add(oilBrush);

        crayon.setIconDimensions(size, size);
        crayon.setToolTipText("Crayon");
        brushBoard.add(crayon);

        marker.setIconDimensions(size, size);
        marker.setToolTipText("Marker");
        brushBoard.add(marker);

        naturalPencil.setIconDimensions(size, size);
        naturalPencil.setToolTipText("Natural Pencil");
        brushBoard.add(naturalPencil);

        waterMarker.setIconDimensions(size, size);
        waterMarker.setToolTipText("Water Marker");
        brushBoard.add(waterMarker);

        brushButton.addMenuComponent(brushBoard);
    }

}
