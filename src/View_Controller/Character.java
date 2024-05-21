package View_Controller;

import Modele.Jeu;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Character {
    private int row;
    private int col;
    private CharacterAnimationPanel characterPanel;
    private int characterSpeed = 2;

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
            //Un call back pour relancer le calcul de la grid
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
                //On avance d'un sur 8 par cases toute les 35 ms
                int dx = Integer.compare(targetX, currentX) * (int)(characterPanel.getWidth()/8) * characterSpeed;
                int dy = Integer.compare(targetY, currentY) * (int)(characterPanel.getHeight()/8) *characterSpeed;

                @Override
                public void actionPerformed(ActionEvent e) {
                    characterPanel.setMoving(true);
                    currentX += dx;
                    if (dx > 0) {
                        //Vers la droite
                        characterPanel.setCurrentAnimation(3);
                    } else if (dx < 0) {
                        //Vers la gauche
                        characterPanel.setCurrentAnimation(1);
                    }
                    if ((dx > 0 && currentX >= targetX) || (dx < 0 && currentX <= targetX)) {
                        //Arrivé à destination horizontale
                        currentX = targetX;
                    }
                    //priorité donné au axe horizontale si l'utilisateur clique sur horizontale et verticale au meme moment
                    if (currentX == targetX) {
                        currentY += dy;
                        if (dy > 0) {
                            //Vers le bas
                            characterPanel.setCurrentAnimation(2);
                        } else if (dy < 0) {
                            //Vers le haut
                            characterPanel.setCurrentAnimation(0);
                        }
                        if ((dy > 0 && currentY >= targetY) || (dy < 0 && currentY <= targetY)) {
                            //Arrivé à destination verticale
                            currentY = targetY;
                        }
                    }
                    characterPanel.setLocation(currentX, currentY);
                    characterPanel.getParent().revalidate();
                    characterPanel.getParent().repaint();

                    if (currentX == targetX && currentY == targetY) {
                        //Arreter le Timer
                        ((Timer) e.getSource()).stop();
                        //Arreter l'animation
                        characterPanel.setMoving(false);
                        //Appelation d'un callback de finition pour relancer la gestion de la grid
                        callback.animationFinished();
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
