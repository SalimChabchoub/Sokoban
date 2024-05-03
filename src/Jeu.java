import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;


public class Jeu extends JFrame implements Observer {
    Point pHeros;
    Point pBloc;

    int nbbloc =2;
    final int L = 15, H = 15;

    HashMap<Case, Point> Map;
    JPanel[][] tabC = new JPanel[L][H];

    public Jeu(Point pHeros, Point pBloc) {
        this.pHeros = pHeros;
        this.pBloc = pBloc;
        build();
        addEC();
    }

    public Point trouvePoint(Case c) {
        return this.Map.get(c);
    }

    public Case trouveCase(Point p) {
        for (Entry<Case, Point> entry : this.Map.entrySet()) {
            if (entry.getValue().equals(p)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void build() {
        Map = new HashMap<>();
        JPanel jp = new JPanel(new BorderLayout());
        JPanel jpC = new JPanel(new GridLayout(L, H));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.add(jpC, BorderLayout.CENTER);

        setTitle("Sobokan");
        setSize(L*25, H*25);
        Border blackline = BorderFactory.createLineBorder(Color.black, 1);
        add(jp);
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < L; j++) {
                Point point = new Point(j, i);
                tabC[i][j] = new JPanel();
                if (i == 0 || j == 0 || j == L - 1 || i == H - 1) {
                    Mur m = new Mur(point);
                    Map.put(m, point);
                    tabC[i][j].setBackground(Color.orange);
                }else if ((i == 10 && j == 10) ||(i == 13 && j == 13)) {
                    Vide v = new Target(point);
                    v.entite = null;
                    tabC[i][j].setBackground(Color.GREEN);
                    Map.put(v,point);
                }  else {
                    Vide v = new Vide(point);
                    if (v.p.equals(pHeros)) {
                        tabC[i][j].setBackground(Color.BLACK);
                        v.entite = new Hero();
                        v.entite.addObserver(this);
                    } else if (v.p.equals(pBloc) || (i == 2 && j == 10)) {
                        tabC[i][j].setBackground(Color.red);
                        v.entite = new Bloc();
                    } else {
                        tabC[i][j].setBackground(Color.white);
                        v.entite = null;
                    }
                    Map.put(v, point);

                }
                tabC[i][j].setBorder(blackline);
                jpC.add(tabC[i][j]);
            }
        }
        //jp.setBorder(blackline);
        this.setVisible(true);

    }

    public void addEC() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Point p;
                Case suivante;
                Case caseHero = trouveCase(pHeros);
                Hero notreHero = (Hero)caseHero.entite;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        p = new Point(pHeros.x, (pHeros.y+H - 1)%H);
                        suivante = trouveCase(p);
                        pHeros = notreHero.Se_deplacer_vers(caseHero,suivante, Map, Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        p = new Point(pHeros.x, (pHeros.y+H + 1)%H);
                        suivante = trouveCase(p);
                        pHeros = notreHero.Se_deplacer_vers(caseHero, suivante, Map,Direction.DOWN);
                        break;
                    case KeyEvent.VK_RIGHT:
                        p = new Point((pHeros.x+L+1)%L, pHeros.y);
                        suivante = trouveCase(p);
                        pHeros = notreHero.Se_deplacer_vers(caseHero, suivante,Map,Direction.RIGHT);
                        break;
                    case KeyEvent.VK_LEFT:
                        p = new Point((pHeros.x+L-1)%L, pHeros.y);
                        suivante = trouveCase(p);
                        pHeros = notreHero.Se_deplacer_vers(caseHero,suivante, Map,Direction.LEFT);
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
        int nbcible =0; //nombre de cible atteint
//        System.out.println("affiche");
        for (int i = 0; i <H; i++) {
            for (int j = 0; j < L; j++) {
                p = new Point(j, i);
                c = trouveCase(p);
                if (c instanceof Mur) {
                    tabC[i][j].setBackground(Color.orange);
                } if (c instanceof Vide) {
                    tabC[i][j].setBackground(Color.white);
                }if(c instanceof  Target){
                    if (!(c.entite instanceof Bloc))
                        nbcible+=1;
                    tabC[i][j].setBackground(Color.GREEN);
                }
                if (c.entite instanceof Hero){
                    tabC[i][j].setBackground(Color.BLACK);
                }
                if (c.entite instanceof Bloc){
                    tabC[i][j].setBackground(Color.RED);
                }
            }
        }
        if (nbcible == 0){
            System.out.println("partie termine");
        }
    }
}
