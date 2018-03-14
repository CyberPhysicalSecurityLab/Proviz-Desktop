package proviz.asci.old;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Burak on 1/24/17.
 */
public class DeviceConfigurationResultPanel extends JPanel{
    private JLabel statusLabel;
    private  JPanel panel1;

    public DeviceConfigurationResultPanel()
    {
        initUI();
        this.add(panel1);
    }

    private void initUI()
    {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(960, 400), new Dimension(960, 400), 0, false));
        statusLabel = new JLabel();
        statusLabel.setIcon(new ImageIcon(getClass().getResource("/icons/checked.png")));
        statusLabel.setText("Device Configuration Successfuly Completed");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel2.add(statusLabel, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 20, 0), -1, 0));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(panel3, gbc);


    }
}
