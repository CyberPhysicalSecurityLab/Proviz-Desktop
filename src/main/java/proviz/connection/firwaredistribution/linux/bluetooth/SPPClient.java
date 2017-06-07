package proviz.connection.firwaredistribution.linux.bluetooth;

/**
 * Created by Burak on 5/6/17.
 */


import proviz.connection.firwaredistribution.linux.ProvizProperties;
import proviz.connection.firwaredistribution.linux.pipe.BufferedReaderInputThread;
import proviz.connection.firwaredistribution.linux.pipe.ComCallBack;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.io.*;
import java.util.Vector;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

/**
 * Created by bigbywolf on 1/18/17.
 * A simple SPP client that connects with an SPP server
 */
public class SPPClient implements DiscoveryListener{


    private String uuidValue = "8848";
    private String btServerAddress = "8019344CB50C";

    private ProvizProperties properties;

    private static Object lock=new Object();
    private static Vector<RemoteDevice> vecDevices=new Vector<>();
    private static String connectionURL=null;
    private MessageListener messageListener;

    private StreamConnection streamConnection=null;

    private OutputStream outStream=null;
    private PrintWriter pWriter=null;
    private InputStream inStream=null;

    private BufferedReaderInputThread inputThread = null;
    private int timeOutCounter = 0; // variable to keep track of how many messages timed out
    private int numOfTimeouts = 3;  // number of timeouts before connection is dropped


    private void resetTimeOutCounter(){
        timeOutCounter = 0;
    }

    private void incrementTimeOutCounter(){
        timeOutCounter++;
    }

    public enum State {
        CONNECTED, NOT_CONNECTED
    }
    private State state = State.NOT_CONNECTED;

    public SPPClient(){
        state = State.NOT_CONNECTED;

        properties = ProvizProperties.getInstance();
        properties.loadProperties();
    }

    public SPPClient(int numOfTimeouts){
        state = State.NOT_CONNECTED;
        this.numOfTimeouts = numOfTimeouts;

        properties = ProvizProperties.getInstance();
        properties.loadProperties();
    }

    public boolean searchForProvizSpp() throws IOException {

        // start state is not connected
        state = State.NOT_CONNECTED;

        properties.loadProperties();
        uuidValue = properties.getBtSspUuid();
        btServerAddress = properties.getBtAddr();

        LocalDevice localDevice = LocalDevice.getLocalDevice();

        DiscoveryAgent agent = localDevice.getDiscoveryAgent();
        agent.startInquiry(DiscoveryAgent.GIAC, this);

        try {
            synchronized(lock){
                lock.wait();
            }
        }
        catch (InterruptedException e) {
        }


        int deviceCount=vecDevices.size();
        int index = -1;

        if(deviceCount <= 0){
            return false;
        }
        else{


            for (int i = 0; i <deviceCount; i++) {
                RemoteDevice remoteDevice= vecDevices.elementAt(i);


                if (remoteDevice.getBluetoothAddress().equals(btServerAddress)){
                    index = i;
                }
            }
        }

        if (index == -1){
            return false;
        }

        RemoteDevice remoteDevice= vecDevices.elementAt(index);
        UUID[] uuidSet = new UUID[1];

        uuidSet[0]=new UUID(uuidValue,true);

        agent.searchServices(null,uuidSet,remoteDevice,this);

        try {
            synchronized(lock){
                lock.wait();
            }
        }
        catch (InterruptedException e) {
e.printStackTrace();
        }

        if(connectionURL==null){
            return false;
        }


        streamConnection=(StreamConnection)Connector.open(connectionURL);

        outStream=streamConnection.openOutputStream();
        pWriter=new PrintWriter(new OutputStreamWriter(outStream));

        inStream=streamConnection.openInputStream();

        inputThread = new BufferedReaderInputThread(inStream);
        inputThread.messageReceivedCallBack(callBack);
        inputThread.start();

        System.out.println(outStream);
        System.out.println(inStream);

        state = state.CONNECTED;
        return true;
    }

    public void shutdown() throws IOException {
        inputThread.shutdown();
        streamConnection.close();
        pWriter.close();
        state = State.NOT_CONNECTED;
    }

    private ComCallBack callBack = new ComCallBack() {
        public void callBackMethod(String message) {
//            logger.debug("Server message: " + message);
            resetTimeOutCounter();

            if (message.contains("[SPP-OK]") || message.contains("[SPP-ERROR]")){
//                logger.debug("server response received");
                synchronized(lock){
                    lock.notify();
                }
            }
            else if (message.contains("[SPP-DISCONNECT]") ){
                try {
                    respondToServer();
                    shutdown();
                } catch (IOException e) {
//                    logger.error(e);
                }
            }
            else if (message.contains("SPP-NEWFIRM")){
                // todo: finish implementation
                respondToServer();
            }
            else{
                respondToServer();
                if (messageListener != null){
                    messageListener.messageEmitted(message);
                }
            }
        }
    };

    public boolean writeToServer(String message) {

        if (state == State.NOT_CONNECTED){
//            logger.debug("BT writeToServer error, no device connected");
            return false;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future future = executor.submit(new Callable() {
            public String call() throws Exception {

                pWriter.write(message + "\r\n");
                pWriter.flush();

                try {
                    synchronized(lock){
                        lock.wait();
                    }
                }
                catch (InterruptedException e) {
                }

                return "OK";
            }
        });

        executor.shutdownNow();
        return true;
    }

    public void respondToServer(){
        pWriter.write("[SPP-OK]\r\n");
        pWriter.flush();
    }

    public void disconnectFromServer() throws IOException {
        writeToServer("[SPP-DISCONNECT]");
        shutdown();
    }

    public State getState() {
        return state;
    }

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public static void main(String[] args) throws IOException {

        ProvizProperties properties = ProvizProperties.getInstance();

        String user = System.getenv("SUDO_USER");
        if (user == null)
            user = System.getProperty("user.name");

        String configFilePath = "/home/" + user + "/.proviz/.config.properties";

        properties.setConfigFilePath(configFilePath);
        properties.loadProperties();

        SPPClient client=new SPPClient();
        boolean connection = client.searchForProvizSpp();

        if (!connection)
            System.exit(0);

        while(true){

            try {
                client.writeToServer("New String from SPP Client\r\n");
                sleep(3000);
            } catch (Exception e) {
                System.out.println("Encountered Error " + e);
            }
        }
    }


    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {

        if(!vecDevices.contains(btDevice)){
            vecDevices.addElement(btDevice);
        }
    }

    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        if(servRecord!=null && servRecord.length>0){
            connectionURL=servRecord[0].getConnectionURL(0,false);
        }
        synchronized(lock){
            lock.notify();
        }
    }
    public void serviceSearchCompleted(int transID, int respCode) {
        synchronized(lock){
            lock.notify();
        }
    }
    public void inquiryCompleted(int discType) {
        synchronized(lock){
            lock.notify();
        }
    }
}
