package proviz.library.utilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Burak on 1/22/17.
 */
public class FloatShowHideAnimation {

    private int speed;
    private JPanel parentPanel;
    private JPanel targetPanel;
    private boolean isSlideInProgress = false;
    private Object lock = new Object();
    private int width;
    private int height;


    public FloatShowHideAnimation(JPanel parentPanel, JPanel targetPanel)
    {
        this.parentPanel = parentPanel;
        this.targetPanel = targetPanel;
        width = targetPanel.getPreferredSize().width;
        height = targetPanel.getPreferredSize().height;
    }

    public void start()
    {
        if(!isSlideInProgress)
        {
            isSlideInProgress = true;
            final Thread t0 = new Thread(new Runnable() {
                @Override
                public void run() {
                    leftSlidePanel();
                }
            });
            t0.setDaemon(true);
            t0.start();
        }
    }

    private void leftSlidePanel()
    {
        synchronized (lock)
        {
            Point initialLocation = new Point(parentPanel.getWidth(),0);


            targetPanel.setVisible(true);


            }
        }
}
