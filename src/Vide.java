import java.util.HashMap;

public class Vide extends Case{

    public Vide(Point p) {
        super(p);
    }

    @Override
    public boolean entrer(Direction d){
        return true;
//        if (this.entite != null){
//            return true;
//        }
//        return false;
    }
}
