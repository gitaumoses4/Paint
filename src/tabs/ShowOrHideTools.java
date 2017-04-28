package tabs;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import paint.EventHandler;
import widgets.ToolBarItem;

/**
 * @author Moses Muigai Gitau
 */
public class ShowOrHideTools extends ToolBarItem {

    private final JCheckBox rulers;
    private final JCheckBox gridLines;
    private final JCheckBox statusBar;
    private final EventHandler eventHandler;

    public ShowOrHideTools(EventHandler eventHandler) {
        super("Show or Hide");
        this.eventHandler = eventHandler;
        rulers = new JCheckBox("Rulers        ");
        rulers.setOpaque(false);
        gridLines = new JCheckBox("Grid Lines   ");
        gridLines.setOpaque(false);
        statusBar = new JCheckBox("Status Bar ");
        statusBar.setOpaque(false);

        constraints.gridx = 0;
        constraints.gridy = 0;
        addComponent(rulers, constraints);
        constraints.gridy = 1;
        addComponent(gridLines, constraints);
        constraints.gridy = 2;
        addComponent(statusBar, constraints);
        setBorder(new EmptyBorder(0, 10, 10, 10));
    }
}
