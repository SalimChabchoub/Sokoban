package View_Controller;

import Modele.Jeu;
import Modele.Level;

import java.io.*;
import java.util.Scanner;

public class LevelLector {
    Scanner s;


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

//    public LevelLector(InputStream in){
//        s = new Scanner(in);
//    }
//
//    public Level readLevel(){
//        Level lev = new Level();
//        String ligne = null;
//        int i = 0;
//        try {
//            i++;
//            ligne = s.nextLine();
//        }catch (Exception e){
//            return null;
//        }while (ligne.charAt(0) == ';'){
//            if(){
//                int j = 1;
//                while (ligne.charAt(j) == ' ')
//                    j++;
//                lev.fixName(ligne.substring(j));
//            }else {
//                for (int j = 0 ;j < ligne.length(); j++){
//                    switch (ligne.charAt(j)){
//                        case '#':
//                            lev.addWall(i,j);
//                            break;
//                        case '@':
//                            lev.addPlayer(i,j);
//                            break;
//                        case '$':
//                            lev.addBox(i,j);
//                            break;
//                        case '.':
//                            lev.addTarget(i,j);
//                            break;
//                        default:
//                            System.err.println("caractere Invalide !");
//                            System.exit(1);
//                    }
//                }
//            }
//            ligne = s.nextLine();
//            i++;
//        }
//        return lev;
//    }
}
