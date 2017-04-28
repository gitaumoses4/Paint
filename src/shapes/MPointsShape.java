package shapes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 * @author Moses Muigai Gitau
 */
public abstract class MPointsShape extends MShape {

    protected ArrayList<Point> points = new ArrayList();
    protected ArrayList<Point.Double> ratios = new ArrayList();

    public MPointsShape(MShapeProperties properties) {
        super(properties);
    }

    public MPointsShape() {
    }

    protected void createRatios() {
        for (Point p : points) {
            Double ratioX = (double) (p.x - start.x) / getBounds().width;
            Double ratioY = (double) (p.y - start.y) / getBounds().height;
            ratios.add(new Point.Double(ratioX, ratioY));
        }
    }

    @Override
    public void resize(int diffX, int diffY) {
        super.resize(diffX, diffY);
        if (editingComplete) {
            Rectangle bounds = getBounds();
            switch (drag) {
                case DRAG_SHAPE:
                    for (Point p : points) {
                        p.x += diffX;
                        p.y += diffY;
                    }
                    break;
                case DRAG_RIGHT:
                case DRAG_LEFT:
                    for (int i = 0; i < points.size(); i++) {
                        Point p = points.get(i);
                        p.x = start.x + (int) (bounds.width * ratios.get(i).x);
                    }
                    break;
                case DRAG_TOP:
                case DRAG_BOTTOM:
                    for (int i = 0; i < points.size(); i++) {
                        Point p = points.get(i);
                        p.y = start.y + (int) (bounds.height * ratios.get(i).y);
                    }
                    break;
                case DRAG_TOP_RIGHT:
                case DRAG_TOP_LEFT:
                case DRAG_BOTTOM_RIGHT:
                case DRAG_BOTTOM_LEFT:
                    for (int i = 0; i < points.size(); i++) {
                        Point p = points.get(i);
                        p.y = start.y + (int) (bounds.height * ratios.get(i).y);
                        p.x = start.x + (int) (bounds.width * ratios.get(i).x);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
