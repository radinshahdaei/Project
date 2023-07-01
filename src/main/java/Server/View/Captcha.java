package Server.View;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.util.Random;

public class Captcha extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int[] digits = new int[4]; // get captcha code
        digits[0] = 0; digits[1] = 1; digits[2] = 2; digits[3] = 3; // example
        String word = "";
        for (int i = 0; i < 4; ++i) {
            word += String.valueOf(digits[i]);
        }
        JFrame jFrame = new JFrame("Captcha");
        BufferedImage image = createImage(word); // captcha image
        JLabel jLabel = new JLabel(new ImageIcon(image));
        jFrame.add(jLabel);
        jFrame.setSize(300 , 300);
        jFrame.setLocation(300 , 300);
        jFrame.setVisible(true);
    }

    private static BufferedImage createImage(String word) {
        BufferedImage bImg = null;

        try {
            int IMAGE_WIDTH = 300 , IMAGE_HEIGHT = 100 , TEXT_SIZE = 30;
            bImg = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB_PRE);
            Graphics2D g2 = bImg.createGraphics();

            g2.setColor(Color.black);
            g2.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

            Font font = new Font(Font.MONOSPACED, Font.BOLD, TEXT_SIZE);
            g2.setFont(font);
            g2.setColor(Color.white);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            char[] chars = word.toCharArray();
            int x = 40;
            int y = IMAGE_HEIGHT / 2 + TEXT_SIZE / 2;
            Random random = new Random();

            for (int i = 0; i < chars.length; i++) {
                char ch = chars[i];
                g2.drawString(String.valueOf(ch), x + font.getSize() * i, y
                        + (int) Math.pow(-1, i) * (TEXT_SIZE / 6));
                x += random.nextInt(15);
            }

            g2.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            bImg = null;
        }
        return bImg;
    }
}
