package proviz.connection.firwaredistribution.arduino;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Burak on 1/27/17.
 */
public class ArduinoFirmwareUpload {

    private BufferedInputStream reader;
    private DataOutputStream writer;
    private STK500V2 stk500V2;
    private Timer timer;
    private volatile boolean isActiveOperationOngoing =false;
    private TimerTask timertask;
    private AtomicBoolean isAnotherTaskRunning = new AtomicBoolean(false);
    private AtomicBoolean isProgrammingTaskFinished = new AtomicBoolean(false);

    public AtomicBoolean getIsAnotherTaskRunning() {
        return isAnotherTaskRunning;
    }

    public void setIsAnotherTaskRunning(AtomicBoolean isAnotherTaskRunning) {
        this.isAnotherTaskRunning = isAnotherTaskRunning;
    }

    public boolean isActiveOperationOngoing() {
        return isActiveOperationOngoing;
    }

    public void setActiveOperationOngoing(boolean activeOperationOngoing) {
        isActiveOperationOngoing = activeOperationOngoing;
    }



    private FirmwareManager firmwareManager;
    public ArduinoFirmwareUpload(BufferedInputStream reader, OutputStream outputStream)
    {
        this.reader = reader;
        this.writer = new DataOutputStream(outputStream);
        stk500V2 = new STK500V2(reader,outputStream);
    }

    public void sendRebootCMD() throws IOException
    {
        String message = "reboot\r\n";
        byte[] byteArray = message.getBytes();
        writer.write(byteArray);
        writer.flush();
    }


    public void startSketchUpload(String fileName,ArduinoFirmwareUpload arduinoBoard,FirmwareManager firmwareManager)
    {

        timer = new Timer();
        this.firmwareManager  = firmwareManager;
         timertask = new TimerTask() {
            int trialNumber =0;
            @Override
            public void run() {
                if (!isAnotherTaskRunning.get()) {
                    isAnotherTaskRunning.set(true);
                    if (!isProgrammingTaskFinished.get()){
                        boolean isSuccesFull = false;
                    if (!isActiveOperationOngoing) {
                        isActiveOperationOngoing = true;
                        if (stk500V2.GetSync()) {
                            isActiveOperationOngoing = true;
                            if (startFirmwareUpload(fileName)) {
                                isSuccesFull = true;
                                cancelTimerTask();
                                isProgrammingTaskFinished.set(true);
                                timer.purge();
                            }

                        }
                        if (trialNumber == 2 && !isSuccesFull) {
                            timer.cancel();
                            timer.purge();
                            firmwareManager.scheduleTaskAgain(arduinoBoard);
                        }
                        trialNumber++;
                        isActiveOperationOngoing = false;
                    }
                    isAnotherTaskRunning.set(false);
                }

                }


            }
        };
        timer.schedule(timertask,0,750);





    }

    private void cancelTimerTask()
    {
        if(timertask != null)
        timertask.cancel();
    }

    private boolean startFirmwareUpload(String fileName)
    {
        if(stk500V2.EnableProgrammingMode()) {
            timer.cancel();
            System.out.println("Enabling prog mode:OK ");
            System.out.println("Firmware transfer has been started");
            stk500V2.uploadSketch(fileName);
            System.out.println("Firmware transfer is finished");
            if(stk500V2.closeProgrammingMode()) {
                firmwareManager.deSelectProgrammedMode();
                firmwareManager.closeFirmwareTransferMode();
                firmwareManager.getIsProgrammingTaskFinished().set(true);
                System.out.println("Programming was finished with closing command");
                return true;
            }

        }
        else
        {
            System.out.println("Enabling prog mode failed");
            return false;
        }
        return false;
    }

    public void receiveCommand() throws IOException
    {
        long timestart = System.currentTimeMillis();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while((timestart + 2000) > System.currentTimeMillis())
        {
            int b = reader.read();
            byteBuffer.put((byte) b);
        }
    }

    public boolean getACKTCP() throws IOException
    {

        long timestart = System.currentTimeMillis();

        while((timestart + 2000) > System.currentTimeMillis())
        {

            int b = reader.read();
            if(b == 82)
                return true;
        }
        return false;
    }
    private  String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
