package main;

import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_Mana;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    public Font pixel, source_80b;
    BufferedImage heartFull, heartHalf, heartBlank, crystalFull, crystalBlank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinish = false;
    public String currentDialogue = "";
    public String eventMsg = "";
    public int commandNum = 0;
    public int pauseCmdNum = 0;
    public int titleScreenState = 0; // 0 is first screen, 1 is next...
    public int slotCol = 0;
    public int slotRow = 0;
    public int subState = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/ThaleahFat.ttf");
            pixel = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/SOURCECODEPRO-REGULAR.TTF");
            source_80b = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        // create HUD
        Entity heart = new OBJ_Heart(gp);
        heartFull = heart.image;
        heartHalf = heart.image2;
        heartBlank = heart.image3;
        Entity crystal = new OBJ_Mana(gp);
        crystalFull = crystal.image;
        crystalBlank = crystal.image2;
    }

    public void addMessage(String text) {

        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(pixel);
        g2.setColor(Color.white);

        // check game states

        // TITLE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }

        // PAUSE
        if (gp.gameState == gp.pauseState) {
            // drawPlayerLife();
            drawPauseScreeen();
        }

        // DIALOGUE
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

        // EVENT
        if (gp.gameState == gp.eventState) {
            drawPlayerLife();
            drawEventDialogueScreen();
        }

        // CHARACTER STAT
        if (gp.gameState == gp.characterStatState) {
            drawCharacterStats();
            drawInventory();
        }

        // OPTION
        if (gp.gameState == gp.optionsState) {
            drawOptionScreen();
        }

    }

    public void drawMessage() {

        int messageX = gp.tileSize - 10;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 25f));

        for (int i = 0; i < message.size(); i++) {

            if (message.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // messagecounter++
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 90 /* 1.5 seconds */) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPlayerLife() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heartBlank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        // CURRENT LIFE
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        while (i < gp.player.life) {
            g2.drawImage(heartHalf, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heartFull, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        // DRAW MAX MANA
        x = gp.tileSize / 2;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while (i < gp.player.maxMana) {
            g2.drawImage(crystalBlank, x, y, null);
            i++;
            x += 35;
        }

        // DRAW CURRENT MANA
        x = gp.tileSize / 2;
        y = (int) (gp.tileSize * 1.5);
        i = 0;
        while (i < gp.player.mana) {
            g2.drawImage(crystalFull, x, y, null);
            i++;
            x += 35;
        }
    }

    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            // GAME TITLE
            g2.setColor(new Color(62, 142, 126));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 58F));
            String text = "A Pidder Productions Game";
            int x = getXCenteredLength(text);
            int y = gp.tileSize * 2;

            // shadow
            g2.setColor(Color.black);
            g2.drawString(text, x + 5, y + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            int x0 = x + 200;
            int y0 = y + 75;
            text = "Alterlight";

            g2.setColor(Color.black);
            g2.drawString(text, x0 + 5, y0 + 5);

            g2.setColor(Color.WHITE);
            g2.drawString(text, x0, y0);

            // player image
            x = (gp.screenWidth / 2) - 85;
            y += (gp.tileSize * 2) - 50;
            int x1 = x;
            int y1 = y;
            g2.drawImage(gp.player.down1, x1 + 30, y1 + 80, gp.tileSize * 2, gp.tileSize * 2, null);

            // menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 38F));
            text = "NEW GAME";
            x = getXCenteredLength(text);
            y += gp.tileSize * 5;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "LOAD GAME (soon!)";
            x = getXCenteredLength(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "QUIT GAME";
            x = getXCenteredLength(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }

        else if (titleScreenState == 1) {
            // CLASS SELECTION
            g2.setColor(new Color(62, 142, 126));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "SELECT CLASS (Soon!)";
            int x = getXCenteredLength(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Melee";
            x = getXCenteredLength(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Magic";
            x = getXCenteredLength(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Ranged";
            x = getXCenteredLength(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Back";
            x = getXCenteredLength(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }

        }

    }

    public void drawPauseScreeen() {

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80F));
        String text = "Paused";
        int x = getXCenteredLength(text);
        int y = (gp.screenHeight / 2) - 140;

        g2.drawString(text, x, y);

        text = "RESUME";
        x = getXCenteredLength(text);
        y += (gp.tileSize + 100);
        g2.drawString(text, x, y);
        if (pauseCmdNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawDialogueScreen() {

        // create window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 8;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 3;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        x += gp.tileSize - 10;
        y += gp.tileSize;

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawEventDialogueScreen() {

        // create window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 8;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 3;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        x += gp.tileSize - 10;
        y += gp.tileSize;

        for (String line : eventMsg.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterStats() {

        // FRAME
        final int frameX = gp.tileSize - 25;
        final int frameY = gp.tileSize - 35;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = (gp.tileSize * 11) + 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // TEXT
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(28F));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 37;

        // NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coins", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);

        // VALUES
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String val;

        val = String.valueOf(gp.player.level);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.strength);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.dexterity);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.attack);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.defense);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.exp);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.nextLevelExp - gp.player.exp);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        val = String.valueOf(gp.player.coins);
        textX = getXAlignToRight(val, tailX);
        g2.drawString(val, textX, textY);
        textY += lineHeight;

        // WEAPON IMAGE
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 11, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 10, null);

    }

    public void drawInventory() {

        // FRAME
        int frameX = (gp.tileSize * 13) + 20;
        int frameY = gp.tileSize - 35;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // SLOTS
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gp.tileSize + 3;

        // DRAW PLAYER ITEMS
        for (int i = 0; i < gp.player.inventory.size(); i++) {

            // EQUIP CURSOR
            if (gp.player.inventory.get(i) == gp.player.currentWeapon
                    || gp.player.inventory.get(i) == gp.player.currentShield) {

                Color c = new Color(240, 190, 90, 120);
                g2.setColor(c);
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // CURSOR
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // DRAW CURSOR
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // DESC FRAME
        int dFrameX = (int) (frameX - frameWidth / 4.8);
        int dFrameY = (frameY + frameHeight) + 20;
        int dFrameWidth = frameWidth + 60;
        int dFrameHeight = gp.tileSize * 4;

        // DESC TEXT
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28f));

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < gp.player.inventory.size()) {
            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
            for (String line : gp.player.inventory.get(itemIndex).desc.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }
        }
    }

    public void drawOptionScreen() {

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        // sub window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                optionsTop(frameX, frameY);
                break;
            case 1:
                optionFullScreenNotif(frameX, frameY);
                break;
            case 2:
                optionControl(frameX, frameY);
                break;
            case 3:
                optionEndGameConfirm(frameX, frameY);
                break;
        }

        gp.keyH.enterPressed = false;
    }

    public void optionsTop(int frameX, int frameY) {

        int textX;
        int textY;

        // TITLE
        String text = "OPTIONS";
        textX = getXCenteredLength(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN TOGGLE
        textX = frameX + gp.tileSize;
        textY += (int) (gp.tileSize * 1.1);
        g2.drawString("FULLSCREEN", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                if (!gp.fullScreen) {
                    gp.fullScreen = true;
                } else if (gp.fullScreen) {
                    gp.fullScreen = false;
                }
                subState = 1;
            }

        }

        // MUSIC
        textY += (int) (gp.tileSize * 1.1);
        g2.drawString("MUSIC", textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // SFX
        textY += (int) (gp.tileSize * 1.1);
        g2.drawString("SFX", textX, textY);
        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // CONTROL
        textY += (int) (gp.tileSize * 1.1);
        g2.drawString("CONTROLS", textX, textY);
        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }

        // RESUME GAME
        textY += (int) (gp.tileSize * 2);
        g2.drawString("RESUME", textX, textY);
        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
        }

        // END GAME
        textY += (int) (gp.tileSize * 1.2);
        g2.drawString("END GAME", textX, textY);
        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }

        // FULLSCREEN CHECKBOX
        textX = (frameX + gp.tileSize * 5) + 1;
        textY = (int) (frameY + gp.tileSize * 1.7);
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);
        if (gp.fullScreen) {
            g2.fillRect(textX, textY, gp.tileSize / 2, gp.tileSize / 2);
        }

        // MUSIC SLIDER
        textX = (frameX + gp.tileSize * 6) - 47;
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volWidth = 24 * gp.music.volScale;
        g2.fillRect(textX, textY, volWidth, 24);

        // SFX SLIDER
        textX = (frameX + gp.tileSize * 6) - 47;
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volWidth = 24 * gp.soundEffect.volScale;
        g2.fillRect(textX, textY, volWidth, 24);

        gp.cfg.saveConfig();

    }

    public void optionEndGameConfirm(int frameX, int frameY) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;
        currentDialogue = "Quit to title screen?";

        g2.drawString(currentDialogue, textX, textY);

        // yes
        String text = "YES";
        textX = getXCenteredLength(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                titleScreenState = 0;
                gp.gameState = gp.titleState;
            }
        }

        // no
        text = "NO";
        textX = getXCenteredLength(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }

    public void optionControl(int frameX, int frameY) {

        int textX;
        int textY;

        String text = "CONTROLS";
        textX = getXCenteredLength(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += (int) (gp.tileSize * 1.2);
        g2.drawString("MOVE", textX, textY);
        textY += (int) (gp.tileSize * 1.2);
        g2.drawString("ACCEPT/ATTACK", textX, textY);
        textY += (int) (gp.tileSize * 1.2);
        g2.drawString("SHOOT/CAST", textX, textY);
        textY += (int) (gp.tileSize * 1.2);
        g2.drawString("INVENTORY", textX, textY);
        textY += (int) (gp.tileSize * 1.2);
        g2.drawString("OPTIONS", textX, textY);
        textY += (int) (gp.tileSize * 1.2);

        textX = frameX + gp.tileSize * 6;
        textY = (int) (frameY + gp.tileSize * 2.2);

        textX -= 20;
        g2.drawString("WASD", textX, textY);
        textY += (int) (gp.tileSize * 1.2);

        g2.drawString("ENTER", textX, textY);
        textY += (int) (gp.tileSize * 1.2);

        g2.drawString("SPACE", textX, textY);
        textY += (int) (gp.tileSize * 1.2);

        g2.drawString("I", textX, textY);
        textY += (int) (gp.tileSize * 1.2);

        g2.drawString("ESC", textX, textY);
        textY += (int) (gp.tileSize * 1.2);

        // back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("BACK", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 3;
            }
        }

    }

    public void optionFullScreenNotif(int frameX, int frameY) {

        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialogue = "The change will take \neffect after \nrestarting the game.";

        for (String line : currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // BACK
        textY += gp.tileSize * 3;
        g2.drawString("BACK", textX, textY);
        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);
            if (gp.keyH.enterPressed) {
                subState = 0;
                // gp.fullScreen = false;
            }
        }
    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0f, 0f, 0f, 0.75f);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXCenteredLength(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public int getXAlignToRight(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
