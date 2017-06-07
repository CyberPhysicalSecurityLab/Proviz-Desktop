package proviz.models.tablet;

import java.util.ArrayList;

/**
 * Created by Burak on 5/12/17.
 */
public class ClusterSensor {
    private ArrayList<ClusterSensorVariable> sensorVariables;
    private String sensorName;
    private String sensorId;

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


    public ArrayList<ClusterSensorVariable> getSensorVariables() {
        return sensorVariables;
    }

    public void setSensorVariables(ArrayList<ClusterSensorVariable> sensorVariables) {
        this.sensorVariables = sensorVariables;
    }

    public ClusterSensor() {}
}
