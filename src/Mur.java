import java.util.HashMap;

public class Mur extends Case{
    public Mur(Point p) {
        super(p);
    }

    @Override
    public boolean entrer( Direction d) {
        return false;
    }
}
