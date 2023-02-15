package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health_Potion extends Entity {

    GamePanel gp;

    public OBJ_Health_Potion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeConsumable;
        name = "Healing Potion";
        lifeReward = 6;
        down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
        desc = "[" + name + "]\nDoes what it sounds\nlike it does. \n+" + lifeReward + "HP";
    }

    public void use(Entity entity) {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + ". +" + lifeReward + "HP.\n[E]";
        entity.life += lifeReward;
        gp.playSoundEffect(gp.powerup);
    }

}
