package proviz.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import proviz.ProjectConstants;
import proviz.models.connection.IncomingDeviceData;

import javax.bluetooth.ServiceRecord;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.swing.*;
import java.io.*;

/**
 * Created by Burak on 2/10/17.
 */
public class BluetoothConnection extends BaseConnection {

    private ServiceRecord serviceRecord;

    public ServiceRecord getServiceRecord() {
        return serviceRecord;
    }

    public void setServiceRecord(ServiceRecord serviceRecord) {
        this.serviceRecord = serviceRecord;
    }

    public BluetoothConnection() {
        setConnection_type(ProjectConstants.CONNECTION_TYPE.BLUETOOTH_CLASSIC);
    }

    @Override
    public void startConnection() {

try {
    CommPortIdentifier portIdentifier = CommPortIdentifier
            .getPortIdentifier("/dev/cu.AdafruitEZ-Link6419-SPP");
    if (portIdentifier.isCurrentlyOwned()) {
        System.out.println("Error: Port is currently in use");
    } else {
        int timeout = 2000;
        CommPort commPort = portIdentifier.open(this.getClass().getName(), timeout);

        if (commPort instanceof SerialPort) {
            SerialPort serialPort = (SerialPort) commPort;
            try {
                serialPort.setSerialPortParams(115200,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
            }

            InputStream in = serialPort.getInputStream();
            OutputStream out = serialPort.getOutputStream();

            (new Thread(new SerialPortReader(codeGenerationTemplate.getBoard(),in))).start();


        } else {
            System.out.println("Error: Only serial ports are handled by this example.");
        }
    }
}
catch(Exception ex)
{
    ex.printStackTrace();
}

    }

    @Override
    public void stopConnection() {

    }

}

