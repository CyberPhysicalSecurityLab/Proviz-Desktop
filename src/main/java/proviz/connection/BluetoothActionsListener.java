package proviz.connection;

import proviz.models.connection.BluetoothDevice;

import javax.bluetooth.RemoteDevice;

/**
 * Created by Burak on 1/27/17.
 */
public interface BluetoothActionsListener {

     void newDeviceDiscovered(BluetoothDevice device) ;



}
