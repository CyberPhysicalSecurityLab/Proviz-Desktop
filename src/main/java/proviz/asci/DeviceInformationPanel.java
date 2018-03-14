package proviz.asci;

import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import proviz.ProjectConstants;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.devicedatalibrary.DataManager;
import proviz.library.utilities.FileOperations;
import proviz.models.devices.Board;

import java.util.ArrayList;

/**
 * Created by Burak on 8/10/17.
 */
public class DeviceInformationPanel extends GridPane {
    private Text deviceInformation;
    private TextField deviceName;
    private Text deviceUniqueID;
    private Text uniqueID;
    private Text deviceTypes;
    private CodeGenerationTemplate template;

    public ChoiceBox<String> getDeviceTypeList() {
        return deviceTypeList;
    }

    private ChoiceBox<String> deviceTypeList;

    public DeviceInformationPanel(CodeGenerationTemplate codeGenerationTemplate){

        this.template = codeGenerationTemplate;
        initUI();
//        getRelatedBoards(template.getBoardView().getType());

    }

    private void initUI(){
        deviceInformation = new Text("Device Information: ");
        deviceName = new TextField();
        deviceName.setPrefWidth(700);
        add(deviceInformation, 0, 0);
        add(deviceName, 1, 0);


        Text deviceUniqueID = new Text("Device Unique ID: ");
        Text uniqueID = new Text();
        uniqueID.setText(template.getBoard().getUniqueId());
        add(deviceUniqueID, 0, 1);
        add(uniqueID, 1, 1);


        Text deviceTypes = new Text("Device Type: ");
        deviceTypeList = new ChoiceBox<String>();
        deviceTypeList.setPrefWidth(700);
        getRelatedBoards(template.getBoard().getDevice_type());
        deviceTypeList.getSelectionModel().selectFirst();
        deviceTypeList.setPrefWidth(700);
        add(deviceTypes, 0, 2);
        add(deviceTypeList, 1, 2);

        setHgap(20);
        setVgap(20);
        setPadding(new Insets(20,10 , 0, 20));

    }

  public String getSelectedDeviceModelName()
  {
      String result = deviceTypeList.getValue();
      String[] splittedResult = result.split("-");
      result = splittedResult[1].replaceAll("\\s+","");
      result = result.toLowerCase();

      return result;
  }


    public String getDeviceName(){
        return deviceName.getText();
    }


    private void getRelatedBoards(ProjectConstants.DEVICE_TYPE device_type)
    {
        ArrayList<Board> boards = DataManager.getInstance().getBoards();

        for(Board board: boards)
        {
            if(device_type== board.getDevice_type())
            {
                deviceTypeList.getItems().add(board.toString());
            }
        }

    }

    public String getDeviceUserFriendlyName()
    {
        return deviceName.getText();
    }



}
