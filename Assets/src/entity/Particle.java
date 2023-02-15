package entity;

import java.awt.*;
import main.GamePanel;

public class Particle extends Entity {

    Entity generator;
    Color c;
    int xd;
    int yd;
    int size;

    public Particle(GamePanel gp, Entity generator, Color c, int size, int speed, int maxLife, int xd, int yd) {
        super(gp);

        this.generator = generator;
        this.c = c;
        this.xd = xd;
        this.yd = yd;
        this.size = size;
        this.maxLife = maxLife;
        this.speed = speed;

        life = maxLife;
        int offset = (gp.tileSize / 2) - (size / 2);
        worldX = generator.worldX + offset;
        worldY = generator.worldY + offset;
    }

    public void update() {

        life--;

        if (life < maxLife / 3) {
            yd++;
        }

        worldX += xd * speed;
        worldY += yd * speed;

        if (life == 0) {
            alive = false;
        }

    }

    public void draw(Graphics2D g2) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        g2.setColor(c);
        g2.fillRect(screenX, screenY, size, size);
    }

}
