package shapes;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

/**
 * @author Moses Muigai Gitau
 */
public class MPolygon extends MPointsShape {

    public MPolygon(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = this.translateMousePoint(e.getPoint());
        if (!editingComplete) {
            points.add(p);
            setBoundingPoints(p);
            drawShape();
        } else {
            super.mouseClicked(e);
        }
    }

    private void setBoundingPoints(Point p) {
        if (p.x > end.x) {
            end.x = p.x;
        }
        if (p.x < start.x) {
            start.x = p.x;
        }
        if (p.y > end.y) {
            end.y = p.y;
        }
        if (p.y < start.y) {
            start.y = p.y;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!editingComplete) {
            Point p = this.translateMousePoint(e.getPoint());
            setBoundingPoints(p);
            drawShape();
        } else {
            super.mousePressed(e);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (!editingComplete) {
            if (points.size() > 1) {
                points.set(points.size() - 1, this.translateMousePoint(e.getPoint()));
                drawShape();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.removeMouseListener(canvas);
        canvas.removeMouseMotionListener(canvas);
        Point releasePoint = this.translateMousePoint(e.getPoint());
        Rectangle bounds = new Rectangle(releasePoint.x - 10, releasePoint.y - 10, 20, 20);
        if (bounds.contains(points.get(0))) {
            editingComplete = true;
            points.add(releasePoint);
            drawShape();
            createRatios();
        }
    }

    @Override
    public void shapeFill(Graphics2D g) {
        if (points.isEmpty()) {
            points.add(start);
            points.add(end);
        }
        int xPoints[] = new int[points.size()];
        int yPoints[] = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xPoints[i] = points.get(i).x;
            yPoints[i] = points.get(i).y;
        }
        g.fillPolygon(xPoints, yPoints, points.size());
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        if (points.isEmpty()) {
            points.add(start);
            points.add(end);
        }
        int xPoints[] = new int[points.size()];
        int yPoints[] = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xPoints[i] = points.get(i).x;
            yPoints[i] = points.get(i).y;
        }
        g.drawPolyline(xPoints, yPoints, points.size());
    }
}
