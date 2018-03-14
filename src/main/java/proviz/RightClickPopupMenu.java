package proviz;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import proviz.asci.DeviceConfigurationDialog;
import proviz.codegeneration.CodeGenerationTemplate;

/**
 * Created by Burak on 8/10/17.
 */
public class RightClickPopupMenu extends ContextMenu {

    private MenuItem program;
    private MenuItem configure;
    private MenuItem details;
    private CodeGenerationTemplate codeGenerationTemplate;

    public RightClickPopupMenu(MainEntrance mainEntrance, CodeGenerationTemplate codeGenerationTemplate) {

        this.codeGenerationTemplate = codeGenerationTemplate;
        program = new MenuItem("Program");
        configure = new MenuItem("Configure");
        details = new MenuItem("Details");
        getItems().addAll(program, configure, details);

        program.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DeviceProgrammingScreen deviceProgrammingScreen = new DeviceProgrammingScreen(mainEntrance,codeGenerationTemplate);
                deviceProgrammingScreen.setMinSize(905, 505);
                mainEntrance.hideRightSideBar();
                mainEntrance.addToStack(true, deviceProgrammingScreen);
            }
        });

        configure.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DeviceConfigurationDialog deviceConfigurationDialog = new DeviceConfigurationDialog(mainEntrance,codeGenerationTemplate);
                mainEntrance.hideRightSideBar();
                mainEntrance.addToStack(true, deviceConfigurationDialog);
            }
        });
    }





}