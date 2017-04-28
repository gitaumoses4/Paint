package tabs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import paint.EventHandler;
import widgets.MenuAction;
import widgets.MDividedButton;
import widgets.MButton;
import widgets.ToolBarItem;

/**
 * @author Moses Muigai Gitau
 */
public class ClipboardTools extends ToolBarItem {

    private final ImageIcon pasteIcon = new ImageIcon(getClass().getResource("images/paste.png"));
    private final ImageIcon cutIcon = new ImageIcon(getClass().getResource("images/cut.png"));
    private final ImageIcon copyIcon = new ImageIcon(getClass().getResource("images/copy.png"));
    private final ImageIcon pasteFromIcon = new ImageIcon(getClass().getResource("images/paste_from.png"));

    private final MDividedButton paste;
    private final MButton cut;
    private final MButton copy;

    private MenuAction paste1;
    private MenuAction pasteFrom;
    private final EventHandler eventHandler;

    public ClipboardTools(EventHandler eventHandler) {
        super("Clipboard");
        this.eventHandler = eventHandler;
        paste = new MDividedButton(pasteIcon, "Paste", true);
        cut = new MButton(cutIcon, "Cut", MButton.RIGHT, false);
        copy = new MButton(copyIcon, "Copy", MButton.RIGHT, false);
        cut.setIconDimensions(14, 14);
        copy.setIconDimensions(14, 14);
        paste.setIconDimensions(30, 30);

        constraints.gridx = 1;
        constraints.gridy = 0;
        addComponent(cut, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        addComponent(copy, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 3;
        addComponent(paste, constraints);
        setPasteMenu();
    }

    private void setPasteMenu() {
        paste1 = new MenuAction("Paste", pasteIcon, 'P', "Paste", 15, 15);
        pasteFrom = new MenuAction("Paste From", pasteFromIcon, 'F', "Paste from...", 15, 15);
        paste.addMenuAction(paste1);
        paste.addMenuAction(pasteFrom);
    }
}
