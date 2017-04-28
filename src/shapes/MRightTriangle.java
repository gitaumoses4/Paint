package shapes;

import java.awt.Graphics2D;

/**
 * @author Moses Muigai Gitau
 */
public class MRightTriangle extends MTriangle {

    public MRightTriangle(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        int xPoints[] = {start.x, start.x, end.x};
        int yPoints[] = {end.y, start.y, end.y};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        int xPoints[] = {start.x, start.x, end.x};
        int yPoints[] = {end.y, start.y, end.y};
        g.drawPolygon(xPoints, yPoints, 3);
    }
}
