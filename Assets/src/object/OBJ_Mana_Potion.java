package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana_Potion extends Entity {

    GamePanel gp;

    public OBJ_Mana_Potion(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typeConsumable;
        name = "Mana Potion";
        manaReward = 3;
        down1 = setup("/objects/manapotion", gp.tileSize, gp.tileSize);
        desc = "[" + name + "]\nDoes what it sounds\nlike it does. \n+" + manaReward + " Mana";
    }

    public void use(Entity entity) {

        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + ". +" + manaReward + "Mana.\n[E]";

        entity.mana += manaReward;
        gp.playSoundEffect(gp.powerup);
    }

}
