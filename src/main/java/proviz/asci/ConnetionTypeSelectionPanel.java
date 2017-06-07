package proviz.asci;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import proviz.ProjectConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Burak on 1/24/17.
 */
public class ConnetionTypeSelectionPanel extends JPanel implements MouseListener{
    private  JPanel panel1;
    private JLabel bluetoothLogo, wifiLogo, usbLogo;
    private JRadioButton bluetoothRadioBox, usbRadioBox, wifiRadioBox;
    private JRadioButton selectedRadioBox;

    public  ConnetionTypeSelectionPanel()
    {
        initializationUI();
        setLayout(new BorderLayout(0,0));
        add(panel1,BorderLayout.CENTER);
    }

    public ProjectConstants.CONNECTION_TYPE getSelectedConnectionType()
    {
        ProjectConstants.CONNECTION_TYPE selectedType = null;
        if(bluetoothRadioBox.isSelected())
            selectedType = ProjectConstants.CONNECTION_TYPE.BLUETOOTH_CLASSIC;
        else if(usbRadioBox.isSelected())
            selectedType = ProjectConstants.CONNECTION_TYPE.SERIAL;
        else if (wifiRadioBox.isSelected())
            selectedType = ProjectConstants.CONNECTION_TYPE.INTERNET;

        return selectedType;
    }
    private void initializationUI()
    {

        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(980, 520));
        panel1.setPreferredSize(new Dimension(980, 520));
        panel1.setMaximumSize(new Dimension(980, 520));
        panel1.setRequestFocusEnabled(true);
        panel1.setVerifyInputWhenFocusTarget(false);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridConstraints.ALIGN_CENTER;
        panel1.add(panel2);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel2.add(panel3);
        bluetoothLogo = new JLabel();
        bluetoothLogo.setIcon(new ImageIcon(getClass().getResource("/images/connection_type_bluetooth.png")));
        bluetoothLogo.setText("");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel3.add(bluetoothLogo, gbc);
        bluetoothRadioBox = new JRadioButton();
        bluetoothRadioBox.setHorizontalTextPosition(11);
        bluetoothRadioBox.setLabel("");
        bluetoothRadioBox.setText("");
        bluetoothRadioBox.setVerticalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        panel3.add(bluetoothRadioBox, gbc);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel2.add(panel4);
        usbLogo = new JLabel();
        usbLogo.setIcon(new ImageIcon(getClass().getResource("/images/connection_type_usb.png")));
        usbLogo.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel4.add(usbLogo, gbc);
        usbRadioBox = new JRadioButton();
        usbRadioBox.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        panel4.add(usbRadioBox, gbc);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridBagLayout());
        panel2.add(panel5);
        wifiLogo = new JLabel();
        wifiLogo.setIcon(new ImageIcon(getClass().getResource("/images/connection_type_wifi.png")));
        wifiLogo.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel5.add(wifiLogo, gbc);
        wifiRadioBox = new JRadioButton();
        wifiRadioBox.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        panel5.add(wifiRadioBox, gbc);
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(bluetoothRadioBox);
        buttonGroup.add(usbRadioBox);
        buttonGroup.add(wifiRadioBox);

        bluetoothLogo.addMouseListener(this);
        usbLogo.addMouseListener(this);
        wifiLogo.addMouseListener(this);

    }



    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof JLabel)
        {
            if(e.getSource() == bluetoothLogo)
            {
                bluetoothRadioBox.setSelected(true);
            }
            else if(e.getSource() == wifiLogo)
            {
                wifiRadioBox.setSelected(true);
            }
            else if(e.getSource() == usbLogo)
            {
                usbRadioBox.setSelected(true);
            }
        }

        else if(e.getSource() instanceof  JRadioButton)
        {
            if(e.getSource() == bluetoothRadioBox)
            {
                selectedRadioBox = bluetoothRadioBox;
            }
            else if(e.getSource() == wifiRadioBox)
            {
                selectedRadioBox = wifiRadioBox;
            }
            else if(e.getSource() == usbRadioBox)
            {
                selectedRadioBox = usbRadioBox;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
