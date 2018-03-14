package proviz.asci;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import proviz.BoardView;
import proviz.MainEntrance;
import proviz.PView;
import proviz.ProjectConstants;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.connection.WiFiConnection;
import proviz.devicedatalibrary.DataManager;
import proviz.library.utilities.SwipePanelsAnimation;
import proviz.models.devices.Board;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Burak on 8/10/17.
 */
public class DeviceConfigurationDialog extends BorderPane {

    private DeviceInformationPanel deviceInformationPanel;
    private ConnectionTypeSelectionPanel connectionTypeSelectionPanel;
    private BluetoothDeviceSearchPanel bluetoothDeviceSearchPanel;
    private WifiArduinoFirstFirmwareUploadPanel wifiArduinoFirstFirmwareUploadPanel;
    private WifiRaspClientSoftwarePanel wifiRaspClientSoftwarePanel;
    private DeviceConfigurationResultPanel deviceConfigurationResultPanel;
    private CodeGenerationTemplate codeGenerationTemplate;
    private Board candidateBoard;
    private StackPane root;
    private ButtonBar buttonBar;
    private Button positiveButt, negativeButt, closeButt;
    private Pane currentPage;
    private SwipePanelsAnimation spa;
    private Toggle tg;
    private Stage stage;
    private PView pView;
    private BoardView boardView;
    private MainEntrance mainEntrance;
    private Pane self;
    private String deviceName;
    private BorderPane deviceInfoBox;
    private VBox deviceInfo;
    private Text deviceInformation;
    private ImageView closeButtonImage;
    private HBox closeButtBox;
    private Separator separator;
    private HBox buttonAreaBox;



    public DeviceConfigurationDialog(MainEntrance mainEntrance,CodeGenerationTemplate codeGenerationTemplate){
        self = this;
        this.mainEntrance = mainEntrance;
        this.codeGenerationTemplate = codeGenerationTemplate;
        boardView = codeGenerationTemplate.getBoardView();
        candidateBoard = new Board();
        candidateBoard.setX(boardView.getCodeGenerationTemplate().getBoard().getX());
        candidateBoard.setY(boardView.getCodeGenerationTemplate().getBoard().getY());
        initUI();
    }

    private void initUI(){
        this.setPrefSize(950, 530);
        deviceInfo = new VBox();
        deviceInfoBox = new BorderPane();
        deviceInformation = new Text("Device Information");
        deviceInformation.setFont(Font.font(15));
        deviceInformation.setTextAlignment(TextAlignment.CENTER);
        closeButt = new Button();
        closeButtonImage = new ImageView(new Image("/icons/small/close_x.png"));
        closeButtonImage.setFitWidth(15);
        closeButtonImage.setPreserveRatio(true);
        closeButt.setGraphic(closeButtonImage);
        closeButtBox = new HBox(closeButt);
        closeButtBox.setAlignment(Pos.CENTER_RIGHT);
        deviceInfoBox.setRight(closeButtBox);
        deviceInfoBox.setLeft(deviceInformation);

        separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);
        separator.setPadding(new Insets(5, 0, 0, 0));
        deviceInfo.getChildren().addAll(deviceInfoBox, separator);
        deviceInfo.setPadding(new Insets(10));
        setTop(deviceInfo);

        buttonBar = new ButtonBar();
        negativeButt = new Button("Back");
        positiveButt = new Button("Continue");
        buttonBar.getButtons().addAll(negativeButt, positiveButt);
        buttonBar.setPadding(new Insets(10));
        buttonAreaBox = new HBox();
        buttonAreaBox.setAlignment(Pos.CENTER_RIGHT);
        buttonAreaBox.getChildren().add(buttonBar);
        setBottom(buttonAreaBox);

        root = new StackPane();
        deviceInformationPanel = new DeviceInformationPanel(codeGenerationTemplate);
        root.getChildren().add(deviceInformationPanel);
        setCenter(root);

        currentPage = new Pane();
        currentPage = deviceInformationPanel;

