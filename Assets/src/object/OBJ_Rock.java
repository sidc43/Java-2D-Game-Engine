package object;

import java.awt.*;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile {

    GamePanel gp;

    public OBJ_Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rock";
        speed = 9;
        maxLife = 120;
        life = maxLife;
        attack = 3;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
    }

    public boolean hasResource(Entity user) {
        boolean hasResource = false;
        if (user.ammo >= useCost) {
            hasResource = true;
        }
        return hasResource;
    }

    public void subtractResource(Entity user) {
        user.ammo -= useCost;
    }

    public Color getParticleColor() {
        Color c = new Color(48, 53, 36);
        return c;
    }

    public int getParticleSize() {
        int size = 6;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }

}
