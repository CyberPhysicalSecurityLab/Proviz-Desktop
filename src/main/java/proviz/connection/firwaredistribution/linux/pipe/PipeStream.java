package proviz.connection.firwaredistribution.linux.pipe;

//import com.google.common.util.concurrent.SimpleTimeLimiter;

import java.io.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by bigbywolf on 1/12/17.
 *
 * The PipeStream class is used to make the use of named pipes easier for interprocess communication.
 * A couple things to note****
 * 1. The a reader has to be created and be open/listening before a write can be performed.
 * 2. The reader should be used in a separate thread and always be listening and removing from the pipe.
 */
public class PipeStream {

    private InputStream pipeIn;
    private OutputStream pipeOut;
    private String pipePath = null;
    private boolean pathProvided = false;
    private short MAX_PACKET_LENGTH = 32767;

    public PipeStream(String pipePath){
        this.pipePath = pipePath;
        this.pathProvided = true;
    }

    public PipeStream(InputStream pipeStream){
        this.pipeIn = pipeStream;
    }

    private boolean startWriteStream(){
        boolean startSuccessful = true;

        if (pathProvided){
            File pipe = new File(pipePath);

            try{
                pipeOut = new FileOutputStream(pipe);
            }catch(FileNotFoundException e){
                System.out.println("FileNotFoundException Pipe not found");
                e.printStackTrace();
                return false;
            }
        }

        return startSuccessful;
    }

    public boolean startReadStream(){
        boolean startSuccessful = true;

        if (pathProvided){
            File pipe = new File(pipePath);

            try{
                pipeIn = new FileInputStream(pipe);
            }catch(FileNotFoundException e){
                System.out.println("FileNotFoundException Pipe not found");
                e.printStackTrace();
                return false;
            }
        }

        return startSuccessful;
    }


    public void StopClient() {
        pipeIn = null;
        pipeOut = null;
    }

    public void StartClient() {
        //initialize the pipe after a small sleep time
        File pipe = new File(pipePath);

        try{
            pipeOut = new FileOutputStream(pipe);
            pipeIn = new FileInputStream(pipe);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public boolean write(byte[] m_pTxDataBuffer, short totalpktLength) {
        boolean wSuccess = false;
        try {
            pipeOut.write(m_pTxDataBuffer, 0, totalpktLength);
            pipeOut.flush();
            wSuccess = true;
        } catch (NullPointerException e) {
            System.out.println("Exception! Write buffer is null.");
            e.printStackTrace();
        }catch(IndexOutOfBoundsException e){
            System.out.println("Exception! Write length exceeds write buffer size or total packets to write contain a invalid value.");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("IOException while writing to the pipe.");
            e.printStackTrace();
        }
        return wSuccess;
    }




    public int read(byte[] pchBuff, int inBufferSize) {
        int byteTransfer = 0;
        try{
            int toRead = pipeIn.available();
            byteTransfer = pipeIn.read(pchBuff, 0, toRead);
        } catch (NullPointerException e) {
            System.out.println("Exception! Read buffer is null.");
            e.printStackTrace();
        }catch (IndexOutOfBoundsException e) {
            System.out.println("Exception! Read length exceeds read buffer size.");
            e.printStackTrace();
        }catch (IOException e) {
            System.out.println("IOException while reading from the pipe.");
            e.printStackTrace();
        }
            return byteTransfer;
    }

    public String readString(){
        try {
            int toRead = pipeIn.available();
            byte[] data = new byte[toRead]; // create buffer to hold message
            read(data, toRead);
            return new String(data);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int readAvailable() throws IOException {
       return pipeIn.available();
    }


}
