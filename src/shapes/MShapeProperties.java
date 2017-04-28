package shapes;

import java.awt.Color;
import paint.Canvas;

/**
 * @author Moses Muigai Gitau
 */
public class MShapeProperties {

    private Canvas canvas = null;
    private double scale = 1.0;
    private Color color1 = Color.black;
    private Color color2 = Color.white;
    private int size = 1;
    private String fillType = "";
    private String outlineType = "";

    public MShapeProperties(Canvas canvas, double scale, Color color1, Color color2, int size, String fillType, String outlineType) {
        this.canvas = canvas;
        this.scale = scale;
        this.color1 = color1;
        this.color2 = color2;
        this.size = size;
        this.fillType = fillType;
        this.outlineType = outlineType;
    }

    public MShapeProperties() {

    }

    public Canvas getCanvas() {
        return canvas;
    }

    public double getScale() {
        return scale;
    }

    public Color getColorOne() {
        return color1;
    }

    public Color getColorTwo() {
        return color2;
    }

    public int getDrawSize() {
        return size;
    }

    public String getFillType() {
        return fillType;
    }

    public String getOutlineType() {
        return outlineType;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setColorOne(Color color1) {
        this.color1 = color1;
    }

    public void setColorTwo(Color color2) {
        this.color2 = color2;
    }

    public void setDrawSize(int size) {
        this.size = size;
    }

    public void setFillType(String fill) {
        this.fillType = fill;
    }

    public void setOutlineType(String outline) {
        this.outlineType = outline;
    }
}
