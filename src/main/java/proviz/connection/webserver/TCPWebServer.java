package proviz.connection.webserver;

import proviz.ProjectConstants;
import proviz.connection.DataReceiveListener;
import proviz.connection.firwaredistribution.arduino.ArduinoFirmwareUpload;
import proviz.devicedatalibrary.DataManager;
import proviz.models.devices.Board;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Burak on 4/15/17.
 */
public class TCPWebServer {
    private ServerSocket socket;
    private Thread serverThread;
    private ArrayList<DataReceiveListener> dataReceiveListeners;
    private ExecutorService executorService;
    HashMap<String, TCPSingleConnectionHandler> tcpSingleConnectionHandlerHashMap;

    private static TCPWebServer self;

    public static TCPWebServer init()
    {
        if(self == null)
            self = new TCPWebServer();
        return self;
    }


    public TCPWebServer()
    {
        executorService = Executors.newFixedThreadPool(ProjectConstants.init().getMaxAllowedThreadNumber());
        dataReceiveListeners = new ArrayList<>();
        tcpSingleConnectionHandlerHashMap = new HashMap<>();
        initializeServer();
    }



    public TCPSingleConnectionHandler getSingleConnectionHandler(String deviceID)
    {
        return tcpSingleConnectionHandlerHashMap.get(deviceID);
    }

    private void initializeServer()
    {
        try {
            socket = new ServerSocket(5867);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void stopServer()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean closeConnection(String ipAddress)
    {
        TCPSingleConnectionHandler tcpSingleConnectionHandler = tcpSingleConnectionHandlerHashMap.get(ipAddress);
        if(tcpSingleConnectionHandler == null)
        {
            return false;
        }
        else
        {
            tcpSingleConnectionHandler.closeConnection();
            return true;
        }
    }



    public void createNewConnection(String boardID)
    {
       Runnable runnable = new Runnable() {
            @Override
            public void run() {

                    try {
                        Socket clientConnection = socket.accept();

                        if(clientConnection.isConnected()) {
                            TCPSingleConnectionHandler tcpSingleConnectionHandler = new TCPSingleConnectionHandler(clientConnection);
                            executorService.execute(tcpSingleConnectionHandler);
                            tcpSingleConnectionHandlerHashMap.put(boardID,tcpSingleConnectionHandler);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
        };

       Thread singleConnectionThread = new Thread(runnable);
       singleConnectionThread.start();
    }

}
