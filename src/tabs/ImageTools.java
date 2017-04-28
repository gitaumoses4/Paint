package tabs;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import paint.EventHandler;
import widgets.MDividedButton;
import widgets.MButton;
import widgets.MenuAction;
import widgets.Tool;
import widgets.ToolBarItem;
import widgets.Tools;

/**
 * @author Moses Muigai Gitau
 */
public class ImageTools extends ToolBarItem {

    private final ImageIcon selectIcon = new ImageIcon(getClass().getResource("images/select.png"));
    private final ImageIcon cropIcon = new ImageIcon(getClass().getResource("images/crop.png"));
    private final ImageIcon resizeIcon = new ImageIcon(getClass().getResource("images/resize.png"));
    private final ImageIcon rotateIcon = new ImageIcon(getClass().getResource("images/rotate_right.png"));
    private final ImageIcon rectangularSelectionIcon = new ImageIcon(getClass().getResource("images/rectangular_selection.png"));
    private final ImageIcon freeFormSelectionIcon = new ImageIcon(getClass().getResource("images/free_form_selection.png"));
    private final ImageIcon selectAllIcon = new ImageIcon(getClass().getResource("images/select_all.png"));
    private final ImageIcon invertSelectionIcon = new ImageIcon(getClass().getResource("images/invert_selection.png"));
    private final ImageIcon deleteIcon = new ImageIcon(getClass().getResource("images/delete.png"));
    private final ImageIcon rotateRightIcon = new ImageIcon(getClass().getResource("images/rotate_right.png"));
    private final ImageIcon rotateLeftIcon = new ImageIcon(getClass().getResource("images/rotate_left.png"));
    private final ImageIcon rotate180Icon = new ImageIcon(getClass().getResource("images/rotate_180.png"));
    private final ImageIcon flipHorizontalIcon = new ImageIcon(getClass().getResource("images/flip_horizontal.png"));
    private final ImageIcon flipVerticalIcon = new ImageIcon(getClass().getResource("images/flip_vertical.png"));

    private final MDividedButton select;
    private final MButton crop;
    private final MButton resize;
    private final MButton rotate;

    private MenuAction freeFormSelection;
    private MenuAction rectangularSelection;
    private MenuAction selectAll;
    private MenuAction invertSelection;
    private MenuAction delete;
    private MenuAction transparentSelection;

    private MenuAction rotateRight;
    private MenuAction rotateLeft;
    private MenuAction rotate180;
    private MenuAction flipVertical;
    private MenuAction flipHorizontal;

    private JLabel selectionShapesHeader;
    private JLabel selectionOptions;
    private final EventHandler eventHandler;
    private final SelectionListener selectionListener = new SelectionListener();

    public static final String FREE_FORM_SELECTION = "Free Form Selection";
    public static final String RECTANGULAR_SELECTION = "Rectangular Selection";

    public ImageTools(EventHandler eventHandler) {
        super("Image");
        this.eventHandler = eventHandler;

        select = new MDividedButton(selectIcon, "Select", true);
        select.setIconDimensions(32, 32);
        select.setName(Tools.SELECTION);
        select.setCursorIcon(new ImageIcon(Shapes.shapeCursor()));
        eventHandler.addTool(select);
        crop = new MButton(cropIcon, "Crop", MButton.RIGHT, false);
        crop.setIconDimensions(14, 14);
        resize = new MButton(resizeIcon, "Resize", MButton.RIGHT, false);
        resize.setIconDimensions(14, 14);
        rotate = new MButton(rotateIcon, "Rotate", MButton.RIGHT, true);
        rotate.setIconDimensions(14, 14);

        constraints.gridx = 1;
        constraints.gridy = 0;
        addComponent(crop, constraints);
        constraints.gridy = 1;
        addComponent(resize, constraints);
        constraints.gridy = 2;
        addComponent(rotate, constraints);
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridheight = 4;
        addComponent(select, constraints);

        setSelectMenu();
        setRotateMenu();
    }

