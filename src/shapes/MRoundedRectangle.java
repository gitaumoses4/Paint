package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author Moses Muigai Gitau
 */
public class MRoundedRectangle extends MShape {

    public MRoundedRectangle(MShapeProperties properties) {
        super(properties);
    }
    @Override
    public void shapeFill(Graphics2D g) {
        Rectangle rect = getBounds();
        g.fillRoundRect(rect.x, rect.y, rect.width, rect.height, rect.width / 4, rect.height / 4);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle rect = getBounds();
        g.drawRoundRect(rect.x, rect.y, rect.width, rect.height, rect.width / 4, rect.height / 4);
    }
}
