package proviz.asci;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import proviz.ProjectConstants;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.devicedatalibrary.DataManager;
import proviz.models.devices.Board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 1/23/17.
 */
public class DeviceInformationPanel extends JPanel {

    private JTextField textField1;
    private JComboBox deviceTypeComboBox;
    private JLabel deviceNameLabel;
    private JLabel deviceUniqueId;
    private JLabel deviceTypeLabel;
    private JPanel deviceNamePanel;
    private JPanel deviceUniqueIdPanel;
    private JPanel deviceTypePanel;
    private JPanel mainPanel;
    private CodeGenerationTemplate template;



    public  DeviceInformationPanel(CodeGenerationTemplate template)
    {
        this.template = template;
        initUI();
        getRelatedBoards(template.getBoardView().getType());

        add(mainPanel);


    }
    public String getDeviceUserFriendlyName()
    {
        return textField1.getText();
    }


    private void initUI()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setMaximumSize(new Dimension(-1, 140));
        mainPanel.setPreferredSize(new Dimension(980, 140));
        deviceNamePanel = new JPanel();
        deviceNamePanel.setLayout(new BorderLayout(40, 0));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 0, 20);
        mainPanel.add(deviceNamePanel, gbc);
        deviceNameLabel = new JLabel();
        deviceNameLabel.setText("Device Name:");
        deviceNamePanel.add(deviceNameLabel, BorderLayout.WEST);
        textField1 = new JTextField();
        textField1.setColumns(35);
        deviceNamePanel.add(textField1, BorderLayout.CENTER);
        deviceUniqueIdPanel = new JPanel();
        deviceUniqueIdPanel.setLayout(new BorderLayout(20, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 0, 20);
        mainPanel.add(deviceUniqueIdPanel, gbc);
        deviceUniqueId = new JLabel();
        deviceUniqueId.setText("Device Unique Id:");
        deviceUniqueIdPanel.add(deviceUniqueId, BorderLayout.WEST);
        final JLabel label1 = new JLabel();
        label1.setText(template.getBoardUniqueCode().toString());
        deviceUniqueIdPanel.add(label1, BorderLayout.CENTER);
        deviceTypePanel = new JPanel();
        deviceTypePanel.setLayout(new BorderLayout(40, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 0, 20);
        mainPanel.add(deviceTypePanel, gbc);
        deviceTypeLabel = new JLabel();
        deviceTypeLabel.setText("Device Type:");
        deviceTypePanel.add(deviceTypeLabel, BorderLayout.WEST);
        deviceTypeComboBox = new JComboBox();
        deviceTypeComboBox.setAlignmentX(0.0f);
        deviceTypeComboBox.setAlignmentY(0.0f);
        deviceTypePanel.add(deviceTypeComboBox, BorderLayout.CENTER);

    }

    private void getRelatedBoards(ProjectConstants.DEVICE_TYPE device_type)
    {
        ArrayList<Board> boards = DataManager.getInstance().getBoards();

        for(Board board: boards)
        {
            if(device_type== board.getDevice_type())
            {
                deviceTypeComboBox.addItem(board);
            }
        }

    }

    public JComboBox getDeviceTypeComboBox() {
        return deviceTypeComboBox;
    }

    public void setDeviceTypeComboBox(JComboBox deviceTypeComboBox) {
        this.deviceTypeComboBox = deviceTypeComboBox;
    }
}
