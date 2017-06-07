package proviz.models.connection;

import proviz.ProjectConstants;

/**
 * Created by Burak on 2/8/17.
 */
public class ReceivedSensorData {

    private String sensorId;
    private String sensorUnit;
    private double sensorValue;
    private ProjectConstants.SENSOR_STATUS sensorStatus;
    private String variableName;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorUnit() {
        return sensorUnit;
    }

    public void setSensorUnit(String sensorUnit) {
        this.sensorUnit = sensorUnit;
    }

    public double getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(double sensorValue) {
        this.sensorValue = sensorValue;
    }


    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public ProjectConstants.SENSOR_STATUS getSensorStatus() {
        return sensorStatus;
    }

    public void setSensorStatus(ProjectConstants.SENSOR_STATUS sensorStatus) {
        this.sensorStatus = sensorStatus;
    }
}
