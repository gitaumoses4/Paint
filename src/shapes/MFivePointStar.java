package shapes;

import java.awt.Graphics2D;

/**
 * @author Moses Muigai Gitau
 */
public class MFivePointStar extends MPointsShape {

    public MFivePointStar(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 6 / 10,
            end.x,
            start.x + (end.x - start.x) * 7 / 10,
            start.x + (end.x - start.x) * 8 / 10,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 2 / 10,
            start.x + (end.x - start.x) * 3 / 10,
            start.x,
            start.x + (end.x - start.x) * 4 / 10};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 4 / 10,
            start.y + (end.y - start.y) * 9 / 20,
            start.y + (end.y - start.y) * 6 / 10,
            end.y,
            start.y + (end.y - start.y) * 7 / 10,
            end.y,
            start.y + (end.y - start.y) * 6 / 10,
            start.y + (end.y - start.y) * 9 / 20,
            start.y + (end.y - start.y) * 4 / 10};
        g.fillPolygon(xPoints, yPoints, 10);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 6 / 10,
            end.x,
            start.x + (end.x - start.x) * 7 / 10,
            start.x + (end.x - start.x) * 8 / 10,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) * 2 / 10,
            start.x + (end.x - start.x) * 3 / 10,
            start.x,
            start.x + (end.x - start.x) * 4 / 10};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 4 / 10,
            start.y + (end.y - start.y) * 9 / 20,
            start.y + (end.y - start.y) * 6 / 10,
            end.y,
            start.y + (end.y - start.y) * 7 / 10,
            end.y,
            start.y + (end.y - start.y) * 6 / 10,
            start.y + (end.y - start.y) * 9 / 20,
            start.y + (end.y - start.y) * 4 / 10};
        g.drawPolygon(xPoints, yPoints, 10);
    }
}
