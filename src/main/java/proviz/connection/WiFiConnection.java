package proviz.connection;

import proviz.ProjectConstants;
import proviz.connection.restwebservice.WebServiceManager;
import proviz.connection.webserver.TCPSingleConnectionHandler;
import proviz.connection.webserver.TCPWebServer;
import proviz.models.devices.Board;

/**
 * Created by Burak on 2/14/17.
 */
public class WiFiConnection extends BaseConnection {


    private ProjectConstants.DEVICE_TYPE device_type;
    private String deviceId;

    public WiFiConnection(Board candidateBoard)
    {
        this.device_type = candidateBoard.getDevice_type();
        this.deviceId = candidateBoard.getUniqueId();
    }

    @Override
    public void stopConnection() {

    }

    @Override
    public void startConnection() {

        if(device_type == ProjectConstants.DEVICE_TYPE.ARDUINO)
        {
            TCPWebServer tcpWebServer = TCPWebServer.init();
            TCPSingleConnectionHandler tcpSingleConnectionHandler = tcpWebServer.getSingleConnectionHandler(deviceId);
            if(tcpSingleConnectionHandler == null)
            {
                tcpWebServer.createNewConnection(deviceId);
            }


        }
        else {
            WebServiceManager webServiceManager = WebServiceManager.getInstance();

            try {
                webServiceManager.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
