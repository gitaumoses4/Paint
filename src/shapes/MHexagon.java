package shapes;

import java.awt.Graphics2D;
/**
 * @author Moses Muigai Gitau
 */
public class MHexagon extends MPointsShape {

    public MHexagon(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            end.x,
            end.x,
            start.x + (end.x - start.x) / 2,
            start.x,
            start.x};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 1 / 4,
            start.y + (end.y - start.y) * 3 / 4,
            end.y,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 1 / 4};
        g.fillPolygon(xPoints, yPoints, 6);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        int xPoints[] = {
            start.x + (end.x - start.x) / 2,
            end.x,
            end.x,
            start.x + (end.x - start.x) / 2,
            start.x,
            start.x};
        int yPoints[] = {
            start.y,
            start.y + (end.y - start.y) * 1 / 4,
            start.y + (end.y - start.y) * 3 / 4,
            end.y,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 1 / 4};
        g.drawPolygon(xPoints, yPoints, 6);
    }
}
