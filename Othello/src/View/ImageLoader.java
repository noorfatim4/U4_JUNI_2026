package View;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {

    public static final ImageIcon PLAYER1_ICON =
            loadAndScale("/resources/sonic.png");
    public static final ImageIcon PLAYER2_ICON =
            loadAndScale("/resources/shadow.png");

    private static ImageIcon loadAndScale(String path) {

        java.net.URL url = ImageLoader.class.getResource(path);

        if (url == null) {
            return null;
        }

        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage()
                .getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}