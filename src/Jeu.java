import java.awt.*;
import java.util.HashMap;
import java.util.Observable;
import java.util.Map.Entry;


public class Jeu extends Observable {
    Point pHeros;
    Point pBloc;

    int nbbloc = 2;
    final int L = 15, H = 15;

    HashMap<Case, Point> Map;


    public Jeu() {
        Point p = new Point(1, 2);
        Point pos_bloc = new Point(8, 8);
        this.pHeros = p;
        this.pBloc = pos_bloc;
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

    public void initialiseGrille() {
        this.Map = new HashMap<>();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < L; j++) {
                Point point = new Point(j, i);
                if (i == 0 || j == 0 || j == L - 1 || i == H - 1 || (j== 1 && i == H/2) || (i== 1 && j == L/2) || (i== 2 && j == L/2)) {
                    Mur m = new Mur(point);
                    Map.put(m, point);

                } else if ((i == 10 && j == 10) || (i == 13 && j == 13)) {
                    Vide v = new Target(point);
                    v.entite = null;
                    Map.put(v, point);
                } else {
                    Vide v = new Vide(point);
                    if (v.p.equals(pHeros)) {
                        v.entite = new Hero();
                        //v.entite.addObserver(this);
                    } else if (v.p.equals(pBloc) || (i == 2 && j == 10)) {
                        v.entite = new Bloc();
                    } else {
                        v.entite = null;
                    }
                    Map.put(v, point);

                }
            }
        }
    }

    public void deplacerHero(Direction d) {
        Point p;
        Case suivante;
        Case caseHero = trouveCase(pHeros);
        Hero notreHero = (Hero) caseHero.entite;
        if (d == Direction.UP) {
            p = new Point(pHeros.x, (pHeros.y + H - 1) % H);
            suivante = trouveCase(p);
            pHeros = notreHero.Se_deplacer_vers(caseHero, suivante, Map, Direction.UP);
        } else if (d == Direction.DOWN) {
            p = new Point(pHeros.x, (pHeros.y + H + 1) % H);
            suivante = trouveCase(p);
            pHeros = notreHero.Se_deplacer_vers(caseHero, suivante, Map, Direction.DOWN);
        } else if (d == Direction.LEFT) {
            p = new Point((pHeros.x + L - 1) % L, pHeros.y);
            suivante = trouveCase(p);
            pHeros = notreHero.Se_deplacer_vers(caseHero, suivante, Map, Direction.LEFT);

        } else {
            p = new Point((pHeros.x + L + 1) % L, pHeros.y);
            suivante = trouveCase(p);
            pHeros = notreHero.Se_deplacer_vers(caseHero, suivante, Map, Direction.RIGHT);
        }
        setChanged();
        notifyObservers();
    }

    public boolean dectecteVictoire() {
        int nbcible = 0;
        Case c;
        for (Entry<Case, Point> entry : this.Map.entrySet()) {
            c = entry.getKey();
            if (c instanceof Target) {
                if (!(c.entite instanceof Bloc))
                    nbcible += 1;
            }
        }

        if (nbcible == 0) {
            System.out.println("partie termine");
            return true;
        }
        return false;
    }
}
