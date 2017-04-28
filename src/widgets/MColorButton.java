package widgets;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Moses Muigai Gitau
 */
public class MColorButton extends MButton {

    private Color color;

    public MColorButton(Color color) {
        super();
        this.color = color;
        this.text = "";
        this.setIconDimensions(10, 10);
        this.initializeButtonProperties();
    }

    public MColorButton(Color color, String text, int textPosition) {
        this(color);
        this.text = text;
        this.textPosition = textPosition;
        this.initializeButtonProperties();
    }

    @Override
    public void paintButtonComponents(Graphics2D g) {
        g.setColor(color);
        int x = (getWidth() - iconWidth) / 2;
        g.fillRect(x + 2, inset + 2, iconWidth - 4, iconHeight - 4);
        g.setColor(new Color(120, 120, 120));
        g.drawRect(x, inset, iconWidth - 1, iconHeight - 1);
        g.setColor(Color.black);
        g.drawString(text, textLocation.x, textLocation.y);
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    public Color getColor() {
        return color;
    }
}
