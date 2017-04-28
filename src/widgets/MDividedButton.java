package widgets;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

/**
 * @author Moses Muigai Gitau
 */
public class MDividedButton extends MButton {

    protected Rectangle displayIconBounds;
    protected Rectangle dropDownBounds;

    public MDividedButton(ImageIcon imageIcon, String text, boolean dropDown) {
        super(imageIcon, text, dropDown);
        isDivided = true;
        displayIconBounds = new Rectangle(0, 0, getWidth(), imageBounds.height);
        dropDownBounds = new Rectangle(0, imageBounds.height, getWidth(), getHeight() - imageBounds.height);
    }

    public void revalidateDimension() {
        displayIconBounds = new Rectangle(0, 0, getWidth(), imageBounds.height);
        dropDownBounds = new Rectangle(0, imageBounds.height, getWidth(), getHeight() - imageBounds.height);
    }

    @Override
    public void paintBackground(Graphics2D g) {
        super.paintBackground(g);
        if (isActive()) {
            g.setColor(borderHover);
            g.drawLine(0, imageBounds.height, getWidth(), imageBounds.height);
        }
    }

    @Override
    public void setIconDimensions(int width, int height) {
        super.setIconDimensions(width, height);
        revalidateDimension();
        repaint();
    }

    @Override
    public void setIconWidth(int width) {
        super.setIconWidth(width);
        revalidateDimension();
        repaint();
    }

    @Override
    public void setIconHeight(int height) {
        super.setIconHeight(height);
        revalidateDimension();
        repaint();
    }

    @Override
    protected void mouseHovered(Graphics2D g) {
        g.setColor(backgroundHover);
        if (displayIconBounds.contains(mouseLocation)) {
            g.fillRect(0, 0, displayIconBounds.width, displayIconBounds.height);
        } else {
            g.fillRect(0, displayIconBounds.height, dropDownBounds.width, dropDownBounds.height);
        }
        g.setColor(borderHover);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawLine(0, displayIconBounds.height, displayIconBounds.width, displayIconBounds.height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (enabled) {
            if (dropDownBounds.contains(e.getPoint())) {
                popupMenu.show(this, popupX, popupY);
            } else {
                this.fireMouseClicked();
            }
        }
    }
}
