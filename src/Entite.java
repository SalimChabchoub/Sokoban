import java.util.HashMap;

public abstract class Entite {
    Case c;

    public Entite(Case c) {
        this.c =c;
    }

    public abstract HashMap<Case,Point>  Se_deplacer_vers(Case suivante, Direction d, int h, int l, HashMap<Case,Point> map);
}