        spa = new SwipePanelsAnimation(buttonBar);
        initializePanels();

        positiveButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            onPositiveButtonClicked();
            }


        });

        closeButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainEntrance.addToStack(false, self);
            }
        });



        negativeButt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){onNegativeButtonClicked();}
        });

    }

    public void initializePanels()
    {
        connectionTypeSelectionPanel = new ConnectionTypeSelectionPanel();
        bluetoothDeviceSearchPanel = new BluetoothDeviceSearchPanel(this);
        wifiArduinoFirstFirmwareUploadPanel = new WifiArduinoFirstFirmwareUploadPanel(this);
        deviceConfigurationResultPanel = new DeviceConfigurationResultPanel();
        wifiRaspClientSoftwarePanel = new WifiRaspClientSoftwarePanel(this,stage);



    }

    private Board loadBoardDetailWithModelName(String modelCode)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String resourceAddress = "";
        if(codeGenerationTemplate.getBoard().getDevice_type() == ProjectConstants.DEVICE_TYPE.ARDUINO) {
            resourceAddress = "boards/arduino/" + modelCode + ".json";
        }
        else if(codeGenerationTemplate.getBoard().getDevice_type() == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI)
        {
            resourceAddress = "boards/raspberrypi/"+modelCode+".json";

        }
        Board tempBoard = null;
        try {
            tempBoard = objectMapper.readValue(ClassLoader.getSystemResource(resourceAddress).toURI().toURL(),Board.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return tempBoard;


    }


    //todo I dont like leaving this function as public.
    public void onPositiveButtonClicked()
    {
        if(currentPage == deviceInformationPanel) {

            spa.switchScreenF(connectionTypeSelectionPanel, deviceInformationPanel, root);
            //previousPage = startScreen;
            currentPage = connectionTypeSelectionPanel;

            candidateBoard = loadBoardDetailWithModelName(deviceInformationPanel.getSelectedDeviceModelName());

            candidateBoard.setUserFriendlyName(deviceInformationPanel.getDeviceUserFriendlyName());
            candidateBoard.setUniqueId(boardView.getCodeGenerationTemplate().getBoard().getUniqueId());
            candidateBoard.setDevice_type(boardView.getCodeGenerationTemplate().getBoard().getDevice_type());
            candidateBoard.setX(boardView.getCodeGenerationTemplate().getBoard().getX());
            candidateBoard.setY(boardView.getCodeGenerationTemplate().getBoard().getY());
            candidateBoard.initializeRequirements();




        }
        else if(currentPage == connectionTypeSelectionPanel)
        {
        if(connectionTypeSelectionPanel.getSelectedTg() == connectionTypeSelectionPanel.rbWifi)
        {
            if(candidateBoard.getDevice_type()== ProjectConstants.DEVICE_TYPE.ARDUINO){
                spa.switchScreenF(wifiArduinoFirstFirmwareUploadPanel, connectionTypeSelectionPanel, root);
                currentPage = wifiArduinoFirstFirmwareUploadPanel;
                wifiArduinoFirstFirmwareUploadPanel.activateFlash();
            }
            else if (candidateBoard.getDevice_type() == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI)
            {
                spa.switchScreenF(wifiRaspClientSoftwarePanel, connectionTypeSelectionPanel, root);

                currentPage = wifiRaspClientSoftwarePanel;
            }
            candidateBoard.setConnectionType(ProjectConstants.CONNECTION_TYPE.INTERNET);
            candidateBoard.setBaseConnection(new WiFiConnection(candidateBoard));
        }
        else if(connectionTypeSelectionPanel.getSelectedTg() == connectionTypeSelectionPanel.rbBluetooth)
        {
            disableNegativeButton();
            disablePostiveButton();
            spa.switchScreenFNotLockButton(bluetoothDeviceSearchPanel, connectionTypeSelectionPanel, root);
            currentPage = bluetoothDeviceSearchPanel;
            candidateBoard.setConnectionType(ProjectConstants.CONNECTION_TYPE.BLUETOOTH_CLASSIC);
            bluetoothDeviceSearchPanel.activeBluetooth();
            enableNegativeButton();

        }
        else if(connectionTypeSelectionPanel.getSelectedTg() == connectionTypeSelectionPanel.rbUSB)
        {
            spa.switchScreenF(deviceConfigurationResultPanel, connectionTypeSelectionPanel, root);
            currentPage = deviceConfigurationResultPanel;
            candidateBoard.setConnectionType(ProjectConstants.CONNECTION_TYPE.SERIAL);

        }
        else
        {
            if((connectionTypeSelectionPanel.getSelectedTg() == null)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("You did not provide enough information");
                alert.setContentText("You must select a device type. Click \"Back\" " +
                        "to configure your device type.");
                alert.show();
            }
        }

        }


        else if(currentPage == wifiArduinoFirstFirmwareUploadPanel || currentPage == wifiRaspClientSoftwarePanel){

            if(currentPage == wifiRaspClientSoftwarePanel)
            {
                if(wifiRaspClientSoftwarePanel.isIpAddressValid())
                {
                    spa.switchScreenF(deviceConfigurationResultPanel, currentPage, root);
                    ((WiFiConnection)candidateBoard.getBaseConnection()).setDeviceIpAddress(wifiRaspClientSoftwarePanel.getIpAddress());

                    currentPage = deviceConfigurationResultPanel;
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"You have to enter valid IP Address");
                    alert.show();
                }
            }

        }
        else if(currentPage == bluetoothDeviceSearchPanel){
            spa.switchScreenF(deviceConfigurationResultPanel, currentPage, root);
            currentPage = deviceConfigurationResultPanel;
        }

        else if(currentPage == deviceConfigurationResultPanel) {
            mainEntrance.addToStack(false, self);

            currentPage = deviceConfigurationResultPanel;
            codeGenerationTemplate.setBoard(candidateBoard);
            DataManager.getInstance().getActiveBoards().add(candidateBoard);
            if(codeGenerationTemplate.getBoard().getDevice_type() == ProjectConstants.DEVICE_TYPE.ARDUINO && codeGenerationTemplate.getBoard().getBaseConnection() instanceof WiFiConnection)
            {
                codeGenerationTemplate.getBoard().getBaseConnection().startConnection();
            }



        }
    }

    private void onNegativeButtonClicked()
    {
        {
            if(currentPage == connectionTypeSelectionPanel){
                spa.switchScreenB(deviceInformationPanel, connectionTypeSelectionPanel, root);

                currentPage = deviceInformationPanel;
            }
            else if(currentPage == bluetoothDeviceSearchPanel ||
                    currentPage == wifiArduinoFirstFirmwareUploadPanel || currentPage == wifiRaspClientSoftwarePanel){

                spa.switchScreenB(connectionTypeSelectionPanel, currentPage, root);

                currentPage = connectionTypeSelectionPanel;
            }
            else if (currentPage == deviceConfigurationResultPanel){

                spa.switchScreenB(connectionTypeSelectionPanel, currentPage, root);

                currentPage = connectionTypeSelectionPanel;
            }

        }
    }

    public void enablePostiviteButton()
    {
        positiveButt.setDisable(false);
    }

    public void disableNegativeButton()
    {
        negativeButt.setDisable(true);
    }

    public void enableNegativeButton()
    {
        negativeButt.setDisable(false);
    }

    public Board getCandidateBoard() {
        return candidateBoard;
    }

    public void setCandidateBoard(Board candidateBoard) {
        this.candidateBoard = candidateBoard;
    }

    public CodeGenerationTemplate getCodeGenerationTemplate() {
        return codeGenerationTemplate;
    }

    public void setCodeGenerationTemplate(CodeGenerationTemplate codeGenerationTemplate) {
        this.codeGenerationTemplate = codeGenerationTemplate;
    }

    public void disablePostiveButton()
    {
        positiveButt.setDisable(true);
    }



}
