package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.QuadCurve2D;
import java.util.ArrayList;

/**
 * @author Moses Muigai Gitau
 */
public class MCloudCallOut extends MPointsShape {

    public MCloudCallOut(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        ArrayList<Point> points = createPoints();
        Rectangle rect = getBounds();
        for (int i = 0; i < points.size(); i++) {
            Point one = points.get(i);
            g.fillArc(one.x, one.y, rect.width / 5, rect.height / 5, 270 - (i * 20), 360);
            if (i == 6) {
                for (int j = 0; j < 3; j++) {
                    g.fillOval(one.x - ((j * 5)), (one.y + rect.height / 5) + j * (10 + (j * 2)), rect.width / (10 + (j * 2)), rect.height / (10 + (j * 2)));
                }
            }
        }
        g.fillOval(start.x + rect.width / 10, start.y + rect.height / 10, rect.width * 4 / 5, rect.height * 4 / 5);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        ArrayList<Point> points = createPoints();
        Rectangle rect = getBounds();
        for (int i = 0; i < points.size(); i++) {
            Point one = points.get(i);
            g.drawArc(one.x, one.y, rect.width / 5, rect.height / 5, 270 - (i * 20), 140);
            if (i == 6) {
                for (int j = 0; j < 3; j++) {
                    g.drawOval(one.x - ((j * 5)), (one.y + rect.height / 5) + j * (10 + (j * 2)), rect.width / (10 + (j * 2)), rect.height / (10 + (j * 2)));
                }
            }
        }
    }

    private Point createCenter() {
        Rectangle rect = getBounds();
        int width = rect.width * 4 / 5;
        int height = rect.height * 4 / 5;
        return new Point(rect.x + width / 2, rect.y + height / 2);
    }

    private ArrayList createPoints() {
        ArrayList<Point> points = new ArrayList();
        for (int i = 0; i < 360; i += 20) {
            Point p = getPoint(i);
            points.add(p);
            end.x = p.x > end.x ? p.x : end.x;
            end.y = p.y > end.y ? p.y : end.y;
            start.x = p.x < start.x ? p.x : start.x;
            start.y = p.y < start.y ? p.y : start.y;
        }
        return points;
    }

    private Point getPoint(double angle) {
        Point center = createCenter();
        Rectangle rect = getBounds();
        int width = rect.width * 4 / 5;
        int height = rect.height * 4 / 5;
        int ePx = center.x + (int) (width / 2 * Math.cos(Math.toRadians(angle)));
        int ePy = center.y + (int) (height / 2 * Math.sin(Math.toRadians(angle)));
        return new Point(ePx, ePy);
    }
}
