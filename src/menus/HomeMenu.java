package menus;

import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import paint.EventHandler;
import tabs.BrushTools;
import tabs.ClipboardTools;
import tabs.ColorTools;
import tabs.ImageTools;
import tabs.ShapeTools;
import tabs.Size;
import tabs.ToolsTools;
import useful.Settings;

/**
 * @author Moses Muigai Gitau
 */
public class HomeMenu extends JPanel {

    private final Settings settings;
    private final EventHandler eventHandler;
    private BoxLayout layout;
    private ClipboardTools clip;
    private ImageTools imageTools;
    private ToolsTools tools;
    private BrushTools brushTools;
    private ShapeTools shapeTools;
    private Size sizeTools;
    private ColorTools colorTools;

    public HomeMenu(final EventHandler eventHandler) {
        this.settings = eventHandler.getSettings();
        this.eventHandler = eventHandler;
        layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        setBorder(new EmptyBorder(0, 10, 0, 10));
        initComponents();
    }

    private void initComponents() {
        clip = new ClipboardTools(eventHandler);
        add(clip);
        imageTools = new ImageTools(eventHandler);
        add(imageTools);
        tools = new ToolsTools(eventHandler);
        add(tools);
        brushTools = new BrushTools(eventHandler);
        add(brushTools);
        shapeTools = new ShapeTools(eventHandler);
        add(shapeTools);
        sizeTools = new Size(eventHandler);
        add(sizeTools);
        colorTools = new ColorTools(eventHandler);
        add(colorTools);
        add(Box.createHorizontalStrut(Integer.MAX_VALUE));
    }

    public ToolsTools getDrawingTools() {
        return this.tools;
    }

    public ColorTools getColorTools() {
        return this.colorTools;
    }

    public ShapeTools getShapeTools() {
        return this.shapeTools;
    }

    public Size getSizeTools() {
        return this.sizeTools;
    }

    public ImageTools getImageTools() {
        return this.imageTools;
    }
}
