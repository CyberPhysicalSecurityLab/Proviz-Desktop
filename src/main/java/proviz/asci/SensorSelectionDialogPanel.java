package proviz.asci;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import proviz.DeviceProgrammingScreen;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.devicedatalibrary.DataManager;
import proviz.library.utilities.FileOperations;
import proviz.models.devices.Sensor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Burak on 1/16/17.
 */
public class SensorSelectionDialogPanel extends JPanel {

    private JPanel contentPane;
    private JComboBox sensorSelectionComboBox;
    private JButton newSensorButton;
    private JPanel sensorQuickLookPanel;
    private JLabel sensorImageLabel;
    private JLabel sensorNameLabel;
    private JLabel sensorDescriptionLabel;
    private CodeGenerationTemplate codeGenerationTemplate;

    public SensorSelectionDialogPanel()
    {
        initUIComponent();
        fillSensorList();
        this.add(contentPane);

    }

    private void initUIComponent()
    {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setMinimumSize(new Dimension(872, 613));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, true));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/images/asci.png")));
        label1.setText("");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(188, 340), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Asci");
        panel4.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel5.setAlignmentX(0.5f);
        panel3.add(panel5, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(640, 480), new Dimension(640, 480), null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null));
        sensorSelectionComboBox = new JComboBox();
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(sensorSelectionComboBox, gbc);
        final JLabel label3 = new JLabel();
        label3.setText("If you did not find it, you can add by clicking Add Sensor button.");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(0, 0, 0, 10);
        panel5.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setText("<html>Hi, my name is Asci. You will make great things in my visual programming kitchen</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel5.add(label4, gbc);
        final JLabel label5 = new JLabel();
        label5.setHorizontalAlignment(10);
        label5.setHorizontalTextPosition(2);
        label5.setText("First you need to pick one sensor from below list");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel5.add(label5, gbc);
        sensorQuickLookPanel = new JPanel();
        sensorQuickLookPanel.setLayout(new GridLayoutManager(1, 2, new Insets(0, 10, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(sensorQuickLookPanel, gbc);
        sensorImageLabel = new JLabel();
        sensorImageLabel.setIcon(new ImageIcon(getClass().getResource("/sensors/images/Ezsonarsensor.jpg")));
        sensorImageLabel.setText("");
        sensorQuickLookPanel.add(sensorImageLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        sensorQuickLookPanel.add(panel6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        sensorNameLabel = new JLabel();
        sensorNameLabel.setText("Ez Sonar LV Max SuperSonic Sensor");
        panel6.add(sensorNameLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(108, 16), null, 0, false));
        sensorDescriptionLabel = new JLabel();
        sensorDescriptionLabel.setText("<html>Asemic writing is a wordless open semantic form of writing.[1][2] The word asemic means \"having no specific semantic content\".[3] With the nonspecificity of asemic writing there comes a vacuum of meaning which is left for the reader to fill in and interpret. All of this is similar to the way one would deduce meaning from an abstract work of art. </html>");
        panel6.add(sensorDescriptionLabel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(200, 16), null, 0, false));
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer5, gbc);
        newSensorButton = new JButton();
        newSensorButton.setText("New Sensor");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 10);
        panel5.add(newSensorButton, gbc);

        sensorSelectionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().compareTo("comboBoxChanged") == 0) {
                    fillSensorQuickView((Sensor)sensorSelectionComboBox.getSelectedItem());
                }
            }
        });
    }
    public static void main(String argc[])
    {
        JFrame window = new JFrame("Device Programming Window");
        window.setSize(1024, 768);
        SensorSelectionDialogPanel sensorSelectionDialogPanel = new SensorSelectionDialogPanel();
        window.setContentPane(sensorSelectionDialogPanel.getContentPane());
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setResizable(true);
        window.setVisible(true);
        window.pack();
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    private void fillSensorQuickView(Sensor selectedSensor) {
        sensorImageLabel.setIcon(selectedSensor.getImage());
        sensorDescriptionLabel.setText("<html>" + selectedSensor.getDescription() + "</html>");
        sensorNameLabel.setText(selectedSensor.getName());
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    private void fillSensorList() {
        ArrayList<Sensor> sensors  = DataManager.getInstance().getSensors();
        for(Sensor sensor:sensors)
        {
            sensorSelectionComboBox.addItem(sensor);
        }
        invalidate();
    }
    public Sensor getCurrentSelection()
    {
        return (Sensor)sensorSelectionComboBox.getSelectedItem();
    }
    }

