package proviz.connection.webserver;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.connection.DataReceiveListener;
import proviz.connection.firwaredistribution.arduino.FirmwareManager;
import proviz.devicedatalibrary.DataManager;
import proviz.models.connection.IncomingDeviceData;
import proviz.models.devices.Board;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Burak on 4/21/17.
 */
public class TCPSingleConnectionHandler implements Runnable {
    private BufferedReader bufferedReader;
    private BufferedInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectMapper objectMapper;
    private Socket clientConnection;
    private ArrayList<DataReceiveListener> dataReceiveListeners;
    private AtomicBoolean isThereAnyActiveFirmwareDistribution = new AtomicBoolean(false);

    public AtomicBoolean getIsFirmwareTransferMode() {
        return isFirmwareTransferMode;
    }

    public void setIsFirmwareTransferMode(AtomicBoolean isFirmwareTransferMode) {
        this.isFirmwareTransferMode = isFirmwareTransferMode;
    }

    private volatile boolean isExitRequestted;
    private AtomicBoolean isFirmwareTransferMode = new AtomicBoolean(false);
    private String boardUniqueId;
    private String temproraryFilePath;

    public boolean isProgrammed() {
        return isProgrammed;
    }

    public void setProgrammed(boolean programmed) {
        isProgrammed = programmed;
    }

    private boolean isProgrammed = false;

    public TCPSingleConnectionHandler(Socket clientConnection)
    {
        try {
            dataInputStream = new BufferedInputStream(clientConnection.getInputStream());
            dataOutputStream = new DataOutputStream(clientConnection.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
            objectMapper = new ObjectMapper();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean  isExitRequestted() {
        return isExitRequestted;
    }

    public void setExitRequestted(boolean exitRequestted) {
        isExitRequestted = exitRequestted;
    }

    public void closeConnection()
    {
     isExitRequestted = true;
    }


    public void suscribeReceiveListener(DataReceiveListener dataReceiveListener)
    {
        dataReceiveListeners.add(dataReceiveListener);
    }

    public void unsuscribeReceiveListener(DataReceiveListener dataReceiveListener)
    {
        dataReceiveListeners.remove(dataReceiveListener);
    }

    public void startFirmwareTransferingMode(String temproraryFilePath)
    {
        isFirmwareTransferMode.set(true);
        isProgrammed = true;
        this.temproraryFilePath = temproraryFilePath;
    }



    private void sendProgrammedStatus() throws IOException {


            String result = "isProgrammed:";
            if (isProgrammed == true)
                result += "1#\r\n";
            else
                result += "0#\r\n";
            byte[] bytes = result.getBytes();
            dataOutputStream.write(bytes);
            dataOutputStream.flush();
        }

    public void startFirmwareDistribution()
    {
        if(isThereAnyActiveFirmwareDistribution.get() == false) {

            if (isProgrammed == true) {
                isThereAnyActiveFirmwareDistribution.set(true);
                FirmwareManager firmwareManager = new FirmwareManager(this, dataInputStream, dataOutputStream, temproraryFilePath);
                firmwareManager.startFirmwareDistribution();
            } else {
                isFirmwareTransferMode.set(false);
            }
        }
    }


    private void startGettingData() throws IOException
    {
        while(!isExitRequestted) {

            if(isFirmwareTransferMode.get())
            {
            }
            else {
                if(bufferedReader != null) {
                    while (!bufferedReader.ready()) {}
                    try {
                        String incomingMessage = bufferedReader.readLine();
                        if(incomingMessage.contains("isProgrammedRequest")) {
                            sendProgrammedStatus();
                        }
                        else {
                            if(incomingMessage.length()>0) {
                                IncomingDeviceData incomingDeviceData = objectMapper.readValue(incomingMessage, IncomingDeviceData.class);
                                boardUniqueId = incomingDeviceData.getDeviceId();
                                ArrayList<Board> boards = DataManager.getInstance().getActiveBoards();
                                for(Board board: boards)
                                {
                                        board.publishReceivedData(incomingDeviceData);
                                }


                            }
                        }
                    }
                    catch (JsonParseException jsonParseException)
                    {
                        jsonParseException.printStackTrace();
                    }
                }
            }
        }
    }
    private void notifyListenersForConnectionEstablished()
    {

        for(DataReceiveListener dataReceiveListener: dataReceiveListeners)
        {
            //dataReceiveListener.connectionEstablished();
        }
    }

    @Override
    public void run() {
        try {
            startGettingData();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
