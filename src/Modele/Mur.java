package Modele;

public class Mur extends Case{
    public Mur(Point p) {
        super(p);
    }

    @Override
    public boolean entrer() {
        return false;
    }
}
