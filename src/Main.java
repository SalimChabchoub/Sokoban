import javax.swing.*;
import java.io.InputStream;
import java.util.logging.Level;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Point p = new Point(1, 2);
        Point pos_bloc = new Point(8, 8);
        Jeu grille = new Jeu(p, pos_bloc);
        MF mf = new MF(grille);
        mf.setVisible(true);
    }
}