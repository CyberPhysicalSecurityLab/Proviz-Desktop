package proviz.connection.firwaredistribution.linux.bluetooth;

import proviz.connection.firwaredistribution.linux.ProvizProperties;
import proviz.connection.firwaredistribution.linux.pipe.ComCallBack;
import proviz.connection.firwaredistribution.linux.pipe.ReadPipeInputThread;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bigbywolf on 1/18/17.
 */
public class AppComHandler {

    private Process proc = null;
    private InputStream in = null;
    private InputStream err = null;


    private ReadPipeInputThread readInThread = null;
    private ReadPipeInputThread errorInThread = null;
    private MessageListener messageListener;

    private ProvizProperties properties;


    public AppComHandler() {

        properties = ProvizProperties.getInstance();
        properties.loadProperties();

        String applicationCommand = "java -jar " + properties.getRemoteProvizDir() + "app/" + properties.getAppName();

        try {
            proc = Runtime.getRuntime().exec(applicationCommand);

            in = proc.getInputStream();
            err = proc.getErrorStream();

            readInThread = new ReadPipeInputThread(in);
            readInThread.messageReceivedCallBack(inCallBack);
            readInThread.start();

            errorInThread = new ReadPipeInputThread(err);
            errorInThread.messageReceivedCallBack(errCallBack);
            errorInThread.start();

        } catch (IOException e) {
        }
    }

    public void closeCom() {
        proc.destroy();
        readInThread.shutdown();
        proc = null;
        in = null;
        err = null;
        messageListener = null;
    }

    private ComCallBack errCallBack = new ComCallBack() {
        @Override
        public void callBackMethod(String message) {
            if (messageListener != null){
                messageListener.messageEmitted(message);
            }
        }
    };

    private ComCallBack inCallBack = new ComCallBack() {
        @Override
        public void callBackMethod(String message) {
            if (messageListener != null){
                messageListener.messageEmitted(message);
            }
        }
    };

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }
}
