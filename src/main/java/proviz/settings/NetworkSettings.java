package proviz.settings;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import proviz.ProjectConstants;
import proviz.SettingsViewListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Burak on 4/6/17.
 */
public class NetworkSettings extends JPanel implements SettingsViewListener {

    private JTextField tabletIpAddressTextField;
    private JLabel tabletIpAddressLabel;

    public NetworkSettings()
    {
        initGUI();

    }

    public void initGUI()
    {

        this.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        this.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabletIpAddressTextField = new JTextField();
        panel2.add(tabletIpAddressTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tabletIpAddressLabel = new JLabel();
        tabletIpAddressLabel.setText("Tablet Ip Address:");
        panel2.add(tabletIpAddressLabel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

    }

    @Override
    public void onCancelPressed() {


    }

    @Override
    public void onApplyPressed() {

    ProjectConstants.init().setTabletIpAddress(tabletIpAddressLabel.getText());

    }
}
