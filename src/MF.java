import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

import static java.lang.Thread.sleep;

public class MF extends JFrame implements Observer {

    Jeu J;
    ImagePanel[][] tabC;

    public MF(Jeu J) {
        this.J = J;
        this.tabC = new ImagePanel[J.L][J.H];
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                build();
                addEC();
            }

        });
    }

    public void build() {
        J.initialiseGrille();
        Case c;
        JPanel jp = new JPanel(new BorderLayout());
        JPanel jpC = new JPanel(new GridLayout(J.L, J.H));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.add(jpC, BorderLayout.CENTER);
        setTitle("Sobokan");
        add(jp);
        for (int i = 0; i < J.H; i++) {
            for (int j = 0; j < J.L; j++) {
                Point point = new Point(j, i);
                tabC[i][j] = new ImagePanel();
                c = J.trouveCase(point);
                if (c instanceof Mur) {
                    tabC[i][j].setImageToDisplay(ImageLoader.wallImage);
                } else {
                    tabC[i][j].setImageToDisplay(ImageLoader.groundImage);
                    if(J.trouveCase(new Point(j, i-1)) instanceof Mur){
                        tabC[i][j].setWallTopImage(ImageLoader.wallTop);
                    }
                    if(J.trouveCase(new Point(j-1, i)) instanceof Mur){
                        tabC[i][j].setWallLeftImage(ImageLoader.wallLeft);
                    }
                    if(J.trouveCase(new Point(j+1, i)) instanceof Mur){
                        tabC[i][j].setWallRightImage(ImageLoader.wallRight);
                    }
                    if(J.trouveCase(new Point(j, i+1)) instanceof Mur){
                        tabC[i][j].setWallBottomImage(ImageLoader.wallBottom);
                    }
                }
                if (c instanceof Target) {
                    tabC[i][j].setTargetImage(ImageLoader.targetImage);
                } else if (c.entite instanceof Hero) {
                    tabC[i][j].setBarrelImage(ImageLoader.tempCharacter);
                    c.entite.addObserver(this);
                } else if (c.entite instanceof Bloc) {
                    tabC[i][j].setBarrelImage(ImageLoader.barrelImage);
                }
                jpC.add(tabC[i][j]);
            }
        }
        this.setVisible(true);
        setSize(J.L * 50, J.H * 50);
        repaint();
    }


    public void addEC() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        J.deplacerHero(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        J.deplacerHero(Direction.DOWN);
                        break;
                    case KeyEvent.VK_RIGHT:
                        J.deplacerHero(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_LEFT:
                        J.deplacerHero(Direction.LEFT);
                        break;
                }
            }

        });
        requestFocus();
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Point p;
                Case c;
                for (int i = 0; i < J.H; i++) {
                    for (int j = 0; j < J.L; j++) {
                        p = new Point(j, i);
                        c = J.trouveCase(p);
                        if (c.entite instanceof Hero) {
                            tabC[i][j].setBarrelImage(ImageLoader.tempCharacter);
                        } else if (c.entite instanceof Bloc) {
                            tabC[i][j].setBarrelImage(ImageLoader.barrelImage);
                        } else {
                            tabC[i][j].setBarrelImage(null);
                        }
                    }
                }
                repaint();
                if (J.dectecteVictoire()) {
                    System.out.println("partie termine");
                }
            }
        });
    }
}
