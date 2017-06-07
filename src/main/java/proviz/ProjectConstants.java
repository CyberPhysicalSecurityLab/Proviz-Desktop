package proviz;

import proviz.codedistribution.ArduinoFlasher;
import proviz.library.utilities.FileOperations;
import proviz.models.devices.Pin;
import freemarker.template.Configuration;

import javax.bluetooth.UUID;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Burak on 9/30/16.
 */
public class ProjectConstants {

    private static ProjectConstants projectConstants;
    public static String codeTemprorayName = "proviz_temp";
    public static Configuration templateConfiguration;
    public static String sensorPath = "sensors";
    private BoardView boardView;
    private String folderSeperator;
    private int deviceId;
    private String provizFolderPath;
    private String ipAddress;
    private int maxAllowedThreadNumber;
    private String serverIpAddress;
    private String bluetoothServerAddress;
    private String sessionUUDIwithTablet;

    public String getBluetoothServerAddress() {
        return bluetoothServerAddress;
    }

    public void setBluetoothServerAddress(String bluetoothServerAddress) {
        this.bluetoothServerAddress = bluetoothServerAddress;
    }

    public String getSessionUUDIwithTablet() {
        return sessionUUDIwithTablet;
    }

    public void setSessionUUDIwithTablet(String sessionUUDIwithTablet) {
        this.sessionUUDIwithTablet = sessionUUDIwithTablet;
    }

    public String getServerIpAddress() {
        return serverIpAddress;
    }

    public void setServerIpAddress(String serverIpAddress) {
        this.serverIpAddress = serverIpAddress;
    }

    public ProjectConstants()
    {
        maxAllowedThreadNumber = 32;
        connectionTypeColorHashMap = new HashMap<>();
        deviceId = 0;
        arduinoCompilerPath = "/Applications/Arduino.app/Contents/MacOS/Arduino";
        switch (FileOperations.init().checkOS())
        {
            case OSX:
            {
                folderSeperator = "/";
                break;
            }
            case WINDOWS:
            {folderSeperator = "\\";
                break;
            }
            case LINUX:
            {
                folderSeperator = "/";
                break;
            }
        }
        provizFolderPath = (System.getProperty("user.home")+folderSeperator+".proviz");
        connectionTypeColorHashMap.put(CONNECTION_TYPE.BLUETOOTH_CLASSIC,Color.BLUE);

    }


    public int getMaxAllowedThreadNumber() {
        return maxAllowedThreadNumber;
    }

    public void setMaxAllowedThreadNumber(int maxAllowedThreadNumber) {
        this.maxAllowedThreadNumber = maxAllowedThreadNumber;
    }

    private UUID bluetoothServiceUUID = new UUID("0000110100001000800000805F9B34FB",false);

    private String arduinoCompilerPath;

    private String tabletIpAddress;

    public static boolean isProd = false;
    public int getIncrementedDeviceID()
    {
       return ++deviceId;

    }
    public Pin.DATA_DIRECTION getOppositePin(Pin.DATA_DIRECTION sourceData_direction)
    {
        if(sourceData_direction == Pin.DATA_DIRECTION.OUTPUT )
            return Pin.DATA_DIRECTION.INPUT;
        else if (sourceData_direction == Pin.DATA_DIRECTION.INPUT)
            return Pin.DATA_DIRECTION.OUTPUT;
        else if (sourceData_direction == Pin.DATA_DIRECTION.SERIAL)
            return Pin.DATA_DIRECTION.SERIAL;
        return Pin.DATA_DIRECTION.UNDEFINED;
    }


    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public UUID getBluetoothServiceUUID() {
        return bluetoothServiceUUID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getProvizFolderPath() {
        return provizFolderPath;
    }

    public void setProvizFolderPath(String provizFolderPath) {
        this.provizFolderPath = provizFolderPath;
    }

    public String getArduinoCompilerPath() {
        return arduinoCompilerPath;
    }

    public void setArduinoCompilerPath(String arduinoCompilerPath) {
        this.arduinoCompilerPath = arduinoCompilerPath;
    }

    public String getTabletIpAddress() {
        return tabletIpAddress;
    }

    public void setTabletIpAddress(String tabletIpAddress) {
        this.tabletIpAddress = tabletIpAddress;
    }


    public enum DEVICE_TYPE
    {
        ARDUINO,
        RASPBERRY_PI,
        BEAGLEBONE,
        Server
    }

    public enum OS_TYPE{
        LINUX,
        OSX,
        WINDOWS
    }


   public enum STATUS_CODE{
        CONNECTION_LOST,
        CONNECTION_NOT_ESTABLISHED,
        CONNECTION_OK
    }

    public HashMap<CONNECTION_TYPE, Color> getConnectionTypeColorHashMap() {
        return connectionTypeColorHashMap;
    }

    public void setConnectionTypeColorHashMap(HashMap<CONNECTION_TYPE, Color> connectionTypeColorHashMap) {
        this.connectionTypeColorHashMap = connectionTypeColorHashMap;
    }

    public String getFolderSeperator() {
        return folderSeperator;
    }

    public void setFolderSeperator(String folderSeperator) {
        this.folderSeperator = folderSeperator;
    }

    public enum CONNECTION_TYPE
    {
        BLUETOOTH_CLASSIC,
        BLUETOOTH_LE,
        RF,
        INTERNET,
        SERIAL

    }



    public enum SENSOR_STATUS{
        UPPERTHRESHOLDEXCEED,
        LOWERTHRESHOLDEXCEED,
        NORMAL
    }
    public enum OPERATION_RESULT{
        SUCCESS,
        FAIL
    }
    private  HashMap<CONNECTION_TYPE,Color> connectionTypeColorHashMap;



    public static ProjectConstants init()
    {
        if(projectConstants == null)
            projectConstants = new ProjectConstants();

        return projectConstants;
    }





}
