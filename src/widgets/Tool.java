package widgets;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * @author Moses Muigai Gitau
 */
public class Tool extends JComponent {

    protected List<ActionListener> listeners = new ArrayList();
    protected boolean active = false;
    protected ImageIcon icon = null;
    protected ImageIcon cursor = null;

    public Tool() {
    }

    public void setActive(boolean active) {
        this.active = active;
        repaint();
    }

    public boolean isActive() {
        return active;
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void removeActionListener(ActionListener listener) {
        listeners.remove(listener);
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
        repaint();
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setCursorIcon(ImageIcon cursor) {
        this.cursor = cursor;
    }

    public ImageIcon getCursorIcon() {
        return cursor;
    }
}
