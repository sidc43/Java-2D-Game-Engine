package main;

import java.awt.image.*;
import java.awt.*;

public class UtilityTool {
    GamePanel gp;
    Graphics2D g2;
    NearChecker nc;

    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        // scale tile
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }
}
