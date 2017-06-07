package proviz.connection.firwaredistribution.linux.pipe;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by bigbywolf on 1/17/17.
 */
public class ReadPipeInputThread extends Thread{

    private volatile boolean running = true;
    private volatile PipeStream inPipe = null;
    private boolean pathProvided = false;
    private volatile ObjectMapper parser = new ObjectMapper();
    private volatile int readBuffer = 0;
    private ComCallBack callBack = null;

    public ReadPipeInputThread(String filePath){
        inPipe = new PipeStream(filePath);
        this.pathProvided = true;
    }

    public ReadPipeInputThread(InputStream pipeStream){
        inPipe = new PipeStream(pipeStream);
    }

    public void messageReceivedCallBack(ComCallBack callBack){
        this.callBack = callBack;
    }

    @Override
    public void run() {
        try{
            readBuffer = 0;
            if (pathProvided)
                inPipe.startReadStream();

            while(running && inPipe != null) try {
                readBuffer = inPipe.readAvailable();
                if (readBuffer > 0) {
                    String dataOut = inPipe.readString();

                    if (callBack != null){
                        callBack.callBackMethod(dataOut);
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        catch (IOException ex){
            System.err.println("IO Exception");
            System.err.println(ex);
        }
        System.out.println("end of read thread");
    }

    public void shutdown(){
        running = false;
        inPipe.StopClient();
    }
}
