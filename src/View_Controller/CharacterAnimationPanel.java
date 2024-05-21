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
    public String hero;
    boolean isMoving=false;

    public CharacterAnimationPanel(String hero) {
        this.hero = hero;
        try {
            spriteSheet = ImageIO.read(new File("Ressources/"+this.hero+".png"));
            poseWidth = spriteSheet.getWidth() / 8;
            poseHeight = spriteSheet.getHeight() / 4;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Timer timer = new Timer(50, e -> {
            // changement de mouvement chaque 50 ms
            if (isMoving) {
                //La derniere pose ne sert pas à grandchose
                currentPose = (currentPose + 1) % 7;
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Calcul du scale pour agrandir ou réduire la taille de l'héros sur son panel
        double scaleFactor = Math.min(getWidth() / (double) poseWidth, getHeight() / (double) poseHeight)*3;
        int scaledWidth = (int)(poseWidth * scaleFactor);
        int scaledHeight = (int)(poseHeight * scaleFactor);

        //Bien centré l'héros
        int x = (getWidth() - scaledWidth) / 2;
        int y = (getHeight() - scaledHeight) / 2;

        g.drawImage(spriteSheet, x, y, x + scaledWidth, y + scaledHeight,
                currentPose * poseWidth, currentAnimation * poseHeight,
                (currentPose + 1) * poseWidth, (currentAnimation + 1) * poseHeight, this);
    }


    //Bouton On/Off de l'animation
    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    //Permettre de choisir que quelle direction va notre héros se diriger
    public void setCurrentAnimation(int animation){currentAnimation=animation;}
}
