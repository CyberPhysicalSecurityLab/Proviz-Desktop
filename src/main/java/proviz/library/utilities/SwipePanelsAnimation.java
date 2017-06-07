package proviz.library.utilities;

import com.intellij.uiDesigner.core.DimensionInfo;
import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 1/18/17.
 */
public class SwipePanelsAnimation {

    private int speed;
    private ArrayList<JPanel> panels;
    private JPanel parentPanel;
    private final Object lock = new Object();
    private boolean isSlideInProgress = false;
    private int activePanelIndex;
    private int width;
    private int height;


    public SwipePanelsAnimation(JPanel parentPanel,ArrayList<JPanel> panelArrayList)
    {
        this.parentPanel = parentPanel;
        this.panels = panelArrayList;
        speed = 100;
        initUI();
    }

    public JPanel getActivePanel()
    {
        return panels.get(activePanelIndex);
    }

    private void initUI()
    {
        parentPanel.setLayout(null);
          width = parentPanel.getPreferredSize().width;
          height = parentPanel.getPreferredSize().height;
        Dimension dimension = new Dimension(width,height);
        for(int i=0; i < panels.size(); i++)
        {
            Point point = new Point((i*width),0);
            panels.get(i).setLocation(point);
            panels.get(i).setPreferredSize(dimension);
            panels.get(i).setMaximumSize(dimension);
            panels.get(i).setMinimumSize(dimension);
            panels.get(i).setBounds(point.x,point.y,width,height);
            parentPanel.add(panels.get(i));
            panels.get(i).revalidate();
        }
        activePanelIndex = 0;

        parentPanel.revalidate();

    }

    public int getActivePanelIndex() {
        return activePanelIndex;
    }

    public void setActivePanelIndex(int activePanelIndex) {
        this.activePanelIndex = activePanelIndex;
    }

    private enum SLIDE_DIRECTION {
        LEFT, RIGHT
    }
    public void slide(SLIDE_DIRECTION slideType)
    {
        if (!isSlideInProgress) {
            isSlideInProgress = true;
            final Thread t0 = new Thread(new Runnable() {
                @Override
                public void run() {
                    parentPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    slidePanel(slideType);
                    parentPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            });
            t0.setDaemon(true);
            t0.start();
        }
        else {
            Toolkit.getDefaultToolkit().beep();
        }
    }

    public void hidePanel(JPanel panel)
    {
        int index = panels.indexOf(panel);


        for(int i = (index); i <panels.size(); i++)
        {
            if(i < panels.size()-1) {
                JPanel aheadPanel = panels.get(i + 1);

                Point oldLocation = aheadPanel.getLocation();
                aheadPanel.setLocation((int)oldLocation.getX()-width,(int)oldLocation.getY());
            }
            if(i == (panels.size()-1))
            {
                JPanel toberemovedPanel = panels.get(index);
                Point formerLastPanelLocation = panels.get(i).getLocation();
                toberemovedPanel.setLocation((int)(formerLastPanelLocation.getX()+width),(int)formerLastPanelLocation.getY());
                panels.remove(toberemovedPanel);
                panels.add(toberemovedPanel);
            }

        }

        parentPanel.revalidate();

    }
    private void slidePanel(SLIDE_DIRECTION slideType) {
        synchronized (lock) {
            if(slideType == SLIDE_DIRECTION.LEFT) activePanelIndex++;
            if(slideType == SLIDE_DIRECTION.RIGHT) activePanelIndex--;
            isSlideInProgress = true;
            int step = 0;
            step = (int) (((float) parentPanel.getWidth() / (float) Toolkit.getDefaultToolkit().getScreenSize().width) * 40.0f);
            if (step < 5)
                step = 5;
            final int max = parentPanel.getWidth();
            final long t0 = System.currentTimeMillis();
            for (int i = 0; i != (max / step); i++) {
                switch (slideType) {
                    case LEFT: {
                        for(int j =0; j< panels.size() ;j++)
                        {
                            Point point = panels.get(j).getLocation();

                            if(i == ((max/step)-1))
                            {
                                point.x -=(parentPanel.getWidth() - (step*((max/step)-1)));
                            }
                            else
                                point.x -= step;
                            panels.get(j).setLocation(point);
                            panels.get(j).setBounds(point.x,point.y,panels.get(j).getWidth(),panels.get(j).getHeight());
                        }
                        break;
                    }
                    case RIGHT: {
                        for(int j =0; j< panels.size() ;j++)
                        {
                            Point point = panels.get(j).getLocation();
                            point.x += step;
                            panels.get(j).setLocation(point);
                            panels.get(j).setBounds(point.x,point.y,panels.get(j).getWidth(),panels.get(j).getHeight());
                        }
                        break;
                    }

                }
                try {
                    Thread.sleep(500 / (max / step));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }



        }



        isSlideInProgress = false;
    }

    public void LeftSwipe()
    {
        if(activePanelIndex!=(panels.size()-1))
        slide(SLIDE_DIRECTION.LEFT);
    }

    public void RightSwipe()
    {
        if(activePanelIndex!=0)
        slide(SLIDE_DIRECTION.RIGHT);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
