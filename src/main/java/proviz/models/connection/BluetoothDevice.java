package proviz.models.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.ProjectConstants;
import proviz.connection.BluetoothActionsListener;
import proviz.models.configuration.ProjectConfiguration;
import proviz.models.devices.Board;

import javax.bluetooth.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Burak on 1/27/17.
 */
public class BluetoothDevice {

    private ServiceRecord[] services;
    private RemoteDevice remoteDevice;
    private Board board;



    public BluetoothDevice() {

    }

    public ServiceRecord[] getServices() {
        return services;
    }


    public void setServices(ServiceRecord[] services) {
        this.services = services;
    }

    @Override
    public String toString() {
        try {
            String name = remoteDevice.getFriendlyName(true);
            if (name != null && name.length() != 0) {
                return name;
            } else {
                return "Unnamed Device - " + remoteDevice.getBluetoothAddress();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    public RemoteDevice getRemoteDevice() {
        return remoteDevice;
    }

    public void setRemoteDevice(RemoteDevice remoteDevice) {
        this.remoteDevice = remoteDevice;
    }



    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
