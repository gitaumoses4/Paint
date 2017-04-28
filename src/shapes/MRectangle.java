package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;

/**
 * @author Moses Muigai Gitau
 */
public class MRectangle extends MShape {

    public MRectangle(MShapeProperties properties) {
        super(properties);
    }

    public static void drawDottedRectangle(int x, int y, int width, int height, int space, int size, Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Stroke dashed = new BasicStroke(size, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{space}, 0);
        g.setStroke(dashed);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        Rectangle rect = getBounds();
        g.fillRect(rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle rect = getBounds();
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
    }
}
