package tabs;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import paint.EventHandler;
import widgets.MButton;
import widgets.ToolBarItem;
import widgets.Tools;

/**
 * @author Moses Muigai Gitau
 */
public class ToolsTools extends ToolBarItem {

    private final ImageIcon pencilIcon = new ImageIcon(getClass().getResource("images/pencil.png"));
    private final ImageIcon colorFillIcon = new ImageIcon(getClass().getResource("images/color_fill.png"));
    private final ImageIcon textIcon = new ImageIcon(getClass().getResource("images/text.png"));
    private final ImageIcon eraserIcon = new ImageIcon(getClass().getResource("images/eraser.png"));
    private final ImageIcon colorPickerIcon = new ImageIcon(getClass().getResource("images/color_picker.png"));
    private final ImageIcon zoomIcon = new ImageIcon(getClass().getResource("images/zoom.png"));

    private final ImageIcon pencilCursor = new ImageIcon(getClass().getResource("images/cursor/pencil.png"));
    private final ImageIcon colorFillCursor = new ImageIcon(getClass().getResource("images/cursor/color_fill.png"));
    private final ImageIcon colorPickerCursor = new ImageIcon(getClass().getResource("images/cursor/color_picker.png"));
    private ImageIcon eraserCursor;
    private final ImageIcon zoomCursor = new ImageIcon(getClass().getResource("images/cursor/zoom.png"));

    private final MButton pencil;
    private final MButton colorFill;
    private final MButton text;
    private final MButton eraser;
    private final MButton colorPicker;
    private final MButton zoom;
    private final EventHandler eventHandler;

    private int eraserWeight = 10;
    private Color eraserColor = Color.white;
    private int eraserSize = 32;

    public ToolsTools(EventHandler eventHandler) {
        super("Tools");
        this.eventHandler = eventHandler;
        pencil = new MButton(pencilIcon);
        pencil.setName(Tools.PENCIL);
        pencil.setCursorIcon(pencilCursor);

        pencil.setIconDimensions(14, 14);
        colorFill = new MButton(colorFillIcon);
        colorFill.setIconDimensions(14, 14);
        colorFill.setName(Tools.BUCKET_FILL);
        colorFill.setCursorIcon(colorFillCursor);

        text = new MButton(textIcon);
        text.setIconDimensions(14, 14);
        text.setName(Tools.TEXT);

        eraser = new MButton(eraserIcon);
        eraser.setIconDimensions(14, 14);
        eraser.setName(Tools.ERASER);
        eraserCursor = new ImageIcon(drawEraser());
        eraser.setCursorIcon(eraserCursor);

        colorPicker = new MButton(colorPickerIcon);
        colorPicker.setIconDimensions(14, 14);
        colorPicker.setName(Tools.COLOR_PICKER);
        colorPicker.setCursorIcon(colorPickerCursor);

        zoom = new MButton(zoomIcon);
        zoom.setIconDimensions(14, 14);
        zoom.setName(Tools.ZOOM);
        zoom.setCursorIcon(zoomCursor);

        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(pencil, constraints);
        constraints.gridx = 1;
        addComponent(colorFill, constraints);
        constraints.gridx = 2;
        addComponent(text, constraints);
        constraints.gridy = 1;
        constraints.gridx = 0;
        addComponent(eraser, constraints);
        constraints.gridx = 1;
        addComponent(colorPicker, constraints);
        constraints.gridx = 2;
        addComponent(zoom, constraints);
        eventHandler.addTools(pencil, colorFill, text, eraser, colorPicker, zoom);
        eventHandler.setActiveTool(Tools.PENCIL);
    }

    private Image drawEraser() {
        BufferedImage image = new BufferedImage(eraserSize, eraserSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(eraserColor);
        g.fillRect(1, 1, (eraserWeight * 2) + 2, (eraserWeight * 2) + 2);
        g.setColor(new Color(255 - eraserColor.getRed(), 255 - eraserColor.getGreen(), 255 - eraserColor.getBlue()));
        g.drawRect(0, 0, (eraserWeight * 2) + 2, (eraserWeight * 2) + 2);
        g.dispose();
        return image;
    }

    public void setEraserWeight(int size) {
        this.eraserWeight = size;
        this.eraserCursor = new ImageIcon(drawEraser());
        this.eraser.setCursorIcon(eraserCursor);
    }

    public void setEraserColor(Color color) {
        this.eraserColor = color;
        this.eraserCursor = new ImageIcon(drawEraser());
        this.eraser.setCursorIcon(eraserCursor);
    }

    public int getEraserWeight() {
        return this.eraserWeight;
    }

    public void setEraserSize(int size) {
        this.eraserSize = size;
        this.eraserCursor = new ImageIcon(drawEraser());
        this.eraser.setCursorIcon(eraserCursor);
    }
}
