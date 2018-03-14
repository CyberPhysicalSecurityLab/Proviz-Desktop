package proviz;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import proviz.codegeneration.ArduinoTemplate;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.codegeneration.RaspberryPiTemplate;
import proviz.connection.DataReceiveListener;
import proviz.models.connection.ReceivedDataModel;
import proviz.models.devices.Board;

import java.io.Serializable;

/**
 * Created by Burak on 9/8/16.
 */

public class BoardView extends Pane implements Serializable,DataReceiveListener {

    public CodeGenerationTemplate getCodeGenerationTemplate() {
        return codeGenerationTemplate;
    }

    public void setCodeGenerationTemplate(CodeGenerationTemplate codeGenerationTemplate) {
        this.codeGenerationTemplate = codeGenerationTemplate;
    }
    private CodeGenerationTemplate codeGenerationTemplate;
    private String receivedData;
    private VBox newImageBox;
    private ImageView newDevice;
    private boolean isClicked;
    private Label deviceNameLabel;



    private String deviceName;



    private Button close;
    private Node self;
    private int linkID;
    private MainEntrance mainEntrance;
    private LiveDataTable liveDataTable;
    private boolean isConfigured;




    public BoardView(MainEntrance mainEntrance,Board board) {
        this.mainEntrance = mainEntrance;
        if(board.getUserFriendlyName() == null || board.getUserFriendlyName().length() == 0)
            board.setUserFriendlyName("Device "+GlobalValues.getInstance().getBoardCounter());
        this.liveDataTable = mainEntrance.getLiveDataTable();
        GlobalValues.getInstance().increaseBoardCounterbyOne();
        initUI(board);

    }




    private void initUI(Board board){

        self = this;

        setMaxWidth(Double.MIN_VALUE);
        setMaxHeight(Double.MIN_VALUE);

        isClicked = false;
        isConfigured = false;

        setType(board);

        close = new Button();
        close.setStyle("-fx-background-color: transparent");
        ImageView closeI = new ImageView(new Image("/icons/small/close_x.png"));
        closeI.setFitWidth(7);
        closeI.setPreserveRatio(true);
        close.setGraphic(closeI);
        close.setPrefWidth(7);
        close.setAlignment(Pos.CENTER_RIGHT);
        setDeviceName(codeGenerationTemplate.getBoard().getUserFriendlyName());
        newImageBox = new VBox();
        newImageBox.setMaxWidth(newDevice.getFitWidth());

        newImageBox.setPadding(new Insets(20));
        newImageBox.getChildren().addAll(close, newDevice, deviceNameLabel);

        getChildren().add(newImageBox);
        makeDraggable(this);

        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                showPopUpMenu(event);
            }
        });


        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isClicked) {
                    // todo right menu need to be shown.

                    System.out.println("boardview !isClicked");
                    System.out.println(isClicked);
                    isClicked = true;


                } else if(isClicked){

                    System.out.println("boardview isClicked");
                    System.out.println(isClicked);
                    isClicked = false;

                }
                buildBorder(isClicked);
            }
        });


    }

    public void makeDraggable(Node node) {
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                node.getScene().setCursor(Cursor.MOVE);
            }
        });

        node.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                node.getScene().setCursor(Cursor.DEFAULT);

            }
        });

        node.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                node.setTranslateX(node.getTranslateX() + event.getX() - 45);
                node.setTranslateY(node.getTranslateY() + event.getY() - 45);

                event.consume();

                node.toFront();

            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Pane parent = (Pane) self.getParent();
                parent.getChildren().remove(self);

                for(Node n : parent.getChildren()){
                    if(n.getTypeSelector().compareTo("DeviceLink") == 0){

                        String nodeID = n.getId();
                        String linkid = String.valueOf(linkID);

                        if(nodeID.contentEquals(linkid)){
                            System.out.println("found link");
                            parent.getChildren().remove(n);
                            break;
                        }
                    }
                }
            }
        });




    }






    public void buildBorder(Boolean isSelected){
        if(isSelected){
            deviceNameLabel.setTextFill(Color.RED);
            newImageBox.setStyle("-fx-border-radius: 5; -fx-border-color: red");
            isClicked = true;
            mainEntrance.setBoardForRightSideBar(codeGenerationTemplate.getBoard());

        }
        else{
            deviceNameLabel.setTextFill(Color.BLACK);
            newImageBox.setStyle(null);
            isClicked = false;
        }
    }

    private void makeConnected()
    {
        deviceNameLabel.setTextFill(Color.BLUE);
        newImageBox.setStyle("-fx-border-radius: 5; -fx-border-color: blue");
    }

    public void showPopUpMenu(ContextMenuEvent event) {
        RightClickPopupMenu rightClickPopupMenu = new RightClickPopupMenu(mainEntrance,codeGenerationTemplate);
        rightClickPopupMenu.show(newImageBox, event.getScreenX(), event.getScreenY());

    }

    public void setType(Board board){
        if(board.getDevice_type() == ProjectConstants.DEVICE_TYPE.ARDUINO){
            newDevice = new ImageView(new Image(getClass().
                    getResourceAsStream("/arduino_img.jpg")));
            codeGenerationTemplate = new ArduinoTemplate(this,liveDataTable);

        }
        else if(board.getDevice_type() == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI){
            newDevice = new ImageView(new Image(getClass().
                    getResourceAsStream("/raspberrypi_img.png")));
            codeGenerationTemplate = new RaspberryPiTemplate(this,liveDataTable);

        }
        else if(board.getDevice_type() == ProjectConstants.DEVICE_TYPE.BEAGLEBONE){
            newDevice = new ImageView(new Image(getClass().
                    getResourceAsStream("/beaglebone_img.jpg")));
            // todo
        }

        codeGenerationTemplate.setBoard(board);
        codeGenerationTemplate.getBoard().setCodeGenerationTemplate(codeGenerationTemplate);


    }

    public String getDeviceName(){
        return deviceName;
    }


    public void registerLinkID(int linkid){
        linkID = linkid;
    }


    public boolean getIsClicked(){
        return isClicked;
    }

    public void setIsClicked(boolean b){
        isClicked = b;
    }

    // burak

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
        deviceNameLabel = new Label();
//        deviceName = ("Device " + toString().valueOf(counter));
        deviceNameLabel.setText(deviceName);
            if(codeGenerationTemplate != null &&codeGenerationTemplate.getBoard() != null)
                codeGenerationTemplate.getBoard().setName(deviceName);

    }


    @Override
    public void onReceivedData(Board board, ReceivedDataModel receivedData) {

    }

    @Override
    public void connectionLost(Board board) {

    }

    @Override
    public void connectionEstablished(Board board) {
        makeConnected();

    }

    public boolean isConfigured() {
        return isConfigured;
    }

    public void setConfigured(boolean configured) {
        isConfigured = configured;
    }


}


