package shapes;

import java.awt.Graphics2D;

/**
 * @author Moses Muigai Gitau
 */
public class MDiamond extends MPointsShape {

    public MDiamond(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        int xPoints[] = {start.x, start.x + (end.x - start.x) / 2, end.x, start.x + (end.x - start.x) / 2};
        int yPoints[] = {start.y + (end.y - start.y) / 2, start.y, start.y + (end.y - start.y) / 2, end.y};
        g.fillPolygon(xPoints, yPoints, 4);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        int xPoints[] = {start.x, start.x + (end.x - start.x) / 2, end.x, start.x + (end.x - start.x) / 2};
        int yPoints[] = {start.y + (end.y - start.y) / 2, start.y, start.y + (end.y - start.y) / 2, end.y};
        g.drawPolygon(xPoints, yPoints, 4);
    }
}
