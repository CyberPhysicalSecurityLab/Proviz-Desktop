package proviz.uicomponents.rightsidebar;

import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import proviz.models.devices.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 2/16/17.
 */

public class BoardDetailView extends VBox {

    private SidebarSection details;
    private ListView detailContent;


    public BoardDetailView(){

        initUI();

    }


    private void initUI(){
        details = new SidebarSection();

        detailContent = new ListView();
        detailContent.getItems().addAll(new ArrayList<String>());
        detailContent.setStyle("-fx-background-color: lightgray");
        details.setCenter(detailContent);

        this.getChildren().add(details);
    }

    //add logic to set the content of Detail View

    public void setBoard(Board board){
        ArrayList<String>  informations = new ArrayList<>();
        informations.add("Name: "+board.getUserFriendlyName());
        informations.add("Make: "+board.getMake());
        informations.add("Model: "+board.getName());
        informations.add("Available Analog Pin: "+board.AvailableAnalogPinNumber());
        informations.add("Available Digital Pin: "+board.AvailableDigitalPinNumber());
        informations.add("Available Serial Pin: "+board.AvailableSerialPinNumber());
        informations.add("Available SPI: "+board.AvailableSPIPinNumber());
        informations.add("Available i2c Pin: "+board.Availablei2cPinNumber());
        if(board.isHas3_3v())
        informations.add("Supported 3.3V PSU: yes");
        else
            informations.add("Supported 3.3V PSU: no");

        if(board.isHas5v())
        informations.add("Supported 5V PSU: yes");
        else
                informations.add("Supported 5V PSU: no");

        detailContent.getItems().clear();
        detailContent.getItems().addAll(informations);
    }


}