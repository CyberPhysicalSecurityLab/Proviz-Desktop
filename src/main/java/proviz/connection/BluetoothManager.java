package proviz.connection;

import com.intel.bluetooth.RemoteDeviceHelper;
import proviz.models.connection.BluetoothDevice;
import proviz.ProjectConstants;

import javax.bluetooth.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Burak on 10/4/16.
 */
public class BluetoothManager {
    UUID[] uuids;
    private static BluetoothManager bluetoothManager;
    private ArrayList<BluetoothDevice> devices;


    private ArrayList<BluetoothActionsListener> bluetoothActionsListeners;


    public static BluetoothManager init()
    {
        if(bluetoothManager == null)
        {
            try {
                bluetoothManager = new BluetoothManager();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BluetoothStateException e) {
                e.printStackTrace();
            }
        }
        return bluetoothManager;
    }

    public void suscribeBleutoothActionListener(BluetoothActionsListener bluetoothActionsListener)
    {
        bluetoothActionsListeners.add(bluetoothActionsListener);
    }

    public void unsuscribeBleutoothActionListener(BluetoothActionsListener bluetoothActionsListener)
    {
        bluetoothActionsListeners.remove(bluetoothActionsListener);
    }

    public BluetoothManager() throws InterruptedException, BluetoothStateException {
        devices = new ArrayList<>();
        bluetoothActionsListeners = new ArrayList<>();

    }

    DiscoveryListener discoveryListener = new DiscoveryListener() {
        @Override
        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            try {


                    BluetoothDevice bluetoothDevice = new BluetoothDevice();
                    bluetoothDevice.setRemoteDevice(btDevice);
                    devices.add(bluetoothDevice);
                    discoverServices(bluetoothDevice);




            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {

        }

        @Override
        public void serviceSearchCompleted(int transID, int respCode) {

        }


        @Override
        public void inquiryCompleted(int discType) {

        }
    };




private void discoverServices(BluetoothDevice bluetoothDevice) throws InterruptedException {
//synchronized (lock2) {
    try {

        LocalDevice.getLocalDevice().getDiscoveryAgent().searchServices(null, uuids, bluetoothDevice.getRemoteDevice(), new DiscoveryListener() {
            @Override
            public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
                System.out.println("ih");
            }

            @Override
            public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
                System.out.println("Services Discovered");
                bluetoothDevice.setServices(servRecord);

            }

            @Override
            public void serviceSearchCompleted(int transID, int respCode) {
                System.out.println("serviceSearchCompleted");
                for (BluetoothActionsListener listener : bluetoothActionsListeners)
                    listener.newDeviceDiscovered(bluetoothDevice);



            }

            @Override
            public void inquiryCompleted(int discType) {
                System.out.println("Inquiry bitti");

            }
        });


    } catch (BluetoothStateException e) {
        e.printStackTrace();
    }

    }




    public void startBluetoothService() {
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                if (LocalDevice.isPowerOn()) {
                    System.out.println(" Bluetooth Search started");
                    LocalDevice.getLocalDevice().setDiscoverable(DiscoveryAgent.GIAC);

                    LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC, discoveryListener);

                }


                uuids = new UUID[1];
                uuids[0] = ProjectConstants.init().getBluetoothServiceUUID();
            }  catch (BluetoothStateException e) {
                e.printStackTrace();
            }
        }
    }).start();

    }



    public boolean pair(BluetoothDevice device) throws Exception
    {

            return RemoteDeviceHelper.authenticate(device.getRemoteDevice());
    }

}
