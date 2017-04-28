package paint;

import imageediting.ImageManip;
import java.awt.Color;
import java.awt.Cursor;
import widgets.Tool;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import menus.FontMenu;
import menus.HomeMenu;
import menus.ViewMenu;
import useful.ColorChangeListener;
import useful.LineSizeChangeListener;
import useful.Settings;
import useful.ShapeChangeListener;
import widgets.MTabbedPane;
import widgets.Tools;

/**
 * @author Moses Muigai Gitau
 */
public class EventHandler implements ActionListener, LineSizeChangeListener {

    private StatusBar statusBar;
    public JScrollPane canvasArea;
    private Canvas canvas;
    private HomeMenu home;
    private FileMenu file;
    private ViewMenu view;
    private FontMenu font;
    private MTabbedPane tabbedPane;
    private String lookAndFeel;
    private final Settings settings = new Settings();
    private HashMap<String, Tool> tools = new HashMap<>();
    private Color colorOne = Color.black;
    private Color colorTwo = Color.white;
    private String activeTool = "";
    private int drawSize = 1;

    public EventHandler(MainFrame mainFrame) {
        setLookAndFeel();
        statusBar = new StatusBar(this);
        canvas = new Canvas(this);
        home = new HomeMenu(this);
        file = new FileMenu(this);
        font = new FontMenu(this);
        tabbedPane = new MTabbedPane(this);
        view = new ViewMenu(this);
        canvasArea = new JScrollPane(canvas);
    }

    private void setLookAndFeel() {
        lookAndFeel = (String) getSettings().getProperty(Settings.LOOK_AND_FEEL);
        try {
            UIManager.setLookAndFeel(lookAndFeel);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
    }

    public JScrollPane getScrollPane() {
        return this.canvasArea;
    }

    public MTabbedPane getMTabbedPane() {
        return tabbedPane;
    }

    public void addTool(Tool tool) {
        this.tools.put(tool.getName(), tool);
        tool.addActionListener(this);
    }

    public void addTools(Tool... tools) {
        for (int i = 0; i < tools.length; i++) {
            this.tools.put(tools[i].getName(), tools[i]);
            tools[i].addActionListener(this);
        }
    }

    public void removeTool(Tool tool) {
        this.tools.remove(tool.getName());
        tool.removeActionListener(this);
    }

    public void removeTools(Tool... tools) {
        for (int i = 0; i < tools.length; i++) {
            this.tools.remove(tools[i].getName());
            tools[i].removeActionListener(this);
        }
    }

    public Tool getToolByName(String name) {
        return tools.get(name);
    }

    public boolean isActive(String nameOfTool) {
        return tools.get(nameOfTool).isActive();
    }

    public Settings getSettings() {
        return settings;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public HomeMenu getHomeMenu() {
        return home;
    }

    public FileMenu getFileMenu() {
        return file;
    }

    public ViewMenu getViewMenu() {
        return view;
    }

    public void scaleCanvas(double scale) {
        canvas.setScale(scale);
    }

    public void setMouseLocation(Point point) {
        statusBar.setMouseLocation(point);
    }

    public void setCanvasSize(Dimension size) {
        statusBar.setCanvasSize(size);
    }

    public void setSelectionSize(Dimension size) {
        statusBar.setSelectionSize(size);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Tool tool = (Tool) e.getSource();
        tool.setActive(true);
        String toolName = tool.getName();
        this.activeTool = toolName;
        for (String name : tools.keySet()) {
            if (!name.equals(toolName)) {
                canvas.releaseCurrentDrawingShape();
                Tool t = tools.get(name);
                t.setActive(false);
            }
        }
        setCursor(toolName);
    }

    public String activeToolType() {
        String[] toolTypes = new String[]{Tools.DRAWING_TOOLS, Tools.BRUSHES, Tools.SHAPES};
        String[] brushes = new String[]{};
        String[] drawingTools = new String[]{};
        String[] shapes = new String[]{Tools.LINE, Tools.CIRCLE, Tools.CURVE, Tools.RECTANGLE, Tools.ROUNDED_RECTANGLE, Tools.CLOUD_CALLOUT,
            Tools.DIAMOND, Tools.DOWN_ARROW, Tools.FIVE_POINT_STAR, Tools.FOUR_POINT_STAR, Tools.HEART, Tools.HEXAGON, Tools.LEFT_ARROW, Tools.LIGHTNING, Tools.OVAL_CALLOUT,
            Tools.PENTAGON, Tools.POLYGON, Tools.RIGHT_ANGLED_TRIANGLE, Tools.RIGHT_ARROW, Tools.ROUNDED_RECTANGLE_CALLOUT, Tools.SIX_POINT_STAR, Tools.TRIANGLE, Tools.UP_ARROW};
        ArrayList<String[]> t = new ArrayList();
        t.add(drawingTools);
        t.add(brushes);
        t.add(shapes);
        for (int index = 0; index < t.size(); index++) {
            String s[] = t.get(index);
            for (int i = 0; i < s.length; i++) {
                if (s[i].equals(activeTool)) {
                    return toolTypes[index];
                }
            }
        }
        return "";
    }

    public String getActiveTool() {
        return this.activeTool;
    }

    public FontMenu getFontMenu() {
        return font;
    }

    public void setColorOne(Color one) {
        this.colorOne = one;
    }

    public Color getColorOne() {
        return colorOne;
    }

    public void setColorTwo(Color two) {
        this.colorTwo = two;
        if (this.getActiveTool().equals(Tools.ERASER)) {
            this.setEraserColor(two);
        }
    }

    public Color getColorTwo() {
        return colorTwo;
    }

    public String getActiveColorTool() {
        return this.home.getColorTools().getActiveColorTool();
    }

    public void setCursor(String toolName) {
        Tool tool = this.getToolByName(toolName);
        if (tool.getCursorIcon() != null) {
            if (toolName.equals(Tools.ERASER)) {
                this.home.getDrawingTools().setEraserColor(colorTwo);
            }
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = ImageManip.removeGray(tool.getCursorIcon());
            Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), tool.getName());
            canvas.setToolCursor(c);
            if (!toolName.equals(Tools.ZOOM)) {
                canvas.clearGlass();
            }
        }
        if (activeTool.equals(Tools.LINE) || activeTool.equals(Tools.CURVE)) {
            enableFillButton(false);
        } else {
            enableFillButton(true);
        }
    }

