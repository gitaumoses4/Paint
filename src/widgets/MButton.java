package widgets;

import imageediting.ImageManip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import useful.Settings;

/**
 * @author Moses Muigai Gitau
 */
public class MButton extends Tool implements MouseListener, MouseMotionListener {

    private final ImageIcon arrow = new ImageIcon(getClass().getResource("arrow.png"));
    protected String text;
    protected int textPosition;
    protected int inset = 5;
    protected Point textLocation;
    private Point iconLocation;
    private Point arrowLocation;
    private Dimension size;

    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;
    public static final int TOP = 4;

    protected Rectangle imageBounds;
    private String name = "";

    private final Font font = new Font(Font.DIALOG, Font.PLAIN, 11);
    private boolean mouseOver = false;
    protected boolean enabled = true;
    protected boolean highlightOnClick = true;
    protected final Color borderHover = new Color(98, 162, 228);
    protected final Color backgroundHover = new Color(201, 224, 247);
    protected final Color backgroundClick = new Color(177, 210, 243);
    protected boolean isDivided = false;
    protected Point mouseLocation = new Point();
    protected int iconWidth;
    protected int iconHeight;
    protected final JPopupMenu popupMenu = new JPopupMenu();
    protected int popupX, popupY;
    private boolean dropDown = true;

    private String command = "";

    /**
     *
     * @param imageIcon
     * @param text
     * @param textPosition DropButton.LEFT, DropButton.RIGHT, DropButton.TOP,
     * DropButton.BOTTOM
     * @param dropDown
     */
    public MButton(ImageIcon imageIcon, String text, int textPosition, boolean dropDown) {
        this();
        this.setIcon(imageIcon);
        this.icon = imageIcon;
        this.text = text;
        this.dropDown = dropDown;
        this.textPosition = textPosition;
        this.setIconDimensions();
        this.initializeButtonProperties();
    }

    /**
     *
     * @param imageIcon
     */
    public MButton(ImageIcon imageIcon) {
        this(imageIcon, "", MButton.RIGHT, false);
        this.setIcon(imageIcon);
    }

