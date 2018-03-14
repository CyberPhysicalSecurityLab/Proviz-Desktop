package proviz.asci;

/**
 * Created by Burak on 8/16/17.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import proviz.DeviceProgrammingScreen;
import proviz.MainEntrance;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.library.utilities.SwipePanelsAnimation;
import proviz.models.codegeneration.Variable;
import proviz.models.devices.Sensor;

import java.util.ArrayList;
public class SensorAddDialog extends BorderPane {

    private Button next, back;
    private ButtonBar buttonBar;
    private StackPane center;
    private boolean isLast;
    private Pane previousPage, currentPage;
    private SensorSelectionDialogPanel sensorSelectionDialogPanel;
    private PinSelectionForSensorPanel pinSelectionForSensorPanel;
    private FinalStageSensorAddPanel finalStageSensorAddPanel;
    private SwipePanelsAnimation spa;
    private Stage stage;
    private DeviceProgrammingScreen deviceProgrammingScreen;
    private Pane self;
    private MainEntrance mainEntrance;
    private Sensor sensorCandidate;
    private CodeGenerationTemplate codeGenerationTemplate;

    private Button closeButton;

    private ArrayList<String> allSensors; //make this of type Sensor

    public SensorAddDialog(MainEntrance mainEntrance,Stage stage, DeviceProgrammingScreen dps){
        this.mainEntrance = mainEntrance;
        deviceProgrammingScreen = dps;
        this.codeGenerationTemplate = dps.getCodeGenerationTemplate();

        this.stage = stage;
        self = this;
        initUI();
        buildButtonEvents();


    }

    private void initUI(){
        this.setMaxSize(945, 550);

        next = new Button();
        back = new Button();
        back.setText("Back");
        next.setText("Next");
        buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(back, next);
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
        HBox bottom = new HBox();
        bottom.getChildren().add(buttonBar);
        bottom.setAlignment(Pos.CENTER_RIGHT);
        this.setBottom(bottom);

        closeButton = new Button();
        ImageView closeX = new ImageView(new Image("/icons/small/close_x.png"));
        closeX.setFitWidth(15);
        closeX.setPreserveRatio(true);
        closeButton.setGraphic(closeX);
        HBox closeBox = new HBox(closeButton);
        closeBox.setPadding(new Insets(5));
        closeBox.setAlignment(Pos.CENTER_RIGHT);
        setTop(closeBox);

        center = new StackPane();
        sensorSelectionDialogPanel =
                new SensorSelectionDialogPanel();
        center.getChildren().add(sensorSelectionDialogPanel);
        currentPage = sensorSelectionDialogPanel;
        this.setCenter(center);

        pinSelectionForSensorPanel =
                new PinSelectionForSensorPanel(codeGenerationTemplate);
        finalStageSensorAddPanel =
                new FinalStageSensorAddPanel();

        spa = new SwipePanelsAnimation(buttonBar);

        allSensors = new ArrayList<>();
    }

    private void buildButtonEvents(){

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainEntrance.addToStack(false, self);
                mainEntrance.addToStack(true, deviceProgrammingScreen);
            }
        });

        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(currentPage == sensorSelectionDialogPanel){
                    spa.switchScreenF(pinSelectionForSensorPanel, sensorSelectionDialogPanel, center);

                    currentPage = pinSelectionForSensorPanel;
                    previousPage = sensorSelectionDialogPanel;
                    sensorCandidate = sensorSelectionDialogPanel.getCurrentSelection();

                    pinSelectionForSensorPanel.setSelectedSensor(sensorCandidate);




                }
                else if(currentPage == pinSelectionForSensorPanel){
                    spa.switchScreenF(finalStageSensorAddPanel, pinSelectionForSensorPanel, center);

                    currentPage = finalStageSensorAddPanel;
                    previousPage = pinSelectionForSensorPanel;

                    next.setText("Finish");


                }

                else if(currentPage == finalStageSensorAddPanel)
                {
                    allSensors.add(finalStageSensorAddPanel.getSensorSelection());
                    sensorCandidate.setParentBoard(codeGenerationTemplate.getBoard());
                    deviceProgrammingScreen.addSensorToScreen(sensorCandidate);
//                    Sensor sensor = new Sensor();
//                    sensor.setName("Burak");
//                    ArrayList<Variable> variables = new ArrayList<>();
//                    Variable variable = new Variable();
//                    variable.setPreferredName("Melih");
//                    variable.setCommunicationVariable(true);
//                    variables.add(variable);
//                    sensor.setVariables(variables);
//                    deviceProgrammingScreen.addSensorToScreen(sensor);
                    mainEntrance.addToStack(false, self);
                    mainEntrance.addToStack(true, deviceProgrammingScreen);
                    //stage.close();
                }

            }
        });

        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(currentPage == sensorSelectionDialogPanel){
                    mainEntrance.addToStack(false, self);
                    mainEntrance.addToStack(true, deviceProgrammingScreen);
                }
                else if(currentPage == pinSelectionForSensorPanel){
                    spa.switchScreenB(sensorSelectionDialogPanel, pinSelectionForSensorPanel, center);

                    currentPage = sensorSelectionDialogPanel;
                    previousPage = pinSelectionForSensorPanel;
                }
                else if(currentPage == finalStageSensorAddPanel){
                    spa.switchScreenB(pinSelectionForSensorPanel, finalStageSensorAddPanel, center);

                    next.setText("Next");

                    currentPage = pinSelectionForSensorPanel;
                    previousPage = finalStageSensorAddPanel;
                }
            }
        });


    }

    public ArrayList<String> getAllSensors() {
        return allSensors;
    }
}

