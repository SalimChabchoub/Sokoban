import java.util.HashMap;

public class Bloc extends Entite{
    public Bloc(Case c){
        super(c);
    }

    @Override
    public HashMap<Case,Point> Se_deplacer_vers(Case suivante, Direction d, int h, int l, HashMap<Case,Point> map) {
        return map;
    }
}
