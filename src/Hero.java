import java.util.HashMap;
import java.util.Map;

public class Hero extends Entite {
    @Override
    public Point Se_deplacer_vers(Case caseHero, Case suivante, HashMap<Case, Point> map, Direction d) {
        if (suivante.entite instanceof Bloc) {
            pousser(suivante, map, d);
        }
        if (suivante.entrer()) {
            map.remove(caseHero);
            map.remove(suivante);
            caseHero.entite = null;
            suivante.entite = this;
            map.put(caseHero, caseHero.p);
            map.put(suivante, suivante.p);
//            System.out.println("passe");
            setChanged();
            notifyObservers();
            return suivante.p;
        }
        return caseHero.p;
    }

    public void pousser(Case elementApousser, HashMap<Case, Point> Map, Direction d) {
        Point p;
        if (d == Direction.UP) {
            p = new Point(elementApousser.p.x, elementApousser.p.y - 1);
        } else if (d == Direction.DOWN) {
            p = new Point(elementApousser.p.x, elementApousser.p.y + 1);
        } else if (d == Direction.LEFT) {
            p = new Point(elementApousser.p.x - 1, elementApousser.p.y);
        } else {
            p = new Point(elementApousser.p.x + 1, elementApousser.p.y);
        }

        for (Map.Entry<Case, Point> entry : Map.entrySet()) {
            if (entry.getValue().equals(p)) {
                Case suivante = entry.getKey();
                elementApousser.entite.Se_deplacer_vers(elementApousser, suivante, Map, d);
                break;
            }
        }
    }
}
