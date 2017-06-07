package proviz;

import proviz.models.uielements.LineModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 10/11/16.
 */
public class ConnectionLinesComponent extends JComponent {

ArrayList<LineModel> lineModels;

    public ConnectionLinesComponent()
    {
        lineModels = new ArrayList<>();

    }

    public void addLine(LineModel lineModel)
    {
        lineModels.add(lineModel);
        repaint();
    }
    public void clearLines()
    {
        lineModels.clear();
        repaint();
    }
    public void removeLine(LineModel lineModel)
    {
        lineModels.remove(lineModel);
        repaint();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        repaint();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(LineModel lineModel: lineModels)
        {
            g.setColor(lineModel.getLineColor());
          BoardView startingComponent =   lineModel.getStartingNode();
            BoardView endingComponent = lineModel.getEndingNode();
            int x1 = startingComponent.getX() + startingComponent.getWidth()/2;
            int y1 = startingComponent.getY() + startingComponent.getHeight()/2;
            int x2 = endingComponent.getX() + endingComponent.getWidth()/2;
            int y2= endingComponent.getY() + endingComponent.getHeight()/2;
            g.drawLine(x1,y1,x2,y2);
        }
    }
}
