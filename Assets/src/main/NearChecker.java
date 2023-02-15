package main;

import entity.Entity;

public class NearChecker {

    GamePanel gp;

    public boolean near = false;

    public NearChecker(GamePanel gp) {
        this.gp = gp;

    }

    public int CheckNear(Entity entity, Entity target[]) {

        int index = 999;
        near = false;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                int xDis = Math.abs(entity.worldX - target[i].worldX);
                int yDis = Math.abs(entity.worldY - target[i].worldY);

                if (xDis < gp.tileSize && yDis < 60) {
                    near = true;
                    index = i;
                }
            }
        }

        if (near) {
            return index;
        }
        return -1;
    }
}