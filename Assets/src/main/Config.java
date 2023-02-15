package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    GamePanel gp;

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));

            // FULLSCREEN
            if (gp.fullScreen) {
                bw.write("On");
            }
            if (!gp.fullScreen) {
                bw.write("Off");
            }
            bw.newLine();

            // MUSIC VOL
            bw.write(String.valueOf(gp.music.volScale));
            bw.newLine();

            // SFX
            bw.write(String.valueOf(gp.soundEffect.volScale));
            bw.newLine();

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCongif() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));

            String s = br.readLine();
            // fs
            if (s.equals("On")) {
                gp.fullScreen = true;
            }
            if (s.equals("Off")) {
                gp.fullScreen = false;
            }

            // music
            s = br.readLine();
            gp.music.volScale = Integer.parseInt(s);

            // sfx
            s = br.readLine();
            gp.soundEffect.volScale = Integer.parseInt(s);

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
