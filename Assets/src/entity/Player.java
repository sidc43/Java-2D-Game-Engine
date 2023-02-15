package entity;

// class imports
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

// library imports
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX, screenY;
    int standCounter = 0;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInvSize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        // Collision mask
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "right";

        // player stats
        maxLife = 10;
        life = maxLife;
        maxMana = 3;
        mana = maxMana;
        speed = 4;
        level = 1;
        ammo = 10;
        strength = 1; // MORE STRENGTH = MORE DMG (BETTER WEAPON HANDLING)
        dexterity = 1; // MORE DEXTERITY = LESS DMG TAKES (BETTER ABILITY TO HANDLE SHIELDS AND SWORDS)
        exp = 0;
        nextLevelExp = 5;
        coins = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);

        // PLAYER CAST / PROJECTILE WEAPONS
        projectile = new OBJ_Fireball(gp);
        // projectile = new OBJ_Rock(gp);
        attack = getAttack(); // TOTAL ATTACK VAL IS DECIDED BY STRENGTH AND WEAPON
        defense = getDefense(); // TOTAL DEFENSE VAL IS DECIDED BY DEXTERITY AND SHIELD

    }

    public void setItems() {

        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        attack = strength * currentWeapon.attackVal;
        return attack;
    }

    public int getDefense() {
        defense = dexterity * currentShield.defenseVal;
        return defense;
    }

    public void getPlayerImage() {

        up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
    }

    public void getPlayerAttackImage() {

        if (currentWeapon.type == typeSword) {
            attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }

        if (currentWeapon.type == typeAxe) {
            attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void update() {

        if (attacking) {
            attacking();
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.ePressed
                || keyH.enterPressed) {

            if (keyH.upPressed) {
                direction = "up";
            }

            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }

            if (keyH.upPressed && keyH.downPressed) {
                speed = 0;
            } else if (keyH.rightPressed && keyH.leftPressed) {
                speed = 0;
            } else {
                speed = 4;
            }

            if (speed == 0) {
                spriteCounter = 0;
                spriteNum = 1;
            }

            if (keyH.enterPressed) {
                gp.playSoundEffect(gp.swordswing);
                attacking = true;
            }

            // check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check if colliding with object
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObj(objIndex);

            // check npc collision
            gp.cChecker.checkEntity(this, gp.npc);
            int npcindex1 = gp.nc.CheckNear(this, gp.npc);
            if (npcindex1 != -1) {
                interactNPC(npcindex1);
            }

            // monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.mob);
            contactMob(monsterIndex);

            // iTile collision
            gp.cChecker.checkEntity(this, gp.iTile);

            // event checker
            gp.eHandler.checkEvent();

            // if collision is false player can move else speed = 0
            if (!collisionOn && !keyH.ePressed) {
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
            keyH.ePressed = false;

            spriteCounter++;

            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (gp.keyH.shotKeyPressed && !projectile.alive && shotAvailCounter == 30 && projectile.hasResource(this)) {

            projectile.set(worldX, worldY, direction, true, this);

            // SUBTRACT MANA
            projectile.subtractResource(this);

            // ADD IT TO LIST
            gp.projectileList.add(projectile);

            shotAvailCounter = 0;

            gp.playSoundEffect(gp.fireball);
        }

        // INVINCIBLE TIME COUNTER
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (shotAvailCounter < 30) {
            shotAvailCounter++;
        }
        if (life > maxLife) {
            life = maxLife;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
    }

    public void attacking() {

        spriteCounter++;

        // ANIMATION
        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // save current x, y, width, and height
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // adjust player for attack
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            // check mob collision w updates coords
            int mobIndex = gp.cChecker.checkEntity(this, gp.mob);

            // reset coords
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

            damageMob(mobIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageITile(iTileIndex);

        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void pickUpObj(int index) {

        if (index != 999) {

            // PICKUP ONLY ITEMS
            if (gp.obj[index].type == typePickupOnly) {

                gp.obj[index].use(this);
                gp.obj[index] = null;
            }
            // INV ITEMS
            else {
                String text;

                if (inventory.size() != maxInvSize) {
                    inventory.add(gp.obj[index]);
                    gp.playSoundEffect(gp.keyPickup);
                    text = "Picked up " + gp.obj[index].name + "!";
                } else {
                    text = "Inventory full!";
                }
                gp.ui.addMessage(text);
                gp.obj[index] = null;
            }
        }
    }

    public void interactNPC(int index) {

        if (index != 999) {

            if (gp.keyH.ePressed) {
                gp.gameState = gp.dialogueState;
                gp.npc[index].speak();
            }
        }
    }

    public void contactMob(int index) {
        if (index != 999) {

            double damage = gp.mob[index].attack - defense * gp.mob[index].factor;
            if (damage < 0) {
                damage = 0;
            }
            if (!invincible && !gp.mob[index].dying) {
                gp.playSoundEffect(gp.receivedamage);
                life -= damage;
                invincible = true;
            }

        }
    }

    public void damageMob(int index, int attack) {
        if (index != 999) {

            if (!gp.mob[index].invincible) {
                gp.playSoundEffect(gp.hitmonster);

                int damage = attack - gp.mob[index].defense;

                if (damage < 0) {
                    damage = 0;
                }
                gp.mob[index].life -= damage;

                if (life <= 0) {
                    life = 0;
                }

                gp.ui.addMessage("You did " + damage + " damage to " + gp.mob[index].name);

                gp.mob[index].invincible = true;
                gp.mob[index].damageReact();

                if (gp.mob[index].life <= 0) {
                    gp.mob[index].dying = true;
                    gp.ui.addMessage("Killed " + gp.mob[index].name + "!");
                    gp.ui.addMessage("Gained +" + gp.mob[index].exp + " EXP");
                    exp += gp.mob[index].exp;
                    checkLevelUp(index);
                }
            }
        }
    }

    public void damageITile(int index) {

        if (index != 999 && gp.iTile[index].destructible && gp.iTile[index].isCorrectItem(this)
                && !gp.iTile[index].invincible) {

            gp.iTile[index].playSE();
            gp.iTile[index].life -= attack;
            System.out.println(gp.iTile[index].life);
            gp.iTile[index].invincible = true;

            if (gp.iTile[index].life <= 0) {
                // particle
                generateParticle(gp.iTile[index], gp.iTile[index]);
                gp.iTile[index] = gp.iTile[index].getDestroyedForm();
            }
        }
    }

    public void checkLevelUp(int index) {

        if (exp >= nextLevelExp) {

            level++;
            nextLevelExp = nextLevelExp * 3;
            life += gp.mob[index].lifeReward;
            if (life > maxLife) {
                life = maxLife;
            }
            mana += 2;
            if (mana > maxMana) {
                mana = maxMana;
            }
            gp.ui.addMessage("Health increased by " + gp.mob[index].lifeReward + "HP!");
            gp.ui.addMessage("Check new stats!");
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSoundEffect(gp.levelup);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You are level " + level + "! You feel stronger...\n[E]";
        }
    }

    public void selectItem() {

        int index = gp.ui.getItemIndexOnSlot();

        if (index < inventory.size()) {

            Entity selectedItem = inventory.get(index);

            if (selectedItem.type == typeSword || selectedItem.type == typeAxe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == typeShield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == typeConsumable) {

                selectedItem.use(this);
                inventory.remove(index);
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        // set image based on direction

        switch (direction) {
            case "up":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right":
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }

                break;
            default:
                image = right1;
        }

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}