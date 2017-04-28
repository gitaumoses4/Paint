package widgets;

import imageediting.ImageManip;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 * @author Moses Muigai Gitau
 */
public class MenuAction extends JMenuItem implements ActionListener {

    MenuAbstractAction action = new MenuAbstractAction();
    Graphics graphics;

    public MenuAction() {
        this.setAction(action);
    }

    public MenuAction(String name) {
        this();
        action.setName(name);
    }

    public MenuAction(ImageIcon icon, int width, int height) {
        this();
        action.setIcon(icon, width, height);
    }

    public MenuAction(String name, ImageIcon icon, int width, int height) {
        this(name);
        action.setIcon(icon, width, height);
    }

    public MenuAction(String name, ImageIcon icon, char mnemonic, int width, int height) {
        this(name, icon, width, height);
        action.setMnemonic(mnemonic);
    }

    public MenuAction(String name, ImageIcon icon, char mnemonic, String toolTip, int width, int height) {
        this(name, icon, mnemonic, width, height);
        action.setToolTip(toolTip);
    }

    public void setToolTip(String toolTip) {
        action.setToolTip(toolTip);
    }

    @Override
    public void paint(Graphics g) {
        graphics = g;
        if (isSelected()) {
            g.setColor(UIManager.getColor("MenuItem.selectionBackground"));
            g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
            g.setColor(g.getColor().darker());
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        super.paint(g);
    }

    @Override
    public void setSelected(boolean value) {
        super.setSelected(value);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    class MenuAbstractAction extends AbstractAction {

        public void setName(String name) {
            this.putValue(AbstractAction.NAME, name);
        }

        public void setIcon(ImageIcon imageIcon, int width, int height) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = image.createGraphics();
            g.drawImage(imageIcon.getImage(), 0, 0, width + 1, height + 1, null);
            g.dispose();
            image = ImageManip.removeGray(new ImageIcon(image));
            this.putValue(AbstractAction.SMALL_ICON, new ImageIcon(image));
        }

        public void setMnemonic(char c) {
            this.putValue(AbstractAction.MNEMONIC_KEY, KeyEvent.getExtendedKeyCodeForChar(c));
        }

        public void setToolTip(String tip) {
            this.putValue(AbstractAction.SHORT_DESCRIPTION, tip);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuAction.this.actionPerformed(e);
        }

    }
}
