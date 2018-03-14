package proviz.asci.old;

import javax.swing.*;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import proviz.models.devices.Pin;
import proviz.models.devices.Sensor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Burak on 1/16/17.
 */
public class FinalStageSensorAddPanel extends JPanel {
    private JPanel contentPane;
    private JScrollPane pinSelectionScrollView;
    private JLabel sensorImageLabel;
    private JPanel pinsList;
    private Box pinBox;

public FinalStageSensorAddPanel()
{
    initializeUIComponents();
    add(contentPane);
}

    public void setSensor(Sensor sensor)
    {
        ArrayList<Pin> pins = sensor.getPins();
        int i =0;
        for(Pin pin:pins)
        {
            String message = pin.getName();
            message += "="+pin.getType().toString().toUpperCase()+" - "+pin.getOrder();
            if(i != 0)
            {
                pinBox.add(Box.createVerticalStrut(1));
            }
            pinBox.add(new JLabel(message));
            i++;

        }
        revalidate();
    }


private void initializeUIComponents()
{
    contentPane = new JPanel();
    contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
    final JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
    final Spacer spacer1 = new Spacer();
    panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    final JPanel panel2 = new JPanel();
    panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
    panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    final JPanel panel3 = new JPanel();
    panel3.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
    contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, true));
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
    final JLabel label3 = new JLabel();
    label3.setText("<html>Please check your pin assignments</html> ");
    GridBagConstraints gbc;
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.NORTHWEST;
    gbc.insets = new Insets(0, 10, 0, 0);
    panel5.add(label3, gbc);
    final JPanel panel6 = new JPanel();
    panel6.setLayout(new GridLayoutManager(1, 2, new Insets(0, 10, 0, 0), -1, -1));
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 4;
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
    final JPanel spacer3 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 3;
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
    final JPanel spacer6 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.weighty = 0.8;
    gbc.fill = GridBagConstraints.VERTICAL;
    panel5.add(spacer6, gbc);

    pinsList = new JPanel(new GridBagLayout());
    JPanel header = new JPanel(new BorderLayout());
    JLabel yourchoiceLabel = new JLabel("Your Choices");
    yourchoiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
    header.add(yourchoiceLabel,BorderLayout.NORTH);
    GridBagConstraints gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.weighty= 0.3;
    pinsList.add(header);


    pinBox = Box.createVerticalBox();
    GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
    gridBagConstraints2.gridx = 0;
    gridBagConstraints2.weighty= 0.7;
    pinsList.add(pinBox,gridBagConstraints2);
    pinSelectionScrollView.setViewportView(pinsList);
    revalidate();
}


    public JPanel getContentPane() {
        return contentPane;
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }
}
