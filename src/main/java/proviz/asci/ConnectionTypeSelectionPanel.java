package proviz.asci;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import proviz.ProjectConstants;

/**
 * Created by Burak on 8/10/17.
 */
public class ConnectionTypeSelectionPanel extends GridPane {

    public RadioButton rbBluetooth;
    public RadioButton rbUSB;
    public RadioButton rbWifi;
    private ToggleGroup tg;
    private GridPane gridPane;


    public ConnectionTypeSelectionPanel(){
        initUI();
    }

    public ProjectConstants.CONNECTION_TYPE getSelectedConnectionType()
    {
        ProjectConstants.CONNECTION_TYPE selectedType = null;
        if(rbBluetooth.isSelected())
            selectedType = ProjectConstants.CONNECTION_TYPE.BLUETOOTH_CLASSIC;
        else if(rbUSB.isSelected())
            selectedType = ProjectConstants.CONNECTION_TYPE.SERIAL;
        else if (rbWifi.isSelected())
            selectedType = ProjectConstants.CONNECTION_TYPE.INTERNET;

        return selectedType;
    }

    private void initUI(){
        gridPane = new GridPane();
        rbBluetooth = new RadioButton();
        HBox hboxBT = new HBox();
        hboxBT.getChildren().add(rbBluetooth);
        hboxBT.setAlignment(Pos.CENTER);

        rbUSB = new RadioButton();
        HBox hboxUSB = new HBox();
        hboxUSB.getChildren().add(rbUSB);
        hboxUSB.setAlignment(Pos.CENTER);

        rbWifi = new RadioButton();
        HBox hboxWifi = new HBox();
        hboxWifi.getChildren().add(rbWifi);
        hboxWifi.setAlignment(Pos.CENTER);

        add(hboxBT, 0, 1);
        add(hboxUSB, 1, 1);
        add(hboxWifi, 2, 1);


        ImageView rbBluetoothI =  new ImageView(new Image
                (ConnectionTypeSelectionPanel.class.getResourceAsStream("/images/connection_type_bluetooth.png")));
        HBox hBoxBTI = new HBox();
        hBoxBTI.getChildren().add(rbBluetoothI);

        ImageView rbUSBI = new ImageView(new Image
                (ConnectionTypeSelectionPanel.class.getResourceAsStream("/images/connection_type_usb.png")));
        HBox hBoxUSBI = new HBox();
        hBoxUSBI.getChildren().add(rbUSBI);

        ImageView rbWifiI = new ImageView(new Image
                (ConnectionTypeSelectionPanel.class.getResourceAsStream("/images/connection_type_wifi.png")));
        HBox hBoxWifiI = new HBox();
        hBoxWifiI.getChildren().add(rbWifiI);

        add(hBoxBTI, 0, 0);
        add(hBoxUSBI, 1, 0);
        add(hBoxWifiI, 2, 0);

        hBoxBTI.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rbBluetooth.setSelected(true);
            }
        });
        hBoxUSBI.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rbUSB.setSelected(true);
            }
        });
        hBoxWifiI.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rbWifi.setSelected(true);
            }
        });

        tg = new ToggleGroup();
        rbBluetooth.setToggleGroup(tg);
        rbUSB.setToggleGroup(tg);
        rbWifi.setToggleGroup(tg);

        setAlignment(Pos.CENTER);



    }

    public Toggle getSelectedTg(){
        return tg.getSelectedToggle();
    }



}
