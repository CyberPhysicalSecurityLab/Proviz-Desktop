package proviz.codedistribution;

import freemarker.template.TemplateException;
import javafx.scene.control.Alert;
import proviz.ProjectConstants;
import proviz.codegeneration.*;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Burak on 2/1/17.
 */
public class CodeDistributionManager {

    private ProjectConstants.CONNECTION_TYPE connection_type;
    private CodeGenerationTemplate codeGenerationTemplate;
    private CodeFlashingDialog codeFlashingDialog;
    private CodeFlasher codeFlasher;
    private boolean initialFlash;

    SwingWorker<Void,Void> connectionThread = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            codeGenerationTemplate.getBoard().getBaseConnection().startConnection();
            return null;
        }
    };

    public  CodeDistributionManager(CodeGenerationTemplate codeGenerationTemplate,boolean initialFlash)
    {
        this.codeGenerationTemplate = codeGenerationTemplate;
        codeFlashingDialog = new CodeFlashingDialog();
        codeFlashingDialog.pack();
        switch (codeGenerationTemplate.getBoard().getDevice_type())
        {
            case ARDUINO:
            {
                codeFlasher = new ArduinoFlasher(this,codeGenerate(),(ArduinoTemplate) codeGenerationTemplate,initialFlash);
                break;
            }
            case RASPBERRY_PI:
            {
                codeFlasher = new RaspberryPiFlasher(this,codeGenerate(),(RaspberryPiTemplate) codeGenerationTemplate);
                break;
            }
        }

    }

    private String codeGenerate()  {
        CodeGeneration codeGeneration = null;
        try {
            codeGeneration = new CodeGeneration(codeGenerationTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return codeGeneration.createCode();
    }

    public void setTextCodeGenerationDialog(String text)
    {
        codeFlashingDialog.setText(text);
    }


    public void showDialog()
    {
        codeFlashingDialog.setVisible(true);
    }
    public void closeDialog()
    {
        codeFlashingDialog.dispose();
    }

    private Alert createFlashingDialog()
    {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Code Flashing");
        alert.setHeaderText("Code is Flashing");
        alert.setContentText("We will close this windows as soon as it is finished.");

        return alert;
    }

    public void flashCode2Device() {
        try {
            Alert alert = createFlashingDialog();
            //alert.showAndWait();
            setTextCodeGenerationDialog("Code is being generated and flashed..");
             new SwingWorker<Void, Void>() {
                 @Override
                 protected void done() {
                     setTextCodeGenerationDialog("Connection Unit Initializing..");
                     codeGenerationTemplate.getBoard().setProgrammed(true);
                     alert.close();
                     connectionThread.execute();
                     super.done();
                 }

                 @Override
                protected Void doInBackground() throws Exception {
                    codeFlasher.FlashCode();
                    return null;
                }
            }.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setInitialFlash(boolean initialFlash) {
        this.initialFlash = initialFlash;
    }
}
