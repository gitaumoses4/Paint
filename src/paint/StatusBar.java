package paint;

import imageediting.ImageManip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import widgets.MButton;

/**
 * @author Moses Muigai Gitau
 */
public class StatusBar extends JPanel implements ChangeListener {

    private final ImageIcon zoomInIcon = new ImageIcon(getClass().getResource("status_bar_zoom_in.png"));
    private final ImageIcon zoomOutIcon = new ImageIcon(getClass().getResource("status_bar_zoom_out.png"));
    private final ImageIcon mouseLocationIcon = new ImageIcon(getClass().getResource("mouse_position.png"));
    private final ImageIcon selectionSizeIcon = new ImageIcon(getClass().getResource("selection_size.png"));
    private final ImageIcon canvasSizeIcon = new ImageIcon(getClass().getResource("canvas_size.png"));
    private final ImageIcon saveSizeIcon = new ImageIcon(getClass().getResource("save_size.png"));

    private JSlider zoomSlider;
    private final HashMap<Integer, String> labels = new HashMap();

    private JLabel zoomLabel;
    private MButton zoomIn;
    private MButton zoomOut;

    private Label mouseLocation;
    private Label selectionSize;
    private Label canvasSize;
    private Label saveSize;

    private final BoxLayout layout;
    private JPanel statusPanel;
    private EventHandler eventHandler;

    public StatusBar(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
        setLayout(new BorderLayout());
        statusPanel = new JPanel();
        layout = new BoxLayout(statusPanel, BoxLayout.X_AXIS);
        statusPanel.setLayout(layout);
        initComponents();
        statusPanel.add(Box.createHorizontalStrut(Integer.MAX_VALUE));
        add(statusPanel);

        this.setPreferredSize(new Dimension(this.getPreferredSize().width, 28));
    }

    private void initializeLabels() {
        labels.put(1, "12.5");
        labels.put(2, "25");
        labels.put(3, "50");
        labels.put(4, "100");
        labels.put(5, "200");
        labels.put(6, "300");
        labels.put(7, "400");
        labels.put(8, "500");
        labels.put(9, "600");
        labels.put(10, "800");
    }

    private void initComponents() {
        initializeLabels();

        mouseLocation = new Label(mouseLocationIcon, 24, 24, "");
        statusPanel.add(mouseLocation);
        selectionSize = new Label(selectionSizeIcon, 24, 24, "");
        statusPanel.add(selectionSize);
        canvasSize = new Label(canvasSizeIcon, 24, 24, "");
        statusPanel.add(canvasSize);
        saveSize = new Label(saveSizeIcon, 24, 24, "");
        statusPanel.add(saveSize);

        JPanel zoom = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        zoomLabel = new JLabel("100%");

        zoomIn = new MButton(zoomInIcon);
        zoomIn.setToolTipText("Zoom In");
        zoomIn.addActionListener((ActionEvent actionEvent) -> {
            zoomIn();
        });
        zoomOut = new MButton(zoomOutIcon);
        zoomOut.setToolTipText("Zoom out");
        zoomOut.addActionListener((ActionEvent actionEvent) -> {
            zoomOut();
        });

        zoomSlider = new JSlider(1, 10);
        zoomSlider.setPreferredSize(new Dimension(100, zoomSlider.getPreferredSize().height));
        zoomSlider.setMinimumSize(zoomSlider.getPreferredSize());
        zoomSlider.setValue(4);
        zoomSlider.setPaintLabels(true);
        zoomSlider.addChangeListener(this);
        c.gridx = 0;
        c.ipadx = 5;
        c.gridy = 0;
        zoom.add(zoomLabel, c);
        c.gridx = 1;
        zoom.add(zoomOut, c);
        c.gridx = 2;
        zoom.add(zoomSlider, c);
        c.gridx = 3;
        zoom.add(zoomIn, c);
        add(zoom, BorderLayout.EAST);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (zoomSlider.getValueIsAdjusting()) {
            int i = zoomSlider.getValue();
            double percentage = Double.parseDouble(labels.get(i));
            zoomLabel.setText(percentage + "%");
            double scale = percentage / 100;
            eventHandler.scaleCanvas(scale);
        }
    }

    public void setMouseLocation(Point point) {
        if (point != null) {
            this.mouseLocation.setText(point.x + "," + point.y + " px");
        } else {
            this.mouseLocation.setText("");
        }
    }

    public void setSelectionSize(Dimension size) {
        if (size == null) {
            this.selectionSize.setText("");
        } else {
            this.selectionSize.setText(size.width + " x " + size.height + " px");
        }
    }

    public void setCanvasSize(Dimension size) {
        if (size == null) {
            this.canvasSize.setText("");
        } else {
            this.canvasSize.setText(size.width + " x " + size.height + " px");
        }
    }

    public void setSaveSize(int size) {
        if (size == 0) {
            this.canvasSize.setText("");
        } else {
            this.canvasSize.setText(size + " kb");
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(200, 200, 200));
        g.drawLine(0, 0, getWidth(), 0);
    }

    class Label extends JPanel {

        private final Image image;
        private final int iconWidth, iconHeight;
        private String text;

        public Label(ImageIcon icon, int iconWidth, int iconHeight, String text) {
            this.text = text;
            this.iconWidth = iconWidth;
            this.iconHeight = iconHeight;
            this.image = icon.getImage();
            this.setPreferredSize(new Dimension(150, iconHeight));
            this.setMinimumSize(new Dimension(150, iconHeight));
        }

        public void setText(String text) {
            this.text = text;
            repaint();
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Font font = g.getFont();
            int imageY = (StatusBar.this.getHeight() - iconHeight) / 2;
            int textY = (StatusBar.this.getHeight() + font.getSize()) / 2;
            g.drawImage(ImageManip.removeGray(new ImageIcon(image)), 0, imageY, iconWidth, iconHeight, this);

            g.drawString(text, iconWidth + 10, textY);
            g.setColor(new Color(200, 200, 200));
            g.drawLine(getWidth() - 1, 0, getWidth() - 1, StatusBar.this.getHeight());
        }
    }

    public JSlider getZoomSlider() {
        return this.zoomSlider;
    }

    public void zoomIn() {
        if (zoomSlider.getValue() < 10) {
            zoomSlider.setValue(zoomSlider.getValue() + 1);
            zoomOut.setEnabled(true);
            zoomSlider.setValueIsAdjusting(true);
            stateChanged(null);
        }
        if (zoomSlider.getValue() == 10) {
            zoomIn.setEnabled(false);
            zoomOut.setEnabled(true);
        }
    }

    public void zoomOut() {
        if (zoomSlider.getValue() > 1) {
            zoomSlider.setValue(zoomSlider.getValue() - 1);
            zoomIn.setEnabled(true);
            zoomSlider.setValueIsAdjusting(true);
            stateChanged(null);
        }
        if (zoomSlider.getValue() == 1) {
            zoomOut.setEnabled(false);
            zoomIn.setEnabled(true);
        }
    }
}
