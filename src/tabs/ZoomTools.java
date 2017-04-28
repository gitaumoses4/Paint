package tabs;

import javax.swing.ImageIcon;
import paint.EventHandler;
import widgets.MButton;
import widgets.ToolBarItem;

/**
 * @author Moses Muigai Gitau
 */
public class ZoomTools extends ToolBarItem {

    private final ImageIcon zoomInIcon = new ImageIcon(getClass().getResource("images/zoom_in.png"));
    private final ImageIcon zoomOutIcon = new ImageIcon(getClass().getResource("images/zoom_out.png"));
    private final ImageIcon zoom100Icon = new ImageIcon(getClass().getResource("images/zoom100.png"));

    private final MButton zoomIn;
    private final MButton zoomOut;
    private final MButton zoom100;
    private final EventHandler eventHandler;

    public ZoomTools(EventHandler eventHandler) {
        super("Zoom");
        this.eventHandler = eventHandler;

        zoomIn = new MButton(zoomInIcon, "Zoom In", MButton.BOTTOM, false);
        zoomIn.setIconDimensions(30, 30);
        zoomOut = new MButton(zoomOutIcon, "Zoom Out", MButton.BOTTOM, false);
        zoomOut.setIconDimensions(30, 30);
        zoom100 = new MButton(zoom100Icon, "100 %", MButton.BOTTOM, false);
        zoom100.setIconDimensions(30, 30);

        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(zoomIn, constraints);
        constraints.gridx = 1;
        addComponent(zoomOut, constraints);
        constraints.gridx = 2;
        addComponent(zoom100, constraints);
    }
}
