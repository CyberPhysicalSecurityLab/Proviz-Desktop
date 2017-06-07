package proviz.models.uielements;

import proviz.BoardView;
import proviz.ProjectConstants;

import java.awt.*;

/**
 * Created by Burak on 10/11/16.
 */
public class LineModel {


    private BoardView startingNode;
    private BoardView endingNode;
    private Color lineColor;
    private int thickness;

    public LineModel(BoardView startingNode, BoardView endingNode, int thickness)
    {
        this.startingNode = startingNode;
        this.endingNode = endingNode;
        this.lineColor = ProjectConstants.init().getConnectionTypeColorHashMap().get(ProjectConstants.CONNECTION_TYPE.SERIAL);
        this.thickness =thickness;
    }

    public BoardView getStartingNode() {
        return startingNode;
    }

    public void setStartingNode(BoardView startingNode) {
        this.startingNode = startingNode;
    }

    public BoardView getEndingNode() {
        return endingNode;
    }

    public void setEndingNode(BoardView endingNode) {
        this.endingNode = endingNode;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
}
