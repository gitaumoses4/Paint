package paint;

import useful.Settings;
import java.awt.Color;
import javax.swing.JPanel;

/**
 * @author Moses Muigai Gitau
 */
public class FileMenu extends JPanel {

    private final Settings settings;
    private final EventHandler eventHandler;

    public FileMenu(final EventHandler eventHandler) {
        this.settings = eventHandler.getSettings();
        this.eventHandler = eventHandler;

        this.setBackground((Color) settings.getProperty(Settings.DISPLAY_COLOR));
    }
}
