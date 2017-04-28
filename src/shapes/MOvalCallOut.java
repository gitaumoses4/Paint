package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * @author Moses Muigai Gitau
 */
public class MOvalCallOut extends MPointsShape {

    public MOvalCallOut(MShapeProperties properties) {
        super(properties);
    }

    @Override
    public void shapeFill(Graphics2D g) {
        Rectangle rect = getBounds();
        int height = rect.height * 4 / 5;
        g.fillOval(rect.x, rect.y, rect.width, height);
        int xPoints[] = {rect.x + (rect.width * 1 / 3), rect.x + (rect.width / 2), rect.x + (rect.width * 2 / 3)};
        int yPoints[] = {rect.y + height - (rect.height / 50), rect.y + rect.height, rect.y + height - (rect.height / 50)};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle rect = getBounds();
        int height = rect.height * 4 / 5;
        g.drawOval(rect.x, rect.y, rect.width, height);
        int xPoints[] = {rect.x + (rect.width * 1 / 3), rect.x + (rect.width / 2), rect.x + (rect.width * 2 / 3)};
        int yPoints[] = {rect.y + height - (rect.height / 50), rect.y + rect.height, rect.y + height - (rect.height / 50)};
        Polygon p = new Polygon(xPoints, yPoints, 3);
        g.drawPolyline(xPoints, yPoints, 3);
        for (int i = 0; i < rect.width; i++) {
            for (int j = 0; j < rect.height; j++) {
                if (p.contains(new Point(rect.x + i, rect.y + j))) {
                    Color color;
                    if (fill) {
                        color = color2;
                    } else {
                        color = new Color(0, 0, 0, 0);
                    }
                    canvas.getGlass().setRGB(rect.x + i, rect.y + j, color.getRGB());
                }
            }
        }
        g.drawPolyline(xPoints, yPoints, 3);
    }
}
