package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);

        name = "Wooden Shield";
        type = typeShield;
        down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
        defenseVal = 1;
        desc = "[" + name + "]\nAn old shield. \n" + defenseVal + " defense.";
    }
}