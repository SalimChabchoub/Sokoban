package View_Controller;

import Modele.Jeu;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Character {
    private int row;
    private int col;
    private CharacterAnimationPanel characterPanel;
    private boolean MoveOn = true;
    private int characterSpeed = 9;

    public Character(int initialRow, int initialCol, CharacterAnimationPanel panel) {
        this.characterPanel = panel;
        setCharacterPosition(initialRow, initialCol);
    }

    public void setCharacterPosition(int row, int col) {
        this.row = row;
        this.col = col;
        int targetX = col * characterPanel.getWidth();
        int targetY = row * characterPanel.getHeight();
        characterPanel.setLocation(targetX, targetY);
    }

    public void moveTo(int row, int col,Jeu J) {
        this.row = row;
        this.col = col;
        animateMovement(new AnimationCallback() {
            @Override
            public void animationFinished() {
                J.setAttendHero(false);
            }
        });
    }


    private void animateMovement(AnimationCallback callback) {
            int targetX = col * characterPanel.getWidth();
            int targetY = row * characterPanel.getHeight();
            Timer timer = new Timer(35, new ActionListener() {
                int currentX = characterPanel.getX();
                int currentY = characterPanel.getY();
                int dx = Integer.compare(targetX, currentX) * characterSpeed;
                int dy = Integer.compare(targetY, currentY) * characterSpeed;

                @Override
                public void actionPerformed(ActionEvent e) {
                    characterPanel.setMoving(true);
                    currentX += dx;
                    if (dx > 0) {
                        characterPanel.setCurrentAnimation(3);
                    } else if (dx < 0) {
                        characterPanel.setCurrentAnimation(1);
                    }
                    if ((dx > 0 && currentX >= targetX) || (dx < 0 && currentX <= targetX)) {
                        currentX = targetX;
                    }
                    if (currentX == targetX) {
                        currentY += dy;
                        if (dy > 0) {
                            characterPanel.setCurrentAnimation(2);
                        } else if (dy < 0) {
                            characterPanel.setCurrentAnimation(0);
                        }
                        if ((dy > 0 && currentY >= targetY) || (dy < 0 && currentY <= targetY)) {
                            currentY = targetY;
                        }
                    }
                    characterPanel.setLocation(currentX, currentY);
                    characterPanel.getParent().revalidate();
                    characterPanel.getParent().repaint();

                    if (currentX == targetX && currentY == targetY) {
                        ((Timer) e.getSource()).stop();
                        characterPanel.setMoving(false);
                        callback.animationFinished();
                        System.out.println("out");
                    }
                }

            });
            timer.start();
        }


    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
