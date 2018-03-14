package proviz.uicomponents;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import proviz.models.codegeneration.Variable;
import proviz.models.devices.Board;

public class SensorAddDetailView extends Pane {

    private Pane mainPane;
    /*private Board board;
    private Bound bound;*/

    private TextField upperBoundTextField;
    private TextField lowerBoundTextField;
    private TextField sampleRateTextField;
    Text upperBoundText,lowerBoundText,sampleRateText ;

    private Variable variable;

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
        upperBoundTextField.setText(Double.toString(variable.getBound().getUpperValue()));
        lowerBoundTextField.setText(Double.toString(variable.getBound().getLowerValue()));

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        sampleRateTextField.setText(Integer.toString(board.getSampleRate()));

    }

    private Board board;


    private GridPane gridPane;

    public SensorAddDetailView(){

        mainPane = new Pane();
        getChildren().add(mainPane);

        upperBoundText = new Text("Upper Bound: ");
        lowerBoundText = new Text("Lower Bound: ");
        sampleRateText = new Text("Sample Rate: ");


        gridPane = new GridPane();
        mainPane.getChildren().add(gridPane);

        mainPane.setPadding(new Insets(10));
        mainPane.setStyle("-fx-background-color: lightgray");

        upperBoundTextField = initializeView(upperBoundText,1);
        lowerBoundTextField = initializeView(lowerBoundText,2);
        sampleRateTextField = initializeView(sampleRateText,3);

        upperBoundTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                variable.getBound().setUpperValue(Double.parseDouble(newValue));
            }
            else
            {
                if(newValue.length() != 0)
                upperBoundTextField.setText(oldValue);
            }
        });

        lowerBoundText.textProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
                variable.getBound().setLowerValue(Double.parseDouble(newValue));
            }
            else
            {
                if(newValue.length() != 0)
                lowerBoundText.setText(oldValue);
            }
        });

        sampleRateTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.matches("\\d+")) {
                board.setSampleRate(Integer.parseInt(newValue));
            }
            else
            {
                if(newValue.length() != 0)
                sampleRateTextField.setText(oldValue);
            }
        });
    }



    public void hideSampleRateFieldTextField()
    {
        gridPane.getChildren().removeAll(sampleRateText,sampleRateTextField);

    }
    public void hideUpperBoundFieldTextField()
    {
        gridPane.getChildren().removeAll(upperBoundText,upperBoundTextField);
    }
    public void hideLowerBoundFieldTextField() {
        gridPane.getChildren().removeAll(lowerBoundText, lowerBoundTextField);
    }
    public void showSampleRateFieldTextField()
    {
        if((gridPane.getChildren().contains(sampleRateText) == false && gridPane.getChildren().contains(sampleRateTextField) == false)) {
            gridPane.add(sampleRateText, 0, 1);
            gridPane.add(sampleRateTextField, 1, 1);
        }
    }
    public void showUpperBoundFieldTextField()
    {
        if((gridPane.getChildren().contains(upperBoundText) == false && gridPane.getChildren().contains(upperBoundTextField) == false)) {
            gridPane.add(upperBoundText, 0, 1);
            gridPane.add(upperBoundTextField, 1, 1);
        }
    }
    public void showLowerBoundFieldTextField() {
        if ((gridPane.getChildren().contains(lowerBoundText) == false && gridPane.getChildren().contains(lowerBoundTextField) == false)) {

            gridPane.add(lowerBoundText, 0, 2);
            gridPane.add(lowerBoundTextField, 1, 2);
        }
    }



    private TextField initializeView(Text text, int row){



        TextField textField = new TextField();

        gridPane.setHgap(5);
        gridPane.setVgap(2);





        return textField;
    }




}
