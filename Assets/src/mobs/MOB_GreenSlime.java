package mobs;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_Mana;
import object.OBJ_Rock;

public class MOB_GreenSlime extends Entity {

    GamePanel gp;

    public MOB_GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Slime";
        lifeReward = 2;
        type = typeMob;
        speed = 2;
        maxLife = 8;
        life = maxLife;
        factor = 0.5;
        attack = 3;
        defense = 0;
        exp = 2;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/mobs/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setup("/mobs/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setup("/mobs/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/mobs/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/mobs/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setup("/mobs/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setup("/mobs/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setup("/mobs/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {

        actionLockCounter++;

        if (actionLockCounter == 80) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // rand num from 1-100 inclusive

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }

        // shoot projectile
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && !projectile.alive && shotAvailCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailCounter = 0;
        }
    }

    public void damageReact() {

        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void checkDrop() {

        // DECIDE DROPS
        int i = new Random().nextInt(100) + 1;

        // SET DROPS
        if (i < 50) {
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if (i >= 50 && i < 75) {
            dropItem(new OBJ_Heart(gp));
        }
        if (i > 75 && i <= 100) {
            dropItem(new OBJ_Mana(gp));
        }

    }

}
