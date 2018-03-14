package proviz.asci;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import proviz.devicedatalibrary.DataManager;
import proviz.models.devices.Sensor;

import java.util.ArrayList;

/**
 * Created by Burak on 8/16/17.
 */
public class SensorSelectionDialogPanel extends BorderPane {

    private Pane contentPane;
    private VBox asci;
    private VBox sensorAdd;
    private Label sensorImageLabel;
    private Label sensorNameLabel;
    private Label sensorDescriptionLabel;
    private ChoiceBox<Sensor> sensorChoice;



    public SensorSelectionDialogPanel(){

        initUI();

    }

    private void initUI(){
        this.setPrefSize(945, 520);
        asci = new VBox();
        Label label1 = new Label();
        label1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/images/asci.png"))));
        label1.setPadding(new Insets(10,10,10,10));
        Label label2 = new Label();
        label2.setText("Asci");
        label2.setAlignment(Pos.CENTER);
        label2.setMaxWidth(Double.MAX_VALUE);
        label2.setPadding(new Insets(10,10,10,10));
        asci.getChildren().addAll(label1, label2);
        asci.setPadding(new Insets(20,10,20,10));
        asci.setStyle("-fx-border-color: lightgray");
        asci.setAlignment(Pos.CENTER);
        asci.setMinHeight(450);
        VBox v = new VBox();
        v.setPadding(new Insets(0,10,0,10));
        v.setAlignment(Pos.CENTER);
        v.getChildren().add(asci);
        this.setLeft(v);

        sensorAdd = new VBox();
        Label welcome = new Label();
        Label pickOne = new Label();

        welcome.setText("Hi, my name is Asci. You will make great things in my visual programming kitchen.");
        VBox top = new VBox();
        top.getChildren().add(welcome);
        top.setMinHeight(150);
        top.setPadding(new Insets(10,10,10,10));
        sensorDescriptionLabel = new Label();
        pickOne.setText("First, you must pick a sensor from the list below.");
        sensorChoice = new ChoiceBox<>();
        sensorChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sensor>() {
            @Override
            public void changed(ObservableValue<? extends Sensor> observable, Sensor oldValue, Sensor newValue) {
                fillSensorQuickView(newValue);
            }
        });
        fillSensorList();
        VBox middle = new VBox();
        middle.getChildren().addAll(pickOne, sensorChoice);
        middle.setMinHeight(150);
        middle.setPadding(new Insets(10,10,10,10));




        VBox bottom = new VBox();
        bottom.getChildren().addAll(sensorDescriptionLabel);
        bottom.setMinHeight(150);
        bottom.setPadding(new Insets(10,10,10,10));


        sensorAdd.getChildren().addAll(top, middle, bottom);
        sensorAdd.setStyle("-fx-border-color: lightgray");
        sensorAdd.setPadding(new Insets(20,10,20,0));
        sensorAdd.setMinHeight(450);
        VBox v2 = new VBox();
        v2.setPadding(new Insets(0, 10, 0, 5));
        v2.getChildren().add(sensorAdd);
        v2.setAlignment(Pos.CENTER);
        this.setCenter(v2);
    }

    private void fillSensorList() {
        ArrayList<Sensor> sensors  = DataManager.getInstance().getSensors();
        for(Sensor sensor:sensors)
        {
            sensorChoice.getItems().add(sensor);
        }
        sensorChoice.getSelectionModel().selectFirst();
    }

    public Sensor getCurrentSelection()
    {
        return sensorChoice.getSelectionModel().getSelectedItem();
    }

    private void fillSensorQuickView(Sensor selectedSensor) {
        sensorDescriptionLabel.setText(  selectedSensor.getName() + "\n"+ selectedSensor.getDescription() );
    }

}
