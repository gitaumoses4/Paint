package tabs;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import paint.EventHandler;
import widgets.MButton;
import widgets.ToolBarItem;

/**
 * @author Moses Muigai Gitau
 */
public class FontTools extends ToolBarItem {

    private final ImageIcon boldIcon = new ImageIcon(getClass().getResource("images/bold.png"));
    private final ImageIcon underlineIcon = new ImageIcon(getClass().getResource("images/underline.png"));
    private final ImageIcon italicIcon = new ImageIcon(getClass().getResource("images/italic.png"));
    private final ImageIcon strikeThroughIcon = new ImageIcon(getClass().getResource("images/strike_through.png"));
    private final JComboBox fonts;
    private final JComboBox fontSizes;
    private final MButton bold;
    private final MButton italic;
    private final MButton underline;
    private final MButton strikeThrough;
    private final EventHandler eventHandler;

    public FontTools(EventHandler eventHandler) {
        super("Fonts");
        this.eventHandler = eventHandler;
        fonts = new JComboBox(new String[]{"Old English Text               "});
        fontSizes = new JComboBox(getFontSizes());
        bold = new MButton(boldIcon);
        bold.setIconDimensions(16, 16);
        italic = new MButton(italicIcon);
        italic.setIconDimensions(16, 16);
        underline = new MButton(underlineIcon);
        underline.setIconDimensions(16, 16);
        strikeThrough = new MButton(strikeThroughIcon);
        strikeThrough.setIconDimensions(16, 16);

        constraints.gridx = 2;
        constraints.gridy = 1;
        addComponent(bold, constraints);
        constraints.gridx = 3;
        addComponent(italic, constraints);
        constraints.gridx = 4;
        addComponent(underline, constraints);
        constraints.gridx = 5;
        addComponent(strikeThrough, constraints);
        constraints.gridx = 0;
        constraints.gridwidth = 1;
        addComponent(fontSizes, constraints);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 6;
        addComponent(fonts, constraints);
    }

    private String[] getFontSizes() {
        String[] sizes = new String[]{"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"};
        return sizes;
    }
}
