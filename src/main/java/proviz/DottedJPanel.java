package proviz;

import javafx.scene.paint.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * Created by Burak on 10/9/16.
 */
public class DottedJPanel extends JPanel {


        public void init() {
            setOpaque(true);
            setBackground(new Color(1f,0f,0f,0f ));
        }

        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            BufferedImage bi = new BufferedImage(30, 30,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D big = bi.createGraphics();
            big.setColor(Color.LIGHT_GRAY);
            big.fillOval(0, 0, 10, 10);
            TexturePaint circles = new TexturePaint(bi,new Rectangle(0,0,10,10));
             g2.setPaint(circles);

           Rectangle rect = new Rectangle(0,0,getWidth(),getHeight());


            g2.fill(rect);

        }





}
