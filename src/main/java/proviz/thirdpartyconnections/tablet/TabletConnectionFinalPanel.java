package proviz.thirdpartyconnections.tablet;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by Burak on 5/13/17.
 */
public class TabletConnectionFinalPanel extends JPanel {

    private JLabel targetIpAddress;
    private JLabel searchingDeviceTextBox;
    private JLabel iniatingSessionTextBox;
    private JLabel synchingCurrentTopologyTextBox;
    private JLabel searchingTargetDeviceStatusImage;
    private JLabel IniatingSessionStatusImage;
    private JLabel SynchingCurrentsearchingTargetDeviceStatusImage;
    private JLabel informingBoardslabel;
    private JLabel informingboardslabelImage;

    private boolean isSearchingTargetFinishedSuccess;
    private boolean isSynchingCurrentTopologyFinishedSuccess;
    private boolean isInformingBoardsFinishedSuccess;
    private boolean isSessionIniationFinishedSuccess;

    public boolean isSearchingTargetFinishedSuccess() {
        return isSearchingTargetFinishedSuccess;
    }

    public void setSearchingTargetFinishedSuccess(boolean searchingTargetFinishedSuccess) {
        isSearchingTargetFinishedSuccess = searchingTargetFinishedSuccess;
    }

    public boolean isSynchingCurrentTopologyFinishedSuccess() {
        return isSynchingCurrentTopologyFinishedSuccess;
    }

    public void setSynchingCurrentTopologyFinishedSuccess(boolean synchingCurrentTopologyFinishedSuccess) {
        isSynchingCurrentTopologyFinishedSuccess = synchingCurrentTopologyFinishedSuccess;
    }

    public boolean isInformingBoardsFinishedSuccess() {
        return isInformingBoardsFinishedSuccess;
    }

    public void setInformingBoardsFinishedSuccess(boolean informingBoardsFinishedSuccess) {
        isInformingBoardsFinishedSuccess = informingBoardsFinishedSuccess;
    }

    public boolean isSessionIniationFinishedSuccess() {
        return isSessionIniationFinishedSuccess;
    }

    public void setSessionIniationFinishedSuccess(boolean sessionIniationFinishedSuccess) {
        isSessionIniationFinishedSuccess = sessionIniationFinishedSuccess;
    }

