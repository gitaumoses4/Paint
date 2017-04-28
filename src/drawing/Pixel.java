package drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;

/**
 * @author Moses Muigai Gitau
 */
public class Pixel extends Point implements Serializable {

    private Color color = new Color(255, 255, 255);
    private BasicStroke stroke = new BasicStroke(1f);
    private boolean marked = false;

    public Pixel() {
    }

    public void setColor(Color c) {
        color = c;
    }

    public void setBasicStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }

    public Color getColor() {
        return color;
    }

    public BasicStroke getBasicStroke() {
        return stroke;
    }

    public void setMarked(boolean b) {
        marked = b;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setWidth(int i) {
        this.stroke = new BasicStroke((float)i);
    }

    public float getWidth() {
        return stroke.getLineWidth();
    }
}
