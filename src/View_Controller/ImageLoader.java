package View_Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static BufferedImage groundImage;
    public static ImageIcon wallImage;
    public static BufferedImage barrelImage;
    public static BufferedImage targetImage;
    public static BufferedImage wallRight;
    public static ImageIcon wallTop;
    public static BufferedImage wallLeft;
    public static BufferedImage wallBottom;
    public static BufferedImage tempCharacter;

    static {
        try {
            groundImage = loadImage("ground.png");
            wallImage = loadAnimatedImage("wall1.gif");
            barrelImage = loadImage("barrel.png");
            targetImage = loadImage("barrelTarget2.png");
            wallRight = loadImage("wallRight.png");
            wallTop = loadAnimatedImage("wallTop.gif");
            wallLeft = loadImage("wallLeft.png");
            tempCharacter = loadImage("character.png");
            wallBottom = loadImage("wallBottom.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage loadImage(String filename) throws IOException {
        URL resource = ImageLoader.class.getClassLoader().getResource(filename);
        if (resource == null) {
            throw new IOException("Resource not found: " + filename);
        }
        return ImageIO.read(resource);
    }

    private static ImageIcon loadAnimatedImage(String filename) throws IOException {
        URL resource = ImageLoader.class.getClassLoader().getResource(filename);
        if (resource == null) {
            throw new IOException("Resource not found: " + filename);
        }
        return new ImageIcon(resource);
    }
}
