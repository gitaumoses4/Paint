package paint;

import shapes.MRoundedRectangleCallOut;
import shapes.MDiamond;
import shapes.MCircle;
import shapes.MCurve;
import drawing.Eraser;
import drawing.Fill;
import shapes.MCloudCallOut;
import shapes.MHexagon;
import shapes.MLine;
import shapes.MPentagon;
import drawing.Pencil;
import imagetools.Selection;
import shapes.MRectangle;
import shapes.MDownArrow;
import shapes.MFivePointStar;
import shapes.MFourPointStar;
import shapes.MHeart;
import shapes.MLeftArrow;
import shapes.MLightning;
import shapes.MOvalCallOut;
import shapes.MRightArrow;
import shapes.MRightTriangle;
import shapes.MRoundedRectangle;
import shapes.MShape;
import shapes.MTriangle;
import shapes.MUpArrow;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import shapes.MPolygon;
import shapes.MShapeProperties;
import shapes.MSixPointStar;
import tabs.ImageTools;
import useful.ColorChangeListener;
import useful.LineSizeChangeListener;
import useful.Settings;
import useful.ShapeChangeListener;
import widgets.Tool;
import widgets.Tools;

/**
 * @author Moses Muigai Gitau
 */
public class Canvas extends Tool implements MouseListener, MouseMotionListener {

    private static final int DRAG_RIGHT = 0;
    private static final int DRAG_BOTTOM = 1;
    private static final int DRAG_BOTH = 2;

    private final Settings settings;
    private int width;
    private int height;
    private Color background = Color.white;
    public int border = 10;

    private Rectangle dragRight;
    private Rectangle dragBottom;
    private Rectangle dragBoth;
    private int dragX = 0, dragY = 0;
    private int dragDirection = -1;
    private int dragWidth, dragHeight;
    private boolean resizing = false;
    private ArrayList<Point> tempPoints = new ArrayList<>();

    private BufferedImage image;
    private BufferedImage glass;

    private Graphics2D graphics;
    private Graphics2D glassGraphics;

    private double scale = 1.0;
    private final EventHandler eventHandler;

    private int zoomWidth = 400;
    private int zoomHeight = 200;

    private Cursor toolCursor = Cursor.getDefaultCursor();
    private MShape currentShape = null;

    private boolean drawingShape = false;

    public Canvas(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        this.setActive(true);
        this.settings = eventHandler.getSettings();
        this.width = (int) settings.getProperty(Settings.CANVAS_WIDTH);
        this.height = (int) settings.getProperty(Settings.CANVAS_HEIGHT);

        this.image = new BufferedImage(width - border, height - border, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width - border, height - border);
        this.graphics = g;

        glass = new BufferedImage(width - border, height - border, BufferedImage.TYPE_INT_ARGB);
        g = glass.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        //transparent glass.
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width - border, height - border);
        this.glassGraphics = g;

        setPreferredSize(new Dimension(width, height));
        this.eventHandler.setCanvasSize(this.getPreferredSize());
        this.setOpaque(false);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setCanvasSize(Dimension r) {
        if (!resizing) {
            width = r.width;
            height = r.height;
            validateImage();
        }
    }

    public void setScale(double scale) {
        this.setPreferredSize(new Dimension((int) ((width - border) * scale), (int) ((height - border) * scale)));
        this.setSize(this.getPreferredSize());
        this.scale = scale;
        if (this.currentShape != null) {
            currentShape.setScale(scale);
        }
        repaint();
    }

