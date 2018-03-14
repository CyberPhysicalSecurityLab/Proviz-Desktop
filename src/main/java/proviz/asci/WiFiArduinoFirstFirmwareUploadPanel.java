package proviz.asci;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import proviz.ProjectConstants;
import proviz.codedistribution.CodeDistributionManager;
import proviz.codegeneration.ArduinoTemplate;
import proviz.library.utilities.FileOperations;
import proviz.models.devices.Board;

/**
 * Created by Burak on 8/10/17.
 */
public class WifiArduinoFirstFirmwareUploadPanel extends BorderPane {
    HBox topBox, middleBox;
    VBox bottomBox;
    Text topText,arduinoUSBText,waitingArduinoText;
    Image usbImage;
    ProgressBar progressBar;
    private DeviceConfigurationDialog dialog;

    public WifiArduinoFirstFirmwareUploadPanel(DeviceConfigurationDialog deviceConfigurationDialog){
        this.dialog = deviceConfigurationDialog;
        initUI();
    }

    private void initUI(){

        topBox = new HBox();
        topText = new Text();
        topText.setText("Now it's time to meet with your Arduino and us.");
        topBox.getChildren().addAll(topText);
        topBox.setAlignment(Pos.CENTER);
        setTop(topBox);


        usbImage = new Image(WifiArduinoFirstFirmwareUploadPanel.class.getResourceAsStream("/images/usb_alone.png"));
        arduinoUSBText = new Text("Please connect your Arduino through USB Port");
        middleBox = new HBox();
        middleBox.setAlignment(Pos.CENTER_LEFT);
        middleBox.getChildren().addAll(new ImageView(usbImage), arduinoUSBText);


        waitingArduinoText = new Text("Waiting Arduino device");
        progressBar = new ProgressBar(-1.0f);
        progressBar.setPrefWidth(800);
        progressBar.setPadding(new Insets(0, 10, 10, 10));
        progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        bottomBox = new VBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(waitingArduinoText, progressBar);

        setCenter(middleBox);
        setBottom(bottomBox);
    }
    public void activateFlash()
    {
        dialog.disablePostiveButton();
        getDeviceAccessPath();
    }
    private void getDeviceAccessPath()
    {
        switch (FileOperations.init().checkOS())
        {
            case OSX:
            {

                ArduinoTemplate arduinoTemplate = new ArduinoTemplate(null);
                Board board = new Board();
                board.setDevice_type(ProjectConstants.DEVICE_TYPE.ARDUINO);
                board.setConnectionType(ProjectConstants.CONNECTION_TYPE.INTERNET);
                arduinoTemplate.setBoard(board);
                board.setUserFriendlyName(dialog.getCandidateBoard().getUserFriendlyName());
                arduinoTemplate.setConnectionType(ProjectConstants.CONNECTION_TYPE.INTERNET);
                CodeDistributionManager codeDistManager = new CodeDistributionManager(arduinoTemplate,true);
                codeDistManager.setInitialFlash(true);
                codeDistManager.flashCode2Device();
                dialog.enablePostiviteButton();

                waitingArduinoText.setText("Initial Firmware Upload Success");
                progressBar.setVisible(false);

                break;
            }

            case LINUX:
            {
                // todo getDeviceAccessPath for Linux
                break;
            }

            case WINDOWS:
            {
                // todo getDeviceAccessPath for Windows
                break;
            }
        }
    }
}
