package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author Moses Muigai Gitau
 */
public class MCircle extends MShape {

    public MCircle(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        Rectangle rect = getBounds();
        g.fillOval(rect.x, rect.y, rect.width, rect.height);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle rect = getBounds();
        g.drawOval(rect.x, rect.y, rect.width, rect.height);
    }
}
