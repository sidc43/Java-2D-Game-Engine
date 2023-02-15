package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    URL soundUrl[] = new URL[30];
    FloatControl fc;
    int volScale = 3;
    float vol;

    public Sound() {

        soundUrl[0] = getClass().getResource("/sound/theme.wav");
        soundUrl[1] = getClass().getResource("/sound/coin.wav");
        soundUrl[2] = getClass().getResource("/sound/powerup.wav");
        soundUrl[3] = getClass().getResource("/sound/unlock.wav");
        soundUrl[4] = getClass().getResource("/sound/fanfare.wav");
        soundUrl[5] = getClass().getResource("/sound/doorlock.wav");
        soundUrl[6] = getClass().getResource("/sound/select.wav");
        soundUrl[7] = getClass().getResource("/sound/cselect.wav");
        soundUrl[8] = getClass().getResource("/sound/scroll.wav");
        soundUrl[9] = getClass().getResource("/sound/back.wav");
        soundUrl[10] = getClass().getResource("/sound/escape.wav");
        soundUrl[11] = getClass().getResource("/sound/hitmonster.wav");
        soundUrl[12] = getClass().getResource("/sound/receivedamage.wav");
        soundUrl[13] = getClass().getResource("/sound/swordswing.wav");
        soundUrl[14] = getClass().getResource("/sound/levelup.wav");
        soundUrl[15] = getClass().getResource("/sound/cursor.wav");
        soundUrl[16] = getClass().getResource("/sound/burning.wav");
        soundUrl[17] = getClass().getResource("/sound/cuttree.wav");
    }

    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundUrl[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVol();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {

        clip.start();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {

        clip.stop();
    }

    public void checkVol() {

        switch (volScale) {
            case 0:
                vol = -80f;
                break;
            case 1:
                vol = -20f;
                break;
            case 2:
                vol = -12f;
                break;
            case 3:
                vol = -5f;
                break;
            case 4:
                vol = 1f;
                break;
            case 5:
                vol = 6f;
                break;
        }
        fc.setValue(vol);
    }
}
