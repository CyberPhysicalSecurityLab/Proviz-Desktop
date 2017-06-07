package proviz;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

/**
 * Created by Burak on 9/9/16.
 */
public class PDropListener extends DropTargetAdapter {
    private DropTarget dropTarget;
    private PView pView;
    private LiveDataTableModel liveDataTableModel;

    public PDropListener(PView pView,LiveDataTableModel liveDataTableModel)
    {
        this.liveDataTableModel = liveDataTableModel;
        this.pView = pView;
        dropTarget = new DropTarget(pView, DnDConstants.ACTION_MOVE,this,true,null);
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        BoardView boardView =null;
        if(dtde.getCurrentDataFlavors()[0].getHumanPresentableName().compareTo("Ardunio") ==0)
            boardView = new BoardView(pView, ProjectConstants.DEVICE_TYPE.ARDUINO,liveDataTableModel);

        else if(dtde.getCurrentDataFlavors()[0].getHumanPresentableName().compareTo("Beaglebone") ==0)
        boardView = new BoardView(pView, ProjectConstants.DEVICE_TYPE.BEAGLEBONE,liveDataTableModel);

        else if(dtde.getCurrentDataFlavors()[0].getHumanPresentableName().compareTo("Raspberry") ==0)
        boardView = new BoardView(pView, ProjectConstants.DEVICE_TYPE.RASPBERRY_PI,liveDataTableModel);

        boardView.setDeviceNumber(ProjectConstants.init().getIncrementedDeviceID());

        boardView.setStatusCode(ProjectConstants.STATUS_CODE.CONNECTION_NOT_ESTABLISHED);
        boardView.setLocation(dtde.getLocation());
        boardView.setBounds(dtde.getLocation().x-50,dtde.getLocation().y-50,125,120);
        pView.add(boardView,new Integer(3));
        pView.bindLine2Computer(boardView);
        pView.insertSensorViewList(boardView);
        pView.invalidate();
        pView.repaint(50L);



    }
}
