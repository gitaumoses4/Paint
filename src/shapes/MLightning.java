package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author Moses Muigai Gitau
 */
public class MLightning extends MPointsShape {

    public MLightning(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        Rectangle rect = getBounds();
        int xPoints[] = {
            rect.x + rect.width * 4 / 10,
            rect.x + rect.width * 6 / 10,
            rect.x + rect.width * 5 / 10,
            rect.x + rect.width * 8 / 10,
            rect.x + rect.width,
            rect.x + rect.width * 4 / 10,
            rect.x + rect.width * 5 / 10,
            rect.x + rect.width * 2 / 10,
            rect.x + rect.width * 3 / 10,
            rect.x
        };
        int yPoints[] = {
            rect.y,
            rect.y + rect.height * 5 / 20,
            rect.y + rect.height * 6 / 20,
            rect.y + rect.height * 10 / 20,
            rect.y + rect.height * 11 / 20,
            rect.y + rect.height,
            rect.y + rect.height * 13 / 20,
            rect.y + rect.height * 12 / 20,
            rect.y + rect.height * 8 / 20,
            rect.y + rect.height * 7 / 20,
            rect.y + rect.height * 3 / 20
        };
        g.fillPolygon(xPoints, yPoints, 11);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle rect = getBounds();
        int xPoints[] = {
            rect.x + rect.width * 4 / 10,
            rect.x + rect.width * 6 / 10,
            rect.x + rect.width * 5 / 10,
            rect.x + rect.width * 8 / 10,
            rect.x + rect.width * 7 / 10,
            rect.x + rect.width,
            rect.x + rect.width * 4 / 10,
            rect.x + rect.width * 5 / 10,
            rect.x + rect.width * 2 / 10,
            rect.x + rect.width * 3 / 10,
            rect.x
        };
        int yPoints[] = {
            rect.y,
            rect.y + rect.height * 5 / 20,
            rect.y + rect.height * 6 / 20,
            rect.y + rect.height * 10 / 20,
            rect.y + rect.height * 11 / 20,
            rect.y + rect.height,
            rect.y + rect.height * 13 / 20,
            rect.y + rect.height * 12 / 20,
            rect.y + rect.height * 8 / 20,
            rect.y + rect.height * 7 / 20,
            rect.y + rect.height * 3 / 20
        };
        g.drawPolygon(xPoints, yPoints, 11);
    }
}
