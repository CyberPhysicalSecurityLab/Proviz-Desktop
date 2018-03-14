package proviz;

import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import proviz.uicomponents.rightsidebar.BoardConnectionView;
import proviz.uicomponents.rightsidebar.BoardDetailView;
import proviz.uicomponents.rightsidebar.SensorListView;

/**
 * Created by Burak on 8/13/17.
 */
public class RightSideBar extends VBox {

    Accordion sensorViewAccordion;
    Accordion boardConnectionViewAccordion;
    Accordion boardDetailViewAccordion;
    TitledPane sensorViewTitledPane;
    TitledPane boardConnectionTitledPane;
    TitledPane boardDetailViewTitledPane;
    SensorListView sensorView;
    BoardConnectionView boardConnectionView;
    BoardDetailView boardDetailView;

    public RightSideBar(){
        sensorViewAccordion = new Accordion();
        boardConnectionViewAccordion = new Accordion();
        boardDetailViewAccordion = new Accordion();
        sensorView = new SensorListView();
        boardConnectionView = new BoardConnectionView();
        boardDetailView = new BoardDetailView();
        sensorViewTitledPane = new TitledPane();
        boardConnectionTitledPane = new TitledPane();
        boardDetailViewTitledPane = new TitledPane();

        sensorViewTitledPane.setText("Sensors");
        boardDetailViewTitledPane.setText("Board");
        boardConnectionTitledPane.setText("Connection");

        sensorViewTitledPane.setContent(sensorView);
        boardDetailViewTitledPane.setContent(boardDetailView);
        boardConnectionTitledPane.setContent(boardConnectionView);

        sensorViewAccordion.getPanes().add(sensorViewTitledPane);
        sensorViewAccordion.setExpandedPane(sensorViewTitledPane);

        boardConnectionViewAccordion.getPanes().add(boardConnectionTitledPane);
        boardConnectionViewAccordion.setExpandedPane(boardConnectionTitledPane);
        boardDetailViewAccordion.getPanes().add(boardDetailViewTitledPane);
        boardDetailViewAccordion.setExpandedPane(boardDetailViewTitledPane);



        this.getChildren().addAll(sensorViewAccordion, boardConnectionViewAccordion, boardDetailViewAccordion);

    }




}
