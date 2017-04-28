package menus;

import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import paint.EventHandler;
import paint.MainFrame;
import tabs.DisplayTools;
import tabs.ShowOrHideTools;
import tabs.ZoomTools;
import useful.Settings;

/**
 * @author Moses Muigai Gitau
 */
public class ViewMenu extends JPanel {

    private final Settings settings;
    private final BoxLayout layout;

    private ZoomTools zoomTools;
    private ShowOrHideTools showOrHideTools;
    private DisplayTools displayTools;

    private final EventHandler eventHandler;

    public ViewMenu(final EventHandler eventHandler) {
        this.settings = eventHandler.getSettings();
        this.eventHandler = eventHandler;

        layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        this.setBackground((Color) settings.getProperty(Settings.DISPLAY_COLOR));
        initComponents();
    }

    private void initComponents() {
        zoomTools = new ZoomTools(eventHandler);
        add(zoomTools);

        showOrHideTools = new ShowOrHideTools(eventHandler);
        add(showOrHideTools);

        displayTools = new DisplayTools(eventHandler);
        add(displayTools);

        add(Box.createHorizontalStrut(Integer.MAX_VALUE));
    }
}
