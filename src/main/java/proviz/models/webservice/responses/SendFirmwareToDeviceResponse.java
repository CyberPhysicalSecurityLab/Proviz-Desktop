package proviz.models.webservice.responses;

import proviz.ProjectConstants;
import proviz.models.webservice.requests.SensorData;

import java.util.ArrayList;

/**
 * Created by Burak on 1/12/17.
 */
public class SendFirmwareToDeviceResponse {
    private String deviceUniqueCode;
    private ProjectConstants.DEVICE_TYPE deviceType;
    private ArrayList<String> sensorUniqueCodes;
    private String code;

    public String getDeviceUniqueCode() {
        return deviceUniqueCode;
    }

    public void setDeviceUniqueCode(String deviceUniqueCode) {
        this.deviceUniqueCode = deviceUniqueCode;
    }

    public ProjectConstants.DEVICE_TYPE getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(ProjectConstants.DEVICE_TYPE deviceType) {
        this.deviceType = deviceType;
    }

    public ArrayList<String> getSensorUniqueCodes() {
        return sensorUniqueCodes;
    }

    public void setSensorUniqueCodes(ArrayList<String> sensorUniqueCodes) {
        this.sensorUniqueCodes = sensorUniqueCodes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
