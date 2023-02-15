package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Diamond_Shield extends Entity {

    public OBJ_Diamond_Shield(GamePanel gp) {
        super(gp);

        name = "Diamond Shield";
        type = typeShield;
        down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
        defenseVal = 5;
        desc = "[" + name + "]\nAn extremely strong \nshield. \n" + defenseVal + " defense.";
    }

}
