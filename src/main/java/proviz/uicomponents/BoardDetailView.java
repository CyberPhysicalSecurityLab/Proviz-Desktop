package proviz.uicomponents;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Burak on 1/15/17.
 */
public class BoardDetailView extends JPanel {
    private JLabel label;

    public BoardDetailView(int width, int height)
    {
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        Dimension dimension = new Dimension(width,height);
        setSize(width,height);
        label = new JLabel("Device Properties");
        add(label);
    }
}
