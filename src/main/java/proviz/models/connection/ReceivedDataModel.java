package proviz.models.connection;

import proviz.models.webservice.requests.SensorData;

import java.util.ArrayList;

/**
 * Created by Burak on 2/8/17.
 */
public class ReceivedDataModel {
    private ArrayList<ReceivedSensorData> sensors;
    private String dateTime;

    public ArrayList<ReceivedSensorData> getSensors() {
        return sensors;
    }

    public void addReceivedSensorData(ReceivedSensorData receivedSensorData)
    {
        sensors.add(receivedSensorData);
    }




    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setSensors(ArrayList<ReceivedSensorData> sensors) {
        this.sensors = sensors;
    }
}
