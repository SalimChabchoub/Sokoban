package View_Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class Menu extends JFrame implements Observer {

    CharacterAnimationPanel characterPanel = new CharacterAnimationPanel("Zoro");

    public Menu() {
        build();
    }

    public void build() {
        //Construction du menu
        JPanel jptitre = new JPanel();
        JPanel menu = new JPanel(new BorderLayout());
        characterPanel.setSize(menu.getWidth() / 10, menu.getWidth() / 10);
        characterPanel.setOpaque(false);
        JLabel titre = new JLabel("Finding Zoro");
        String[] characters = {"Zoro", "Escanor", "Gojo"};// mettre les valeur du combo box
        titre.setFont(new Font("Lato", Font.BOLD, 24));
        titre.setForeground(Color.DARK_GRAY);
        jptitre.add(titre);
        JPanel jpb = new JPanel();
        JButton play = new JButton("Play");
        JButton quit = new JButton("Quit");
        JComboBox<String> charac = new JComboBox<>(characters);
        jpb.add(play);
        jpb.add(charac);
        jpb.add(quit);
        characterPanel.setMoving(true);
        menu.add(characterPanel, BorderLayout.CENTER);
        menu.add(jptitre, BorderLayout.PAGE_START);
        menu.add(jpb, BorderLayout.PAGE_END);
        add(menu);
        setTitle("Sokoban");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ajouter les evenements pour les boutons
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MF mf = new MF(characterPanel.hero);
                mf.setVisible(true);
            }
        });

        charac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String characSelected = (String) charac.getSelectedItem();
                menu.remove(characterPanel);
                switch (characSelected) {
                    case "Zoro":
                        characterPanel = new CharacterAnimationPanel("Zoro");
                        characterPanel.setSize(menu.getWidth() / 10, menu.getWidth() / 10);
                        break;
                    case "Escanor":
                        characterPanel = new CharacterAnimationPanel("Escanor");
                        characterPanel.setSize(menu.getWidth() / 20, menu.getWidth() / 20);

                        break;
                    case "Gojo":
                        characterPanel = new CharacterAnimationPanel("Gojo");
                        characterPanel.setSize(menu.getWidth() / 10, menu.getWidth() / 10);
                        break;
                    default:
                        break;
                }


                characterPanel.setOpaque(false);
                menu.add(characterPanel, BorderLayout.CENTER);
                characterPanel.setMoving(true);
                menu.revalidate();

                menu.repaint();
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });

    }

    @Override
    public void update(Observable o, Object arg) {
    }
}
