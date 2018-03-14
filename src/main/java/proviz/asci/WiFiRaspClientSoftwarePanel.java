package proviz.asci;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import proviz.ProjectConstants;
import proviz.library.utilities.FileOperations;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Burak on 8/10/17.
 */
public class WifiRaspClientSoftwarePanel extends VBox {

    DirectoryChooser directoryChooser;
    Desktop desktop;
    Text mainMessageText, ipAdress,minorMessage;
    TextField ipAddressTextField;
    HBox textBox,ipAdressBox,buttBox,minorMessageBox;
    Button getClientSoftwareBttn;
    private Stage stage;
    private DeviceConfigurationDialog deviceConfigurationDialog;
    private File selectedFolder;

    private  Pattern VALID_IPV4_PATTERN = null;
    private  Pattern VALID_IPV6_PATTERN = null;
    private  final String ipv4Pattern = "(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])";
    private  final String ipv6Pattern = "([0-9a-f]{1,4}:){7}([0-9a-f]){1,4}";

    WifiRaspClientSoftwarePanel(DeviceConfigurationDialog deviceConfigurationDialog,Stage stage){
        directoryChooser = new DirectoryChooser();
         desktop = Desktop.getDesktop();
         this.stage = stage;
        this.deviceConfigurationDialog = deviceConfigurationDialog;
        initUI(stage);
        VALID_IPV4_PATTERN = Pattern.compile(ipv4Pattern, Pattern.CASE_INSENSITIVE);
        VALID_IPV6_PATTERN = Pattern.compile(ipv6Pattern, Pattern.CASE_INSENSITIVE);
    }

    public boolean isIpAddressValid()
    {
        boolean result = false;
        Matcher m1 = VALID_IPV4_PATTERN.matcher(ipAddressTextField.getText());
        if(m1.matches())
            result = true;
        Matcher m2 = VALID_IPV6_PATTERN.matcher(ipAddressTextField.getText());
        if(m2.matches())
            result = true;
        return result;
    }

    public String getIpAddress()
    {
        return ipAddressTextField.getText();
    }



    void initUI(Stage stage)
    {
        mainMessageText = new Text("RPI Client Software Needs to be Prepared.");
        minorMessage = new Text("You have to enter your Raspberry Pi IP adress. We will use to notify your raspberry pi when new firmware is available for your device.");
        minorMessageBox  = new HBox();
        minorMessageBox.setPadding(new Insets(20));
        minorMessageBox.getChildren().add(minorMessage);
        textBox = new HBox();
        textBox.setPadding(new Insets(20));


        ipAdressBox = new HBox();
        ipAdress = new Text("Board IP Adress:");
        ipAddressTextField = new TextField();
        ipAddressTextField.setPrefWidth(700);
        ipAddressTextField.setPadding(new Insets(0,0,0,20));
        ipAdressBox.getChildren().addAll(ipAdress,ipAddressTextField);
        ipAdressBox.setPadding(new Insets(20));


        buttBox = new HBox();
        getClientSoftwareBttn = new Button("Get Client Software");
        getClientSoftwareBttn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                selectedFolder = directoryChooser.showDialog(stage);
                generateClientApp();

            }
        });

        buttBox.getChildren().add(getClientSoftwareBttn);
        buttBox.setPadding(new Insets(20));
        buttBox.setAlignment(Pos.CENTER_LEFT);

        textBox.getChildren().add(mainMessageText);
        textBox.setAlignment(Pos.TOP_CENTER);

        this.getChildren().addAll(textBox, minorMessageBox, ipAdressBox,buttBox);
    }
    private void generateClientApp()
    {

            if(ProjectConstants.isProd == true)
            {
                try {
                    //todo You need to client code generation part for prod version.
                    CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
                    if (codeSource == null)
                        throw new NullPointerException("Protection domain is null");
                    URL jarPath = codeSource.getLocation();
                    JarFile jarfile = new JarFile(codeSource.getLocation().getFile());
                    URI uri = URI.create("jar:file:" + jarfile);
                    Map<String, String> env = new HashMap<>();
                    env.put("create", "true");
                    FileSystem fileSystem = FileSystems.newFileSystem(uri, env, null);
                    Path generatedClientPath = fileSystem.getPath("/clientapplication");
                    Files.copy(generatedClientPath,new File("/Users/Burak/tst").toPath(), StandardCopyOption.REPLACE_EXISTING);

                }

                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
            else
            {
                URL clientApplicationPath = WifiRaspClientSoftwarePanel.class.getClassLoader().getResource("clientapplication");
                URL configPropertiesFilePath = WifiRaspClientSoftwarePanel.class.getClassLoader().getResource(".config.properties");
                File configPropertiesFile = new File(configPropertiesFilePath.getFile());
                File clientApplicationFolder = new File(clientApplicationPath.getFile());
                try {
                    FileUtils.copyDirectory(clientApplicationFolder,selectedFolder);
                    FileUtils.copyFileToDirectory(configPropertiesFile,selectedFolder);
                    deviceConfigurationDialog.onPositiveButtonClicked();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


    }
    private String getUSBSystemPath()
    {
        switch (FileOperations.init().checkOS())
        {
            case OSX:
            {
                return "/Volumes";
            }
            case LINUX:
            {
                return "/media/"+System.getProperty("user.name");
            }
            case WINDOWS:
            {
                // todo windows root folder needs to be added.
                break;
            }
        }
        return null;
    }
}
