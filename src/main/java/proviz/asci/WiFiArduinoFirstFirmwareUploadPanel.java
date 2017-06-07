package proviz.asci;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import freemarker.template.TemplateException;
import org.usb4java.*;
import proviz.ProjectConstants;
import proviz.codedistribution.ArduinoFlasher;
import proviz.codedistribution.CodeDistributionManager;
import proviz.codegeneration.ArduinoTemplate;
import proviz.codegeneration.CodeGeneration;
import proviz.library.utilities.FileOperations;
import proviz.models.devices.Board;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import javax.usb.*;


/**
 * Created by Burak on 1/24/17.
 */
public class WiFiArduinoFirstFirmwareUploadPanel extends JPanel {

    private JProgressBar progressBar1;
    private JLabel statusTextLabel;
    private JPanel panel1;
    private DeviceConfigurationDialog dialog;


    public  WiFiArduinoFirstFirmwareUploadPanel(DeviceConfigurationDialog dialog)
    {
        initUI();
        this.add(panel1);
        this.dialog = dialog;


    }

    public void activateFlash()
    {dialog.disableOKButton();
        getDeviceAccessPath();
    }

    private void initUI()
    {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(960, 400));
        panel1.setPreferredSize(new Dimension(960, 400));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        statusTextLabel = new JLabel();
        statusTextLabel.setText("Waiting Arduino device");
        panel2.add(statusTextLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        progressBar1 = new JProgressBar();
        progressBar1.setIndeterminate(true);
        panel2.add(progressBar1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, 20));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel3, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Now It's time to meet with your Arduino and us.");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setIcon(new ImageIcon(getClass().getResource("/images/usb_alone.png")));
        label2.setText("Please connect your Arduino through USB Port");
        panel3.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

    }
    private static BufferedReader getOutput(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }
    private static BufferedReader getError(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
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
                    dialog.enableOKButton();

                    statusTextLabel.setText("Initial Firmware Upload Success");
                    progressBar1.setVisible(false);

                break;
            }

            case LINUX:
            {

                break;
            }

            case WINDOWS:
            {

                break;
            }
        }
    }



}
