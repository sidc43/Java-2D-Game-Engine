package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity {

    public OBJ_Axe(GamePanel gp) {
        super(gp);

        name = "Wooden Axe";
        attackVal = 2;
        type = typeAxe;
        down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
        attackArea.width = 30;
        attackArea.height = 30;
        desc = "[" + name + "]\nA woodcutter's axe. \n" + attackVal + " attack.";
    }

}
