package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import paint.Canvas;
import tabs.ShapeTools;
import useful.ColorChangeListener;
import useful.LineSizeChangeListener;
import useful.ShapeChangeListener;

/**
 * @author Moses Muigai Gitau
 */
public abstract class MShape implements MouseMotionListener, MouseListener,
        ColorChangeListener, ShapeChangeListener, LineSizeChangeListener {

    protected boolean active = true;
    protected boolean editingComplete = false;
    protected Canvas canvas;
    protected double scale;
    protected Color color1, color2;
    protected int size;
    protected int dragX = 0, dragY = 0;
    protected static final int DRAG_TOP = 1;
    protected static final int DRAG_TOP_RIGHT = 2;
    protected static final int DRAG_RIGHT = 3;
    protected static final int DRAG_BOTTOM_RIGHT = 4;
    protected static final int DRAG_BOTTOM = 5;
    protected static final int DRAG_BOTTOM_LEFT = 6;
    protected static final int DRAG_LEFT = 7;
    protected static final int DRAG_TOP_LEFT = 8;
    protected static final int DRAG_SHAPE = 9;
    protected static final int DRAG_NONE = -1;

    protected int drag = DRAG_NONE;
    protected boolean fill = false;
    protected boolean solidOutline = true;

    protected Point start;
    protected Point end;

    protected Stroke outlineStroke = new BasicStroke(1);
    protected Stroke fillStroke = new BasicStroke(1);

    public MShape(MShapeProperties properties) {
        this.canvas = properties.getCanvas();
        this.scale = properties.getScale();
        this.color1 = properties.getColorOne();
        this.color2 = properties.getColorTwo();
        this.size = properties.getDrawSize();
        setFillAndOutline(properties.getFillType(), properties.getOutlineType());
    }

    public MShape() {

    }

    public void setInactive(boolean value) {
        active = value;
    }

    protected void drawShape() {
        canvas.clearGlass();
        drawShape(start, end);
    }

    public void drawShape(Point start, Point end) {
        Graphics2D g = getGraphics();
        this.start = start;
        this.end = end;
        g.setStroke(new BasicStroke(size));
        if (fill) {
            g.setColor(color2);
            shapeFill(g);
        }
        if (solidOutline) {
            g.setColor(color1);
            shapeOutline(g);
        }
        if (editingComplete) {
            this.drawResizeHelper();
        }
        repaint();
    }

    public abstract void shapeFill(Graphics2D g);

    public abstract void shapeOutline(Graphics2D g);

    public void drawResizeHelper() {
        Graphics2D g = getGraphics();
        Rectangle rect = getBounds();
        g.setStroke(new BasicStroke(1));
        for (Rectangle r : this.getBoundingRectangles()) {
            if (r.width == 20) {
                g.setColor(Color.black);
                g.drawRect((r.x + 7), r.y + 7, (int) (5 / scale), (int) (5 / scale));
            }
        }
        g.setColor(Color.white);
        MRectangle.drawDottedRectangle(rect.x, rect.y, rect.width, rect.height, 2, 2, g);
        g.setColor(Color.blue);
        MRectangle.drawDottedRectangle(rect.x, rect.y, rect.width, rect.height, 2, 1, g);
        repaint();
    }

    public boolean isInactive() {
        return !active;
    }

    public void repaint() {
        this.canvas.repaint();
    }

    public void setScale(double scale) {
        this.scale = scale;
        drawShape();
    }

    public Graphics2D getGraphics() {
        return canvas.getGlassGraphics();
    }

    protected Point translateMousePoint(Point p) {
        int x = (int) (p.x / scale);
        int y = (int) (p.y / scale);
        return new Point(x, y);
    }

    public Rectangle getBounds() {
        if (start.x > end.x) {
            int temp = start.x;
            start.x = end.x;
            end.x = temp;
        }
        if (start.y > end.y) {
            int temp = start.y;
            start.y = end.y;
            end.y = temp;
        }
        return new Rectangle(start.x, start.y, (end.x - start.x) <= 1 ? 1 : end.x - start.x, (end.y - start.y) <= 1 ? 1 : end.y - start.y);
    }

    /**
     * TOP , TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM , BOTTOM_LEFT, LEFT,
     * TOP_LEFT, INSIDE
     *
     * @return
     */
    public Rectangle[] getBoundingRectangles() {
        Rectangle r = getBounds();
        if (r != null) {
            Rectangle top = new Rectangle(r.x - 10 + r.width / 2, r.y - 10, 20, 20);
            Rectangle topRight = new Rectangle(r.x + r.width - 10, r.y - 10, 20, 20);
            Rectangle right = new Rectangle(r.x + r.width - 10, r.y - 10 + r.height / 2, 20, 20);
            Rectangle bottomRight = new Rectangle(r.x + r.width - 10, r.y + r.height - 10, 20, 20);
            Rectangle bottom = new Rectangle(r.x - 10 + r.width / 2, r.y - 10 + r.height, 20, 20);
            Rectangle bottomLeft = new Rectangle(r.x - 10, r.y + r.height - 10, 20, 20);
            Rectangle left = new Rectangle(r.x - 10, r.y - 10 + r.height / 2, 20, 20);
            Rectangle topLeft = new Rectangle(r.x - 10, r.y - 10, 20, 20);
            Rectangle inside = new Rectangle(r.x + 10, r.y + 10, r.width - 20, r.height - 20);
            return new Rectangle[]{top, topRight, right, bottomRight, bottom, bottomLeft, left, topLeft, inside};
        } else {
            return null;
        }
    }

    public void setColor(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        drawShape();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        color1 = e.getButton() == 3 ? canvas.getColorTwo() : canvas.getColorOne();
        color2 = e.getButton() == 3 ? canvas.getColorOne() : canvas.getColorTwo();
        Point p = this.translateMousePoint(e.getPoint());
        dragX = p.x;
        dragY = p.y;
        Rectangle bounds[] = getBoundingRectangles();
        if (bounds[0].contains(p)) {
            drag = DRAG_TOP;
        } else if (bounds[1].contains(p)) {
            drag = DRAG_TOP_RIGHT;
        } else if (bounds[2].contains(p)) {
            drag = DRAG_RIGHT;
        } else if (bounds[3].contains(p)) {
            drag = DRAG_BOTTOM_RIGHT;
        } else if (bounds[4].contains(p)) {
            drag = DRAG_BOTTOM;
        } else if (bounds[5].contains(p)) {
            drag = DRAG_BOTTOM_LEFT;
        } else if (bounds[6].contains(p)) {
            drag = DRAG_LEFT;
        } else if (bounds[7].contains(p)) {
            drag = DRAG_TOP_LEFT;
        } else if (bounds[8].contains(p)) {
            drag = DRAG_SHAPE;
        } else {
            // drag = DRAG_NONE;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point p = this.translateMousePoint(e.getPoint());
        Rectangle bounds[] = getBoundingRectangles();
        if (editingComplete) {
            if (bounds[0].contains(p) || bounds[4].contains(p)) {
                canvas.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            } else if (bounds[1].contains(p) || bounds[5].contains(p)) {
                canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
            } else if (bounds[2].contains(p) || bounds[6].contains(p)) {
                canvas.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
            } else if (bounds[3].contains(p) || bounds[7].contains(p)) {
                canvas.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
            } else if (bounds[8].contains(p)) {
                canvas.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            } else {
                canvas.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point p = this.translateMousePoint(e.getPoint());
        int x = p.x;
        int y = p.y;
        int diffX = x - dragX;
        int diffY = y - dragY;
        if (drag != DRAG_NONE) {
            resizing(diffX, diffY, p);
        }
        dragX = x;
        dragY = y;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        color1 = e.getButton() == 3 ? canvas.getColorTwo() : canvas.getColorOne();
        color2 = e.getButton() == 3 ? canvas.getColorOne() : canvas.getColorTwo();
        Point p = this.translateMousePoint(e.getPoint());
        if (!this.getBounds().contains(p)) {
            finishedEditing();
        }
    }

    public void finishedEditing() {
        canvas.removeMouseListener(this);
        canvas.removeMouseMotionListener(this);
        canvas.addMouseMotionListener(canvas);
        canvas.addMouseListener(canvas);
        canvas.removeColorChangeListener(this);
        canvas.removeShapeChangeListener(this);
        canvas.removeLineSizeChangeListener(this);
        this.editingComplete = false;
        drawShape();
        canvas.releaseCurrentDrawingShape();
        repaint();
    }

    public boolean editingComplete() {
        return !editingComplete;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.removeMouseListener(canvas);
        canvas.removeMouseMotionListener(canvas);
        editingComplete = true;
        drawShape();
        drag = DRAG_NONE;
        dragX = 0;
        dragY = 0;
    }

    public void resizing(int diffX, int diffY, Point mouseLocation) {
        resize(diffX, diffY);
        drawShape();
        repaint();
    }

    public void resize(int diffX, int diffY) {
        if (editingComplete) {
            switch (drag) {
                case DRAG_SHAPE:
                    start.x += diffX;
                    start.y += diffY;
                    end.x += diffX;
                    end.y += diffY;
                    break;
                case DRAG_RIGHT:
                    if (start.x > end.x) {
                        start.x += diffX;
                    } else {
                        end.x += diffX;
                    }
                    break;
                case DRAG_LEFT:
                    if (start.x < end.x) {
                        start.x += diffX;
                    } else {
                        end.x += diffX;
                    }
                    break;
                case DRAG_BOTTOM:
                    if (start.y > end.y) {
                        start.y += diffY;
                    } else {
                        end.y += diffY;
                    }
                    break;
                case DRAG_TOP:
                    if (start.y < end.y) {
                        start.y += diffY;
                    } else {
                        end.y += diffY;
                    }
                    break;
                case DRAG_TOP_RIGHT:
                    if (start.x > end.x) {
                        start.x += diffX;
                    } else {
                        end.x += diffX;
                    }
                    if (start.y < end.y) {
                        start.y += diffY;
                    } else {
                        end.y += diffY;
                    }
                    break;
                case DRAG_TOP_LEFT:
                    if (start.y < end.y) {
                        start.y += diffY;
                    } else {
                        end.y += diffY;
                    }
                    if (start.x < end.x) {
                        start.x += diffX;
                    } else {
                        end.x += diffX;
                    }
                    break;
                case DRAG_BOTTOM_RIGHT:
                    if (start.y > end.y) {
                        start.y += diffY;
                    } else {
                        end.y += diffY;
                    }
                    if (start.x > end.x) {
                        start.x += diffX;
                    } else {
                        end.x += diffX;
                    }
                    break;
                case DRAG_BOTTOM_LEFT:
                    if (start.y > end.y) {
                        start.y += diffY;
                    } else {
                        end.y += diffY;
                    }
                    if (start.x < end.x) {
                        start.x += diffX;
                    } else {
                        end.x += diffX;
                    }
                    break;
                default:
                    break;
            }

            if (Math.abs(start.x - end.x) < 20) {
                if (drag == DRAG_RIGHT || drag == DRAG_TOP_RIGHT || drag == DRAG_BOTTOM_RIGHT) {
                    end.x = start.x + 20;
                } else {
                    start.x = end.x - 20;
                }
            }
            if (Math.abs(start.y - end.y) < 20) {
                if (drag == DRAG_TOP || drag == DRAG_TOP_RIGHT || drag == DRAG_TOP_LEFT) {
                    start.y = end.y - 20;
                } else {
                    end.y = start.y + 20;
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void cancelEditing() {
        finishedEditing();
        editingComplete = false;
        canvas.clearGlass();
        drawShape();
    }

    @Override
    public void colorChanged(Color color1, Color color2) {
        this.setColor(color1, color2);
    }

    @Override
    public void shapeFillChanged(String fill) {
        setFill(fill);
        drawShape();
    }

    @Override
    public void shapeOutlineChanged(String outline) {
        setOutline(outline);
        drawShape();
    }

    private void setFillAndOutline(String fill, String outline) {
        setFill(fill);
        setOutline(outline);
    }

    private void setFill(String fill) {
        if (fill.equals(ShapeTools.SOLID_FILL)) {
            this.fill = true;
            this.color2 = canvas.getColorTwo();
        } else if (fill.equals(ShapeTools.NO_FILL)) {
            this.fill = false;
        }
    }

    private void setOutline(String outline) {
        if (outline.equals(ShapeTools.NO_OUTLINE)) {
            this.solidOutline = false;
        } else if (outline.equals(ShapeTools.SOLID_COLOR)) {
            this.solidOutline = true;
        }
    }

    @Override
    public void lineSizeChanged(int size) {
        this.size = size;
        drawShape();
    }
}
