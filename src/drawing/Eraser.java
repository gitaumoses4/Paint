package drawing;

import shapes.MLine;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

/**
 * @author Moses Muigai Gitau
 */
public class Eraser {

    public Eraser() {
    }

    public static void erase(Graphics2D graphics, int size, Color color, ArrayList<Point> points, double scale) {
        graphics.setColor(color);
        for (int i = 0; i < points.size() - 1; i++) {
            Point first = points.get(i);
            Point second = points.get(i + 1);
            ArrayList<Point> pointsIn = MLine.pointsInLine(first.x, first.y, second.x, second.y);
            for (int j = 0; j < pointsIn.size(); j++) {
                Point point = pointsIn.get(j);
                graphics.fillRect(point.x, point.y, (int) (size / scale), (int) (size / scale));
            }
        }
    }
}
