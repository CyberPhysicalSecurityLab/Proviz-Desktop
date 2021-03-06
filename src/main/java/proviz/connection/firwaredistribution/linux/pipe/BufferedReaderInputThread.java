package proviz.connection.firwaredistribution.linux.pipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by bigbywolf on 1/18/17.
 */
public class BufferedReaderInputThread extends Thread{

    private volatile boolean running = true;
    private ComCallBack callBack = null;
    private volatile BufferedReader bReader = null;
    private volatile boolean paused = false;


    public BufferedReaderInputThread(InputStream pipeStream){
        bReader = new BufferedReader(new InputStreamReader(pipeStream));
    }

    public void messageReceivedCallBack(ComCallBack callBack){
        this.callBack = callBack;
    }

    @Override
    public void run() {
        try{
            String lineRead ;
            while(running) try {
                if (!paused) {
                    if (bReader.ready()) {
                        if ((lineRead = bReader.readLine()) != null) {
                            if (callBack != null) {
                                callBack.callBackMethod(lineRead);
                            }
                        }
                    }
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }

    public void shutdown() throws IOException {
        running = false;
        bReader.close();
    }
}

