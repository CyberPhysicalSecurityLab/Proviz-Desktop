package proviz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import proviz.connection.DataReceiveListener;
import proviz.devicedatalibrary.DataManager;
import proviz.models.connection.IncomingDeviceData;
import proviz.models.connection.ReceivedDataModel;
import proviz.models.connection.ReceivedSensorData;
import proviz.models.devices.Board;
import proviz.models.uielements.LiveDataTableEntryModel;

import java.util.ArrayList;


/**
 * Created by Burak on 10/20/16.
 */
public class LiveDataTable extends AnchorPane implements DataReceiveListener {

    private TableView<LiveDataTableEntryModel> tableView = new TableView<LiveDataTableEntryModel>();

    private TableColumn<LiveDataTableEntryModel,String> boardID;
    private TableColumn<LiveDataTableEntryModel,String> sensorName;
    private TableColumn<LiveDataTableEntryModel,String> dataName;
    private TableColumn<LiveDataTableEntryModel,String> value;
    private TableColumn<LiveDataTableEntryModel,String> message;


    public void subscribeDataReceiverLister(Board board)
    {
        board.subscribe(this);


    }


    public LiveDataTable(){

        initUI();

    }

    private void initUI(){


        boardID = new TableColumn<>("Board Name");
        boardID.setCellValueFactory(new PropertyValueFactory<LiveDataTableEntryModel,String>("boardUserFriendlyName"));
        sensorName = new TableColumn<>("Sensor Name");
        sensorName.setCellValueFactory(new PropertyValueFactory<LiveDataTableEntryModel,String>("sensorName"));
        dataName = new TableColumn<>("Data Name");
        dataName.setCellValueFactory(new PropertyValueFactory<LiveDataTableEntryModel,String>("dataName"));
        value = new TableColumn<>("Value");
        value.setCellValueFactory(new PropertyValueFactory<LiveDataTableEntryModel,String>("value"));
        message = new TableColumn<>("Status");
        message.setCellValueFactory(new PropertyValueFactory<LiveDataTableEntryModel,String>("sensorStatus"));

tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getColumns().addAll(boardID,sensorName,dataName,value,message);


        this.setTopAnchor(tableView,0.0);
        this.setBottomAnchor(tableView,0.0);
        this.setLeftAnchor(tableView,0.0);
        this.setRightAnchor(tableView,0.0);

        this.getChildren().add(tableView);



    }
    private LiveDataTableEntryModel getLiveDataTableEntryModelFromdataName(String dataName)
    {
        for(LiveDataTableEntryModel liveDataTableEntryModel: tableView.getItems())
        {
            if(dataName.compareTo(liveDataTableEntryModel.getDataName()) == 0)
                return liveDataTableEntryModel;
        }
        return null;

    }


    @Override
    public void onReceivedData(Board board, ReceivedDataModel receivedData) {
        for (ReceivedSensorData receivedSensorData : receivedData.getSensors()) {
            LiveDataTableEntryModel liveDataTableEntryModel = getLiveDataTableEntryModelFromdataName(receivedSensorData.getVariableName());
            if (liveDataTableEntryModel == null) {
                liveDataTableEntryModel = new LiveDataTableEntryModel(receivedSensorData.getVariableName());


                tableView.getItems().add(liveDataTableEntryModel);
            }
            liveDataTableEntryModel.setSensorStatus(receivedSensorData.getSensorStatus());
            liveDataTableEntryModel.setBoardUserFriendlyName(board.getUserFriendlyName());
            liveDataTableEntryModel.setValue(receivedSensorData.getSensorValue());
            liveDataTableEntryModel.setSensorName(DataManager.getInstance().getActiveSensor(receivedSensorData.getSensorId()).getName());
            tableView.refresh();
        }

    }

    @Override
    public void connectionLost(Board board) {

    }

    @Override
    public void connectionEstablished(Board board) {

    }
}