    public MButton() {
        this.dropDown = false;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     *
     * @param imageIcon
     * @param text
     * @param dropDown
     */
    public MButton(ImageIcon imageIcon, String text, boolean dropDown) {
        this(imageIcon, text, MButton.BOTTOM, dropDown);
    }

    public boolean isDropDown() {
        return dropDown;
    }

    public void setDropDown(boolean dropDown) {
        this.dropDown = dropDown;
    }

    protected void initializeButtonProperties() {
        this.size = createDimensions();
        this.getImageAndTextLocations();
        this.setPreferredSize(size);
        this.setMinimumSize(size);
        this.setFont(font);
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        repaint();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public int getWidth() {
        return size.width;
    }

    @Override
    public int getHeight() {
        return size.height;
    }

    public void setActionCommand(String s) {
        this.command = s;
    }

    public String getActionCommand() {
        return command;
    }

    public void hightlightOnClick(boolean highlight) {
        this.highlightOnClick = highlight;
    }

    protected void getImageAndTextLocations() {
        int textX;
        int textY;
        int iconX;
        int iconY;
        int arrowX;
        int arrowY;
        int textWidth = getFontMetrics(font).stringWidth(text);
        int textHeight = getFont().getSize();
        int arrowWidth = arrow.getIconWidth();
        int arrowHeight = arrow.getIconHeight();
        if (textPosition == MButton.TOP || textPosition == MButton.BOTTOM) {
            textX = (getWidth() - textWidth) / 2;
            iconX = (getWidth() - iconWidth) / 2;
            arrowX = (getWidth() - arrowWidth) / 2;
            if (textPosition == MButton.TOP) {
                textY = inset;
                iconY = textY + textHeight + inset;
                arrowY = iconY + inset;
            } else {
                iconY = inset;
                textY = iconY + iconHeight + inset;
                arrowY = textY + textHeight + inset;
            }
        } else {
            iconY = (getHeight() - iconHeight) / 2;
            textY = (getHeight() - textHeight) / 2;
            arrowY = (getHeight() - arrowHeight) / 2;
            if (textPosition == MButton.RIGHT) {
                iconX = inset;
                textX = iconX + iconWidth + inset;
                arrowX = textX + textWidth + inset;
            } else {
                arrowX = inset;
                textX = inset + arrowX;
                iconX = textX + textWidth + inset;
            }
        }
        popupX = getX();
        popupY = getY() + getHeight();
        textLocation = new Point(textX, textY + textHeight);
        iconLocation = new Point(iconX, iconY);
        arrowLocation = new Point(arrowX, arrowY);
        imageBounds = new Rectangle(iconX, iconY, iconWidth + iconX, iconHeight + iconY + inset);
    }

    protected final void setIconDimensions() {
        iconWidth = icon.getIconWidth();
        iconHeight = icon.getIconHeight();
    }

    public void setIconWidth(int width) {
        iconWidth = width;
        this.initializeButtonProperties();
        repaint();
    }

    public void setIconHeight(int height) {
        iconHeight = height;
        this.initializeButtonProperties();
        repaint();
    }

    public void setIconDimensions(int width, int height) {
        setIconWidth(width);
        setIconHeight(height);
    }

    private Dimension createDimensions() {
        int width = inset + inset;
        int height = inset + inset;
        int textWidth = getFontMetrics(getFont()).stringWidth(text);
        int textHeight = getFont().getSize();
        if (textPosition == MButton.TOP || textPosition == MButton.BOTTOM) {
            width += Integer.max(textWidth, iconWidth);
            height += inset + iconHeight + textHeight;
            if (isDropDown()) {
                height += inset * 2;
            }
        } else {
            width += inset + textWidth + iconWidth + inset;
            if (text.isEmpty()) {
                width = inset * 2 + iconWidth;
            }
            if (isDropDown()) {
                width += inset * 2;
            }
            height += Integer.max(textHeight, iconHeight);
        }
        return new Dimension(width, height);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setInset(int inset) {
        this.inset = inset;
        this.initializeButtonProperties();
        this.repaint();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (mouseOver) {
            mouseHovered(g);
        } else {
            paintBackground(g);
        }
        paintButtonComponents(g);
        if (!enabled) {
            notEnabled(g);
        }
    }

    protected void notEnabled(Graphics2D g) {
        Color background = (Color) new Settings().getProperty(Settings.TABBED_PANE_CONTENT_COLOR);
        g.setColor(new Color(background.getRed(), background.getGreen(), background.getBlue(), 180));
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    protected void paintButtonComponents(Graphics2D g) {
        g.setColor(Color.black);
        if (icon != null) {
            g.drawImage(ImageManip.removeGray(icon), iconLocation.x, iconLocation.y, this.iconWidth, this.iconHeight, this);
            g.drawString(text, textLocation.x, textLocation.y);
            if (isDropDown()) {
                g.drawImage(arrow.getImage(), arrowLocation.x, arrowLocation.y, arrow.getIconWidth(), arrow.getIconHeight(), this);
            }
        }
    }

    protected void paintBackground(Graphics2D g) {
        if (isActive()) {
            g.setColor(backgroundClick);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(borderHover);
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        } else {
            g.setColor(new Color(0, 0, 0, 0));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    protected void mouseHovered(Graphics2D g) {
        g.setColor(backgroundHover);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(borderHover);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
    }

    public void addMenuAction(JMenuItem item) {
        popupMenu.add(item);
    }

    public void addMenuComponent(Component component) {
        popupMenu.add(component);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (enabled) {
            if (isDropDown()) {
                popupMenu.show(this, popupX, popupY);
            } else {
                this.fireMouseClicked();
            }
            repaint();
        }
    }

    protected void fireMouseClicked() {
        this.setActive(true);
        ActionEvent event = new ActionEvent(this, 0, command, 0);
        for (ActionListener l : listeners) {
            l.actionPerformed(event);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (enabled) {
            mouseOver = true;
            mouseLocation = e.getPoint();
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (enabled) {
            mouseOver = false;
            mouseLocation = e.getPoint();
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (enabled) {
            mouseLocation = e.getPoint();
            repaint();
        }
    }

    public JPopupMenu getPopup() {
        return this.popupMenu;
    }
}
