package shapes;

import java.awt.Graphics2D;

/**
 * @author Moses Muigai Gitau
 */
public class MPentagon extends MPointsShape {

    public MPentagon(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            end.x,
            start.x + (end.x - start.x) * 5 / 6,
            start.x + (end.x - start.x) / 6,
            start.x};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 1 / 3,
            end.y,
            end.y,
            start.y + (end.y - start.y) * 1 / 3};
        g.fillPolygon(xPoints, yPoints, 5);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            end.x,
            start.x + (end.x - start.x) * 5 / 6,
            start.x + (end.x - start.x) / 6,
            start.x};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 1 / 3,
            end.y,
            end.y,
            start.y + (end.y - start.y) * 1 / 3};
        g.drawPolygon(xPoints, yPoints, 5);
    }
}
