package proviz.thirdpartyconnections.tablet;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Created by Burak on 5/13/17.
 */
public class TabletConnectionFinalPanel extends Pane {

    private Label targetIpAddress;
    private Label searchingDeviceTextBox;
    private Label iniatingSessionTextBox;
    private Label synchingCurrentTopologyTextBox;
    private Label searchingTargetDeviceStatusImage;
    private Label iniatingSessionStatusImage;
    private Label synchingCurrentsearchingTargetDeviceStatusImage;
    private Label informingBoardslabel;
    private Label informingboardslabelImage;

    private boolean isSearchingTargetFinishedSuccess = true;
    private boolean isSynchingCurrentTopologyFinishedSuccess = true;
    private boolean isInformingBoardsFinishedSuccess = true;
    private boolean isSessionIniationFinishedSuccess = true;

    public TabletConnectionFinalPanel(){
        initUI();
    }

    public boolean isSearchingTargetFinishedSuccess() {
        return isSearchingTargetFinishedSuccess;
    }

    public void setSearchingTargetFinishedSuccess(boolean searchingTargetFinishedSuccess) {
        isSearchingTargetFinishedSuccess = searchingTargetFinishedSuccess;
    }

    public boolean isSynchingCurrentTopologyFinishedSuccess() {
        return isSynchingCurrentTopologyFinishedSuccess;
    }

    public void setSynchingCurrentTopologyFinishedSuccess(boolean synchingCurrentTopologyFinishedSuccess) {
        isSynchingCurrentTopologyFinishedSuccess = synchingCurrentTopologyFinishedSuccess;
    }

    public boolean isInformingBoardsFinishedSuccess() {
        return isInformingBoardsFinishedSuccess;
    }

    public void setInformingBoardsFinishedSuccess(boolean informingBoardsFinishedSuccess) {
        isInformingBoardsFinishedSuccess = informingBoardsFinishedSuccess;
    }

    public boolean isSessionIniationFinishedSuccess() {
        return isSessionIniationFinishedSuccess;
    }

    public void setSessionIniationFinishedSuccess(boolean sessionIniationFinishedSuccess) {
        isSessionIniationFinishedSuccess = sessionIniationFinishedSuccess;
    }



    public void SearchingTargetDeviceFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            searchingTargetDeviceStatusImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/error_small.png"))));
            searchingTargetDeviceStatusImage.setVisible(true);
            isSearchingTargetFinishedSuccess = true;
        }
        else
        {
            searchingTargetDeviceStatusImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/error_small.png"))));
            searchingTargetDeviceStatusImage.setVisible(true);
            isSearchingTargetFinishedSuccess = false;

        }
    }

    public void SynchingCurrentTopologyFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            synchingCurrentsearchingTargetDeviceStatusImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/checked_small.png"))));
            synchingCurrentsearchingTargetDeviceStatusImage.setVisible(true);
            isSynchingCurrentTopologyFinishedSuccess = true;
        }
        else
        {
            synchingCurrentsearchingTargetDeviceStatusImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/error_small.png"))));
            synchingCurrentsearchingTargetDeviceStatusImage.setVisible(true);
            isSynchingCurrentTopologyFinishedSuccess = false;
        }
    }

    public void InformingBoardsFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            informingboardslabelImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/error_small.png"))));
            informingboardslabelImage.setVisible(true);
            isInformingBoardsFinishedSuccess = true;
        }
        else
        {
            informingboardslabelImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/error_small.png"))));
            informingboardslabelImage.setVisible(true);
            isInformingBoardsFinishedSuccess = false;
        }
    }

    public void SessionInitiationFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            iniatingSessionStatusImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/error_small.png"))));
            iniatingSessionStatusImage.setVisible(true);
            isSessionIniationFinishedSuccess = true;
        }
        else
        {
            iniatingSessionStatusImage.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/icons/small/error_small.png"))));
            iniatingSessionStatusImage.setVisible(true);
            isSessionIniationFinishedSuccess = false;
        }
    }

    public void initUI(){

        Label label1 = new Label();
        ImageView designer = new ImageView(new Image("/images/designer.png"));
        designer.setPreserveRatio(true);
        designer.setFitWidth(300);
        label1.setGraphic(designer);
        label1.setAlignment(Pos.CENTER);
        VBox vBox1 = new VBox();
        vBox1.getChildren().add(label1);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPadding(new Insets(10));

        targetIpAddress = new Label();

        searchingDeviceTextBox = new Label();
        searchingDeviceTextBox.setText("Searching Target Device");
        searchingTargetDeviceStatusImage = new Label();


        synchingCurrentTopologyTextBox = new Label();
        synchingCurrentTopologyTextBox.setText("Synching Current Topology with Tablet");
        synchingCurrentsearchingTargetDeviceStatusImage = new Label();

        informingBoardslabel = new Label();
        informingBoardslabel.setText("Informing boards to create connection with Table");
        informingboardslabelImage= new Label();

        iniatingSessionTextBox = new Label();
        iniatingSessionTextBox.setText("Initiating Session Create Protocol");
        iniatingSessionStatusImage = new Label();

        SynchingCurrentTopologyFinished(true);
        SearchingTargetDeviceFinished(true);
        SessionInitiationFinished(true);
        InformingBoardsFinished(true);

        GridPane gridPane1 = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(85);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(15);
        gridPane1.getColumnConstraints().addAll(col1, col2);
        gridPane1.add(targetIpAddress, 0, 0);
        gridPane1.add(searchingDeviceTextBox, 0, 1);
        gridPane1.add(searchingTargetDeviceStatusImage, 1, 1);
        gridPane1.add(iniatingSessionTextBox, 0, 2);
        gridPane1.add(iniatingSessionStatusImage, 1, 2);
        gridPane1.add(synchingCurrentTopologyTextBox, 0, 3);
        gridPane1.add(synchingCurrentsearchingTargetDeviceStatusImage, 1, 3);
        gridPane1.add(informingBoardslabel, 0, 4);
        gridPane1.add(informingboardslabelImage, 1, 4);
        gridPane1.setPadding(new Insets(10));
        gridPane1.setAlignment(Pos.CENTER);

        HBox center = new HBox();
        center.getChildren().addAll(vBox1, gridPane1);
        center.setAlignment(Pos.CENTER);
        center.setStyle("-fx-border-radius: 2; -fx-border-color: darkgray; -fx-border-insets: 10;" +
                " -fx-pref-height: 450; -fx-pref-width: 920");
        center.setPadding(new Insets(20));
        getChildren().add(center);

    }

}
