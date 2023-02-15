package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
import javax.imageio.*;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

    GamePanel gp;
    Graphics2D g2;

    // POSITIONS, COLLISION AREAS
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;

    // IMAGES
    public BufferedImage image, image2, image3;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, breath1, breath2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
            attackRight2;

    // STATE
    public String dialogues[] = new String[20];
    public String direction = "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean invincible = false;
    public int dialogueIndex = 0;
    public boolean collision = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    public boolean hpBarOn = false;

    // COUTNERS
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;
    public int hpBarCounter = 0;
    public int shotAvailCounter = 0;

    // PLAYER ATTR
    public int maxLife;
    public int life;
    public String name;
    public int speed;
    public int level;
    public int maxMana;
    public int mana;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coins;
    public double factor;
    public Entity currentWeapon;
    public Entity currentShield;
    public Projectile projectile;
    public int useCost;
    public int ammo;

    // MOB & POTION REWARDS
    public int lifeReward;
    public int manaReward;

    // COIN VALUES
    public int coinVal;

    // ITEM ATTR
    public int attackVal;
    public int defenseVal;
    public String desc = "";

    // ENTITY TYPES
    public int type;
    public final int typePlayer = 0;
    public final int typeNPC = 1;
    public final int typeMob = 2;

    // ITEM TYPE
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;
    public final int typePickupOnly = 7;

    public Entity(GamePanel gp) {
        this.gp = gp;

    }

    public void setAction() {
    }

    public void damageReact() {
    }

    public void use(Entity entity) {
    }

    public void checkDrop() {
    }

    public Color getParticleColor() {
        Color c = null;
        return c;
    }

    public int getParticleSize() {
        int size = 0;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 0;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 0;
        return maxLife;
    }

    public void generateParticle(Entity generator, Entity target) {

        Color c = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gp, target, c, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gp, target, c, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gp, target, c, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gp, target, c, size, speed, maxLife, 2, 1);
        gp.particleList.add(p1);
        gp.particleList.add(p2);
        gp.particleList.add(p3);
        gp.particleList.add(p4);
    }

    public void dropItem(Entity droppedItem) {

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] == null) {
                gp.obj[i] = droppedItem;
                gp.obj[i].worldX = worldX; // dead mob's coords
                gp.obj[i].worldY = worldY;
                break;
            }
        }
    }

    public void speak() {
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }

        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.mob);
        gp.cChecker.checkEntity(this, gp.iTile);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == typeMob && contactPlayer) {
            damagePlayer(attack);
        }

        if (!collisionOn) {
            switch (direction) {
                case "up":
                    worldY -= speed;
                    break;
                case "down":
                    worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }

        spriteCounter++;

        if (spriteCounter > 12) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailCounter < 30) {
            shotAvailCounter++;
        }
    }

    public void damagePlayer(int attack) {

        if (!gp.player.invincible) {
            gp.playSoundEffect(gp.receivedamage);

            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;
                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;
                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;
                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;
                default:
                    image = right1;
            }

            // MOB HP BAR
            if (type == typeMob && hpBarOn) {

                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarVal = oneScale * life;

                // OUTLINE
                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                // INNER
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarVal, 10);

                hpBarCounter++;

                if (hpBarCounter > 300) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                alphaChange(g2, 0.45f);
            }

            if (dying) {
                dyingAnimation(g2);
                // life = 0;
            }

            g2.drawImage(image, screenX, screenY, null);

            alphaChange(g2, 1f);
        }
    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            alphaChange(g2, 0f);
        }

        if (dyingCounter > i && dyingCounter <= i * 2) {
            alphaChange(g2, 1f);
        }

        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            alphaChange(g2, 0f);
        }

        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            alphaChange(g2, 1f);
        }

        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            alphaChange(g2, 0f);
        }

        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            alphaChange(g2, 1f);
        }

        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            alphaChange(g2, 0f);
        }

        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            alphaChange(g2, 1f);
        }

        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void alphaChange(Graphics2D g2, float alphaVal) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaVal));
    }

    public BufferedImage setup(String imagePath, int width, int height) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try {

            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

}