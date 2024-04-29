import java.util.HashMap;

public class Hero extends Entite{

    public Hero(Case c) {
        super(c);
    }
    @Override
    public void Se_deplacer_vers(Case suivante, Direction d, int h, int l, HashMap<Case,Point> map){
        if (suivante.entrer(d)){
            map.remove(this.c);
            map.remove(suivante);
            this.c.entite = null;
            suivante.entite = this;
            map.put(c,this.c.p);
            c.move(d,h,l);
            c.entite = new Hero(suivante);
            map.put(suivante,suivante.p);
        }
    }
}
