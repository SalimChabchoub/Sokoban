package View_Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CharacterAnimationPanel extends JPanel {
    private BufferedImage spriteSheet;
    private int currentPose = 0;
    public int currentAnimation = 2;
    public int poseWidth;
    public int poseHeight;
    boolean isMoving=false;

    public CharacterAnimationPanel() {
        try {
            spriteSheet = ImageIO.read(new File("Ressources/test_walk.png"));
            poseWidth = spriteSheet.getWidth() / 8; // Assuming 8 poses per row
            poseHeight = spriteSheet.getHeight() / 4; // Assuming 4 rows of animations
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer(50, e -> {
            if (isMoving) {
                currentPose = (currentPose + 1) % 7; // Change pose every 100 milliseconds
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calculate the scaling factor to enlarge the character
        double scaleFactor = Math.min(getWidth() / (double) poseWidth, getHeight() / (double) poseHeight)*3;
        int scaledWidth = (int)(poseWidth * scaleFactor);
        int scaledHeight = (int)(poseHeight * scaleFactor);

        // Calculate the coordinates to keep the character centered
        int x = (getWidth() - scaledWidth) / 2;
        int y = (getHeight() - scaledHeight) / 2;

        g.drawImage(spriteSheet, x, y, x + scaledWidth, y + scaledHeight,
                currentPose * poseWidth, currentAnimation * poseHeight,
                (currentPose + 1) * poseWidth, (currentAnimation + 1) * poseHeight, this);
    }


    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    public void setCurrentAnimation(int animation){currentAnimation=animation;}
}
