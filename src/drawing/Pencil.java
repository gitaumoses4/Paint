package drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Moses Muigai Gitau
 */
public class Pencil {

    public Pencil() {
    }

    public static void draw(Graphics2D g, Color lineColor, int size, ArrayList<Point> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            int x1 = points.get(i).x;
            int x2 = points.get(i + 1).x;
            int y1 = points.get(i).y;
            int y2 = points.get(i + 1).y;
            g.setStroke(new BasicStroke((float) size));
            g.setColor(lineColor);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
