package proviz.asci;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.models.devices.Pin;
import proviz.models.devices.Sensor;

import java.util.ArrayList;

/**
 * Created by Burak on 8/16/17.
 */
public class PinSelectionForSensorPanel extends BorderPane {

    private VBox asci;
    private VBox pinSelectionBox;
    private Label sensorImageLabel;
    private CodeGenerationTemplate codeGenerationTemplate;
    private Sensor candidateSensor;
    private VBox pinList;

    public PinSelectionForSensorPanel(CodeGenerationTemplate codeGenerationTemplate){
        this.codeGenerationTemplate = codeGenerationTemplate;
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

        pinSelectionBox = new VBox();
        Label nextStep = new Label();
        Label reminder = new Label();
        nextStep.setText("In order to generate your code, you must now assign your pin.");
        nextStep.setPadding(new Insets(10, 10, 10, 10));
        reminder.setText("Remember to complete the required pin assignment");
        reminder.setPadding(new Insets(10, 10, 10 , 10));
        VBox top = new VBox();
        top.setMinHeight(150);
        top.getChildren().addAll(nextStep, reminder);

        sensorImageLabel = new Label();
        setSensorImageLabel();
        sensorImageLabel.setPadding(new Insets(5, 5, 5, 5));
        sensorImageLabel.setMinHeight(150);

         pinList = new VBox();
        pinList.setStyle("-fx-border-color: lightgray");






        HBox bottom = new HBox();
        bottom.setPrefHeight(150);

        HBox middle = new HBox();
        middle.getChildren().addAll(sensorImageLabel, pinList);
        middle.setPrefHeight(150);
        pinSelectionBox.getChildren().addAll(top, middle, bottom);
        pinSelectionBox.setStyle("-fx-border-color: lightgray");
        pinSelectionBox.setPadding(new Insets(20,10,20,0));
        pinSelectionBox.setMinHeight(450);
        pinSelectionBox.setAlignment(Pos.CENTER);

        VBox v2 = new VBox();
        v2.getChildren().addAll(pinSelectionBox);
        v2.setAlignment(Pos.CENTER);
        v2.setPadding(new Insets(0, 10, 0, 5));


        this.setCenter(v2);

    }

    private void addPinRowtoPinList(HBox pinSelectionRow)
    {
        pinList.getChildren().add(pinSelectionRow);
    }

    public void setSensorImageLabel(){
        sensorImageLabel.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/sensors/images/Ezsonarsensor.jpg"))));

    }

    public void setSelectedSensor(Sensor sensor)
    {


        for (Pin sensorPin : sensor.getPins()) {
            ArrayList<Pin> pinCandidateList = new ArrayList<>();
            ArrayList<Pin> boardPins = null;
            switch (sensorPin.getType()) {
                case analog: {
                    boardPins = codeGenerationTemplate.getBoard().getAnalogPins();
                    break;
                }
                case comm: {
                    boardPins = codeGenerationTemplate.getBoard().getSerialPins();
                    break;
                }
                case digital: {
                    boardPins = codeGenerationTemplate.getBoard().getDigitalPins();
                    break;
                }
                case  i2c:
                {
                    boardPins = codeGenerationTemplate.getBoard().getI2CPins();
                    break;
                }
                case spibus:
                {
                    boardPins  = codeGenerationTemplate.getBoard().getSPIBusPins();
                    break;
                }
            }
            if (sensorPin.getType() != Pin.PINTYPE.ground && sensorPin.getType() != Pin.PINTYPE.vcc) {
                for (Pin boardPin : boardPins) {
                    if (boardPin.isAvailable()) {
                        pinCandidateList.add(boardPin);
                    }
                }


                addPinRowtoPinList(preparePinSelectionRow(sensorPin, pinCandidateList));

            }
        }


    }

    private HBox preparePinSelectionRow(Pin sensorPin, ArrayList<Pin> pinCandidateList)
    {
        Label pinAssignment = new Label();
        pinAssignment.setText(sensorPin.getName());
        pinAssignment.setAlignment(Pos.CENTER);
        ChoiceBox<String> pinAssignmentChoice = new ChoiceBox<>();
        for(Pin pin: pinCandidateList)
        pinAssignmentChoice.getItems().add(pin.toString());
        HBox borderedChoice1 = new HBox();
        borderedChoice1.setPrefWidth(400);
        pinAssignmentChoice.getSelectionModel().selectFirst();
        borderedChoice1.getChildren().addAll(pinAssignment, pinAssignmentChoice);
        borderedChoice1.setPadding(new Insets(10,10,10,0));
        return borderedChoice1;
    }


//    public void makeChangesOnSelectedSensor()
//    {
//        for (Component component : pinSelectionList.getComponents()) {
//            PinSelectionRow row = (PinSelectionRow) component;
//            Pin boardPin = row.getSelectedPin();
//            Pin sensorPin = row.getSensorPin();
//            sensorPin.setOrder(boardPin.getOrder());
//            boardPin.setAvailable(false);
//            sensorPin.setAvailable(false);
//            if(sensorPin.getDataDirection() == Pin.DATA_DIRECTION.OUTPUT)
//                boardPin.setDataDirection(Pin.DATA_DIRECTION.INPUT);
//            else if(sensorPin.getDataDirection() == Pin.DATA_DIRECTION.INPUT)
//                boardPin.setDataDirection(Pin.DATA_DIRECTION.OUTPUT);
//            boardPin.setIsrequired(sensorPin.isrequired());
//        }
//    }



}

