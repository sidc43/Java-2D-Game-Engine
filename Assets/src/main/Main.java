package main;

import javax.swing.JFrame;

public class Main {

    public static JFrame window;

    public static void main(String[] args) {

        // create game window
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String verison = "0.0.7";
        window.setTitle("Alterlight v" + verison);

        // add gamepanel to window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        gamePanel.cfg.loadCongif();
        if (gamePanel.fullScreen) {
            window.setUndecorated(true);
        }

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        // call game loop
        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}