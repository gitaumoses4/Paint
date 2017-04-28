package paint;

import useful.Settings;
import menus.ViewMenu;
import menus.HomeMenu;
import widgets.MTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.LookAndFeel;
import menus.FontMenu;

/**
 * @author Moses Muigai Gitau
 */
public class MainFrame extends JFrame {

    private final Dimension size = new Dimension(900, 500);
    private int tabbedPaneHeight = 115;
    private MTabbedPane tabbedPane;
    private static final String operatingSystem = System.getProperty("os.name").toLowerCase();
    private String lookAndFeel;
    private Font tabbedPaneFont;
    private EventHandler eventHandler;

    private StatusBar statusBar;
    private Canvas canvas;
    private HomeMenu home;
    private FileMenu file;
    private ViewMenu view;
    private FontMenu font;
    private JScrollPane canvasArea;

    public MainFrame() {
        eventHandler = new EventHandler(this);
        setSize(size);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        getContentPane().setBackground(new Color(201, 211, 226));
    }

    public String getLookAndFeel() {
        return lookAndFeel;
    }

    public void setLookAndFeel(LookAndFeel lookAndFeel) {
        eventHandler.getSettings().addSettings(Settings.LOOK_AND_FEEL, lookAndFeel);
    }

    public static boolean isWindows() {
        return (operatingSystem.contains("win"));
    }

    public static boolean isMac() {
        return operatingSystem.contains("mac");
    }

    public static boolean isUnix() {
        return operatingSystem.contains("nix")
                || operatingSystem.contains("nux")
                || operatingSystem.contains("aix");
    }

    public static boolean isSolaris() {
        return operatingSystem.contains("unos");
    }

    private void initComponents() {

        statusBar = eventHandler.getStatusBar();

        canvas = eventHandler.getCanvas();
        canvasArea = eventHandler.getScrollPane();
        canvasArea.setOpaque(false);
        canvasArea.getViewport().setOpaque(false);

        add(canvasArea);
        add(statusBar, BorderLayout.SOUTH);
        tabbedPane = eventHandler.getMTabbedPane();
        tabbedPaneFont = (Font) eventHandler.getSettings().getProperty(Settings.TABBED_PANE_FONT);
        tabbedPane.setFont(tabbedPaneFont);

        int menuBarPreferredWidth = tabbedPane.getPreferredSize().width;
        Dimension menuBarSize = new Dimension(menuBarPreferredWidth, tabbedPaneHeight);
        tabbedPane.setPreferredSize(menuBarSize);
        file = eventHandler.getFileMenu();
        home = eventHandler.getHomeMenu();
        view = eventHandler.getViewMenu();
        font = eventHandler.getFontMenu();

        tabbedPane.addTab(file, "File");
        tabbedPane.addTab(home, "Home");
        tabbedPane.addTab(view, "View");
        tabbedPane.addTab(font, "Font");
        tabbedPane.setEnabledAt(1);

        add(tabbedPane, BorderLayout.NORTH);
    }

    public int getTabbedPaneHeight() {
        return tabbedPaneHeight;
    }

    public void setTabbedPaneHeight(int height) {
        tabbedPaneHeight = height;
    }
}
