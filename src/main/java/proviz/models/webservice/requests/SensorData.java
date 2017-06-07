package proviz.models.webservice.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import proviz.ProjectConstants;

/**
 * Created by Burak on 1/12/17.
 */
public class SensorData {

    private String sensorName;
    private String sensorId;
    private String sensorUnit;
    private double sensorValue;
    private String variableName;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
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

    public String getSensorUnit() {
        return sensorUnit;
    }

    public void setSensorUnit(String sensorUnit) {
        this.sensorUnit = sensorUnit;
    }
}
