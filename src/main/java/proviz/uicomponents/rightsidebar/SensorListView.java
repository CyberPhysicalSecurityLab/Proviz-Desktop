package proviz.uicomponents.rightsidebar;


import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * Created by Burak on 1/14/17.
 */
public class SensorListView extends VBox {

    SidebarSection sensor;
    ListView sensorContent;

    public SensorListView(){

        sensor = new SidebarSection();
        sensor.addSectionTitle("Sensors");

        sensorContent = new ListView();
        sensor.setCenter(sensorContent);


        this.getChildren().add(sensor);

    }


    //add logic to set the content in Sensor view
}

