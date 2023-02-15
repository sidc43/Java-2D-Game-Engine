package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed, enterPressed, shotKeyPressed;

    // debug
    boolean showDebugText = false;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        // PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }

        // PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        // DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        // CHARACTER STAT STATE
        else if (gp.gameState == gp.characterStatState) {
            characterStatState(code);
        }

        // EVENT STATE
        else if (gp.gameState == gp.eventState) {
            eventState(code);
        }

        // OPTION STATE
        else if (gp.gameState == gp.optionsState) {
            optionsState(code);
        }

    }

    public void titleState(int code) {

        if (gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.playSoundEffect(gp.scroll);
                gp.ui.commandNum--;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.playSoundEffect(gp.scroll);
                downPressed = true;
                gp.ui.commandNum++;
            }
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0:
                        gp.ui.titleScreenState = 1;
                        gp.playSoundEffect(gp.selectOption);
                        break;
                    case 1:
                        break;
                    case 2:
                        System.exit(0);
                        break;
                }
            }
        } else if (gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.playSoundEffect(gp.scroll);
                gp.ui.commandNum--;
            }
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.playSoundEffect(gp.scroll);
                gp.ui.commandNum++;
            }
            if (gp.ui.commandNum > 3) {
                gp.ui.commandNum = 0;
            }
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 3;
            }

            if (code == KeyEvent.VK_ENTER) {
                switch (gp.ui.commandNum) {
                    case 0:
                        System.out.println("Do some melee specific stuff");
                        gp.gameState = gp.playState;
                        gp.player.strength = 2;
                        gp.playSoundEffect(gp.classSelect);
                        break;
                    case 1:
                        System.out.println("Do some magic specific stuff");
                        gp.player.maxMana = 5;
                        gp.gameState = gp.playState;
                        gp.playSoundEffect(gp.classSelect);
                        break;
                    case 2:
                        System.out.println("Do some ranged specific stuff");
                        gp.gameState = gp.playState;
                        gp.playSoundEffect(gp.classSelect);
                        break;
                    case 3:
                        gp.playSoundEffect(gp.back);
                        gp.ui.titleScreenState = 0;
                        gp.ui.commandNum = 0;
                        break;
                }
            }
        }
    }

    public void playState(int code) {

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;

        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_E) {
            ePressed = true;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_I) {
            gp.gameState = gp.characterStatState;
        }
        if (code == KeyEvent.VK_T) {
            if (!showDebugText) {
                showDebugText = true;
            } else if (showDebugText) {
                showDebugText = false;
            }

        }

        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        }

        if (code == KeyEvent.VK_R) {
            gp.tileM.loadMap("/maps/WorldV2.txt");
        }
    }

    public void pauseState(int code) {

        if (code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_P) {
            gp.playSoundEffect(gp.escape);
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.playSoundEffect(gp.scroll);
            gp.ui.pauseCmdNum--;
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.playSoundEffect(gp.scroll);
            downPressed = true;
            gp.ui.pauseCmdNum++;
        }
        if (gp.ui.pauseCmdNum > 1) {
            gp.ui.pauseCmdNum = 0;
        }
        if (gp.ui.pauseCmdNum < 0) {
            gp.ui.pauseCmdNum = 1;
        }

        if (code == KeyEvent.VK_ENTER) {
            switch (gp.ui.pauseCmdNum) {
                case 0:
                    gp.playSoundEffect(gp.selectOption);
                    gp.gameState = gp.playState;
                    break;
                case 1:
                    System.exit(0);
                    break;
            }
        }
    }

    public void dialogueState(int code) {

        if (code == KeyEvent.VK_E) {
            gp.gameState = gp.playState;
        }
    }

    public void eventState(int code) {

        if (code == KeyEvent.VK_E) {
            gp.gameState = gp.playState;
        }
    }

    public void characterStatState(int code) {

        if (code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.slotRow--;
            if (gp.ui.slotRow < 0) {
                gp.ui.slotRow = 0;
            }
            if (gp.ui.slotRow > 4) {
                gp.ui.slotRow = 4;
            }
            gp.playSoundEffect(gp.cursor);
        }
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            gp.ui.slotCol--;
            if (gp.ui.slotCol < 0) {
                gp.ui.slotCol = 0;
            }
            if (gp.ui.slotCol > 4) {
                gp.ui.slotCol = 4;
            }
            gp.playSoundEffect(gp.cursor);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.slotRow++;
            if (gp.ui.slotRow < 0) {
                gp.ui.slotRow = 0;
            }
            if (gp.ui.slotRow > 3) {
                gp.ui.slotRow = 3;
            }
            gp.playSoundEffect(gp.cursor);
        }
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            gp.ui.slotCol++;
            if (gp.ui.slotCol < 0) {
                gp.ui.slotCol = 0;
            }
            if (gp.ui.slotCol > 4) {
                gp.ui.slotCol = 4;
            }
            gp.playSoundEffect(gp.cursor);
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }

    public void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCmdNum = 0;
        switch (gp.ui.subState) {
            case 0:
                maxCmdNum = 5;
                break;
            case 3:
                maxCmdNum = 1;
                break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSoundEffect(gp.cursor);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCmdNum;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSoundEffect(gp.cursor);
            if (gp.ui.commandNum > maxCmdNum) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.ui.subState == 0) {
                // MUSIC
                if (gp.ui.commandNum == 1 && gp.music.volScale > 0) {
                    gp.music.volScale--;
                    gp.music.checkVol();
                    gp.playSoundEffect(gp.cursor);
                }

                // SFX
                if (gp.ui.commandNum == 2 && gp.soundEffect.volScale > 0) {
                    gp.soundEffect.volScale--;
                    gp.playSoundEffect(gp.cursor);
                }
            }
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.ui.subState == 0) {

                // MUSIC
                if (gp.ui.commandNum == 1 && gp.music.volScale < 5) {
                    gp.music.volScale++;
                    gp.music.checkVol();
                    gp.playSoundEffect(gp.cursor);
                }

                // SFX
                if (gp.ui.commandNum == 2 && gp.soundEffect.volScale < 5) {
                    gp.soundEffect.volScale++;
                    gp.playSoundEffect(gp.cursor);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;

        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;

        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;

        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            shotKeyPressed = false;
        }
    }
}
