package View_Controller;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
public class MusicPlayer {

    private Clip clip;
    private FloatControl volumeControl;

    public void playMusic(String musicFilePath) {
        try {
            File musicPath = new File(musicFilePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
                //Controle du volume avec les nbres floats négatifs
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            } else {
                System.out.println("Can't find file");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        //Arret music
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {
            volumeControl.setValue(volume);
        }
    }

    public float getMinimumVolume() {
        return volumeControl.getMinimum();
    }

    public float getMaximumVolume() {
        return volumeControl.getMaximum();
    }

    public float getCurrentVolume() {
        return volumeControl.getValue();
    }
}
