import java.util.HashMap;
import java.util.Observable;

public abstract class Entite extends Observable {
    public abstract Point Se_deplacer_vers(Case origine, Case suivante, HashMap<Case,Point> map);
}
