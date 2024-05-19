package View_Controller;

import Modele.*;
import Modele.Point;
import Modele.Jeu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MF extends JFrame implements Observer {

    Jeu J;

    JPanel[][] tabC;

    public MF(Jeu J) {
        this.J = J;
        tabC = new JPanel[J.H][J.L];
        build();
        addEC();
    }

    public void build() {
        Case c;
        JPanel jp = new JPanel(new BorderLayout());
        JPanel jpC = new JPanel(new GridLayout(J.H, J.L));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton restartButton = new JButton("Reset");
        jp.add(restartButton, BorderLayout.BEFORE_FIRST_LINE);
        jp.add(jpC, BorderLayout.CENTER);

        setTitle("Sobokan");
        setSize(J.L * 25, J.H * 25);
        Border blackline = BorderFactory.createLineBorder(Color.black, 1);
        add(jp);
        for (int i = 0; i < J.H; i++) {
            for (int j = 0; j < J.L; j++) {
                Point point = new Point(j, i);
                tabC[i][j] = new JPanel();
                c = J.trouveCase(point);
                if (c instanceof Mur) {
                    tabC[i][j].setBackground(Color.orange);
                } else if (c instanceof Target) {
                    tabC[i][j].setBackground(Color.GREEN);

                } else if (c instanceof Vide) {
                    if (c.entite instanceof Hero) {
                        tabC[i][j].setBackground(Color.BLACK);
                        c.entite.addObserver(this);
                    } else if (c.entite instanceof Bloc) {
                        tabC[i][j].setBackground(Color.red);
                    } else {
                        tabC[i][j].setBackground(Color.white);
                    }
                }
                tabC[i][j].setBorder(blackline);
                jpC.add(tabC[i][j]);
            }
        }
        this.setVisible(true);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                J = new Jeu();
                LevelLector.readLevel("src/View_Controller/Levels.txt", J);
                getContentPane().removeAll();
                tabC = new JPanel[J.H][J.L];
                build();
                MF.this.requestFocus();
            }
        });
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
        Point p;
        Case c;
        for (int i = 0; i < J.H; i++) {
            for (int j = 0; j < J.L; j++) {
                p = new Point(j, i);
                c = J.trouveCase(p);
                if (c instanceof Mur) {
                    tabC[i][j].setBackground(Color.orange);
                }
                if (c instanceof Vide) {
                    tabC[i][j].setBackground(Color.white);
                }
                if (c instanceof Target) {
                    tabC[i][j].setBackground(Color.GREEN);
                }
                if (c.entite instanceof Hero) {
                    System.out.println(J.pHeros.x + " " + J.pHeros.y);
                    tabC[i][j].setBackground(Color.BLACK);
                }
                if (c.entite instanceof Bloc) {
                    tabC[i][j].setBackground(Color.RED);
                }
            }
        }
        if (J.dectecteVictoire()) {
            System.out.println("partie termine");
            JLabel label = new JLabel("<html><center> Victory !<br> Level Completed !<br><img width = '100' height = '100' src ='" + getClass().getResource("zoro.jpg") + "'></center></html>");
            int result = JOptionPane.showOptionDialog(this, label, "Victoire", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"OK"}, "OK");
            if (result == JOptionPane.OK_OPTION)
                dispose();
        }
    }
}
