package imageediting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 * @author Moses Muigai Gitau
 */
public class ImageManip {

    ImageIcon icon = new ImageIcon(getClass().getResource("../images/resize.png"));

    public ImageManip() {
    }

    public static BufferedImage grayScale(BufferedImage original) {
        int alpha, red, green, blue;
        int newPixel;
        BufferedImage grayScale = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        int[] avgLUT = new int[766];
        for (int i = 0; i < avgLUT.length; i++) {
            avgLUT[i] = (int) (i / 3);
        }
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();
                newPixel = red + green + blue;
                newPixel = avgLUT[newPixel];
                newPixel = new Color(newPixel, newPixel, newPixel, alpha).getRGB();
                grayScale.setRGB(i, j, newPixel);
            }
        }
        return grayScale;
    }

    public static BufferedImage changeColor(ImageIcon imageIcon, Color from, Color to) {
        BufferedImage initial = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = initial.createGraphics();
        imageIcon.paintIcon(null, g, 0, 0);
        g.dispose();
        for (int i = 0; i < imageIcon.getIconWidth(); i++) {
            for (int j = 0; j < imageIcon.getIconHeight(); j++) {
                Color c = new Color(initial.getRGB(i, j));
                if (c.equals(from)) {
                    initial.setRGB(i, j, to.getRGB());
                }
            }
        }
        return initial;
    }

    public static BufferedImage makeTransparent(ImageIcon imageIcon, Color from) {
        Color to = new Color(from.getRed(), from.getGreen(), from.getBlue(), 0);
        return changeColor(imageIcon, from, to);
    }

    /**
     * Removes gray background from application's icons.
     *
     * @param imageIcon
     * @return
     */
    public static BufferedImage removeGray(ImageIcon imageIcon) {
        Color from = new Color(128, 128, 128, 255);
        return makeTransparent(imageIcon, from);
    }

    public static BufferedImage resize(Image image, int width, int height) {
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = buffered.createGraphics();
        
        g.drawImage(image, 0, 0, width, height, null);
        return buffered;
    }
}
