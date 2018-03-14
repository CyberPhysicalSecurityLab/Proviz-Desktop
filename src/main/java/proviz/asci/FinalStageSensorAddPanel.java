package proviz.asci;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Burak on 8/16/17.
 */
public class FinalStageSensorAddPanel extends BorderPane {

    private Label sensorImageLabel;
    private VBox pinSelectionBox;
    private VBox asci;
    private BorderPane borderedChoice;
    private Label pinAssignment;

    public FinalStageSensorAddPanel(){

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
        Label finalStep = new Label();
        finalStep.setText("Please check your pin assignment.");
        finalStep.setPadding(new Insets(10, 10, 10, 10));
        VBox top = new VBox();
        top.setMinHeight(150);
        top.getChildren().addAll(finalStep);

        sensorImageLabel = new Label();
        setSensorImageLabel();
        sensorImageLabel.setPadding(new Insets(5, 5, 5, 5));
        sensorImageLabel.setMinHeight(150);

        setChoiceSelection();

        HBox bottom = new HBox();
        bottom.setPrefHeight(150);

        HBox middle = new HBox();
        middle.getChildren().addAll(sensorImageLabel, borderedChoice);
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

    public void setSensorImageLabel(){ //will take ID of selected pin
        sensorImageLabel.setGraphic(new ImageView(
                new Image(getClass().getResourceAsStream("/sensors/images/Ezsonarsensor.jpg"))));
    }

    public void setChoiceSelection(){ //will take ID of selected pin to set borderedChoice box
        pinAssignment = new Label();
        pinAssignment.setText("12C Communication ");
        pinAssignment.setAlignment(Pos.CENTER);
        Label pinAssignmentChoice = new Label();
        pinAssignmentChoice.setText("12C_0");
        Label label3 = new Label();
        label3.setText("Your pin assignment.");
        label3.setAlignment(Pos.CENTER);
        borderedChoice = new BorderPane();
        borderedChoice.setMinHeight(sensorImageLabel.getHeight());
        borderedChoice.setPrefWidth(400);
        borderedChoice.setTop(label3);
        borderedChoice.setCenter(pinAssignmentChoice);
        borderedChoice.setStyle("-fx-border-color: lightgray");
        borderedChoice.setPadding(new Insets(10, 10, 10, 10));

    }

    public String getSensorSelection(){
        return pinAssignment.getText();
    }
}

