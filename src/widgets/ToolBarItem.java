package widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Moses Muigai Gitau
 */
public class ToolBarItem extends JPanel {

    protected final Color labelColor = new Color(150, 150, 150);
    protected final JLabel label;
    protected final JPanel contentPane;
    private GridBagLayout gridBagLayout = new GridBagLayout();
    protected GridBagConstraints constraints = new GridBagConstraints();

    public ToolBarItem(String text) {
        setOpaque(false);
        setLayout(new BorderLayout());
        label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(labelColor);
        add(label, BorderLayout.SOUTH);
        setBorder(new EmptyBorder(2,2,2,2));
        contentPane = new JPanel(gridBagLayout);
        contentPane.setOpaque(false);
        add(contentPane);
    }

    public void addComponent(Component component, GridBagConstraints constraints) {
        contentPane.add(component, constraints);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(200, 200, 200));
        g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
    }
}
