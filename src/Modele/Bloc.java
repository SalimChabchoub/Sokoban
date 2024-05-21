package Modele;

import java.util.HashMap;

public class Bloc extends Entite {
    @Override
    public Point Se_deplacer_vers(Case origine, Case suivante, HashMap<Case, Point> map, Direction d) {
        //Si la case suivante le permette
        if (suivante.entrer()) {
            //la case mère passe son entité à la case suivante
            map.remove(origine);
            map.remove(suivante);
            origine.entite = null;
            suivante.entite = this;
            map.put(origine, origine.p);
            map.put(suivante, suivante.p);
            setChanged();
            notifyObservers();
            return suivante.p;
        }
        return origine.p;
    }
}
