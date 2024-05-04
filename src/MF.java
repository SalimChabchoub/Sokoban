import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class MF extends JFrame implements Observer {

    Jeu J;
    JPanel[][] tabC;

    public MF(Jeu J) {
        this.J = J;
        this.tabC = new JPanel[J.L][J.H];
        build();
        addEC();
    }

    public void build() {
        J.initialiseGrille();
        Case c;
        JPanel jp = new JPanel(new BorderLayout());
        JPanel jpC = new JPanel(new GridLayout(J.L, J.H));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                } else if (c.entite instanceof Hero) {
                    tabC[i][j].setBackground(Color.BLACK);
                    c.entite.addObserver(this);
                } else if (c.entite instanceof Bloc) {
                    tabC[i][j].setBackground(Color.red);
                } else {
                    tabC[i][j].setBackground(Color.white);
                }
                tabC[i][j].setBorder(blackline);
                jpC.add(tabC[i][j]);
            }
        }
        this.setVisible(true);
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
                    tabC[i][j].setBackground(Color.BLACK);
                }
                if (c.entite instanceof Bloc) {
                    tabC[i][j].setBackground(Color.RED);
                }
            }
        }
        if (J.dectecteVictoire()) {
            System.out.println("partie termine");
        }
    }
}
