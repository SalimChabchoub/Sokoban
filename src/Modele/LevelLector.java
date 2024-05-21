package Modele;

import Modele.Jeu;
import Modele.Level;

import java.io.*;
import java.util.Scanner;

public class LevelLector {

    public static int readLevel(Jeu jeu, int debut) {
        Level lev = new Level();
        int i = 0;
        String fichier = "Ressources/Levels.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
//                if (ligne.isEmpty()){
//                    continue;
//                }
                if (i >= debut) {
                    if (ligne.charAt(0) == ';')
                        break;
                    for (int j = 0; j < ligne.length(); j++) {
                        switch (ligne.charAt(j)) {
                            case '#':
                                lev.addWall(i - debut, j, jeu);
                                break;
                            case ' ':
                                lev.addVide(i - debut, j, jeu);
                                break;
                            case '@':
                                lev.addPlayer(i - debut, j, jeu);
                                break;
                            case '$':
                                lev.addBox(i - debut, j, jeu);
                                break;
                            case '.':
                                lev.addTarget(i - debut, j, jeu);
                                break;
                            default:
                                System.err.println("caractere Invalide !");
                                System.exit(1);
                        }
                    }
                }
                i++;
                jeu.L = ligne.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        jeu.H = i - debut;
        return i;
    }
}
