import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private BufferedImage imageToDisplay;
    private BufferedImage barrelImage;
    private BufferedImage targetImage;
    private ImageIcon animatedIcon;
    private BufferedImage wallLeftImage;
    private ImageIcon wallTopImage;
    private BufferedImage wallRightImage;
    private BufferedImage wallBottomImage;
    public void setImageToDisplay(BufferedImage image) {
        this.imageToDisplay = image;
    }
    public void setImageToDisplay(ImageIcon icon) {
        this.animatedIcon = icon;
    }
    public void setBarrelImage(BufferedImage barrelImage) {
        this.barrelImage = barrelImage;
    }
    public void setWallTopImage(ImageIcon wallTopImage) {
        this.wallTopImage = wallTopImage;
    }

    public void setWallRightImage(BufferedImage wallRightImage) {
        this.wallRightImage = wallRightImage;
    }

    public void setWallLeftImage(BufferedImage wallLeftImage) {
        this.wallLeftImage = wallLeftImage;
    }
    public void setWallBottomImage(BufferedImage wallBottomImage) {
        this.wallBottomImage = wallBottomImage;
    }
    public void setTargetImage(BufferedImage targetImage) {
        this.targetImage = targetImage;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        if (imageToDisplay != null) {
            g.drawImage(imageToDisplay, 0, 0, panelWidth, panelHeight, null);
        }
        if (animatedIcon != null) {
            g.drawImage(animatedIcon.getImage(), 0, 0, panelWidth, panelHeight, this);
        }
        if (wallTopImage != null) {
            g.drawImage(wallTopImage.getImage(), 0, 0, panelWidth, panelHeight, this);
        }
        if (targetImage != null) {
            int imageWidth = targetImage.getWidth();
            int imageHeight = targetImage.getHeight();

            double scaleX = (double) panelWidth / imageWidth;
            double scaleY = (double) panelHeight / imageHeight;
            double scale = Math.min(scaleX, scaleY)*0.75;

            int newWidth = (int) (imageWidth * scale);
            int newHeight = (int) (imageHeight * scale);

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newWidth) / 2;
            g.drawImage(targetImage, x, y, newWidth, newHeight, null);
        }
        if (barrelImage != null) {
            int imageWidth = barrelImage.getWidth();
            int imageHeight = barrelImage.getHeight();

            double scaleX = (double) panelWidth / imageWidth;
            double scaleY = (double) panelHeight / imageHeight;
            double scale = Math.min(scaleX, scaleY)*0.75;

            int newWidth = (int) (imageWidth * scale);
            int newHeight = (int) (imageHeight * scale);

            int x = (panelWidth - newWidth) / 2;
            int y = (panelHeight - newWidth) / 2;
            g.drawImage(barrelImage, x, y, newWidth, newHeight, null);
        }
        if (wallRightImage != null) {
            g.drawImage(wallRightImage, 0, 0, panelWidth, panelHeight, null);
        }
        if (wallLeftImage != null) {
            g.drawImage(wallLeftImage, 0, 0, panelWidth, panelHeight, null);
        }
        if (wallBottomImage != null) {
            g.drawImage(wallBottomImage, 0, 0, panelWidth, panelHeight, null);
        }
    }
}