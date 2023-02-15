package tile_interactive;

import java.awt.Color;
import entity.Entity;
import main.GamePanel;

public class IT_DryTree extends InteractiveTile {

    GamePanel gp;

    public IT_DryTree(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/tilesinteractive/drytree", gp.tileSize, gp.tileSize);
        destructible = true;
        life = 12;
    }

    public boolean isCorrectItem(Entity entity) {
        boolean isCorrectItem = false;

        if (entity.currentWeapon.type == typeAxe) {
            isCorrectItem = true;
        }

        return isCorrectItem;
    }

    public void playSE() {
        gp.playSoundEffect(gp.cuttree);
    }

    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gp, worldX / gp.tileSize, worldY / gp.tileSize);
        return tile;
    }

    public Color getParticleColor() {
        Color c = new Color(78, 53, 36);
        return c;
    }

    public int getParticleSize() {
        int size = 6;
        return size;
    }

    public int getParticleSpeed() {
        int speed = 1;
        return speed;
    }

    public int getParticleMaxLife() {
        int maxLife = 20;
        return maxLife;
    }

}
