package proviz.models.connection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import proviz.ProjectConstants;
import proviz.models.devices.Board;
import proviz.models.webservice.requests.SensorData;

import java.util.ArrayList;

/**
 * Created by Burak on 1/12/17.
 */

public class IncomingDeviceData {

    private String deviceId;
    private String deviceName;
    private ArrayList<SensorData> sensors;
    private String dateTime;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public ArrayList<SensorData> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<SensorData> sensors) {
        this.sensors = sensors;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
