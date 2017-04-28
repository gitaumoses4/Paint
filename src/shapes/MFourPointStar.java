package shapes;

import java.awt.Graphics2D;

/**
 * @author Moses Muigai Gitau
 */
public class MFourPointStar extends MPointsShape {

    public MFourPointStar(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 5 / 8,
            end.x,
            start.x + (end.x - start.x) * 5 / 8,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 3 / 8,
            start.x,
            start.x + (end.x - start.x) * 3 / 8,};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 3 / 8,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 5 / 8,
            end.y,
            start.y + (end.y - start.y) * 5 / 8,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 3 / 8};
        g.fillPolygon(xPoints, yPoints, 8);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 5 / 8,
            end.x,
            start.x + (end.x - start.x) * 5 / 8,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 3 / 8,
            start.x,
            start.x + (end.x - start.x) * 3 / 8,};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 3 / 8,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 5 / 8,
            end.y,
            start.y + (end.y - start.y) * 5 / 8,
            start.y + (end.y - start.y) / 2,
            start.y + (end.y - start.y) * 3 / 8};
        g.drawPolygon(xPoints, yPoints, 8);
    }
}
