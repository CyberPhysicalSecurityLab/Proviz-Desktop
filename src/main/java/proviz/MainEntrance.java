/*
Chester
*/


package proviz;


import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import proviz.models.Topology;
import proviz.models.devices.Board;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import proviz.models.devices.Sensor;
import proviz.models.uielements.BoardConnectionViewEntry;
import proviz.thirdpartyconnections.tablet.TabletConnectionCreatorDialog;
import proviz.uicomponents.rightsidebar.*;


import java.awt.*;
import java.awt.dnd.DragGestureEvent;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


/**
 * Created by Burak on 9/4/16.
 */
public class MainEntrance extends AnchorPane {





    private DeviceSelectionBox dvs;
    private static TabPane tab;
    private Tab topology;
    private static Tab dataTable;
    private static PView pView;

    private LiveDataTable dt;
    private Topology allBoards;
    private static Stage stage;
    private static StackPane topologyStack;
    private Node self;
    private RightSideBar rightSideBar;
    private AnchorPane centerPane;



    private MainEntrance mainEntrance;


    public MainEntrance(Stage stage) {

        // setLiveDataTableModel(new LiveDataTableModel());
        // LiveDataTable liveDataTable = new LiveDataTable(getLiveDataTableModel());
        //
        // tabbedPane1.add(new JScrollPane(liveDataTable), "Data Table");
        //
        // new PDropListener(pView, liveDataTableModel);

        HBox topPanel = new HBox();
        mainEntrance = this;
        self = this;
        Image openFolder = new Image(MainEntrance.class.getResourceAsStream("/icons/small/opened_folder.png"));
        Image save = new Image(MainEntrance.class.getResourceAsStream("/icons/small/save.png"));
        Image print = new Image(MainEntrance.class.getResourceAsStream("/icons/small/print.png"));
        Image tablet = new Image(MainEntrance.class.getResourceAsStream("/icons/small/android_tablet.png"));
        Image settings = new Image(MainEntrance.class.getResourceAsStream("/icons/small/settings.png"));

        Button openButt = new Button();
        openButt.setGraphic(new ImageView(openFolder));
        openButt.setMaxSize(openButt.getWidth()-30, openButt.getHeight()-30);

        Button saveButt = new Button();
        saveButt.setGraphic(new ImageView(save));

        Button printButt = new Button();
        printButt.setGraphic(new ImageView(print));

        Button tabButt = new Button();
        tabButt.setGraphic(new ImageView(tablet));

        Button settButt = new Button();
        settButt.setGraphic(new ImageView(settings));

        topPanel.getChildren().addAll(openButt, saveButt, printButt, tabButt, settButt);
        topPanel.setSpacing(20);
        topPanel.setAlignment(Pos.CENTER_LEFT);
        topPanel.setPadding(new Insets(10, 0, 5, 10));

        getChildren().add(topPanel);
        setTopAnchor(topPanel,2.0);
        setLeftAnchor(topPanel,2.0);

        dvs = new DeviceSelectionBox();
        VBox vbox = new VBox(dvs);
        vbox.setOpacity(100);
        vbox.toFront();
        getChildren().add(vbox);
        setLeftAnchor(vbox,0.0);
        setBottomAnchor(vbox,2.0);
        setTopAnchor(vbox,2.0);
        vbox.setStyle("-fx-background-color: inherit");
        dvs.setTranslateY(40);

        topPanel.toFront();

        tab = new TabPane();
        tab.setStyle("-fx-border-color: lightgray;" +
                "-fx-border-radius: 2");

        topology = new Tab("Topology");
        topology.setClosable(false);
        pView = new PView(this);

        registerOnDragDropped(pView);



        dataTable = new Tab("Data Table");
        dataTable.setClosable(false);
        dt = new LiveDataTable();
        dataTable.setContent(dt);

        tab.setSide(Side.BOTTOM);
        tab.getTabs().addAll(topology, dataTable);

        topologyStack = new StackPane();
        //topology.setContent(topologyStack);
        //topologyStack.getChildren().add(pView);

        topologyStack.getChildren().add(tab);
        topology.setContent(pView);

        centerPane = new AnchorPane();
        centerPane.setPrefSize(945,550);
        centerPane.getChildren().add(tab);
        centerPane.setTopAnchor(tab,0.0);
        centerPane.setBottomAnchor(tab,0.0);
        centerPane.setLeftAnchor(tab,0.0);
        centerPane.setRightAnchor(tab,0.0);
        centerPane.toBack();

        getChildren().add(centerPane);
        setBottomAnchor(centerPane,2.0);
        setTopAnchor(centerPane,55.0);
        setRightAnchor(centerPane,2.0);
        setLeftAnchor(centerPane,80.0);


        tabButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                TabletConnectionCreatorDialog tabletConnectionCreatorDialog =
                        new TabletConnectionCreatorDialog(mainEntrance);

                addToStack(true, tabletConnectionCreatorDialog);
            }
        });

        saveButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                saveData();

            }
        });

        openButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadSavedFile(stage);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
        });

        printButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean onTab = tab.isVisible();

                if(onTab) {
                    PrinterJob job = PrinterJob.createPrinterJob();

                    if(job == null){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("No Printers Available");
                        alert.setContentText("There are no printer available. Set up a printer to continue.");
                        alert.show();
                    }

                    else if (job != null && job.showPageSetupDialog(getScene().getWindow())) {
                        Printer printer = job.getPrinter();
                        PageLayout pageLayout = printer.createPageLayout(Paper.A4,
                                PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);


                        double width = getWidth();
                        double height = getHeight();

                        PrintResolution resolution = job.getJobSettings().getPrintResolution();

                        width /= resolution.getFeedResolution();
                        height /= resolution.getCrossFeedResolution();

                        double scaleX = pageLayout.getPrintableWidth() / width / 600;
                        double scaleY = pageLayout.getPrintableHeight() / height / 600;

                        Scale scale = new Scale(scaleX, scaleY);

//                        getLeft().setVisible(false);
//                        getTop().setVisible(false);
//                        getTransforms().add(scale);

                        boolean success = job.printPage( self);
                        if (success) {
                            job.endJob();
                        }

//                        getTransforms().remove(scale);
//                        getLeft().setVisible(true);
//                        getTop().setVisible(true);
                    }
                }
                else{

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Exit Configuration Panel");
                    alert.setContentText("You may only print Board Topology. Please exit configuration or programming " +
                            "panel.");
                    alert.show();
                }
            }
        });

        rightSideBar = new RightSideBar();