    public void SearchingTargetDeviceFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            searchingTargetDeviceStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
            searchingTargetDeviceStatusImage.setVisible(true);
            isSearchingTargetFinishedSuccess = true;
        }
        else
        {
            searchingTargetDeviceStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/error_small.png")));
            searchingTargetDeviceStatusImage.setVisible(true);
            isSearchingTargetFinishedSuccess = false;

        }
    }

    public void SynchingCurrentTopologyFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            SynchingCurrentsearchingTargetDeviceStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
            SynchingCurrentsearchingTargetDeviceStatusImage.setVisible(true);
            isSynchingCurrentTopologyFinishedSuccess = true;
        }
        else
        {
            SynchingCurrentsearchingTargetDeviceStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/error_small.png")));
            SynchingCurrentsearchingTargetDeviceStatusImage.setVisible(true);
            isSynchingCurrentTopologyFinishedSuccess = false;
        }
    }

    public void InformingBoardsFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            informingboardslabelImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
            informingboardslabelImage.setVisible(true);
            isInformingBoardsFinishedSuccess = true;
        }
        else
        {
            informingboardslabelImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/error_small.png")));
            informingboardslabelImage.setVisible(true);
            isInformingBoardsFinishedSuccess = false;
        }
    }

    public void SessionInitiationFinished(boolean isSuccessful)
    {
        if(isSuccessful)
        {
            IniatingSessionStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
            IniatingSessionStatusImage.setVisible(true);
            isSessionIniationFinishedSuccess = true;
        }
        else
        {
            IniatingSessionStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/error_small.png")));
            IniatingSessionStatusImage.setVisible(true);
            isSessionIniationFinishedSuccess = false;
        }
    }


    public void rollBackUI()
    {
        informingboardslabelImage.setVisible(false);
        SynchingCurrentsearchingTargetDeviceStatusImage.setVisible(false);
        IniatingSessionStatusImage.setVisible(false);
        searchingTargetDeviceStatusImage.setVisible(false);
    }

    public void initiliazeUI()
    {
        final JPanel panel1 = this;
        panel1.setLayout(new GridBagLayout());
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setMinimumSize(new Dimension(872, 613));
        panel2.setPreferredSize(new Dimension(872, 613));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel1.add(panel2, gbc);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, new Color(-4473925)));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel3, gbc);
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/images/designer.png")));
        label1.setInheritsPopupMenu(true);
        label1.setText("");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(4, 1, new Insets(50, 25, 0, 25), -1, -1));
        panel3.add(panel4, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new BorderLayout(0, 0));
        panel4.add(panel5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        searchingDeviceTextBox = new JLabel();
        searchingDeviceTextBox.setText("Searching Target Device");
        panel5.add(searchingDeviceTextBox, BorderLayout.WEST);
        searchingTargetDeviceStatusImage = new JLabel();
        searchingTargetDeviceStatusImage.setHorizontalAlignment(4);
        searchingTargetDeviceStatusImage.setHorizontalTextPosition(11);
        searchingTargetDeviceStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
        searchingTargetDeviceStatusImage.setText("");
        searchingTargetDeviceStatusImage.setVisible(false);
        panel5.add(searchingTargetDeviceStatusImage, BorderLayout.EAST);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new BorderLayout(0, 0));
        panel4.add(panel6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        synchingCurrentTopologyTextBox = new JLabel();
        synchingCurrentTopologyTextBox.setHorizontalAlignment(2);
        synchingCurrentTopologyTextBox.setHorizontalTextPosition(2);
        synchingCurrentTopologyTextBox.setText("Synching Current Topology with Tablet");
        panel6.add(synchingCurrentTopologyTextBox, BorderLayout.WEST);
        SynchingCurrentsearchingTargetDeviceStatusImage = new JLabel();
        SynchingCurrentsearchingTargetDeviceStatusImage.setHorizontalAlignment(4);
        SynchingCurrentsearchingTargetDeviceStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
        SynchingCurrentsearchingTargetDeviceStatusImage.setText("");
        SynchingCurrentsearchingTargetDeviceStatusImage.setVisible(false);
        panel6.add(SynchingCurrentsearchingTargetDeviceStatusImage, BorderLayout.EAST);
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new BorderLayout(0, 0));
        panel6.add(panel7, BorderLayout.SOUTH);
        informingBoardslabel = new JLabel();
        informingBoardslabel.setHorizontalAlignment(2);
        informingBoardslabel.setHorizontalTextPosition(2);
        informingBoardslabel.setText("Informing boards to create connection with Table");
        panel7.add(informingBoardslabel, BorderLayout.WEST);
        informingboardslabelImage = new JLabel();
        informingboardslabelImage.setHorizontalAlignment(4);
        informingboardslabelImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
        informingboardslabelImage.setText("");
        informingboardslabelImage.setVisible(false);
        panel7.add(informingboardslabelImage, BorderLayout.EAST);
        final JPanel panel8 = new JPanel();
        panel8.setLayout(new BorderLayout(0, 0));
        panel4.add(panel8, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        iniatingSessionTextBox = new JLabel();
        iniatingSessionTextBox.setText("Initiating Session Create Protocol");
        panel8.add(iniatingSessionTextBox, BorderLayout.WEST);
        IniatingSessionStatusImage = new JLabel();
        IniatingSessionStatusImage.setHorizontalAlignment(4);
        IniatingSessionStatusImage.setIcon(new ImageIcon(getClass().getResource("/icons/small/checked_small.png")));
        IniatingSessionStatusImage.setText("");
        IniatingSessionStatusImage.setVisible(false);
        panel8.add(IniatingSessionStatusImage, BorderLayout.EAST);
        final JPanel panel9 = new JPanel();
        panel9.setLayout(new GridBagLayout());
        panel4.add(panel9, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        targetIpAddress = new JLabel();
        targetIpAddress.setText("Target Device: 10.203.123.222");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel9.add(targetIpAddress, gbc);
    }
}
