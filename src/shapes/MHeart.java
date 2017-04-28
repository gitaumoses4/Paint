package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.QuadCurve2D;

/**
 * @author Moses Muigai Gitau
 */
public class MHeart extends MPointsShape {

    public MHeart(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        Rectangle rect = getBounds();
        Point one = new Point(start.x, start.y + (rect.height) / 5);
        Point two = new Point(start.x + (rect.width) / 2, start.y + (rect.height) / 5);
        Point three = new Point(two.x + rect.width / 2, two.y);
        Point four = new Point(start.x + (rect.width) / 2, end.y);
        int xPoints[] = {one.x - 1, three.x + 1, four.x};
        int yPoints[] = {one.y - 1 + rect.height / 10, three.y - 1 + rect.height / 10, four.y + 1};
        Polygon inside = new Polygon(xPoints, yPoints, 3);

        g.fillArc(one.x, one.y, two.x - one.x, rect.height / 5, 0, 180);
        g.fillArc(two.x, two.y, rect.width / 2, rect.height / 5, 0, 180);
        QuadCurve2D.Double left = new QuadCurve2D.Double(one.x, one.y + rect.height / 10, one.x - (four.x - one.x) / 8, one.y + (four.y - one.y) / 4, four.x, four.y);
        QuadCurve2D.Double right = new QuadCurve2D.Double(two.x + rect.width / 2, two.y + rect.height / 10, two.x + rect.width / 2 + (four.x - two.x + rect.width / 2) / 8, two.y + (four.y - two.y) / 4, four.x, four.y);

        g.fill(left);
        g.fill(right);
        g.fill(inside);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle rect = getBounds();
        Point one = new Point(start.x, start.y + (rect.height) / 5);
        Point two = new Point(start.x + (rect.width) / 2, start.y + (rect.height) / 5);
        Point four = new Point(start.x + (rect.width) / 2, end.y);

        g.drawArc(one.x, one.y, two.x - one.x, rect.height / 5, 0, 180);
        g.drawArc(two.x, two.y, rect.width / 2, rect.height / 5, 0, 180);
        QuadCurve2D.Double left = new QuadCurve2D.Double(one.x, one.y + rect.height / 10, one.x - (four.x - one.x) / 8, one.y + (four.y - one.y) / 4, four.x, four.y);
        QuadCurve2D.Double right = new QuadCurve2D.Double(two.x + rect.width / 2, two.y + rect.height / 10, two.x + rect.width / 2 + (four.x - two.x + rect.width / 2) / 8, two.y + (four.y - two.y) / 4, four.x, four.y);

        g.draw(left);
        g.draw(right);
    }
}
