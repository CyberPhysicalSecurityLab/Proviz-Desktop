package proviz.asci.old;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import proviz.ProjectConstants;
import proviz.codegeneration.ArduinoTemplate;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.codegeneration.RaspberryPiTemplate;
import proviz.connection.WiFiConnection;
import proviz.devicedatalibrary.DataManager;
import proviz.library.utilities.SwipePanelsAnimation;
import proviz.models.devices.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeviceConfigurationDialog extends JDialog {
    private JPanel contentPane;
    private JButton positiveBttn;
    private JButton negativeBttn;
    private JPanel mainPanel;
    private JPanel dialogMainPanel;
    private DeviceConfigurationResultPanel deviceConfigurationResultPanel;
    private ConnetionTypeSelectionPanel connetionTypeSelectionPanel;
    private BluetoothDeviceSearchPanel bluetoothDeviceSearchPanel;
    private WiFiArduinoFirstFirmwareUploadPanel wiFiArduinoFirstFirmwareUploadPanel;
    private WiFiRaspClientSoftwarePanel wiFiRaspClientSoftwarePanel;
    private DeviceInformationPanel deviceInformationPanel;
    private SwipePanelsAnimation swipePanelsAnimation;
    private CodeGenerationTemplate codeGenerationTemplate;
    private Board candidateBoard;

    public DeviceConfigurationDialog(CodeGenerationTemplate codeGenerationTemplate) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(positiveBttn);
        this.codeGenerationTemplate = codeGenerationTemplate;
        positiveBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        negativeBttn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        initUI();
    }

    private void initUI() {

        deviceInformationPanel = new DeviceInformationPanel(codeGenerationTemplate);

        deviceConfigurationResultPanel = new DeviceConfigurationResultPanel();
        connetionTypeSelectionPanel = new ConnetionTypeSelectionPanel();
        bluetoothDeviceSearchPanel = new BluetoothDeviceSearchPanel(this);
        wiFiArduinoFirstFirmwareUploadPanel = new WiFiArduinoFirstFirmwareUploadPanel(this);
        wiFiRaspClientSoftwarePanel = new WiFiRaspClientSoftwarePanel(this);


        Dimension dimension = new Dimension(960, 400);
        dialogMainPanel.setPreferredSize(dimension);
        dialogMainPanel.setMaximumSize(dimension);
        dialogMainPanel.setMinimumSize(dimension);


        ArrayList<JPanel> panels = new ArrayList<>();
        panels.add(deviceInformationPanel);
        panels.add(connetionTypeSelectionPanel);
        panels.add(bluetoothDeviceSearchPanel);
        panels.add(wiFiArduinoFirstFirmwareUploadPanel);
        panels.add(wiFiRaspClientSoftwarePanel);
        panels.add(deviceConfigurationResultPanel);

        swipePanelsAnimation = new SwipePanelsAnimation(dialogMainPanel, panels);


    }

    public void skip2NextPanel() {
        onOK();
    }

    private void onOK() {
        // add your code here
        JPanel activePanel = swipePanelsAnimation.getActivePanel();
        if (activePanel instanceof DeviceInformationPanel) {
            DeviceInformationPanel deviceInformationPanel = (DeviceInformationPanel) activePanel;

            candidateBoard = new Board(((Board) deviceInformationPanel.getDeviceTypeComboBox().getSelectedItem()));
            candidateBoard.setProgrammed(false);
            // todo gecici sonra sil
            candidateBoard.setUniqueId(codeGenerationTemplate.getBoardUniqueCode().toString().replace("-", "_"));

            if (codeGenerationTemplate instanceof ArduinoTemplate)
                candidateBoard.setDevice_type(ProjectConstants.DEVICE_TYPE.ARDUINO);
            else if (codeGenerationTemplate instanceof RaspberryPiTemplate)
                candidateBoard.setDevice_type(ProjectConstants.DEVICE_TYPE.RASPBERRY_PI);
            // todo tst
            candidateBoard.initializeRequirements();
            candidateBoard.setUserFriendlyName(deviceInformationPanel.getDeviceUserFriendlyName());


        } else if (activePanel instanceof DeviceConfigurationResultPanel) {

            if (candidateBoard != null) {
                codeGenerationTemplate.setBoard(candidateBoard);
                candidateBoard.setCodeGenerationTemplate(codeGenerationTemplate);
                candidateBoard.setProgrammed(true);
                DataManager.getInstance().getActiveBoards().add(candidateBoard);
                if (candidateBoard.getDevice_type() == ProjectConstants.DEVICE_TYPE.ARDUINO && candidateBoard.getBaseConnection() instanceof WiFiConnection) {
                    candidateBoard.getBaseConnection().startConnection();
                }
                codeGenerationTemplate.getBoardView().setDeviceName(candidateBoard.getUserFriendlyName());
                codeGenerationTemplate.getBoardView().repaint();
                // codeGenerationTemplate.getBoardView().getParentView().revalidate();
                dispose();
            }

        } else if (activePanel instanceof ConnetionTypeSelectionPanel) {
            reArrangePanelsbyConnectionType(connetionTypeSelectionPanel.getSelectedConnectionType());
            if (connetionTypeSelectionPanel.getSelectedConnectionType() == ProjectConstants.CONNECTION_TYPE.BLUETOOTH_CLASSIC) {
                disableOKButton();
                bluetoothDeviceSearchPanel.activeBluetooth();
            } else if (connetionTypeSelectionPanel.getSelectedConnectionType() == ProjectConstants.CONNECTION_TYPE.INTERNET) {
                candidateBoard.setBaseConnection(new WiFiConnection(candidateBoard));
                if (codeGenerationTemplate.getBoardView().getType() == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI) {
                    swipePanelsAnimation.hidePanel(wiFiArduinoFirstFirmwareUploadPanel);
                } else if (codeGenerationTemplate.getBoardView().getType() == ProjectConstants.DEVICE_TYPE.ARDUINO) {
                    wiFiArduinoFirstFirmwareUploadPanel.activateFlash();
                    swipePanelsAnimation.hidePanel(wiFiRaspClientSoftwarePanel);
                }


            }
        } else if (activePanel instanceof BluetoothDeviceSearchPanel) {


        } else if (activePanel instanceof WiFiArduinoFirstFirmwareUploadPanel) {

        } else if (activePanel instanceof WiFiRaspClientSoftwarePanel) {


        }
        if (swipePanelsAnimation.getActivePanel() != deviceConfigurationResultPanel)
            swipePanelsAnimation.LeftSwipe();

    }

    private void reArrangePanelsbyConnectionType(ProjectConstants.CONNECTION_TYPE connection_type) {
        switch (connection_type) {
            case BLUETOOTH_CLASSIC: {
                swipePanelsAnimation.hidePanel(wiFiRaspClientSoftwarePanel);
                swipePanelsAnimation.hidePanel(wiFiArduinoFirstFirmwareUploadPanel);
                codeGenerationTemplate.setConnectionType(ProjectConstants.CONNECTION_TYPE.BLUETOOTH_CLASSIC);
                candidateBoard.setConnectionType(ProjectConstants.CONNECTION_TYPE.BLUETOOTH_CLASSIC);
                break;
            }
            case INTERNET: {
                swipePanelsAnimation.hidePanel(bluetoothDeviceSearchPanel);
                if (candidateBoard.getDevice_type() == ProjectConstants.DEVICE_TYPE.RASPBERRY_PI)
                    swipePanelsAnimation.hidePanel(wiFiArduinoFirstFirmwareUploadPanel);
                if (candidateBoard.getDevice_type() == ProjectConstants.DEVICE_TYPE.ARDUINO)
                    swipePanelsAnimation.hidePanel(wiFiRaspClientSoftwarePanel);
                codeGenerationTemplate.setConnectionType(ProjectConstants.CONNECTION_TYPE.INTERNET);
                candidateBoard.setConnectionType(ProjectConstants.CONNECTION_TYPE.INTERNET);

                break;

            }
            case SERIAL: {
                swipePanelsAnimation.hidePanel(wiFiArduinoFirstFirmwareUploadPanel);
                swipePanelsAnimation.hidePanel(wiFiRaspClientSoftwarePanel);
                swipePanelsAnimation.hidePanel(bluetoothDeviceSearchPanel);
                codeGenerationTemplate.setConnectionType(ProjectConstants.CONNECTION_TYPE.SERIAL);
                candidateBoard.setConnectionType(ProjectConstants.CONNECTION_TYPE.SERIAL);

                break;
            }
        }
    }

    public void disableOKButton() {
        positiveBttn.setEnabled(false);
    }

    public void enableOKButton() {
        positiveBttn.setEnabled(true);
    }

    public void disableCancelButton() {
        negativeBttn.setEnabled(false);
    }

    public void enableCanceButton() {
        negativeBttn.setEnabled(true);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
//        DeviceConfigurationDialog dialog = new DeviceConfigurationDialog();
//        dialog.pack();
//        dialog.setVisible(true);
//        System.exit(0);
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setMinimumSize(new Dimension(980, 600));
        contentPane.setPreferredSize(new Dimension(980, 600));
        contentPane.setRequestFocusEnabled(false);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        positiveBttn = new JButton();
        positiveBttn.setText("Finish");
        panel2.add(positiveBttn, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        negativeBttn = new JButton();
        negativeBttn.setText("Cancel");
        panel2.add(negativeBttn, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        contentPane.add(mainPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(-1, 520), null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(panel3, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Device Informations");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.setBackground(new Color(-11381417));
        panel3.add(panel4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 2), new Dimension(-1, 2), new Dimension(-1, 2), 0, false));
        dialogMainPanel = new JPanel();
        dialogMainPanel.setLayout(new GridBagLayout());
        dialogMainPanel.setBackground(new Color(-1118482));
        dialogMainPanel.setMinimumSize(new Dimension(960, 400));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(dialogMainPanel, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
