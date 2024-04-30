import java.util.HashMap;

public class Bloc extends Entite{
    @Override
    public Point Se_deplacer_vers(Case origine, Case suivante, HashMap<Case,Point> map){
        if (suivante.entrer()){
            map.remove(origine);
            map.remove(suivante);
            origine.entite = null;
            suivante.entite = this;
            map.put(origine,origine.p);
            map.put(suivante,suivante.p);
            System.out.println("passe");
            setChanged();
            notifyObservers();
            return suivante.p;
        }
        return origine.p;
    }
}
