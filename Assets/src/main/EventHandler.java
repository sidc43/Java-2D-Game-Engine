package main;

public class EventHandler {
    GamePanel gp;
    EventRect eventRect[][];

    int prevEventX, prevEventY;
    boolean canTouchEvent = true;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            // CHANGE TO TRIGGER EVENT FROM FURTHER
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            //

            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }

    }

    public void checkEvent() {

        // check is player is > n tile away from prev event
        int xDis = Math.abs(gp.player.worldX - prevEventX);
        int yDis = Math.abs(gp.player.worldY - prevEventY);
        int distance = Math.max(xDis, yDis);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(27, 16, "right")) {
                damagePit(27, 16, gp.eventState);
            }

            if (hit(23, 12, "up")) {
                healingPool(23, 12, gp.eventState);
            }
        }

    }

    public boolean hit(int col, int row, String reqDir) {

        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && !eventRect[col][row].eventDone) {
            if (gp.player.direction.contentEquals(reqDir) || reqDir.contentEquals("any")) {
                hit = true;

                prevEventX = gp.player.worldX;
                prevEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.eventMsg = "You fall into a 'pit' \n[E]";
        gp.player.life -= 1;
        // eventRect[col][row].eventDone = true;
        canTouchEvent = false;
    }

    public void healingPool(int col, int row, int gameState) {
        gp.gameState = gameState;
        if (gp.player.life != gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
            gp.ui.eventMsg = "You drink water. Health increased. \n[E]";
            gp.aSetter.setMob();
        } else if (gp.player.life >= gp.player.maxLife) {
            gp.ui.eventMsg = "You are already at full health. \n[E]";
        }
        canTouchEvent = false;
    }
}