    private void validateImage() {
        BufferedImage newImage = new BufferedImage(width - border, height - border, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        this.graphics = g;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width - border, height - border);
        g.drawImage(image, 0, 0, (int) (image.getWidth()), (int) (image.getHeight()), this);
        image = newImage;

        BufferedImage newImage1 = new BufferedImage(width - border, height - border, BufferedImage.TYPE_INT_ARGB);
        g = newImage1.createGraphics();
        this.glassGraphics = g;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, width - border, height - border);
        g.drawImage(glass, 0, 0, (int) (glass.getWidth()), (int) (glass.getHeight()), this);
        glass = newImage1;

    }

    public Graphics2D getImageGraphics() {
        return graphics;
    }

    public void setCanvasSize(int width, int height) {
        if (!resizing) {
            this.width = width;
            this.height = height;
            validateImage();
        }
    }

    @Override
    public void paint(Graphics gr) {
        super.paint(gr);
        Graphics2D g = (Graphics2D) gr;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        createShadow(g);
        g.setColor(background);
        g.fillRect(0, 0, (int) ((width - border) * scale), (int) ((height - border) * scale));
        if (!this.drawingShape) {
            drawDraggable(g);
        }
        g.drawImage(image, 0, 0, (int) ((width - border) * scale), (int) ((height - border) * scale), this);
        g.drawImage(glass, 0, 0, (int) ((width - border) * scale), (int) ((height - border) * scale), this);
        if (resizing) {
            drawResizeHelper(g);
        }
    }

    private void createShadow(Graphics2D g) {
        int gray = 200;
        int alpha = 0;
        for (int i = 0; i < 10; i++) {
            gray -= 6;
            alpha += 5;
            g.setColor(new Color(gray, gray, gray, alpha));
            g.fillRect(10, 10, (int) (width * scale) - (i * 2), (int) (height * scale) - (i * 2));
        }
    }

    private void drawDraggable(Graphics2D g) {
        int dragSize = 20;
        dragRight = new Rectangle((int) ((width - border) * scale) - 5, (((int) ((height - border) * scale)) / 2) - 5, dragSize, dragSize);
        dragBottom = new Rectangle((((int) ((width - border) * scale)) / 2) - 5, (int) ((height - border) * scale) - 5, dragSize, dragSize);
        dragBoth = new Rectangle((int) ((width - border) * scale) - 5, ((int) ((height - border) * scale)) - 5, dragSize, dragSize);

        int _size = 5;
        g.setColor(Color.white);
        g.fillRect((int) ((width - border) * scale), ((int) ((height - border) * scale)) / 2, _size, _size);
        g.fillRect(((int) ((width - border) * scale)) / 2, (int) ((height - border) * scale), _size, _size);
        g.fillRect((int) ((width - border) * scale), ((int) ((height - border) * scale)), _size, _size);

        g.setColor(Color.black);
        g.drawRect((int) ((width - border) * scale), ((int) ((height - border) * scale)) / 2, _size, _size);
        g.drawRect(((int) ((width - border) * scale)) / 2, (int) ((height - border) * scale), _size, _size);
        g.drawRect((int) ((width - border) * scale), ((int) ((height - border) * scale)), _size, _size);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle visibleArea = new Rectangle(e.getPoint().x, e.getPoint().y, zoomWidth, zoomHeight);
        int x = (int) (e.getPoint().x / scale);
        int y = (int) (e.getPoint().y / scale);
        String activeTool = eventHandler.getActiveTool();
        if (activeTool.equals(Tools.BUCKET_FILL)) {
            //fillRegion4Point(x, y, eventHandler.getColorOne());
            fillRegion6Point(x, y, eventHandler.getColorOne());
        } else if (activeTool.equals(Tools.COLOR_PICKER)) {
            this.useColorPicker(x, y);
        } else if (activeTool.equals(Tools.ZOOM)) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                zoomIn(visibleArea);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                zoomOut(visibleArea);
            }
        }
    }

    public void releaseCurrentDrawingShape() {
        if (drawingShape) {
            if (!currentShape.editingComplete()) {
                this.currentShape.cancelEditing();
            }
            this.drawingShape = false;
            for (int i = 0; i < glass.getWidth(); i++) {
                for (int j = 0; j < glass.getHeight(); j++) {
                    Color color = new Color(glass.getRGB(i, j), true);
                    if (!color.equals(new Color(0, 0, 0, 0))) {
                        image.setRGB(i, j, color.getRGB());
                    }
                }
            }
            clearGlass();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = (int) (e.getPoint().x / scale);
        int y = (int) (e.getPoint().y / scale);
        String activeTool = eventHandler.getActiveTool();
        dragX = e.getXOnScreen();
        dragY = e.getYOnScreen();
        dragWidth = width;
        dragHeight = height;
        if (isActive()) {
            if (dragRight.contains(e.getPoint())) {
                dragDirection = DRAG_RIGHT;
            } else if (dragBottom.contains(e.getPoint())) {
                dragDirection = DRAG_BOTTOM;
            } else if (dragBoth.contains(e.getPoint())) {
                dragDirection = DRAG_BOTH;
            }
        }
        if (activeTool.equals(Tools.PENCIL)) {
            usePencil(x, y);
        } else if (activeTool.equals(Tools.ERASER)) {
            useEraser(x, y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        tempPoints.clear();
        if (resizing) {
            dragDirection = -1;
            resizing = false;
            if (dragWidth < 1) {
                dragWidth = 10;
            }
            if (dragHeight < 1) {
                dragHeight = 10;
            }
            this.setCanvasSize(dragWidth, dragHeight);
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int diffX = (int) ((dragX - e.getXOnScreen()) / scale);
        int diffY = (int) ((dragY - e.getYOnScreen()) / scale);
        if (dragDirection == DRAG_RIGHT) {
            dragWidth -= diffX;
            resizing = true;
        } else if (dragDirection == DRAG_BOTTOM) {
            dragHeight -= diffY;
            resizing = true;
        } else if (dragDirection == DRAG_BOTH) {
            dragWidth -= diffX;
            dragHeight -= diffY;
            resizing = true;
        } else if (e.getPoint().x / scale < width - border && e.getPoint().y / scale < height - border) {
            int x = (int) (e.getPoint().x / scale);
            int y = (int) (e.getPoint().y / scale);
            String activeTool = eventHandler.getActiveTool();
            if (activeTool.equals(Tools.PENCIL)) {
                usePencil(x, y);
            } else if (activeTool.equals(Tools.ERASER)) {
                useEraser(x, y);
            } else if (eventHandler.activeToolType().equals(Tools.SHAPES) || activeTool.equals(Tools.SELECTION)) {
                if (this.tempPoints.isEmpty()) {
                    this.tempPoints.add(new Point(x, y));
                    this.setCurrentShape(activeTool);
                    if (currentShape != null) {
                        eventHandler.addColorChangeListener(currentShape);
                        eventHandler.addShapeChangeListener(currentShape);
                        eventHandler.addLineSizeChangeListener(currentShape);
                    }
                    this.drawingShape = true;
                    this.addMouseListener(currentShape);
                    this.addMouseMotionListener(currentShape);
                } else if (this.tempPoints.size() == 1) {
                    this.tempPoints.add(new Point(x, y));
                } else {
                    this.tempPoints.set(1, new Point(x, y));
                    drawShape();
                }
            }
        }
        repaint();
        dragY = e.getYOnScreen();
        dragX = e.getXOnScreen();
        int x = (int) (e.getX() / scale);
        int y = (int) (e.getY() / scale);
        if (x > width * scale || y > height * scale || x < 0 || y < 0) {
            eventHandler.setMouseLocation(null);
        } else {
            eventHandler.setMouseLocation(new Point(x, y));
        }
    }

    private void setCurrentShape(String activeTool) {
        Color colorOne = eventHandler.getColorOne();
        Color colorTwo = eventHandler.getColorTwo();
        int drawSize = eventHandler.getDrawSize();
        String fillType = eventHandler.getFill();
        String outlineType = eventHandler.getOutline();

        MShapeProperties properties = new MShapeProperties();
        properties.setCanvas(this);
        properties.setColorOne(colorOne);
        properties.setColorTwo(colorTwo);
        properties.setDrawSize(drawSize);
        properties.setFillType(fillType);
        properties.setOutlineType(outlineType);
        properties.setScale(scale);
        if (activeTool.equals(Tools.LINE)) {
            currentShape = new MLine(properties);
        } else if (activeTool.equals(Tools.CURVE)) {
            currentShape = new MCurve(properties);
        } else if (activeTool.equals(Tools.CIRCLE)) {
            currentShape = new MCircle(properties);
        } else if (activeTool.equals(Tools.RECTANGLE)) {
            currentShape = new MRectangle(properties);
        } else if (activeTool.equals(Tools.ROUNDED_RECTANGLE)) {
            currentShape = new MRoundedRectangle(properties);
        } else if (activeTool.equals(Tools.POLYGON)) {
            currentShape = new MPolygon(properties);
        } else if (activeTool.equals(Tools.TRIANGLE)) {
            currentShape = new MTriangle(properties);
        } else if (activeTool.equals(Tools.RIGHT_ANGLED_TRIANGLE)) {
            currentShape = new MRightTriangle(properties);
        } else if (activeTool.equals(Tools.DIAMOND)) {
            currentShape = new MDiamond(properties);
        } else if (activeTool.equals(Tools.PENTAGON)) {
            currentShape = new MPentagon(properties);
        } else if (activeTool.equals(Tools.HEXAGON)) {
            currentShape = new MHexagon(properties);
        } else if (activeTool.equals(Tools.RIGHT_ARROW)) {
            currentShape = new MRightArrow(properties);
        } else if (activeTool.equals(Tools.LEFT_ARROW)) {
            currentShape = new MLeftArrow(properties);
        } else if (activeTool.equals(Tools.UP_ARROW)) {
            currentShape = new MUpArrow(properties);
        } else if (activeTool.equals(Tools.DOWN_ARROW)) {
            currentShape = new MDownArrow(properties);
        } else if (activeTool.equals(Tools.FOUR_POINT_STAR)) {
            currentShape = new MFourPointStar(properties);
        } else if (activeTool.equals(Tools.FIVE_POINT_STAR)) {
            currentShape = new MFivePointStar(properties);
        } else if (activeTool.equals(Tools.SIX_POINT_STAR)) {
            currentShape = new MSixPointStar(properties);
        } else if (activeTool.equals(Tools.ROUNDED_RECTANGLE_CALLOUT)) {
            currentShape = new MRoundedRectangleCallOut(properties);
        } else if (activeTool.equals(Tools.OVAL_CALLOUT)) {
            currentShape = new MOvalCallOut(properties);
        } else if (activeTool.equals(Tools.CLOUD_CALLOUT)) {
            currentShape = new MCloudCallOut(properties);
        } else if (activeTool.equals(Tools.HEART)) {
            currentShape = new MHeart(properties);
        } else if (activeTool.equals(Tools.LIGHTNING)) {
            currentShape = new MLightning(properties);
        } else if (activeTool.equals(Tools.SELECTION)) {
            currentShape = new Selection(properties, image, eventHandler.getSelectionType());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (dragRight != null) {
            int x = (int) (e.getX() / scale);
            int y = (int) (e.getY() / scale);
            if (dragRight.contains(e.getPoint())) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
            } else if (dragBottom.contains(e.getPoint())) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
            } else if (dragBoth.contains(e.getPoint())) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
            } else if (x > width || y > height || x < 3 || y < 3) {
                this.setCursor(Cursor.getDefaultCursor());
                if (eventHandler.getActiveTool().equals(Tools.ZOOM)) {
                    this.clearGlass();
                }
            } else {
                if (this.getCursor() != toolCursor) {
                    this.setCursor(toolCursor);
                }
                if (eventHandler.getActiveTool().equals(Tools.ZOOM)) {
                    this.useZoomTool(x, y);
                }
            }
            mouseMoved(x, y);
        }
    }

    public void mouseMoved(int x, int y) {
        if (x > width * scale || y > height * scale) {
            eventHandler.setMouseLocation(null);
        } else {
            eventHandler.setMouseLocation(new Point(x, y));
        }
    }

    public Rectangle getDragRight() {
        return dragRight;
    }

    public Rectangle getDragBottom() {
        return dragBottom;
    }

    public Rectangle getDragBoth() {
        return dragBoth;
    }

    private void drawResizeHelper(Graphics2D g) {
        g.setColor(Color.black);
        if (dragWidth > width) {
            if (dragHeight > height) {
                this.setBounds(getX(), getY(), dragWidth, dragHeight);
            } else {
                this.setBounds(getX(), getY(), dragWidth, height);
            }
        } else if (dragHeight > height) {
            this.setBounds(getX(), getY(), width, dragHeight);
        }
        eventHandler.setCanvasSize(new Dimension(dragWidth, dragHeight));
        MRectangle.drawDottedRectangle(0, 0, (int) ((dragWidth - 1) * scale), (int) ((dragHeight - 1) * scale), 2, 1, g);
    }

    /**
     *
     * @param x
     * @param y
     * @param fill
     */
    public void fillRegion4Point(int x, int y, Color fill) {
        Fill fillRegion = new Fill();
        fillRegion.fillRegion(this, image, fill, new Color(image.getRGB(x, y + (int) (13 / scale)), true), new Point(x, y + (int) (13 / scale)), false);
    }

    /**
     *
     * @param x
     * @param y
     * @param fill
     */
    public void fillRegion6Point(int x, int y, Color fill) {
        Fill fillRegion = new Fill();
        fillRegion.fillRegion(this, image, fill, new Color(image.getRGB(x, y + (int) (13 / scale)), true), new Point(x, y + (int) (13 / scale)), true);
    }

    public void useEraser(int x, int y) {
        tempPoints.add(new Point(x, y));
        int eraserSize = (eventHandler.getEraserWeight() * 2) + 2;
        Eraser.erase(graphics, eraserSize, eventHandler.getColorTwo(), tempPoints, scale);
        repaint();
    }

    public void usePencil(int x, int y) {
        tempPoints.add(new Point(x, y + (int) (13 / scale)));
        Pencil.draw(graphics, eventHandler.getColorOne(), eventHandler.getDrawSize(), tempPoints);
        repaint();
    }

    public void useColorPicker(int x, int y) {
        y = y + (int) (13 / scale);
        Color color = new Color(image.getRGB(x, y));
        if (eventHandler.getActiveColorTool().equals(Tools.COLOR_ONE)) {
            eventHandler.getHomeMenu().getColorTools().setColorOne(color);
        } else {
            eventHandler.getHomeMenu().getColorTools().setColorTwo(color);
        }
    }

    public void clearGlass() {
        for (int i = 0; i < glass.getWidth(); i++) {
            for (int j = 0; j < glass.getHeight(); j++) {
                glass.setRGB(i, j, new Color(0, 0, 0, 0).getRGB());
            }
        }
        repaint();
    }

    public Graphics2D getGlassGraphics() {
        return this.glassGraphics;
    }

    public void useZoomTool(int x, int y) {
        this.clearGlass();
        glassGraphics.setColor(Color.black);
        int w = (int) (zoomWidth / scale) > (int) ((this.width - border) / scale) ? (int) ((this.width - border) / scale) : (int) (zoomWidth / scale);
        int h = (int) (zoomHeight / scale) > (int) ((height - border) / scale) ? (int) ((height - border) / scale) : (int) (zoomHeight / scale);
        x = x - (w / 2) < 0 ? 0 : x + (w / 2) > width - border ? width - border - w - 1 : x - w / 2;
        y = y - (h / 2) < 0 ? 0 : y + h / 2 > height - border ? height - border - h - 1 : y - h / 2;
        glassGraphics.drawRect(x, y, w, (int) (zoomHeight / scale));
        repaint();
    }

    public void setToolCursor(Cursor c) {
        this.toolCursor = c;
        this.setCursor(c);
    }

    private void zoomOut(Rectangle visible) {
        eventHandler.zoomOut();
        eventHandler.getScrollPane().getHorizontalScrollBar().setValue(visible.y - visible.height);
        eventHandler.getScrollPane().getVerticalScrollBar().setValue(visible.x - visible.width);
        repaint();
    }

    private void zoomIn(Rectangle visible) {
        eventHandler.zoomIn();
        eventHandler.getScrollPane().getHorizontalScrollBar().setValue(visible.y - visible.height);
        eventHandler.getScrollPane().getVerticalScrollBar().setValue(visible.x - visible.width);

        repaint();
    }

    private void drawShape() {
        clearGlass();
        Point start = tempPoints.get(0);
        Point end = tempPoints.get(1);
        start = new Point(start.x + (int) (5 / scale), start.y + (int) (5 / scale));
        end = new Point(end.x + (int) (5 / scale), end.y + (int) (5 / scale));
        currentShape.drawShape(start, end);
        repaint();
    }

    public void addColorChangeListener(ColorChangeListener l) {
        eventHandler.addColorChangeListener(l);
    }

    public void removeColorChangeListener(ColorChangeListener l) {
        eventHandler.removeColorChangeListener(l);
    }

    public void addShapeChangeListener(ShapeChangeListener l) {
        eventHandler.addShapeChangeListener(l);
    }

    public void removeShapeChangeListener(ShapeChangeListener l) {
        eventHandler.removeShapeChangeListener(l);
    }

    public void addLineSizeChangeListener(LineSizeChangeListener l) {
        eventHandler.addLineSizeChangeListener(l);
    }

    public void removeLineSizeChangeListener(LineSizeChangeListener l) {
        eventHandler.removeLineSizeChangeListener(l);
    }

    public Color getColorOne() {
        return this.eventHandler.getColorOne();
    }

    public Color getColorTwo() {
        return this.eventHandler.getColorTwo();
    }

    public BufferedImage getGlass() {
        return this.glass;
    }

    public boolean isTransparentSelection() {
        return eventHandler.isTransparentSelection();
    }

    public void selectAll() {
        MShapeProperties properties = new MShapeProperties();
        properties.setCanvas(this);
        properties.setScale(scale);
        currentShape = new Selection(properties, image, ImageTools.RECTANGULAR_SELECTION);
        eventHandler.addColorChangeListener(currentShape);
        eventHandler.addShapeChangeListener(currentShape);
        eventHandler.addLineSizeChangeListener(currentShape);
        this.addMouseListener(currentShape);
        this.addMouseMotionListener(currentShape);
        this.removeMouseListener(this);
        this.removeMouseMotionListener(this);
        ((Selection) currentShape).selectAll();
    }

    public void deleteSelection() {
        if (currentShape instanceof Selection) {
            ((Selection) currentShape).delete();
        }
    }

    public void invertSelection() {
        if (currentShape instanceof Selection) {
            ((Selection) currentShape).invertSelection();
        }
    }
}
