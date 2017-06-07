package proviz.models.webservice.requests;

import proviz.ProjectConstants;

/**
 * Created by Burak on 1/12/17.
 */
public class SendFirmwareToDeviceRequest {
    private String sensorName;
    private String sensorId;
    private String sensorUnit;
    private double sensorValue;

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
}
