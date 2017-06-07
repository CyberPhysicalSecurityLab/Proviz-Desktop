package proviz.thirdpartyconnections.tablet;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Burak on 5/13/17.
 */
public class IpAddressFormPanel extends JPanel {
    private JTextField enterTabletIPAddressTextField;


    public String getIpAddress()
    {
        String ipAddress =  enterTabletIPAddressTextField.getText();
        return ipAddress;
    }


    public void initiliazeUI()
    {
        JPanel panel1 = this;
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(872, 613));
        panel1.setPreferredSize(new Dimension(872, 613));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.6;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("<html>Hi,\n<br>\nMy name is Berke, \n<br>\n<br>\nI am going to help you to creation connection between\n<br>\nthis application and your Android tablet application.\n<br>\n<br>\nPlease enter your tablet IP address into below field \n<br>\nthen click next button.\n<br>\n<br>\nYou can find your IP address from Proviz Tablet Application.</html>");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 10, 0, 0);
        panel2.add(label1, gbc);
        enterTabletIPAddressTextField = new JTextField();
        enterTabletIPAddressTextField.setText("Enter Tablet IP Address");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 0, 10);
        panel2.add(enterTabletIPAddressTextField, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel3, gbc);
        final JLabel label2 = new JLabel();
        label2.setIcon(new ImageIcon(getClass().getResource("/images/designer.png")));
        label2.setInheritsPopupMenu(true);
        label2.setText("");
        panel3.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));

    }

}
