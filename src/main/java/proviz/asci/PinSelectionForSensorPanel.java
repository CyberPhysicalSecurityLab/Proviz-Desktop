package proviz.asci;

import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.Spacer;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.models.devices.Pin;
import proviz.models.devices.Sensor;
import proviz.PinSelectionRow;

import java.util.ArrayList;

/**
 * Created by Burak on 1/16/17.
 */
public class PinSelectionForSensorPanel extends JPanel {

    private JPanel contentPane;
    private JPanel pinSelectionList;
    private CodeGenerationTemplate template;

    private JScrollPane pinSelectionScrollView;
    private JLabel sensorImageLabel;
    private Sensor selectedSensor;

    public PinSelectionForSensorPanel(CodeGenerationTemplate codeGenerationTemplate)
    {
        this.template = codeGenerationTemplate;
        initUI();
        add(contentPane);

    }
    public void setSelectedSensor(Sensor sensor)
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.gridx = 0;
        this.selectedSensor = sensor;

        for (Pin sensorPin : sensor.getPins()) {
            ArrayList<Pin> pinCandidateList = new ArrayList<>();
            ArrayList<Pin> boardPins = null;
            switch (sensorPin.getType()) {
                case analog: {
                    boardPins = template.getBoard().getAnalogPins();
                    break;
                }
                case comm: {
                    boardPins = template.getBoard().getSerialPins();
                    break;
                }
                case digital: {
                    boardPins = template.getBoard().getDigitalPins();
                    break;
                }
                case  i2c:
                {
                    boardPins = template.getBoard().getI2CPins();
                    break;
                }
                case spibus:
                {
                    boardPins  = template.getBoard().getSPIBusPins();
                    break;
                }
            }
            if (sensorPin.getType() != Pin.PINTYPE.ground && sensorPin.getType() != Pin.PINTYPE.vcc) {
                for (Pin boardPin : boardPins) {
                    if (boardPin.isAvailable()) {
                        pinCandidateList.add(boardPin);
                    }
                }

                PinSelectionRow pinSelectionRow = new PinSelectionRow(sensorPin, pinCandidateList);
                pinSelectionList.add(pinSelectionRow, gridBagConstraints);
            }
        }
        pinSelectionScrollView.setViewportView(pinSelectionList);

    }
    public void makeChangesOnSelectedSensor()
    {
        for (Component component : pinSelectionList.getComponents()) {
            PinSelectionRow row = (PinSelectionRow) component;
            Pin boardPin = row.getSelectedPin();
            Pin sensorPin = row.getSensorPin();
            sensorPin.setOrder(boardPin.getOrder());
            boardPin.setAvailable(false);
            sensorPin.setAvailable(false);
            if(sensorPin.getDataDirection() == Pin.DATA_DIRECTION.OUTPUT)
            boardPin.setDataDirection(Pin.DATA_DIRECTION.INPUT);
            else if(sensorPin.getDataDirection() == Pin.DATA_DIRECTION.INPUT)
                boardPin.setDataDirection(Pin.DATA_DIRECTION.OUTPUT);
            boardPin.setIsrequired(sensorPin.isrequired());
        }
    }


    private void initUI ()
    {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setMinimumSize(new Dimension(872, 591));
        contentPane.setPreferredSize(new Dimension(872, 591));
        contentPane.setRequestFocusEnabled(true);
        contentPane.setVerifyInputWhenFocusTarget(false);
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
        panel3.add(panel4, new GridConstraints(0, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/images/asci.png")));
        label1.setText("");
        panel4.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(188, 340), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Asci");
        panel4.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel5.setAlignmentX(0.5f);
        panel3.add(panel5, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(640, 480), new Dimension(640, 480), null, 0, false));
        panel5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), null));
        final JLabel label3 = new JLabel();
        label3.setText("<html>Now we need to you make pin assignment for code generation process.</html> ");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel5.add(label3, gbc);
        final JLabel label4 = new JLabel();
        label4.setHorizontalAlignment(10);
        label4.setHorizontalTextPosition(2);
        label4.setText("Do not forget complete required pin assignment.");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 0.2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel5.add(label4, gbc);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 10, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel5.add(panel6, gbc);
        sensorImageLabel = new JLabel();
        sensorImageLabel.setIcon(new ImageIcon(getClass().getResource("/sensors/images/Ezsonarsensor.jpg")));
        sensorImageLabel.setText("");
        panel6.add(sensorImageLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panel7, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pinSelectionScrollView = new JScrollPane();
        panel7.add(pinSelectionScrollView, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel5.add(spacer5, gbc);

        pinSelectionList = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        pinSelectionList.setLayout(gridBagLayout);

    }


    public JPanel getContentPane() {
        return contentPane;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }


    public Sensor getSelectedSensor() {
        return selectedSensor;
    }
}
