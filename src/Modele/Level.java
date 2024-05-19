package Modele;

public class Level {


    public void addWall(int i, int j, Jeu jeu) {
        Point p = new Point(j, i);
        Mur m = new Mur(p);
        jeu.Map.put(m, p);
    }

    public void addPlayer(int i, int j, Jeu jeu) {
        Point p = new Point(j, i);
        Vide v = new Vide(p);
        v.entite = new Hero();
        jeu.pHeros = p;
        jeu.Map.put(v, p);

    }

    public void addBox(int i, int j, Jeu jeu) {
        Point p = new Point(j, i);
        Vide v = new Vide(p);
        v.entite = new Bloc();
        jeu.Map.put(v, p);
    }

    public void addTarget(int i, int j, Jeu jeu) {
        Point p = new Point(j, i);
        Vide v = new Target(p);
        v.entite = null;
        jeu.Map.put(v, p);

    }

    public void addVide(int i, int j, Jeu jeu) {
        Point p = new Point(j, i);
        Vide v = new Vide(p);
        v.entite = null;
        jeu.Map.put(v, p);
    }


}
