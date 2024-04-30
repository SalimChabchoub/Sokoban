import java.util.HashMap;

public class Hero extends Entite{

    public Hero(Case c) {
        super(c);
    }

//    public void pousser(Case suivante,Direction d){
//        if(suivante.entrer())
//    }
    @Override
    public HashMap<Case,Point> Se_deplacer_vers(Case suivante, Direction d, int h, int l, HashMap<Case,Point> map){
        if (suivante.entrer(d)){
//            if (suivante.entite instanceof Bloc){
//
//                suivante.entite.Se_deplacer_vers();
//            }
            map.remove(this.c);
            map.remove(suivante);
            //this.c.entite = null;
            Case ancienne = new Vide(this.c.p);
            //suivante.entite = this;
            map.put(ancienne,this.c.p);
            this.c.move(d,h,l);
            //c.entite = new Hero(suivante);
            map.put(this.c,this.c.p);
            System.out.println((this.c.entite == null) + " fact");
        }
        return map;
    }
}
