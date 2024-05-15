import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CharacterAnimationPanel extends JPanel {
    private BufferedImage spriteSheet;
    private int currentPose = 0;
    private int currentAnimation = 1;
    private int poseWidth;
    private int poseHeight;
    boolean isMoving;

    public CharacterAnimationPanel() {
        try {
            // Load the sprite sheet
            spriteSheet = ImageIO.read(new File("Ressources/test_walk.png"));
            poseWidth = spriteSheet.getWidth() / 8; // 8 pose par lignes
            poseHeight = spriteSheet.getHeight() / 4; // 4 ligne
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer(100, e -> {
            if (isMoving) {
                currentPose = (currentPose + 1) % 7; // Y a que 7 pose qui nous intérésse
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessine cette pose actuel
        int x = (getWidth() - poseWidth) / 2;
        int y = (getHeight() - poseHeight) / 2;
        g.drawImage(spriteSheet, x, y, (x + poseWidth)*2, (y + poseHeight)*2,
                currentPose * poseWidth, currentAnimation * poseHeight,
                (currentPose + 1) * poseWidth, (currentAnimation + 1) * poseHeight, this);
    }
    public void setMoving(boolean moving) {
        isMoving = moving;
    }
}
