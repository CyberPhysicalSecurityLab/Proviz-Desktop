package proviz;

import proviz.connection.DataReceiveListener;
import proviz.devicedatalibrary.DataManager;
import proviz.models.codegeneration.Variable;
import proviz.models.connection.ReceivedDataModel;
import proviz.models.connection.ReceivedSensorData;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;
import proviz.models.uielements.LiveDataTableEntryModel;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Burak on 10/20/16.
 */
public class LiveDataTableModel extends AbstractTableModel implements DataReceiveListener {
    private ArrayList<LiveDataTableEntryModel> liveDataTableEntryModelArrayList;

    public void subscribeDataReceiverLister(Board board)
    {
        board.subscribe(this);


    }

    private void updateData()
    {
        fireTableDataChanged();
    }

    public  LiveDataTableModel()
    {
        super();
        liveDataTableEntryModelArrayList = new ArrayList<>();

    }

    @Override
    public String getColumnName(int column) {
       if(column ==0)
       {
           return "Board Id";
       }
        if(column ==1)
        {
            return "Sensor Name";
        }
        if(column ==2)
        {
            return "Data Name";
        }
        else if(column ==3)
        {
            return "Value";
        }
        else if(column == 4)
        {
            return "Message";
        }
        return "err";
    }

    @Override
    public int getRowCount() {
        return liveDataTableEntryModelArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        LiveDataTableEntryModel liveDataTableEntryModel  = liveDataTableEntryModelArrayList.get(rowIndex);
        if(columnIndex == 0)
        {
            return liveDataTableEntryModel.getBoardUserFriendlyName();
        }
        else if(columnIndex ==1)
        {
        return liveDataTableEntryModel.getSensorName();
        }
        else if(columnIndex == 2)
        {
           return liveDataTableEntryModel.getDataName();
        }
        else if(columnIndex == 3)
        {
            return liveDataTableEntryModel.getValue();
        }
        else if(columnIndex == 4)
        {
            return liveDataTableEntryModel.getSensorStatus().toString();
        }
        return null;
    }

    private LiveDataTableEntryModel getLiveDataTableEntryModelFromdataName(String dataName)
    {
        for(LiveDataTableEntryModel liveDataTableEntryModel: liveDataTableEntryModelArrayList)
        {
            if(dataName.compareTo(liveDataTableEntryModel.getDataName()) == 0)
                return liveDataTableEntryModel;
        }
        return null;

    }

    @Override
    public void onReceivedData(Board board,ReceivedDataModel receivedData) {

        for (ReceivedSensorData receivedSensorData : receivedData.getSensors()) {
            LiveDataTableEntryModel liveDataTableEntryModel = getLiveDataTableEntryModelFromdataName(receivedSensorData.getVariableName());
            if (liveDataTableEntryModel == null) {
                liveDataTableEntryModel = new LiveDataTableEntryModel(receivedSensorData.getVariableName());


                liveDataTableEntryModelArrayList.add(liveDataTableEntryModel);
            }
            liveDataTableEntryModel.setSensorStatus(receivedSensorData.getSensorStatus());
            liveDataTableEntryModel.setBoardUserFriendlyName(board.getUserFriendlyName());
            liveDataTableEntryModel.setValue(receivedSensorData.getSensorValue());
            liveDataTableEntryModel.setSensorName(DataManager.getInstance().getActiveSensor(receivedSensorData.getSensorId()).getName());
            updateData();

        }
    }

    @Override
    public void connectionLost(Board board) {

    }

    @Override
    public void connectionEstablished(Board board) {

    }
}
