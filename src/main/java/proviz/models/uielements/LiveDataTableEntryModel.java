package proviz.models.uielements;

import proviz.ProjectConstants;

/**
 * Created by Burak on 2/9/17.
 */
public class LiveDataTableEntryModel {

    private String boardUserFriendlyName;
    private String sensorName;
    private String dataName;
    private double value;
    private ProjectConstants.SENSOR_STATUS sensorStatus;

    public LiveDataTableEntryModel(String variableName)
    {
        this.dataName = variableName;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ProjectConstants.SENSOR_STATUS getSensorStatus() {
        return sensorStatus;
    }

    public void setSensorStatus(ProjectConstants.SENSOR_STATUS sensorStatus) {
        this.sensorStatus = sensorStatus;
    }

    public String getBoardUserFriendlyName() {
        return boardUserFriendlyName;
    }

    public void setBoardUserFriendlyName(String boardUserFriendlyName) {
        this.boardUserFriendlyName = boardUserFriendlyName;
    }
}
