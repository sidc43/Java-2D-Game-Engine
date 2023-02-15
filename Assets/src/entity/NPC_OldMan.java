package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

    public NPC_OldMan(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;

        getNPCImage();
        setDialogue();
    }

    public void getNPCImage() {
        up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {

        dialogues[0] = "Hello, wanderer. \n[E]";
        dialogues[1] = "What business have you? \n[E]";
        dialogues[2] = "Remember the Alterlight. \n[E]";
        dialogues[3] = "Not very intelligent, are you? \n[E]";
        dialogues[4] = "State your business. \n[E]";
        dialogues[5] = "The Alterworld guides us. \n[E]";
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

    }

    public void speak() {

        super.speak();
    }
}