    private void setSelectMenu() {
        selectionShapesHeader = new JLabel("Selection shapes");
        Font font = selectionShapesHeader.getFont().deriveFont(Font.BOLD);
        selectionShapesHeader.setFont(font);

        select.addMenuComponent(selectionShapesHeader);
        select.addMenuComponent(new JSeparator());

        freeFormSelection = new MenuAction("Free form Selection", freeFormSelectionIcon, 'F', "Free Form Selection", 20, 20);
        freeFormSelection.addActionListener(selectionListener);
        rectangularSelection = new MenuAction("Rectangular Selection", rectangularSelectionIcon, 'R', "Rectangular Selection", 20, 20);
        rectangularSelection.setSelected(true);
        rectangularSelection.addActionListener(selectionListener);

        select.addMenuAction(rectangularSelection);
        select.addMenuAction(freeFormSelection);

        selectionOptions = new JLabel("Selection Options");
        selectionOptions.setFont(font);

        select.addMenuComponent(new JSeparator());
        select.addMenuComponent(selectionOptions);
        select.addMenuComponent(new JSeparator());

        selectAll = new MenuAction("Select All", selectAllIcon, 'S', "Select All", 20, 20);
        selectAll.addActionListener(selectionListener);
        invertSelection = new MenuAction("Invert Selection", invertSelectionIcon, 'I', "Invert selection", 20, 20);
        invertSelection.addActionListener(selectionListener);
        delete = new MenuAction("Delete", deleteIcon, 'D', "Delete selection", 20, 20);
        delete.addActionListener(selectionListener);
        transparentSelection = new MenuAction("Transparent Selection");
        transparentSelection.addActionListener(selectionListener);
        transparentSelection.setMnemonic('T');
        transparentSelection.setToolTip("Transparent Selection");

        select.addMenuAction(selectAll);
        select.addMenuAction(invertSelection);
        select.addMenuAction(delete);
        select.addMenuAction(transparentSelection);
    }

    private void setRotateMenu() {
        rotateLeft = new MenuAction("Rotate Left 90\u00B0", rotateLeftIcon, 'L', "Rotate Left 90 degrees", 20, 20);
        rotateRight = new MenuAction("Rotate Right 90\u00B0", rotateRightIcon, 'R', "Rotate Right 90 degrees", 20, 20);
        rotate180 = new MenuAction("Rotate 180\u00B0", rotate180Icon, 't', "Rotate 180 degrees", 20, 20);
        flipVertical = new MenuAction("Flip Vertical", flipVerticalIcon, 'V', "Flip Vertical 180 degrees", 20, 20);
        flipHorizontal = new MenuAction("Flip Horizontal", flipHorizontalIcon, 'H', "Flip Horizontal 180 degrees", 20, 20);

        rotate.addMenuAction(rotateLeft);
        rotate.addMenuAction(rotateRight);
        rotate.addMenuAction(rotate180);
        rotate.addMenuAction(flipVertical);
        rotate.addMenuAction(flipHorizontal);
    }

    public String getSelectionType() {
        return freeFormSelection.isSelected() ? FREE_FORM_SELECTION : RECTANGULAR_SELECTION;
    }

    public boolean isTransparentSelection() {
        return transparentSelection.isSelected();
    }

    class SelectionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == rectangularSelection) {
                freeFormSelection.setSelected(false);
                rectangularSelection.setSelected(true);
                select.setIcon(rectangularSelectionIcon);
            } else if (e.getSource() == freeFormSelection) {
                select.setIcon(freeFormSelectionIcon);
                rectangularSelection.setSelected(false);
                freeFormSelection.setSelected(true);
            } else if (e.getSource() == selectAll) {
                eventHandler.selectAll();
            } else if (e.getSource() == delete) {
                eventHandler.deleteSelection();
            } else if (e.getSource() == invertSelection) {
                eventHandler.invertSelection();
            }
        }

    }
}
