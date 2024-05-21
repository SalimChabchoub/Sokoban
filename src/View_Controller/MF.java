package View_Controller;

import Modele.*;
import Modele.Point;
import Modele.Jeu;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import static java.lang.Thread.sleep;

public class MF extends JFrame implements Observer {

    Jeu J;
    ImagePanel[][] tabC;
    private JLayeredPane layeredPane;
    private JPanel jp;
    private CharacterAnimationPanel characterPanel;
    private Character character;

    public MF(Jeu jeu) {
        this.J = jeu;
        this.tabC = new ImagePanel[J.H][J.L];
        Thread backgroundMusicThread = new Thread(() -> {
            MusicPlayer musicPlayer = new MusicPlayer();
            musicPlayer.playMusic("Ressources/backgroundMusic.wav");
            musicPlayer.setVolume(-30.0f);
        });
        //On va mettre la music de fond sur un thread séparé pour ne pas géner le reste du code
        backgroundMusicThread.start();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                build();
                addEC();
            }

        });
    }

    public void build() {
        Case c;
        //On prend les dimensions de l'écran pour utiliser le 1/4
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(screenSize.width/2, screenSize.width/2));
        //On préfére le modèle carré meme si nos ressources marche sur d'autre formes
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLayersSizeAndPosition();
            }
        });
        jp = new JPanel(new BorderLayout());
        JPanel jpC = new JPanel(new GridLayout(J.H, J.L));
        jp.setBounds(0, 0, screenSize.width/2, screenSize.width/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JButton restartButton = new JButton("Reset");
        jp.add(restartButton, BorderLayout.BEFORE_FIRST_LINE);
        jp.add(jpC, BorderLayout.CENTER);
        setTitle("Sobokan");
        add(jp);
        setSize(screenSize.width/2, screenSize.width/2);
        characterPanel = new CharacterAnimationPanel();
        characterPanel.setSize(jpC.getWidth() / J.L, jpC.getWidth() / J.H);
        characterPanel.setOpaque(false);
        layeredPane.add(jp, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(characterPanel, JLayeredPane.PALETTE_LAYER);
        add(layeredPane);
        for (int i = 0; i < J.H; i++) {
            for (int j = 0; j < J.L; j++) {
                Point point = new Point(j, i);
                tabC[i][j] = new ImagePanel();
                c = J.trouveCase(point);
                if (c instanceof Mur) {
                    tabC[i][j].setImageToDisplay(ImageLoader.wallImage);
                } else {
                    tabC[i][j].setImageToDisplay(ImageLoader.groundImage);
                    if (J.trouveCase(new Point(j, i - 1)) instanceof Mur) {
                        tabC[i][j].setWallTopImage(ImageLoader.wallTop);
                    }
                    if (J.trouveCase(new Point(j - 1, i)) instanceof Mur) {
                        tabC[i][j].setWallLeftImage(ImageLoader.wallLeft);
                    }
                    if (J.trouveCase(new Point(j + 1, i)) instanceof Mur) {
                        tabC[i][j].setWallRightImage(ImageLoader.wallRight);
                    }
                    if (J.trouveCase(new Point(j, i + 1)) instanceof Mur) {
                        tabC[i][j].setWallBottomImage(ImageLoader.wallBottom);
                    }
                }
                if (c instanceof Target) {
                    tabC[i][j].setTargetImage(ImageLoader.targetImage);
                } else if (c.entite instanceof Hero) {
                    character = new Character(i, j, characterPanel);
                    c.entite.addObserver(this);
                } else if (c.entite instanceof Bloc) {
                    tabC[i][j].setBarrelImage(ImageLoader.barrelImage);
                }
                jpC.add(tabC[i][j]);
            }
        }
        this.setVisible(true);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Load le meme niveau
                J.loadnextlevel(0);
                getContentPane().removeAll();
                tabC = new ImagePanel[J.H][J.L];
                build();
                MF.this.requestFocus();
            }
        });
    }


    private void adjustLayersSizeAndPosition() {
        int width = layeredPane.getWidth();
        int height = layeredPane.getHeight();

        jp.setSize(width, height);

        int cellWidth = width / J.L;
        int cellHeight = height / J.H;

        characterPanel.setSize(cellWidth, cellHeight);
        character.setCharacterPosition(character.getRow(), character.getCol());
        layeredPane.revalidate();
        layeredPane.repaint();
        characterPanel.repaint();
    }

    public void addEC() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_Z:
                    case KeyEvent.VK_UP:
                        J.deplacerHero(Direction.UP);
                        break;
                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        J.deplacerHero(Direction.DOWN);
                        break;
                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        J.deplacerHero(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_Q:
                    case KeyEvent.VK_LEFT:
                        J.deplacerHero(Direction.LEFT);
                        break;
                }
            }

        });
        requestFocus();
    }
    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Point p;
                Case c;
                for (int i = 0; i < J.H; i++) {
                    for (int j = 0; j < J.L; j++) {
                        p = new Point(j, i);
                        c = J.trouveCase(p);
                        if (c.entite instanceof Bloc) {
                            tabC[i][j].setBarrelImage(ImageLoader.barrelImage);
                        } else {
                            tabC[i][j].setBarrelImage(null);
                        }
                        if (c.entite instanceof Hero) {
                            final int finalI = i;
                            final int finalJ = j;
                            J.setAttendHero(true);
                            //Lancement du thread qui gère les animations du caractères
                            Thread heroMovementThread = new Thread(() -> character.moveTo(finalI, finalJ, J));
                            heroMovementThread.start();
                        }
                    }
                }
                repaint();
                if (J.dectecteVictoire()) {
                    //Gestion de l'effet sonors de la victoires
                    try {
                        AudioInputStream audio = AudioSystem.getAudioInputStream(new File("Ressources/levelwin.wav").getAbsoluteFile());
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(audio);
                            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                            volumeControl.setValue(-30.0f);
                            clip.start();
                        } catch (LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (UnsupportedAudioFileException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    JLabel label = new JLabel("<html><center> Victory !<br> Level Completed !<br><img width = '100' height = '100' src ='" + MF.class.getClassLoader().getResource("one-piece-zoro.gif") + "'></center></html>");
                    int result = JOptionPane.showOptionDialog(MF.this, label, "Victoire", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Play Again", "Next Level"}, "Play Again");
                    dispose();
                    J.loadnextlevel(result);
                    getContentPane().removeAll();
                    tabC = new ImagePanel[J.H][J.L];
                    build();
                }
            }
        });
    }
}
