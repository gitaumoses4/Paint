package widgets;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import paint.EventHandler;
import useful.Settings;

/**
 * @author Moses Muigai Gitau
 */
public class MTabbedPane extends JPanel {

    private final JPanel contentPane = new JPanel();
    private final Color lineColor = new Color(180, 180, 180);
    private final TopBar topBar = new TopBar();
    private final CardLayout contentPaneLayout = new CardLayout();
    private final BoxLayout topBarLayout = new BoxLayout(topBar, BoxLayout.X_AXIS);

    private final Color topBarColor;
    private final Color contentPaneColor;
    
    private final Settings settings;

    private final ArrayList<Title> titles = new ArrayList();
    private final EventHandler eventHandler;

    public MTabbedPane(EventHandler eventHandler) {
        this.eventHandler=eventHandler;
        this.settings = eventHandler.getSettings();
        contentPane.setLayout(contentPaneLayout);
        topBar.setLayout(topBarLayout);
        setLayout(new BorderLayout());

        topBarColor = (Color) settings.getProperty(Settings.TABBED_PANE_TOP_COLOR);
        contentPaneColor = (Color) settings.getProperty(Settings.TABBED_PANE_CONTENT_COLOR);
        
        topBar.setBackground(topBarColor);
        contentPane.setBackground(contentPaneColor);

        add(contentPane, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);
    }

    public void addTab(Component component, String name) {
        Title title = !name.equals("File") ? new Title(name) : new FileTab(name);
        component.setBackground(contentPaneColor);
        topBar.add(title);
        titles.add(title);
        contentPane.add(component, name);
    }

    public Color getContentBackground() {
        return this.contentPaneColor;
    }

    public void setEnabledAt(int index) {
        if (index < titles.size()) {
            Title title = titles.get(index);
            title.mouseClicked(null);
            contentPaneLayout.show(contentPane, title.getText());
        } else {
            throw new IllegalArgumentException("Tab index not found: index " + index);
        }
    }

    public void setEnabledAt(String name) {
        for (Title title : titles) {
            if (title.getText().equals(name)) {
                setEnabledAt(titles.indexOf(title));
                return;
            }
        }
        throw new IllegalArgumentException("Tab name: " + name + " does not exist");
    }

    class Title extends JLabel implements MouseListener {

        private Color background;
        private final Font textFont;
        private boolean clicked = false;
        private boolean mouseOver = false;

        public Title(String text) {
            super(text);
            background = topBarColor;
            setBorder(new EmptyBorder(5, 10, 5, 10));
            textFont = (Font) settings.getProperty(Settings.TABBED_PANE_FONT);
            setFont(textFont);
            addMouseListener(this);
        }

        @Override
        public void paint(Graphics g) {
            if (mouseOver && !clicked) {
                if (isFile()) {
                    g.setColor(background.brighter());
                } else {
                    g.setColor(new Color(250, 250, 250));
                }
            } else {
                g.setColor(background);
            }
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paint(g);
            g.setColor(lineColor);
            if (clicked) {
                g.drawLine(0, 0, 0, getHeight());
                g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
            } else {
                if (mouseOver && !isFile()) {
                    g.setColor(new Color(245, 245, 245));
                    g.drawLine(2, 0, 2, getHeight());
                    g.drawLine(getWidth() - 2, 0, getWidth() - 2, getHeight());
                }
                g.setColor(lineColor);
                g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            titles.stream().forEach((title) -> {
                if (!title.equals(this) && !title.isFile()) {
                    title.setBackgroundColor(topBarColor);
                    title.setClicked(false);
                } else if (title.equals(this)) {
                    title.setBackgroundColor(contentPaneColor);
                    title.setClicked(true);
                }
            });
            contentPaneLayout.show(contentPane, getText());
            repaint();
        }

        public boolean isFile() {
            return this instanceof FileTab;
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseOver = true;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseOver = false;
            repaint();
        }

        public void setClicked(boolean clicked) {
            this.clicked = clicked;
            repaint();
        }

        public void setBackgroundColor(Color background) {
            this.background = background;
        }
    }

    class FileTab extends Title {

        private final Color fileTabColor = (Color) settings.getProperty(Settings.FILE_TAB_COLOR);

        public FileTab(String text) {
            super(text);
            setBackgroundColor(fileTabColor);
            setForeground(Color.white);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

    }

    class TopBar extends JPanel {

        private int max = 0;

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(lineColor);
            for (Title title : titles) {
                max = Integer.max(max, title.getX() + title.getWidth());
            }
            g.drawLine(max, getHeight() - 1, getWidth(), getHeight() - 1);
        }
    }
}
