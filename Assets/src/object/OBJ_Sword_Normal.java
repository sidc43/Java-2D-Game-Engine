package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword_Normal extends Entity {

    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);

        name = "Normal Sword";
        type = typeSword;
        down1 = setup("/objects/sword_normal", gp.tileSize, gp.tileSize);
        attackVal = 1;
        attackArea.width = 32;
        attackArea.height = 36;
        desc = "[" + name + "]\nAn old sword. \n" + attackVal + " attack.";
    }

}
