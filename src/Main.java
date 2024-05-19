import Modele.Jeu;
import Modele.Level;
import View_Controller.LevelLector;
import View_Controller.MF;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Jeu grille = new Jeu();
        LevelLector.readLevel("src/View_Controller/Levels.txt", grille);
        System.out.println(grille.H);
        System.out.println(grille.L);
        MF mf = new MF(grille);
        mf.setVisible(true);
    }
}