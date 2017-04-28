package paint;

import java.awt.EventQueue;

/**
 * @author Moses Muigai Gitau
 */
public class Paint {

    public Paint() {
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }

}