    public int getEraserWeight() {
        return this.home.getDrawingTools().getEraserWeight();
    }

    public void setEraserWeight(int size) {
        this.home.getDrawingTools().setEraserWeight(size);
    }

    public void setEraserColor(Color color) {
        this.home.getDrawingTools().setEraserColor(color);
    }

    public void setEraserSize(int size) {
        this.home.getDrawingTools().setEraserSize(size);
    }

    public void setActiveTool(String name) {
        this.activeTool = name;
        this.getToolByName(name).setActive(true);
        setCursor(name);
    }

    public void zoomIn() {
        this.getStatusBar().zoomIn();
    }

    public void zoomOut() {
        this.getStatusBar().zoomOut();
    }

    public int getDrawSize() {
        return drawSize;
    }

    public void addColorChangeListener(ColorChangeListener l) {
        this.home.getColorTools().addColorChangeListener(l);
    }

    public void removeColorChangeListener(ColorChangeListener l) {
        this.home.getColorTools().removeColorChangeListener(l);
    }

    public void addShapeChangeListener(ShapeChangeListener l) {
        this.home.getShapeTools().addShapeChangeListener(l);
    }

    public void removeShapeChangeListener(ShapeChangeListener l) {
        this.home.getShapeTools().removeShapeChangeListener(l);
    }

    public void addLineSizeChangeListener(LineSizeChangeListener l) {
        this.home.getSizeTools().addLineSizeChangeListener(l);
    }

    public void removeLineSizeChangeListener(LineSizeChangeListener l) {
        this.home.getSizeTools().removeLineSizeChangeListener(l);
    }

    public String getFill() {
        return home.getShapeTools().getFill();
    }

    public String getOutline() {
        return home.getShapeTools().getOutline();
    }

    public void enableFillButton(boolean value) {
        if (home != null) {
            home.getShapeTools().enableFillButton(value);
        }
    }

    public String getSelectionType() {
        return this.home.getImageTools().getSelectionType();
    }

    public boolean isTransparentSelection() {
        return this.home.getImageTools().isTransparentSelection();
    }

    @Override
    public void lineSizeChanged(int size) {
        this.drawSize = size;
    }

    public void selectAll() {
        canvas.selectAll();
    }

    public void deleteSelection() {
        canvas.deleteSelection();
    }
    public void invertSelection(){
        canvas.invertSelection();
    }
}
