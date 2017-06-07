package proviz.asci;

import proviz.ProjectConstants;
import proviz.connection.BluetoothActionsListener;
import proviz.connection.BluetoothConnection;
import proviz.connection.BluetoothManager;
import proviz.models.connection.BluetoothDevice;

import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Burak on 1/24/17.
 */
public class BluetoothDeviceSearchPanel extends JPanel implements BluetoothActionsListener{

    private JProgressBar deviceSearchProgressBar;
    private JLabel searchStatusLabel;
    private JList deviceListView;
    private JButton pairBttn;
    private JPanel panel1;
    HashMap<String, BluetoothDevice> deviceHashMap;
    private BluetoothManager bluetoothManager;
    private DeviceConfigurationDialog deviceConfigurationDialog;



    public BluetoothDeviceSearchPanel(DeviceConfigurationDialog deviceConfigurationDialog)
    {
        initUI();
        DefaultListModel<String> listModel;
        this.deviceConfigurationDialog  = deviceConfigurationDialog;
        listModel = new DefaultListModel<>();
        setBackground(Color.RED);
        setLayout(new GridBagLayout());
        pairBttn.setEnabled(false);
        Dimension dimension = new Dimension(980,400);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setMinimumSize(dimension);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        add(panel1,constraints);
        deviceSearchProgressBar.setIndeterminate(true);
        deviceHashMap = new HashMap<>();

        deviceListView.setModel(listModel);

        deviceListView.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                pairBttn.setEnabled(true);
            }
        });



        pairBttn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {

                    searchStatusLabel.setText("Preparing for Pair Request");

                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        StreamConnection streamConnection;

                        @Override
                        protected Void doInBackground() throws Exception {
                            BluetoothDevice selectedBluetoothDevice = deviceHashMap.get(deviceListView.getSelectedValue());

                            if (selectedBluetoothDevice.getServices().length > 0) {

                                 streamConnection  = (StreamConnection) Connector.open(selectedBluetoothDevice.getServices()[0].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, true));

                                BluetoothConnection bluetoothConnection = new BluetoothConnection();

                                bluetoothConnection.setServiceRecord(selectedBluetoothDevice.getServices()[0]);
                                deviceConfigurationDialog.getCandidateBoard().setBaseConnection(bluetoothConnection);
                                bluetoothConnection.setCodeGenerationTemplate(deviceConfigurationDialog.getCodeGenerationTemplate());

                                //todo BLuecove has bug. System could not authenticate with devices.
                            }

                            try {
                                deviceConfigurationDialog.enableOKButton();



                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }

                            return null;

                        }

                        @Override
                        protected void done() {
                                searchStatusLabel.setText("Pair Operation OK.");
                            try {
                                streamConnection.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }


                        }

                    };

                    worker.execute();
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
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
        });

        searchStatusLabel.setText("Device Searching..");
    }

    public void activeBluetooth()
    {
        bluetoothManager = BluetoothManager.init();
        bluetoothManager.suscribeBleutoothActionListener(this);
        bluetoothManager.startBluetoothService();
    }
    private void initUI()
    {
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(960, 400));
        panel1.setPreferredSize(new Dimension(960, 400));
        panel1.setRequestFocusEnabled(true);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(panel2, gbc);
        deviceSearchProgressBar = new JProgressBar();
        deviceSearchProgressBar.setValue(50);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(deviceSearchProgressBar, gbc);
        searchStatusLabel = new JLabel();
        searchStatusLabel.setText("Device Searching..");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        panel1.add(searchStatusLabel, gbc);
        deviceListView = new JList();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel1.add(deviceListView, gbc);
        pairBttn = new JButton();
        pairBttn.setText("Pair");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(pairBttn, gbc);
        final JLabel label1 = new JLabel();
        label1.setText("Available Devices");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        panel1.add(label1, gbc);

    }

    @Override
    public void newDeviceDiscovered(BluetoothDevice device) {


        try {
            String deviceName  = device.getRemoteDevice().getFriendlyName(false);
            if(!(deviceName != null && deviceName.length()>0))
            {
                deviceName = device.getRemoteDevice().getBluetoothAddress();
            }

            deviceHashMap.put(deviceName,device);

            updateList();

        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private void updateList()
    {
        DefaultListModel<String> bluetoothdevicelist = new DefaultListModel<>();
        Object[] keys= deviceHashMap.keySet().toArray();
        for(Object deviceName: keys)
        {
            bluetoothdevicelist.addElement((String)deviceName);
        }

        deviceListView.setModel(bluetoothdevicelist);
    }
}
