package menus;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import paint.EventHandler;
import tabs.ClipboardTools;
import tabs.ColorTools;
import tabs.FontTools;

/**
 * @author Moses Muigai Gitau
 */
public class FontMenu extends JPanel {

    private final EventHandler eventHandler;
    private FontTools fontTools;
    private ClipboardTools clipboard;
    private ColorTools colorTools;
    private final BoxLayout layout;

    public FontMenu(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        layout = new BoxLayout(this, BoxLayout.X_AXIS);

        setLayout(layout);
        initComponents();
    }

    private void initComponents() {
        clipboard = new ClipboardTools(eventHandler);
        add(clipboard);
        fontTools = new FontTools(eventHandler);
        add(fontTools);
        colorTools = new ColorTools(eventHandler);
        add(colorTools);

        add(Box.createHorizontalStrut(Integer.MAX_VALUE));
    }
}
