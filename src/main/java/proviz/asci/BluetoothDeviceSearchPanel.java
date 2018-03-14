package proviz.asci;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import proviz.connection.BluetoothActionsListener;
import proviz.connection.BluetoothConnection;
import proviz.connection.BluetoothManager;
import proviz.models.connection.BluetoothDevice;

import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Burak on 8/10/17.
 */
public class BluetoothDeviceSearchPanel extends VBox implements BluetoothActionsListener {
    //private Stage stage;
    ProgressBar pBar;
    Button pairButt;
    Text statusLabel;
    HashMap<String, BluetoothDevice> deviceHashMap;
    ListView<String> deviceListView;
    ObservableList<String> foundDevices;
    private BluetoothManager bluetoothManager;
    private DeviceConfigurationDialog deviceConfigurationDialog;


    public BluetoothDeviceSearchPanel(DeviceConfigurationDialog deviceConfigurationDialog){
         deviceListView = new ListView<>();
        foundDevices = FXCollections.observableArrayList();
        initUI();

        deviceHashMap = new HashMap<>();

        //deviceListView.setModel(listModel);
        // todo devicelist viewde device geldigi zaman pairBttn enable yapacaksin.

        pairButt.setDisable(true);


        pairButt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                statusLabel.setText("Preparing for Pair Request");

                Task task = new Task<Void>()
                {
                    @Override
                    protected void succeeded() {

                    }

                    StreamConnection streamConnection;
                    @Override
                    public void run() {

                        BluetoothDevice selectedBluetoothDevice = deviceHashMap.get(deviceListView.getSelectionModel().getSelectedItem());

                        if (selectedBluetoothDevice.getServices().length > 0) {

                            try {
                                streamConnection  = (StreamConnection) Connector.open(selectedBluetoothDevice.getServices()[0].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, true));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            BluetoothConnection bluetoothConnection = new BluetoothConnection();

                            bluetoothConnection.setServiceRecord(selectedBluetoothDevice.getServices()[0]);
                            deviceConfigurationDialog.getCandidateBoard().setBaseConnection(bluetoothConnection);
                            bluetoothConnection.setCodeGenerationTemplate(deviceConfigurationDialog.getCodeGenerationTemplate());

                            //todo BLuecove has bug. System could not authenticate with devices.
                        }

                        try {
                            deviceConfigurationDialog.enablePostiviteButton();
                            statusLabel.setText("Pair Operation OK.");
                            try {
                                streamConnection.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }



                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    protected Void call() throws Exception {
                        return null;
                    }
                };




                new Thread(task).start();
            }
        });




        statusLabel.setText("Device Searching..");
    }
    public void activeBluetooth()
    {
        bluetoothManager = BluetoothManager.init();
        bluetoothManager.suscribeBleutoothActionListener(this);
        bluetoothManager.startBluetoothService();
    }

    private void initUI(){
        Text aDev = new Text("Available Devices");
        deviceListView.setPrefHeight(300);
        deviceListView.setPrefWidth(720);
        deviceListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(pairButt.isDisabled())
                    pairButt.setDisable(false);
            }
        });
//        if(pairButt.isDisabled())
//            pairButt.setDisable(false);
        pairButt = new Button("Pair");
        pairButt.setDisable(true);
        HBox pairButtBox = new HBox();
        pairButtBox.getChildren().add(pairButt);
        pairButtBox.setAlignment(Pos.BASELINE_RIGHT);
        pairButtBox.setPadding(new Insets(0, 20, 10, 0));

         statusLabel = new Text("Searching Devices");
        pBar = new ProgressBar(-1.0f); //would add code so that progress bar stops once device is found
        pBar.setPrefWidth(720);
        pBar.setPadding(new Insets(0, 0, 20, 0));

        setAlignment(Pos.CENTER);
        getChildren().addAll(aDev, deviceListView, pairButtBox, statusLabel, pBar);
    }

    @Override
    public void newDeviceDiscovered(BluetoothDevice device) {
        try {
            String deviceName  = device.getRemoteDevice().getFriendlyName(false);
            if(!(deviceName != null && deviceName.length()>0))
            {
                deviceName = device.getRemoteDevice().getBluetoothAddress();
            }

            deviceHashMap.put(deviceName,device);

            updateList();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateList()
    {
        Object[] keys= deviceHashMap.keySet().toArray();
        for(Object deviceName: keys)
        {
            foundDevices.add((String)deviceName);
        }

        deviceListView.setItems(foundDevices);

    }


}
