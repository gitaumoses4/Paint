package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author Moses Muigai Gitau
 */
public class MLine extends MShape {

    private static final int DRAG_START = 1;
    private static final int DRAG_END = 2;

    public MLine(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void drawResizeHelper() {
        getGraphics().setStroke(new BasicStroke(1));
        getGraphics().setColor(Color.white);
        getGraphics().fillRect(start.x, start.y, 6, 6);
        getGraphics().fillRect(end.x, end.y, 6, 6);
        getGraphics().setColor(Color.black);
        getGraphics().drawRect(start.x, start.y, 6, 6);
        getGraphics().drawRect(end.x, end.y, 6, 6);
    }

    public static void dottedLine(int x1, int y1, int x2, int y2, int space, Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        Stroke dashed = new BasicStroke(space, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{space}, 0);
        g.setStroke(dashed);
        g.drawLine(x1, y1, x2, y2);
    }

    public static ArrayList<Point> pointsInLine(int x, int y, int x2, int y2) {
        ArrayList<Point> points = new ArrayList();
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) {
            dx1 = -1;
        } else if (w > 0) {
            dx1 = 1;
        }
        if (h < 0) {
            dy1 = -1;
        } else if (h > 0) {
            dy1 = 1;
        }
        if (w < 0) {
            dx2 = -1;
        } else if (w > 0) {
            dx2 = 1;
        }
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -1;
            } else if (h > 0) {
                dy2 = 1;
            }
            dx2 = 0;
        }
        int numerator = longest >> 1;
        for (int i = 0; i <= longest; i++) {
            points.add(new Point(x, y));
            numerator += shortest;
            if (!(numerator < longest)) {
                numerator -= longest;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
        }
        return points;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = (int) (e.getPoint().x / scale);
        int y = (int) (e.getPoint().y / scale);
        Rectangle startRect = new Rectangle(start.x, start.y, 10, 10);
        Rectangle endRect = new Rectangle(end.x, end.y, 10, 10);
        Rectangle inRect = new Rectangle(x, y, 10, 10);

        if (startRect.contains(new Point(x, y)) || endRect.contains(new Point(x, y))) {
            canvas.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            repaint();
        } else {
            boolean move = false;
            for (Point p : MLine.pointsInLine(start.x, start.y, end.x, end.y)) {
                if (inRect.contains(p)) {
                    move = true;
                    canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                }
            }
            if (!move) {
                canvas.setCursor(Cursor.getDefaultCursor());
            }
        }
        canvas.mouseMoved(x, y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (int) (e.getPoint().x / scale);
        int y = (int) (e.getPoint().y / scale);
        Rectangle inRect = new Rectangle(x, y, 10, 10);
        boolean inMe = false;
        for (Point p : MLine.pointsInLine(start.x, start.y, end.x, end.y)) {
            if (inRect.contains(p)) {
                inMe = true;
            }
        }
        if (!inMe) {
            editingComplete = false;
            finishedEditing();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = (int) (e.getPoint().x / scale);
        int y = (int) (e.getPoint().y / scale);
        dragX = x;
        dragY = y;
        Rectangle startRect = new Rectangle(start.x, start.y, 10, 10);
        Rectangle endRect = new Rectangle(end.x, end.y, 10, 10);
        Rectangle inRect = new Rectangle(x, y, 10, 10);

        if (startRect.contains(e.getPoint())) {
            this.drag = DRAG_START;
        }
        if (endRect.contains(e.getPoint())) {
            this.drag = DRAG_END;
        }
        for (Point p : MLine.pointsInLine(start.x, start.y, end.x, end.y)) {
            if (inRect.contains(p)) {
                this.drag = DRAG_SHAPE;
            }
        }

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public void resizing(int diffX, int diffY, Point p) {
        if (start != null) {
            switch (drag) {
                case DRAG_START:
                    start = p;
                    break;
                case DRAG_END:
                    end = p;
                    break;
                case DRAG_SHAPE:
                    end.x += diffX;
                    end.y += diffY;
                    start.x += diffX;
                    start.y += diffY;
                    break;
                default:
                    break;
            }
            drawShape();
        }
    }

    @Override
    public void shapeFill(Graphics2D g) {
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        g.drawLine(start.x, start.y, end.x, end.y);
    }
}
