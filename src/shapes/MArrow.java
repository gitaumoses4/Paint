package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import paint.Canvas;

/**
 * @author Moses Muigai Gitau
 */
public class MArrow extends MPointsShape {

    int direction = -1;
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;

    public MArrow(MShapeProperties properties, int direction) {
        super(properties);
        this.direction = direction;
    }

    @Override
    public void shapeFill(Graphics2D g) {
        Point start = new Point(this.start);
        Point end = new Point(this.end);
        switch (direction) {
            case UP:
                end.y = this.end.x;
                end.x = this.start.y;
                start.x = this.end.y;
                start.y = this.start.x;
                break;
            case DOWN:
                end.y = this.start.x;
                end.x = this.end.y;
                start.y = this.end.x;
                start.x = this.start.y;
                break;
            case LEFT:
                start.x = this.end.x;
                start.y = this.end.y;
                end.y = this.start.y;
                end.x = this.start.x;
                break;
            default:
                break;
        }
        int xPoints[] = {
            start.x,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) / 2,
            end.x,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) / 2,
            start.x
        };
        int yPoints[] = {
            start.y + (end.y - start.y) / 4,
            start.y + (end.y - start.y) / 4,
            start.y,
            start.y + (end.y - start.y) / 2,
            end.y,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 3 / 4,};
        if (direction == UP || direction == DOWN) {
            g.fillPolygon(yPoints, xPoints, 7);
        } else {
            g.fillPolygon(xPoints, yPoints, 7);
        }
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Point start = new Point(this.start);
        Point end = new Point(this.end);
        switch (direction) {
            case UP:
                end.y = this.end.x;
                end.x = this.start.y;
                start.x = this.end.y;
                start.y = this.start.x;
                break;
            case DOWN:
                end.y = this.start.x;
                end.x = this.end.y;
                start.y = this.end.x;
                start.x = this.start.y;
                break;
            case LEFT:
                start.x = this.end.x;
                start.y = this.end.y;
                end.y = this.start.y;
                end.x = this.start.x;
                break;
            default:
                break;
        }
        int xPoints[] = {
            start.x,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) / 2,
            end.x,
            start.x + (end.x - start.x) / 2,
            start.x + (end.x - start.x) / 2,
            start.x
        };
        int yPoints[] = {
            start.y + (end.y - start.y) / 4,
            start.y + (end.y - start.y) / 4,
            start.y,
            start.y + (end.y - start.y) / 2,
            end.y,
            start.y + (end.y - start.y) * 3 / 4,
            start.y + (end.y - start.y) * 3 / 4,};
        if (direction == UP || direction == DOWN) {
            g.drawPolygon(yPoints, xPoints, 7);
        } else {
            g.drawPolygon(xPoints, yPoints, 7);
        }
    }
}
