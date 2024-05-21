import Modele.Jeu;
import View_Controller.MF;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        MF mf = new MF(jeu);
        mf.setVisible(true);
    }
}