package proviz.connection.firwaredistribution.arduino;

import proviz.connection.webserver.TCPSingleConnectionHandler;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Burak on 1/27/17.
 */
public class FirmwareManager {

    private String path;
    private ArduinoFirmwareUpload arduinoBoard;
    private Timer timer;
    private TCPSingleConnectionHandler tcpSingleConnectionHandler;
    private volatile boolean isActiveOperationOngoing =false;
    private  TimerTask timerTask;
    private AtomicBoolean isAnotherTaskRunning = new AtomicBoolean(false);

    public AtomicBoolean getIsProgrammingTaskFinished() {
        return isProgrammingTaskFinished;
    }

    public void setIsProgrammingTaskFinished(AtomicBoolean isProgrammingTaskFinished) {
        this.isProgrammingTaskFinished = isProgrammingTaskFinished;
    }

    private AtomicBoolean isProgrammingTaskFinished = new AtomicBoolean(false);




    public FirmwareManager(TCPSingleConnectionHandler tcpSingleConnectionHandler, BufferedInputStream bufferedInputStream, DataOutputStream dataOutputStream, String path)
    {
        this.path = path;
        this.tcpSingleConnectionHandler = tcpSingleConnectionHandler;
        arduinoBoard = new ArduinoFirmwareUpload(bufferedInputStream,dataOutputStream);

    }

    public void startFirmwareDistribution()
    {
        timer = new Timer();
        tcpSingleConnectionHandler.setProgrammed(true);
        scheduleTaskAgain(arduinoBoard);
    }

    public void closeFirmwareTransferMode()
    {
        tcpSingleConnectionHandler.getIsFirmwareTransferMode().set(false);
        tcpSingleConnectionHandler.setProgrammed(false);
    }

    public void deSelectProgrammedMode()
    {
        tcpSingleConnectionHandler.setProgrammed(false);
    }
    public void distributeFirmware(ArduinoFirmwareUpload arduinoBoard,String filePath)
    {

        arduinoBoard.startSketchUpload(filePath,arduinoBoard,this);
        System.out.println("Firmware Upload Started");
        timerTask.cancel();
        timer.cancel();
        timer.purge();
        timer = null;
    }


    public void scheduleTaskAgain(ArduinoFirmwareUpload arduinoBoard)
    {
        if(timer == null)
            timer = new Timer();
         timerTask = new TimerTask() {
            int trialNumber = 0;
            @Override
            public void run() {
                if (!isAnotherTaskRunning.get()) {
                    isAnotherTaskRunning.set(true);
                    if(!isProgrammingTaskFinished.get())
                    {
                    if (!isActiveOperationOngoing) {
                        isActiveOperationOngoing = true;
                        try {
                            arduinoBoard.sendRebootCMD();
                            System.out.println("Sending RBT command #" + trialNumber);
                            if (arduinoBoard.getACKTCP() == true) {
                                System.out.println("We got ACK");
                                distributeFirmware(arduinoBoard, path);


                            } else
                                System.out.println("We missed bootloader time interval.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        trialNumber++;
                        isActiveOperationOngoing = false;
                    }
                    isAnotherTaskRunning.set(false);
                }

                }


            }
        };
        timer.schedule(timerTask,0,2000);
    }

}
