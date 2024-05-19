package Modele;

public class Target extends  Vide{

    public Target(Point p) {
        super(p);
    }

    public boolean atteint_cible(Case c){
        if (this.entite instanceof Bloc){
            return true;
        }
        return false;
    }
}
