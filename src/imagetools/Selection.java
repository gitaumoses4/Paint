package imagetools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import shapes.MPointsShape;
import shapes.MRectangle;
import shapes.MShapeProperties;
import tabs.ImageTools;

/**
 * @author Moses Muigai Gitau
 */
public class Selection extends MPointsShape {

    private BufferedImage image;
    private String selectionType;
    private boolean selectionComplete = false;
    private Shape selectedShape;
    private BufferedImage selected;
    private boolean deleted = false;

    public Selection(MShapeProperties properties, BufferedImage canvasImage, String selectionType) {
        super(properties);
        this.image = canvasImage;
        this.selectionType = selectionType;
    }

    public void selectAll() {
        this.start = new Point(0, 0);
        this.end = new Point(image.getWidth() - 1, image.getHeight() - 1);
        drawShape();
        this.editingComplete = true;
        this.selectionComplete = true;
        this.transferPixels();
        drawShape();
    }

    public void delete() {
        deleted = true;
        canvas.clearGlass();
        //prevent null pointer
        start = new Point();
        end = new Point();

        this.finishedEditing();
    }

    public void invertSelection() {
        this.drawShape();
        Rectangle rect = getBounds();
        selected = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = selected.createGraphics();
        for (int i = 0; i < rect.width; i++) {
            for (int j = 0; j < rect.height; j++) {
                Point p = new Point(rect.x + i, rect.y + j);
                if (!selectedShape.contains(p)) {
                    Color color = new Color(image.getRGB(p.x, p.y), true);
                    g.setColor(color);
                    g.drawLine(i, j, i, j);
                    image.setRGB(p.x, p.y, new Color(0, 0, 0, 0).getRGB());
                }
            }
        }
        g.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point p = this.translateMousePoint(e.getPoint());
        if (!editingComplete) {
            if (this.selectionType.equals(ImageTools.FREE_FORM_SELECTION)) {
                points.add(p);
            }
        } else {
            super.mouseDragged(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!editingComplete) {
            editingComplete = true;
            for (Point p : points) {
                setBoundingPoints(p);
            }
            transferPixels();
            this.createRatios();
        }
        super.mouseReleased(e);
    }

    private void setBoundingPoints(Point p) {
        if (end != null || start != null) {
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
    }

    private void transferPixels() {
        this.drawShape();
        Rectangle rect = getBounds();
        selected = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = selected.createGraphics();
        canvas.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        for (int i = 0; i < rect.width; i++) {
            for (int j = 0; j < rect.height; j++) {
                Point p = new Point(rect.x + i, rect.y + j);
                if (selectedShape.contains(p)) {
                    Color color = new Color(image.getRGB(p.x, p.y), true);
                    g.setColor(color);
                    g.drawLine(i, j, i, j);
                    image.setRGB(p.x, p.y, new Color(0, 0, 0, 0).getRGB());
                }
            }
        }
        canvas.setCursor(Cursor.getDefaultCursor());
        g.dispose();
    }

    @Override
    public void finishedEditing() {
        selectionComplete = true;
        drawShape();
        super.finishedEditing();
    }

    @Override
    public void shapeFill(Graphics2D g) {
        shapeOutline(g);
    }

    @Override
    public void shapeOutline(Graphics2D g) {
        Rectangle fill = getBounds();
        if (!deleted) {
            if (this.selectionType.equals(ImageTools.RECTANGULAR_SELECTION)) {
                g.setColor(Color.black);
                if (!selectionComplete) {
                    MRectangle.drawDottedRectangle(start.x, start.y, fill.width, fill.height, 2, 1, g);
                }
                selectedShape = fill;
            } else if (this.selectionType.equals(ImageTools.FREE_FORM_SELECTION)) {
                int xPoints[] = new int[points.size()];
                int yPoints[] = new int[points.size()];
                int length = points.size();
                for (int i = 0; i < length; i++) {
                    Point point = points.get(i);
                    xPoints[i] = point.x;
                    yPoints[i] = point.y;
                    if (!selectionComplete && !editingComplete) {
                        Color color = new Color(image.getRGB(point.x, point.y), true);
                        if (color.equals(new Color(0, 0, 0, 0))) {
                            color = new Color(255, 255, 255);
                        }
                        Color invert = (new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), 255));
                        g.setColor(invert);
                        g.setStroke(new BasicStroke(1));
                        Point point1 = i == length - 1 ? points.get(0) : points.get(i + 1);
                        g.drawLine(point.x, point.y, point1.x, point1.y);
                    }
                }
                selectedShape = new Polygon(xPoints, yPoints, length);
            }
            g.drawImage(selected, start.x, start.y, fill.width, fill.height, null);
        }
    }
}
