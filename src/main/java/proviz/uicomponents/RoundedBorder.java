package proviz.uicomponents;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Created by Burak on 1/14/17.
 */
public class RoundedBorder extends AbstractBorder {
    private  Color color;
    private int roundRadius = -1;

    public RoundedBorder(Color color)
    {
        this.color = color;

    }
    public void setRoundRadius(int roundRadius)
    {
    this.roundRadius = roundRadius;
    }

    @Override public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Shape border = getBorderShape(x, y, width - 1, height - 1);
        g2.setPaint(new Color(0x0, true));
        Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
        corner.subtract(new Area(border));
        g2.fill(corner);
        g2.setPaint(color);
        g2.draw(border);
        g2.dispose();
    }
    public Shape getBorderShape(int x, int y, int w, int h) {
        if(roundRadius == -1)
         roundRadius = h;
        return new RoundRectangle2D.Double(x, y, w, h, roundRadius, roundRadius);
    }
    @Override public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }
    @Override public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, 8, 4, 8);
        return insets;
    }
}
