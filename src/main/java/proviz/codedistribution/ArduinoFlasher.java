package proviz.codedistribution;


import proviz.ProjectConstants;
import proviz.codegeneration.ArduinoTemplate;
import proviz.connection.webserver.TCPSingleConnectionHandler;
import proviz.connection.webserver.TCPWebServer;
import proviz.connection.webserver.WiFiConnectionListener;
import proviz.connection.firwaredistribution.arduino.FirmwareManager;

import java.io.*;


/**
 * Created by Burak on 9/30/16.
 */
public class ArduinoFlasher extends CodeFlasher implements WiFiConnectionListener {


    private ArduinoTemplate arduinoTemplate;
    private ProcessBuilder processBuilder;
    private String compilerPortName;
    private CodeDistributionManager codeDistributionManager;
    private FirmwareManager firmwareManager;
    private boolean isinitialFlash;



    public ArduinoFlasher(CodeDistributionManager codeDistributionManager, String code, ArduinoTemplate arduinoTemplate, boolean initialFlash)
    {
        this.code = code;
        prepareTempFile();
        this.isinitialFlash = initialFlash;
        this.codeDistributionManager = codeDistributionManager;
        this.connection_type = arduinoTemplate.getBoard().getConnectionType();
        this.arduinoTemplate = arduinoTemplate;
        try {
            switch (connection_type) {
                case BLUETOOTH_CLASSIC: {
                    createTempFile(code);
                    this.compilerPortName = "/dev/tty.AdafruitEZ-Link6419-SPP";
                    //todo burayi device isminden alsana?
                    break;
                }
                case INTERNET: {


                    if(!initialFlash) {

                        createTempFile(code);
                        TCPWebServer tcpWebServer = TCPWebServer.init();

                        TCPSingleConnectionHandler tcpSingleConnectionHandler = tcpWebServer.getSingleConnectionHandler(arduinoTemplate.getBoard().getUniqueId());
                        if(tcpSingleConnectionHandler == null)
                        {
                            TCPWebServer.init().createNewConnection(arduinoTemplate.getBoard().getUniqueId());
                        }
                    }
                    else
                    {
                        serialFlash();
                    }

                    break;
                }
                case SERIAL: {

                    serialFlash();

                    break;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }

    private void serialFlash()
    {
        this.compilerPortName = "/dev/cu.usbmodem1411";
        createTempFile(code);
    }

    private void prepareTempFile()
    {
        String tempFilePath = ProjectConstants.init().getProvizFolderPath()+ProjectConstants.init().getFolderSeperator()+"temp";
        String filePath = tempFilePath + ProjectConstants.init().getFolderSeperator()+"temp.ino";
        temproraySourceCodePath = filePath;
    }

    private static BufferedReader getOutput(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    private static BufferedReader getError(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }

    private void flashCodeUsingArduinoIDE()
    {
        String uploadPath = temproraySourceCodePath;
        String compilerPath = ProjectConstants.init().getArduinoCompilerPath();
        String boardArgument = "arduino:avr:mega:cpu=atmega2560";
        processBuilder = new ProcessBuilder(new String[]{compilerPath, "--upload", uploadPath, "--board", boardArgument, "--port", compilerPortName});
        Process process = null;
        try {
            process = processBuilder.start();
            BufferedReader output = getOutput(process);
            BufferedReader error = getError(process);
            String ligne = "";

            while ((ligne = output.readLine()) != null) {
                System.out.println(ligne);
            }

            while ((ligne = error.readLine()) != null) {
                System.out.println(ligne);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int FlashCode() {

        if(arduinoTemplate.getBoard().getConnectionType() == ProjectConstants.CONNECTION_TYPE.INTERNET)
        {
if(isinitialFlash)
{
    flashCodeUsingArduinoIDE();
}
else
{
    String path = getSketchFilePath();
    TCPSingleConnectionHandler tcpSingleConnectionHandler  = TCPWebServer.init().getSingleConnectionHandler(arduinoTemplate.getBoard().getUniqueId());
    tcpSingleConnectionHandler.startFirmwareTransferingMode(path);
    tcpSingleConnectionHandler.startFirmwareDistribution();
}
        }
        else {
            flashCodeUsingArduinoIDE();
        }
        return -1;
    }

    public String getSketchFilePath()
    {
        String uploadPath =  temproraySourceCodePath;
        String compilerPath = ProjectConstants.init().getArduinoCompilerPath();

        processBuilder = new ProcessBuilder(new String[]{compilerPath,"--verify", "--board", arduinoTemplate.getBoard().getCompilerArgument(),uploadPath,"--verbose-build"});
        Process process  = null;
        try { process = processBuilder.start();
            BufferedReader output = getOutput(process);
            BufferedReader error = getError(process);
            String ligne = "";

            while ((ligne = output.readLine()) != null) {
                if(ligne.contains("ino.hex"))
                {
                   String[] str = ligne.split("\"");
                    for(String chunk : str)
                    {
                        if(chunk.contains("ino.hex")) {
                            process.destroy();
                            return chunk;
                        }
                    }

                }
                System.out.println(ligne);
            }

            while ((ligne = error.readLine()) != null) {
                System.out.println(ligne);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
//    return "/Users/Burak/Desktop/Blink.ino.hex";
    }


    @Override
    protected String createTempFile(String code) {

        File file = new File(temproraySourceCodePath);
        if(file.exists() != false)
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(temproraySourceCodePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println(code);
        printWriter.close();
        return temproraySourceCodePath;
    }
    private void deleteFile(String path)
    {
        File file = new File(path);
        if(file.exists())
        {
            file.delete();
        }
    }

    public ProjectConstants.CONNECTION_TYPE getConnection_type() {
        return connection_type;
    }

    public void setConnection_type(ProjectConstants.CONNECTION_TYPE connection_type) {
        this.connection_type = connection_type;
    }

    public String getCompilerPortName() {
        return compilerPortName;
    }

    public void setCompilerPortName(String compilerPortName) {
        this.compilerPortName = compilerPortName;
    }

    @Override
    public void connectionEstablished() {



    }


}