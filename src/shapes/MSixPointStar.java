package shapes;

import java.awt.Graphics2D;

/**
 * @author Moses Muigai Gitau
 */
public class MSixPointStar extends MPointsShape {

    public MSixPointStar(MShapeProperties properties) {
        super(properties);
    }
    @Override
    public void shapeFill(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 2 / 3,
            end.x,
            start.x + (end.x - start.x) * 5 / 6,
            end.x,
            start.x + (end.x - start.x) * 2 / 3,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 1 / 3,
            start.x,
            start.x + (end.x - start.x) * 1 / 6,
            start.x
        };
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) / 4,
            start.y + (end.y - start.y) / 4,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 3 / 4,
            end.y,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 1 / 4,
            start.y + (end.y - start.y) * 1 / 4,};
        g.fillPolygon(xPoints, yPoints, 12);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 2 / 3,
            end.x,
            start.x + (end.x - start.x) * 5 / 6,
            end.x,
            start.x + (end.x - start.x) * 2 / 3,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 1 / 3,
            start.x,
            start.x + (end.x - start.x) * 1 / 6,
            start.x,
            start.x + (end.x - start.x) * 1 / 3,
        };
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) / 4,
            start.y + (end.y - start.y) / 4,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 3 / 4,
            end.y,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 1 / 4,
            start.y + (end.y - start.y) * 1 / 4,};
        g.drawPolygon(xPoints, yPoints, 12);
    }
}
