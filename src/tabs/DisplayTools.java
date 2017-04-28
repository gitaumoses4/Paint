package tabs;

import javax.swing.ImageIcon;
import paint.EventHandler;
import widgets.MButton;
import widgets.ToolBarItem;

/**
 * @author Moses Muigai Gitau
 */
public class DisplayTools extends ToolBarItem {

    private final EventHandler eventHandler;
    private final ImageIcon fullScreenIcon = new ImageIcon(getClass().getResource("images/fullscreen.png"));
    private final ImageIcon thumbnailIcon = new ImageIcon(getClass().getResource("images/thumbnail.png"));

    private final MButton fullScreen;
    private final MButton thumbNail;

    public DisplayTools(EventHandler eventHandler) {
        super("Display");
        this.eventHandler = eventHandler;
        this.fullScreen = new MButton(fullScreenIcon, "Full Screen", MButton.BOTTOM, false);
        this.thumbNail = new MButton(thumbnailIcon, "Thumbnail", MButton.BOTTOM, false);

        this.fullScreen.setIconDimensions(30, 30);
        this.thumbNail.setIconDimensions(30, 30);

        constraints.gridx = 0;
        constraints.gridy = 0;
        this.addComponent(fullScreen, constraints);
        constraints.gridx = 1;
        this.addComponent(thumbNail, constraints);
    }
}
