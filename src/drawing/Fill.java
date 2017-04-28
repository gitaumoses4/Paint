package drawing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Stack;
import paint.Canvas;

/**
 * @author Moses Muigai Gitau
 */
public class Fill {

    private BufferedImage _canvasImage;
    private Color _fill;
    private Color _initial;
    private Point _start;
    private boolean _sixPixels;
    private Canvas _canvas;

    public Fill() {
    }

    public void fillRegion(Canvas canvas, BufferedImage canvasImage, Color fill, Color initial, Point start, boolean sixPixels) {
        _canvasImage = canvasImage;
        _fill = fill;
        _initial = initial;
        _start = start;
        _sixPixels = sixPixels;
        _canvas = canvas;
        FillThread fillThread = new FillThread();
        fillThread.start();
    }

    class FillThread extends Thread {

        @Override
        public void run() {
            Cursor fillCursor = _canvas.getCursor();
            int width = _canvasImage.getWidth();
            int height = _canvasImage.getHeight();
            HashMap<Point, Pixel> allPoints = new HashMap<>();
            Stack<Point> stack = new Stack();
            stack.push(_start);
            if (_initial.equals(_fill)) {
                return;
            }
            _canvas.setToolCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            while (!stack.isEmpty()) {
                Point n = stack.pop();
                Color color = new Color(_canvasImage.getRGB(n.x, n.y), true);
                if (n.x > 0 && n.x < width - 1 && n.y > 0 && n.y < height - 1) {
                    if (color.equals(_initial)) {
                        Pixel pixel = new Pixel();
                        pixel.setColor(_fill);
                        pixel.setMarked(true);
                        allPoints.put(n, pixel);
                        _canvasImage.createGraphics().setColor(_fill);
                        _canvasImage.createGraphics().drawLine(n.x, n.y, n.x, n.y);
                        _canvasImage.setRGB(n.x, n.y, _fill.getRGB());
                        Point west = new Point(n.x - 1, n.y);
                        allPoints.putIfAbsent(west, new Pixel());
                        Point east = new Point(n.x + 1, n.y);
                        allPoints.putIfAbsent(east, new Pixel());
                        Point north = new Point(n.x, n.y + 1);
                        allPoints.putIfAbsent(north, new Pixel());
                        Point south = new Point(n.x, n.y - 1);
                        allPoints.putIfAbsent(south, new Pixel());
                        if (_sixPixels) {
                            Point northeast = new Point(n.x + 1, n.y - 1);
                            allPoints.putIfAbsent(northeast, new Pixel());
                            Point southeast = new Point(n.x + 1, n.y + 1);
                            allPoints.putIfAbsent(southeast, new Pixel());
                            Point southwest = new Point(n.x - 1, n.y + 1);
                            allPoints.putIfAbsent(southwest, new Pixel());
                            Point northwest = new Point(n.x - 1, n.y - 1);
                            allPoints.putIfAbsent(northwest, new Pixel());
                            if (!allPoints.get(southeast).isMarked()) {
                                stack.add(southeast);
                            }
                            if (!allPoints.get(northeast).isMarked()) {
                                stack.add(northeast);
                            }
                            if (!allPoints.get(northwest).isMarked()) {
                                stack.add(northwest);
                            }
                            if (!allPoints.get(southwest).isMarked()) {
                                stack.add(southwest);
                            }
                        }
                        if (!allPoints.get(west).isMarked()) {
                            stack.add(west);
                        }
                        if (!allPoints.get(east).isMarked()) {
                            stack.add(east);
                        }
                        if (!allPoints.get(north).isMarked()) {
                            stack.add(north);
                        }
                        if (!allPoints.get(south).isMarked()) {
                            stack.add(south);
                        }
                    }
                }
                _canvas.repaint();
            }
            _canvas.setToolCursor(fillCursor);
        }
    }
}
