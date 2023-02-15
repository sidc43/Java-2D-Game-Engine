package main;

import javax.swing.JPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.image.*;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS AND WORLD SIZE INITIALIZATION
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; // tile size that will be displayed on high res monitors
    public final int maxScreenCol = 20; // horizontal size
    public final int maxScreenRow = 12; // vertical size
    public final int screenWidth = tileSize * maxScreenCol; // 960px
    public final int screenHeight = tileSize * maxScreenRow; // 576px
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxScreenRow;

    // FULLSCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    // MUSIC
    public final int theme = 0;
    public final int keyPickup = 1;
    public final int powerup = 2;
    public final int unlock = 3;
    public final int fanfare = 4;
    public final int locked = 5;
    public final int selectOption = 6;
    public final int classSelect = 7;
    public final int scroll = 8;
    public final int back = 9;
    public final int escape = 10;
    public final int hitmonster = 11;
    public final int receivedamage = 12;
    public final int swordswing = 13;
    public final int levelup = 14;
    public final int cursor = 15;
    public final int fireball = 16;
    public final int cuttree = 17;

    // GET METHODS
    final int FPS = 60;
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;
    public Player player = new Player(this, keyH);
    public NearChecker nc = new NearChecker(this);
    public Entity obj[] = new Entity[20];
    public Entity npc[] = new Entity[10];
    public Entity mob[] = new Entity[20];
    public ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public InteractiveTile iTile[] = new InteractiveTile[50];
    public ArrayList<Entity> particleList = new ArrayList<>();
    public Config cfg = new Config(this);

    // OPTIONS
    public boolean fullScreen = false;

    // GAMESTATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int eventState = 4;
    public final int characterStatState = 5;
    public final int optionsState = 6;

    // DAY CYCLE
    public int time = 0;
    public boolean dayTime = true;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);

        // improve rendering performance
        this.setDoubleBuffered(true);

        // key input
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMob();
        aSetter.setInteractiveTile();
        playMusic(theme);
        gameState = titleState;

        // FULLSCREEN
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        if (fullScreen) {
            setFullScreen();
        }
    }

    public void setFullScreen() {

        // get monitor specs
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        // get width and height
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();

    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // RESTRICT FPS
        double drawInterval = 1000000000 / FPS; // 0.1666 sec
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                // repaint();
                drawToTempScreen(); // draw to buffered image
                drawToScreen(); // draw buffered image to screen
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                if (drawCount != 60) {
                    System.out.println("FPS: " + drawCount);
                }
                drawCount = 0;
                timer = 0;
            }
        }
    }
    // RESTRICT FPS

    // change player position
    public void update() {

        if (gameState == playState) {
            // player update
            player.update();

            // day cycle
            // time count
            time++;

            if (time <= 3600) {
                dayTime = true;
            } else if (time > 3600) {
                dayTime = false;
            }
            if (time > 7200) {
                time = 0;
            }
            // npc update
            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    npc[i].update();
                }
            }

            for (int i = 0; i < mob.length; i++) {
                if (mob[i] != null) {
                    if (mob[i].alive && !mob[i].dying) {
                        mob[i].update();
                    }
                    if (!mob[i].alive) {
                        mob[i].checkDrop();

                        mob[i] = null;
                    }
                }
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive) {
                        projectileList.get(i).update();
                    }
                    if (!projectileList.get(i).alive) {
                        projectileList.remove(i);
                    }
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    if (particleList.get(i).alive) {
                        particleList.get(i).update();
                    }
                    if (!particleList.get(i).alive) {
                        particleList.remove(i);
                    }
                }
            }

            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].update();
                }
            }

        } else if (gameState == pauseState) {
            // nothing
        }

    }

    public void drawToTempScreen() {

        if (gameState == titleState) {
            ui.draw(g2);
        }
        // DRAW OTHERS
        else {
            // tiles
            tileM.draw(g2);

            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].draw(g2);
                }
            }

            // add entities to list
            entityList.add(player);

            for (int i = 0; i < npc.length; i++) {
                if (npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for (int i = 0; i < mob.length; i++) {
                if (mob[i] != null) {
                    entityList.add(mob[i]);
                }
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }

            // sort render order
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int res = Integer.compare(e1.worldY, e2.worldY);
                    return res;
                }

            });

            // draw entities
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            // EMPTY LIST
            entityList.clear();

            // ui
            ui.draw(g2);
        }

        if (keyH.showDebugText) {
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.white);

            int x = 10;
            int y = 100;
            int lineheight = 20;
            g2.drawString("World X: " + player.worldX, x, y);
            y += lineheight;
            g2.drawString("World Y: " + player.worldY, x, y);
            y += lineheight;
            g2.drawString("Col: " + (player.worldX + player.solidArea.x) / tileSize, x, y);
            y += lineheight;
            g2.drawString("Row: " + (player.worldY + player.solidArea.y) / tileSize, x, y);
            y += lineheight;
        }
    }

    public void drawToScreen() {

        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {

        music.stop();
    }

    public void playSoundEffect(int i) {

        soundEffect.setFile(i);
        soundEffect.play();
    }
}
