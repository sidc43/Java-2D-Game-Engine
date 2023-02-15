package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Mana extends Entity {

    GamePanel gp;

    public OBJ_Mana(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = typePickupOnly;
        manaReward = 1;
        name = "Mana Crystal";
        down1 = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
    }

    public void use(Entity entity) {
        gp.playSoundEffect(gp.keyPickup);
        gp.ui.addMessage("+" + manaReward + " mana");
        entity.mana += manaReward;
    }

}
