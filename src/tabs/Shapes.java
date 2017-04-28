package tabs;

import widgets.MButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import paint.EventHandler;
import widgets.MButton;
import widgets.Tools;

/**
 * @author Moses Muigai Gitau
 */
public class Shapes extends JPanel {

    private final String[] iconNames = {"line", "curve", "circle", "rectangle", "rounded_rectangle", "polygon",
        "triangle", "right_triangle", "diamond", "pentagon", "hexagon", "right_arrow", "left_arrow", "up_arrow",
        "down_arrow", "four_point_star", "five_point_star", "six_point_star", "rounded_rectangle_callout",
        "oval_callout", "cloud_callout", "heart", "lightning"};
    private final String[] toolNames = {Tools.LINE, Tools.CURVE, Tools.CIRCLE, Tools.RECTANGLE, Tools.ROUNDED_RECTANGLE, Tools.POLYGON,
        Tools.TRIANGLE, Tools.RIGHT_ANGLED_TRIANGLE, Tools.DIAMOND, Tools.PENTAGON, Tools.HEXAGON, Tools.RIGHT_ARROW, Tools.LEFT_ARROW, Tools.UP_ARROW,
        Tools.DOWN_ARROW, Tools.FOUR_POINT_STAR, Tools.FIVE_POINT_STAR, Tools.SIX_POINT_STAR, Tools.ROUNDED_RECTANGLE_CALLOUT, Tools.OVAL_CALLOUT,
        Tools.CLOUD_CALLOUT, Tools.HEART, Tools.LIGHTNING};
    private final ImageIcon[] icons = new ImageIcon[iconNames.length];
    private final MButton[] buttons = new MButton[icons.length];
    private final GridLayout layout;
    private final JPanel contentPane;
    private final JScrollPane scrollPane;
    private final EventHandler eventHandler;

    public Shapes(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        layout = new GridLayout(0, 7);

        contentPane = new JPanel();
        contentPane.setOpaque(false);
        contentPane.setLayout(layout);

        scrollPane = new JScrollPane(contentPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(Color.white);
        scrollPane.setPreferredSize(new Dimension(180, 60));
        this.setPreferredSize(new Dimension(180, 70));
        this.setMinimumSize(new Dimension(180, 70));
        add(scrollPane);
        setOpaque(false);
        initButtons();
    }

    private void initButtons() {
        for (int i = 0; i < iconNames.length; i++) {
            icons[i] = new ImageIcon(getClass().getResource("images/" + iconNames[i] + ".png"));
            buttons[i] = new MButton(icons[i]);
            buttons[i].setIconDimensions(16, 16);
            buttons[i].setInset(1);
            buttons[i].setName(toolNames[i]);
            buttons[i].setCursorIcon(new ImageIcon(shapeCursor()));
            contentPane.add(buttons[i]);
            eventHandler.addTool(buttons[i]);
        }
    }

    public static Image shapeCursor() {
        BufferedImage cursor = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = cursor.createGraphics();
        g.setColor(Color.white);
        g.drawLine(4, 0, 4, 10);
        g.drawLine(6, 0, 6, 10);
        g.drawLine(0, 4, 10, 4);
        g.drawLine(0, 6, 10, 6);
        g.setColor(Color.black);
        g.drawLine(5, 0, 5, 10);
        g.drawLine(0, 5, 10, 5);
        g.dispose();
        return cursor;
    }
}
