package useful;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import paint.MainFrame;
import tabs.ShapeTools;

/**
 * @author Moses Muigai Gitau
 */
public class Settings {

    private HashMap<String, Object> settings = new HashMap();

    //settings file location
    private final String SETTINGS_LOCATION = "paint.conf";

    //default settings names
    public static final String DISPLAY_COLOR = "Display Color";
    public static final String LOOK_AND_FEEL = "Look And Feel";
    public static final String TABBED_PANE_FONT = "Tabbed Font font";
    public static final String TABBED_PANE_TOP_COLOR = "Tabbed Pane Top Color";
    public static final String TABBED_PANE_CONTENT_COLOR = "Tabbed Pane Content Color";
    public static final String FILE_TAB_COLOR = "File Tab Color";
    public static final String DEFAULT_PAINT_COLORS = "Default Paint Colors";
    public static final String CANVAS_HEIGHT = "Canvas Height";
    public static final String CANVAS_WIDTH = "Canvas Width";
    public static final String FILL = "Fill";
    public static final String OUTLINE = "Outline";

    public Settings() {
        readDefaultSettings();
        HashMap _settings = readSavedSettings();
        if (_settings.size() == settings.size()) {
            settings = _settings;
        }
    }

    private void readDefaultSettings() {
        settings.put(DISPLAY_COLOR, new Color(240, 240, 240));
        settings.put(LOOK_AND_FEEL, getLookAndFeel());
        settings.put(TABBED_PANE_FONT, new Font("Segoe UI", Font.PLAIN, 13));
        settings.put(TABBED_PANE_CONTENT_COLOR, new Color(245, 245, 247));
        settings.put(TABBED_PANE_TOP_COLOR, new Color(255, 255, 255));
        settings.put(FILE_TAB_COLOR, new Color(25, 121, 202));
        settings.put(DEFAULT_PAINT_COLORS, new Color[]{new Color(0, 0, 0),
            new Color(127, 127, 127), new Color(136, 0, 21), new Color(237, 28, 36),
            new Color(225, 127, 39), new Color(255, 242, 0), new Color(34, 177, 76),
            new Color(0, 162, 232), new Color(63, 72, 204), new Color(163, 73, 164),
            new Color(255, 255, 255), new Color(195, 195, 195), new Color(185, 122, 87),
            new Color(255, 174, 201), new Color(255, 201, 14), new Color(239, 228, 176),
            new Color(181, 230, 29), new Color(153, 217, 234), new Color(112, 146, 190),
            new Color(200, 191, 231)});
        settings.put(CANVAS_WIDTH, 500);
        settings.put(CANVAS_HEIGHT, 400);
        settings.put(FILL, ShapeTools.NO_FILL);
        settings.put(OUTLINE, ShapeTools.SOLID_COLOR);
        writeSettingsToFile();
    }

    private String getLookAndFeel() {
        String lookAndFeel;
        if (MainFrame.isWindows()) {
            lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        } else if (MainFrame.isUnix()) {
            lookAndFeel = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
        } else {
            lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        }
        return lookAndFeel;
    }

    /**
     *
     * @return
     */
    private HashMap readSavedSettings() {
        HashMap<String, Object> _settings = new HashMap();
        try {

            File file = new File(SETTINGS_LOCATION);

            FileInputStream fileInputStream = new FileInputStream(file);

            try (ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

                _settings = (HashMap<String, Object>) objectInputStream.readObject();

            }

        } catch (IOException | ClassNotFoundException ex) {

        }

        return _settings;

    }

    /**
     *
     *
     *
     */
    private void writeSettingsToFile() {

        try {

            File file = new File(SETTINGS_LOCATION);

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

                objectOutputStream.writeObject(settings);

            }

        } catch (Exception ex) {

        }

    }

    /**
     *
     *
     * @param name
     *
     * @param setting
     *
     */
    public void addSettings(String name, Object setting) {

        settings.put(name, setting);

        writeSettingsToFile();

    }

    /**
     *
     *
     *
     * @param name
     *
     * @return
     *
     */
    public Object getProperty(String name) {

        return settings.get(name);

    }

}
