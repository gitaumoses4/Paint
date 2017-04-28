package shapes;

import shapes.MArrow;
import shapes.MPointsShape;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import paint.Canvas;

/**
 * @author Moses Muigai Gitau
 */
public class MRoundedRectangleCallOut extends MPointsShape {

    public MRoundedRectangleCallOut(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        Rectangle rect = getBounds();
        int height = rect.height - (rect.height / 6);
        g.fillRoundRect(rect.x, rect.y, rect.width, height, rect.width / 4, height / 4);
        int xPoints[] = {rect.x + (rect.width / 5), rect.x + (rect.width / 4), rect.x + (rect.width / 3)};
        int yPoints[] = new int[]{rect.y + height - 2, rect.y + rect.height, rect.y + height - 2};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle rect = getBounds();
        int height = rect.height - (rect.height / 6);
        g.drawRoundRect(rect.x, rect.y, rect.width, height, rect.width / 4, height / 4);
        int xPoints[] = {rect.x + (rect.width / 5), rect.x + (rect.width / 4), rect.x + (rect.width / 3)};
        int yPoints[] = new int[]{rect.y + height - 2, rect.y + rect.height, rect.y + height - 2};
        g.drawPolyline(xPoints, yPoints, 3);
        Polygon p = new Polygon(xPoints, yPoints, 3);
        for (int i = 0; i < rect.width; i++) {
            for (int j = 0; j < rect.height; j++) {
                Point point = new Point(rect.x + i, rect.y + j);
                if (p.contains(point)) {
                    Color color;
                    if (fill) {
                        color = color2;
                    } else {
                        color = new Color(0, 0, 0, 0);
                    }
                    canvas.getGlass().setRGB(point.x, point.y, color.getRGB());
                }
            }
        }
        g.drawPolyline(xPoints, yPoints, 3);
    }
}
