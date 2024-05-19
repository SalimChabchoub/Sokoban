package View_Controller;

import Modele.Jeu;
import Modele.Level;

import java.io.*;
import java.util.Scanner;

public class LevelLector {

    public static Level readLevel(String fichier, Jeu jeu) {
        Level lev = new Level();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                if (ligne.charAt(0) == ';')
                    break;

                for (int j = 0; j < ligne.length(); j++) {
                    switch (ligne.charAt(j)) {
                        case '#':
                            lev.addWall(i, j, jeu);
                            break;
                        case ' ':
                            lev.addVide(i, j, jeu);
                            break;
                        case '@':
                            lev.addPlayer(i, j, jeu);
                            break;
                        case '$':
                            lev.addBox(i, j, jeu);
                            break;
                        case '.':
                            lev.addTarget(i, j, jeu);
                            break;
                        default:
                            System.err.println("caractere Invalide !");
                            System.exit(1);
                    }
                }
                i++;
                jeu.L = ligne.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        jeu.H = i;
        return lev;
    }
}
