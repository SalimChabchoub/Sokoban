import java.util.HashMap;
import java.util.Map;

public class Hero extends Entite{
    @Override
    public Point Se_deplacer_vers(Case caseHero, Case suivante, HashMap<Case,Point> map){
        if(suivante.entite instanceof Bloc){
            pousser(suivante,map);
        }
        if (suivante.entrer()){
            map.remove(caseHero);
            map.remove(suivante);
            caseHero.entite = null;
            suivante.entite = this;
            map.put(caseHero,caseHero.p);
            map.put(suivante,suivante.p);
            System.out.println("passe");
            setChanged();
            notifyObservers();
            return suivante.p;
        }
        return caseHero.p;
    }
    public void pousser(Case elementApousser, HashMap<Case,Point> Map){
        Point p = new Point(elementApousser.p.x,elementApousser.p.y-1);
        for (Map.Entry<Case,Point> entry: Map.entrySet()){
            if (entry.getValue().equals(p)){
                Case suivante = entry.getKey();
                elementApousser.entite.Se_deplacer_vers(elementApousser,suivante,Map);
                break;
            }
        }
    }
}
