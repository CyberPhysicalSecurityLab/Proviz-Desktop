package proviz.connection;

import proviz.models.connection.BluetoothDevice;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

/**
 * Created by Burak on 2/6/17.
 */
public class BluetoothServiceFindListener implements DiscoveryListener {

    BluetoothDevice bluetoothDevice;
    private Object lock;
    public BluetoothServiceFindListener (BluetoothDevice bluetoothDevice,Object lock)
    {
        this.lock = lock;
this.bluetoothDevice =bluetoothDevice;
    }
    @Override
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {

    }

    @Override
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
    bluetoothDevice.setServices(servRecord);

    }

    @Override
    public void serviceSearchCompleted(int transID, int respCode) {
System.out.println("n");
    }

    @Override
    public void inquiryCompleted(int discType) {
synchronized (lock)
{
    lock.notifyAll();
}
    }
}
