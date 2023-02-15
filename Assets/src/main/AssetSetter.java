package main;

import entity.NPC_OldMan;
import mobs.MOB_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Diamond_Shield;
import object.OBJ_Health_Potion;
import object.OBJ_Key;
import object.OBJ_Mana_Potion;
import tile_interactive.IT_DryTree;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize * 12;
        gp.obj[i].worldY = gp.tileSize * 24;
        i++;
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 38;
        gp.obj[i].worldY = gp.tileSize * 9;
        i++;
        gp.obj[i] = new OBJ_Diamond_Shield(gp);
        gp.obj[i].worldX = gp.tileSize * 26;
        gp.obj[i].worldY = gp.tileSize * 16;
        i++;
        gp.obj[i] = new OBJ_Health_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 28;
        gp.obj[i].worldY = gp.tileSize * 39;
        i++;
        gp.obj[i] = new OBJ_Health_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 38;
        gp.obj[i].worldY = gp.tileSize * 28;
        i++;
        gp.obj[i] = new OBJ_Mana_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 28;
        gp.obj[i].worldY = gp.tileSize * 40;
        i++;
        gp.obj[i] = new OBJ_Mana_Potion(gp);
        gp.obj[i].worldX = gp.tileSize * 38;
        gp.obj[i].worldY = gp.tileSize * 41;
        i++;
    }

    public void setNPC() {

        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }

    public void setMob() {

        int i = 0;
        gp.mob[i] = new MOB_GreenSlime(gp);
        gp.mob[i].worldX = gp.tileSize * 23;
        gp.mob[i].worldY = gp.tileSize * 36;
        i++;
        gp.mob[i] = new MOB_GreenSlime(gp);
        gp.mob[i].worldX = gp.tileSize * 23;
        gp.mob[i].worldY = gp.tileSize * 42;
        i++;
        gp.mob[i] = new MOB_GreenSlime(gp);
        gp.mob[i].worldX = gp.tileSize * 24;
        gp.mob[i].worldY = gp.tileSize * 37;
        i++;
        gp.mob[i] = new MOB_GreenSlime(gp);
        gp.mob[i].worldX = gp.tileSize * 34;
        gp.mob[i].worldY = gp.tileSize * 42;
        i++;
        gp.mob[i] = new MOB_GreenSlime(gp);
        gp.mob[i].worldX = gp.tileSize * 38;
        gp.mob[i].worldY = gp.tileSize * 42;
        i++;
    }

    public void setInteractiveTile() {

        int i = 0;
        gp.iTile[i] = new IT_DryTree(gp, 27, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 28, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 29, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 31, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 32, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 33, 12);
        i++;

        gp.iTile[i] = new IT_DryTree(gp, 30, 20);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 31, 20);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 32, 20);
        i++;

        gp.iTile[i] = new IT_DryTree(gp, 30, 22);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 31, 22);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 32, 22);
        i++;
    }
}
