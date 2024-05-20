package Modele;

import View_Controller.LevelLector;

import java.util.HashMap;
import java.util.Observable;
import java.util.Map.Entry;


public class Jeu extends Observable {
    public Point pHeros;
    public int L, H;
    boolean attendHero = false;
    public HashMap<Case, Point> Map = new HashMap<>();


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

    public void deplacerHero(Direction d) {
        if (!attendHero) {
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
    }

    public void setAttendHero(boolean value) {
        attendHero = value;
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


    public Jeu copyJeu(Jeu original) {
        Jeu Copy = new Jeu();
        Copy.L = original.L;
        Copy.H = original.H;
        Copy.pHeros = new Point(original.pHeros.x, original.pHeros.y);
        Copy.Map = new HashMap<>();
        for (Case c : original.Map.keySet()) {
            Copy.Map.put(c, new Point(c.p.x, c.p.y));
        }
        return Copy;
    }
}
