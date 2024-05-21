package Modele;

import java.util.HashMap;
import java.util.Observable;
import java.util.Map.Entry;


public class Jeu extends Observable {
    public Point pHeros;
    public int L, H;
    boolean attendHero = false;
    public HashMap<Case, Point> Map = new HashMap<>();
    public int ligne=0;

    public Jeu(){
        this.ligne = LevelLector.readLevel(this, 0);
    }
    public void loadnextlevel(int resultat){
        Map.clear();
        if (resultat == 1) {
            ligne++;
            ligne = LevelLector.readLevel(this, ligne);
        } else {
            ligne = LevelLector.readLevel(this, ligne - H);
        }
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
            return true;
        }
        return false;
    }
}
