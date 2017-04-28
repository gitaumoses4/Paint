package shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.QuadCurve2D;
/**
 * @author Moses Muigai Gitau
 */
public class MCurve extends MPointsShape {

    private boolean makeCurve = false;
    private boolean resize = true;

    public MCurve(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void drawShape() {
        drawCurve();
    }

    @Override
    public void drawShape(Point start, Point end) {
        drawCurve(start, end, start.x, start.y);
    }

    public void drawCurve() {
        canvas.clearGlass();
        drawCurve(this.start, this.end, getControlX(), getControlY());
    }

    public int getControlX() {
        return points.get(2).x;
    }

    public int getControlY() {
        return points.get(2).y;
    }

    public void setControlX(int x) {
        this.points.get(2).x = x;
    }

    public void setControlY(int y) {
        this.points.get(2).y = y;
    }

    public void drawCurve(Point start, Point end, int controlX, int controlY) {
        this.start = new Point(start);
        this.end = new Point(end);
        if (points.isEmpty()) {
            points.add(start);
            points.add(end);
            points.add(new Point());
        } else {
            points.set(0, start);
            points.set(1, end);
        }
        Graphics2D g = getGraphics();
        QuadCurve2D.Double curve = new QuadCurve2D.Double(start.x, start.y, controlX, controlY, end.x, end.y);
        g.setColor(color1);
        g.setStroke(new BasicStroke(size));
        g.draw(curve);
        g.setStroke(new BasicStroke(1));
        if (editingComplete && resize) {
            drawResizeHelper();
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        if (makeCurve && !editingComplete) {
            Point p = this.translateMousePoint(e.getPoint());
            int x = p.x;
            int y = p.y;
            setControlX(x);
            setControlY(y);
            canvas.clearGlass();
            drawCurve();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (editingComplete) {
            super.mouseMoved(e);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = this.translateMousePoint(e.getPoint());
        int x = p.x;
        int y = p.y;
        dragX = x;
        dragY = y;
        super.mousePressed(e);
        if (!makeCurve && !editingComplete) {
            setControlX(x);
            setControlY(y);
            canvas.clearGlass();
            drawCurve();
            makeCurve = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.removeMouseMotionListener(canvas);
        canvas.removeMouseListener(canvas);
        if (makeCurve && !editingComplete) {
            editingComplete = true;
            this.createRatios();
            drawCurve();
        }
    }

    private Point maxPoint() {
        int maxX = Math.max(Math.max(getControlX(), points.get(0).x), points.get(1).x) + 5;
        int maxY = Math.max(Math.max(getControlY(), points.get(0).y), points.get(1).y) + 5;
        return new Point(maxX, maxY);
    }

    private Point minPoint() {
        int minX = Math.min(Math.min(getControlX(), points.get(0).x), points.get(1).x) - 5;
        int minY = Math.min(Math.min(getControlY(), points.get(0).y), points.get(1).y) - 5;
        return new Point(minX, minY);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(minPoint().x, minPoint().y, maxPoint().x - minPoint().x, maxPoint().y - minPoint().y);
    }

    @Override
    public void shapeFill(Graphics2D g) {
    }

    @Override
    public void shapeOutline(Graphics2D g) {
    }
}
