package proviz.models.tablet;

import proviz.ProjectConstants;

import java.util.ArrayList;

/**
 * Created by Burak on 5/12/17.
 */
public class ClusterBoard {
    private String boardId;
    private String boardName;
    /*
    0 - Arduino
    1 - Raspberry Pi
    2 - BeagleBone
     */
    private ProjectConstants.DEVICE_TYPE boardType;
    private ArrayList<ClusterSensor> sensors;

    public ClusterBoard() {
    }

    public String getBoardId() {

        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public ProjectConstants.DEVICE_TYPE getBoardType() {
        return boardType;
    }

    public void setBoardType(ProjectConstants.DEVICE_TYPE boardType) {
        this.boardType = boardType;
    }

    public String getBoardName() {
        return boardName;

    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public ArrayList<ClusterSensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<ClusterSensor> sensors) {
        this.sensors = sensors;
    }




}