//        this.setRight(rightSideBar);

    }

    private void saveData() { //saves existing BoardView data to json file

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Topology");

        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("JSON", ".proviz");
        fileChooser.getExtensionFilters().add(ext);
        File file = fileChooser.showSaveDialog(new Stage());

        ObjectMapper mapper = new ObjectMapper();

        allBoards = new Topology();

        for (Node n : pView.getChildren()) {
            if (n.getTypeSelector().compareTo("BoardView") == 0) {
                BoardView bV = (BoardView) n;
                allBoards.getCodeGenerationTemplates().add(bV.getCodeGenerationTemplate());

            }
        }

        try {
            mapper.writeValue(file, allBoards);
            String jsonInString = mapper.writeValueAsString(allBoards);
            System.out.println(jsonInString);
        }
        catch (IOException ioe) {
            System.out.println(ioe);
        }

    }

    public void registerOnDragDropped(PView pView) {


        pView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                pView.setComputerViewEffect(true);
            }
        });


        pView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {

                Dragboard db = event.getDragboard();
                final String id = db.getString();

                double mousePosY = event.getY();
                double mousePosX = event.getX();

                Board board = new Board();
                board.setDevice_type(ProjectConstants.DEVICE_TYPE.valueOf(db.getString()));
                board.setX(mousePosX);
                board.setY(mousePosY);
                pView.createBoardView(board);

                event.setDropCompleted(true);

            }
        });

        pView.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
                event.consume();
                pView.setComputerViewEffect(false);
            }
        });
    }
    public void loadSavedFile(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Existing Project");
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Open Your Project");
            alert.setContentText("Would you like to open your project in this window or a new window?");

            ButtonType thisWindowButton = new ButtonType("This Window");
            ButtonType newWindowButton = new ButtonType("New Window");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(thisWindowButton, newWindowButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == thisWindowButton){
                openProject(stage, selectedFile);
            }
            else if(result.get() == newWindowButton){
                Stage newStage = new Stage();
                openProject(newStage, selectedFile);

            }


        }
    }

    public void openProject(Stage stage, File file) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        Topology allBoards = null;
        try {
            allBoards = mapper.readValue(file, Topology.class);

            MainEntrance mainEntrance = new MainEntrance(stage);
            stage.setScene(new Scene(mainEntrance, 1024, 600));
            stage.setX(100);
            stage.setY(50);

            System.out.println(allBoards);

//            for (Board boardModel : allBoards.getBoardModels()) {
//
//                pView.createBoardView(boardModel);
//
//            }

            stage.show();

        } catch (IOException ioe) {
            System.out.println(ioe.getStackTrace());
        }
    }
    public  void addToStack(boolean add, Pane pane){



        if(add) {
           centerPane.getChildren().add(pane);
           centerPane.setTopAnchor(pane,4.0);
           centerPane.setRightAnchor(pane,4.0);
           centerPane.setLeftAnchor(pane,4.0);
           centerPane.setBottomAnchor(pane,4.0);
           centerPane.toBack();

           pane.prefHeightProperty().bind(centerPane.heightProperty());
           pane.prefWidthProperty().bind(centerPane.widthProperty());
           tab.setVisible(false);


        }else{
            centerPane.getChildren().clear();
            centerPane.getChildren().add(tab);
            tab.setVisible(true);

        }
    }

    public static Stage getStage(){
        return stage;
    }

    public void setBoardForRightSideBar(Board board) {
        setRightSideBar();
    }
    public void setRightSideBar(){
//        this.setRight(rightSideBar);
    }
    public void hideRightSideBar()
    {
//        this.setRight(null);
    }

    public LiveDataTable getLiveDataTable() {
        return dt;
    }






}

