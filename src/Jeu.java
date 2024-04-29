import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;


public class Jeu extends JFrame implements Observer, Runnable {
    Hero h;
    Bloc b;
    final int L = 15,H = 15;

    HashMap<Case,Point> Map;
    JPanel[][] tabC = new JPanel[L][H];

    Thread gamethread;
    public Jeu(Hero h,Bloc b){
        this.h = h;
        this.b = b;
        build();
        addEC();
    }

    public Point trouvePoint(Case c){
        return this.Map.get(c);
    }

    public Case trouveCase (Point p){
        for (Entry<Case,Point> entry: this.Map.entrySet()){
            if (entry.getValue().equals(p)){
                return entry.getKey();
            }
        }
        return null;
    }

    public void build(){
        Map = new HashMap<>();
        JPanel jp = new JPanel(new BorderLayout());
        JPanel jpC = new JPanel(new GridLayout(L,H));
        /*JPanel jpInfo =  new JPanel(new BorderLayout());*/
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jp.add(jpC,BorderLayout.CENTER);

        setTitle("Ma première fenêtre");
        setSize(400, 400);
        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        add(jp);
        for (int i = 0 ; i<H; i++){
            for (int j = 0 ; j<L; j++){
                Point point = new Point(j,i);
                tabC[i][j] = new JPanel();
                if (i == 0 || j == 0){
                    Mur m = new Mur(point);
                    Map.put(m,point);
                    tabC[i][j].setBackground( Color.orange);
                }else {
                    Vide v = new Vide(point);
                    if (v.p.equals(h.c.p)){
                        tabC[i][j].setBackground(Color.BLACK);
                        v.entite = h;
                    } else if (v.p.equals(b.c.p)) {
                        tabC[i][j].setBackground(Color.red);
                        v.entite = b;
                    } else {tabC[i][j].setBackground( Color.white);}
                    Map.put(v,point);

                }
                tabC[i][j].setBorder(blackline);
                jpC.add(tabC[i][j]);
            }
        }
        jp.setBorder(blackline);
        this.setVisible(true);

    }
    public void addEC(){
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Point p;
                Case suivante;
                //tabC[h.c.p.y][h.c.p.x].setBackground(Color.white);
                switch (e.getKeyCode()){
                    case KeyEvent.VK_UP:
                        p = new Point(h.c.p.x,h.c.p.y-1);

                        suivante = trouveCase(p);
                        h.Se_deplacer_vers(suivante,Direction.UP,H,L,Map);
                        break;
                    case KeyEvent.VK_DOWN:
                        p = new Point(h.c.p.x,h.c.p.y+1);
                        suivante = trouveCase(p);
                        h.Se_deplacer_vers(suivante,Direction.DOWN,H,L,Map);
                        break;
                    case KeyEvent.VK_RIGHT:
                        p = new Point(h.c.p.x+1,h.c.p.y);
                        suivante = trouveCase(p);
                        h.Se_deplacer_vers(suivante,Direction.RIGHT,H,L,Map);
                        break;
                    case KeyEvent.VK_LEFT:
                        p = new Point(h.c.p.x-1,h.c.p.y);
                        suivante = trouveCase(p);
                        h.Se_deplacer_vers(suivante,Direction.LEFT,H,L,Map);
                        break;

                }
            }

        });
        requestFocus();
    }
    @Override
    public void run() {
        while (gamethread != null){
            System.out.println(h.c.p.x + "," +h.c.p.y);

        }
    }
    public void startGameThread(){
        gamethread = new Thread(this);
        gamethread.start();
    }



    @Override
    public void update(Observable o, Object arg) {
//        for (int i = 0 ; i<H; i++){
//            for (int j = 0 ; j<L; j++){
//                Point p = new Point(j,i);
//                Case ca = trouveCase(p);
//                if (trouveCase(p) instanceof Mur){
//                    //System.out.println(ca.p.x + "/" +ca.p.y);
//                    tabC[trouveCase(p).p.y][trouveCase(p).p.x].setBackground(Color.orange);
//                    this.setVisible(true);
//                }
//                if (trouveCase(p).entite == null){
//                    tabC[trouveCase(p).p.y][trouveCase(p).p.x].setBackground(Color.white);
//                }
//                if(p.equals(h.c.p)){
//                    tabC[h.c.p.y][h.c.p.x].setBackground(Color.BLACK);
//                }
//            }
//        }
//        for (Entry<Case,Point> entry :Map.entrySet()){
//            Case ca = entry.getKey();
//            if (ca.entite == null){
//                //tabC[ca.p.y][ca.p.x].setBackground(Color.white);
//            }
////            if(entry.getKey().p.equals(h.c.p)){
////                tabC[h.c.p.y][h.c.p.x].setBackground(Color.BLACK);
////            }
//
//        }
        Point p;
        Case c;
        for (int i =0 ;i<H;i++){
            for (int j =0;j<L;j++){
                p = new Point(i,j);

                c = trouveCase(p);
                if (c instanceof Mur){
                    tabC[i][j].setBackground(Color.orange);
                } else if (c instanceof Vide) {
                    tabC[i][j].setBackground(Color.white);
                }
            }
        }
        tabC[b.c.p.y][b.c.p.x].setBackground(Color.RED);
        tabC[h.c.p.y][h.c.p.x].setBackground(Color.BLACK);

    }
}
