package Modele;

public class Level {
    static final int Vide = 0;
    static final int Mur = 0;
    static final int joueur = 0;
    static final int Cible = 0;
    static final int Box = 0;


    int[][] grille;
    int l, c;

    String name;

    public Level() {
        grille = new int[1][1];
        l = c = 0;
    }

    private int adjust(int c, int i) {
        while (c <= i)
            c *= 2;
        return c;
    }

    private void resizeBoard(int l, int c) {
        int oldL = grille.length;
        int oldC = grille[0].length;

        if ((oldL <= l) || (oldC <= c)) {
            int newL = adjust(oldL, l);
            int newC = adjust(oldC, c);

            int[][] nGrille = new int[newL][newC];
            for (int i = 0; i < oldL; i++) {
                for (int j = 0; j < oldC; j++)
                    nGrille[i][j] = grille[i][j];

            }
            grille = nGrille;
        }
        if (this.l <= l)
            this.l = l + 1;
        if (this.c <= c)
            this.c = c + 1;
    }

    public void fixName(String nm) {
        name = nm;
    }

    public void emptyCell(int i, int j) {

    }

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

    public int lines() {
        return 0;
    }

    public int columns() {
        return 0;
    }

    public String name() {
        return name;
    }

    public boolean isEmpty(int i, int j) {
        return false;
    }

    public boolean hasWall(int i, int j) {
        return false;
    }

    public boolean hasTarget(int i, int j) {
        return false;
    }

    public boolean hasPlayer(int i, int j) {
        return false;
    }

    public boolean hasBox(int i, int j) {
        return false;
    }

}
