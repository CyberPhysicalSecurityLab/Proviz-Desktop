package proviz.models.devices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.ProjectConstants;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.connection.BaseConnection;
import proviz.connection.DataReceiveListener;
import proviz.devicedatalibrary.DataManager;
import proviz.models.Bound;
import proviz.models.codegeneration.Variable;
import proviz.models.connection.IncomingDeviceData;
import proviz.models.connection.ReceivedDataModel;
import proviz.models.connection.ReceivedSensorData;
import proviz.models.webservice.requests.SensorData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Burak on 9/30/16.
 */
@JsonIgnoreProperties({"dataReceiveListener","codeGenerationTemplate"})
public class Board {
    private DataReceiveListener dataReceiveListener;
    private String name;
    private String make;
    private String userFriendlyName;
    private int analogInput;
    private String uniqueId;
    private int digitalInput;
    private int serialPortPairs;
    private int i2cPinNumber;
    private int spiBusNumber;
    private boolean has5v;
    private boolean has3_3v;
    private int analogStartingPinNumber;
    private int digitalStartingPinNumber;
    private String compilerArgument;
    private int serialStartingPinNumber;
    private int i2cStartingPinNumber;
    private int spiBusStartingPinNumber;
    private ProjectConstants.DEVICE_TYPE device_type;
    private ArrayList<Pin> analogPins;
    private ArrayList<Pin> digitalPins;
    private ArrayList<Pin> serialPins;
    private ArrayList<Pin> I2CPins;
    private ArrayList<Pin> SPIBusPins;
    private ArrayList<Sensor> sensors;
    private ProjectConstants.CONNECTION_TYPE connectionType;
    private boolean isHasAliveConnection;
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public CodeGenerationTemplate getCodeGenerationTemplate() {
        return codeGenerationTemplate;
    }

    public void setCodeGenerationTemplate(CodeGenerationTemplate codeGenerationTemplate) {
        this.codeGenerationTemplate = codeGenerationTemplate;
    }

    private CodeGenerationTemplate codeGenerationTemplate;

    private IncomingDeviceData incomingDeviceData;
    @JsonIgnore
    public ArrayList<DataReceiveListener> listenerSubscribeList;
    @JsonIgnore
    private boolean isProgrammed;
    @JsonIgnore
    public BaseConnection baseConnection;

    private int sampleRate;

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public BaseConnection getBaseConnection() {
        return baseConnection;
    }

    public void setBaseConnection(BaseConnection baseConnection) {
        this.baseConnection = baseConnection;

    }

    public  Board(Board board)
    {
        listenerSubscribeList = new ArrayList<>();
        this.name = board.getName();
        this.make = board.getMake();
        this.userFriendlyName = board.getUserFriendlyName();
        this.analogInput = board.getAnalogInput();
        this.uniqueId = board.getUniqueId();
        this.digitalInput = board.getDigitalInput();
        this.serialPortPairs = board.getSerialPortPairs();
        this.i2cPinNumber = board.getI2cPinNumber();
        this.spiBusNumber = board.getSpiBusNumber();
        this.has3_3v = board.isHas3_3v();
        this.has5v = board.isHas5v();
        this.analogStartingPinNumber = board.getAnalogStartingPinNumber();
        this.digitalStartingPinNumber = board.getDigitalStartingPinNumber();
        this.compilerArgument = board.getCompilerArgument();
        this.serialStartingPinNumber =  board.getSerialStartingPinNumber();
        this.i2cStartingPinNumber = board.getI2cStartingPinNumber();
        this.spiBusStartingPinNumber = board.getSpiBusStartingPinNumber();
        this.device_type = board.getDevice_type();

        this.analogPins = (ArrayList<Pin>)(board.getAnalogPins().clone());
        this.digitalPins = (ArrayList<Pin>)board.getDigitalPins().clone();
        this.serialPins = (ArrayList<Pin>)board.getSerialPins().clone();
        this.I2CPins = (ArrayList<Pin>)board.getI2CPins().clone();
        this.SPIBusPins = (ArrayList<Pin>)board.getSPIBusPins().clone();
        this.sensors = (ArrayList<Sensor>)board.getSensors().clone();

        this.connectionType = board.getConnectionType();



    }

    public void subscribe(DataReceiveListener listener)
    {
        if(listenerSubscribeList == null)
            listenerSubscribeList = new ArrayList<>();
        listenerSubscribeList.add(listener);
    }

    public void publishReceivedData(IncomingDeviceData incomingDeviceData)
    {



        for(DataReceiveListener dataReceiveListener: listenerSubscribeList)
        {
            if(!isHasAliveConnection)
            {
                dataReceiveListener.connectionEstablished(this);
                isHasAliveConnection = true;
            }
            ReceivedDataModel receivedDataModel  = new ReceivedDataModel();
            ArrayList<ReceivedSensorData> sensorDataArrayList = new ArrayList<>();
            for( SensorData sensorData: incomingDeviceData.getSensors()) {

                Sensor sensor = getSensor(sensorData.getSensorId());

                if(sensor != null) {
                    for(Variable variable: sensor.getVariables()) {
                        if (variable.isCommunicationVariable()) {
                            if (variable.getPreferredName().compareTo(sensorData.getVariableName()) == 0) {
                                ReceivedSensorData receivedSensorData = new ReceivedSensorData();

                                if (sensorData.getSensorValue() > variable.getBound().getUpperValue())
                                    receivedSensorData.setSensorStatus(ProjectConstants.SENSOR_STATUS.UPPERTHRESHOLDEXCEED);
                                else if (sensorData.getSensorValue() < variable.getBound().getLowerValue())
                                    receivedSensorData.setSensorStatus(ProjectConstants.SENSOR_STATUS.LOWERTHRESHOLDEXCEED);
                                else
                                    receivedSensorData.setSensorStatus(ProjectConstants.SENSOR_STATUS.NORMAL);
                                receivedSensorData.setSensorId(sensorData.getSensorId());
                                receivedSensorData.setSensorUnit(sensorData.getSensorUnit());
                                receivedSensorData.setVariableName(sensorData.getVariableName());
                                receivedSensorData.setSensorValue(sensorData.getSensorValue());
                                sensorDataArrayList.add(receivedSensorData);
                            }



                        }
                    }
                }



            }
            receivedDataModel.setSensors(sensorDataArrayList);
            dataReceiveListener.onReceivedData(this,receivedDataModel);
        }
        this.incomingDeviceData = incomingDeviceData;

    }

    private Sensor getSensor(String sensorId)
    {
        ArrayList<Sensor> activeSensors = DataManager.getInstance().activeSensors;

        for(Sensor sensor:activeSensors)
        {
            if(sensorId.compareTo(sensor.getUniqueIdWithUnderScore()) == 0)
            {
                return sensor;
            }
        }

        return null;
    }

    public  Board(){
        initializeRequirements();
    }
    public void initializeRequirements()
    {
        analogPins = initialize(analogStartingPinNumber,analogInput, Pin.PINTYPE.analog);
        digitalPins = initialize(digitalStartingPinNumber,digitalInput, Pin.PINTYPE.digital);
        serialPins = initialize(serialStartingPinNumber,serialPortPairs, Pin.PINTYPE.comm);
        I2CPins  = initialize(i2cStartingPinNumber,i2cPinNumber,Pin.PINTYPE.i2c);
        SPIBusPins = initialize(spiBusStartingPinNumber,spiBusNumber, Pin.PINTYPE.spibus);
        sensors = new ArrayList<>();
        isHasAliveConnection = false;
    }

    @Override
    public String toString() {
        return make+" - "+name;
    }

    private ArrayList<Pin> initialize(int startingPinOrder, int totalPinNumber, Pin.PINTYPE pintype)
{
    ArrayList<Pin> pins = new ArrayList<>();
for(int i = 0; i<totalPinNumber; i++){
    Pin pin = new Pin();
    pin.setIsrequired(false);
    pin.setOrder(startingPinOrder+i);
    pin.setType(pintype);
    switch (pintype)
    {
        case analog:
        {
            pin.setName("A_"+i);
            break;
        }
        case digital:
        {
            pin.setName("D_"+i);
            break;
        }
        case ground:
        {
            pin.setName("GND");
            break;
        }
        case vcc:
        {
            pin.setName("VCC");
            break;
        }
        case comm:{
            pin.setName("TX/RX_"+i);

        }
    }
    pins.add(pin);

}
    return pins;
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getAnalogInput() {
        return analogInput;
    }

    public void setAnalogInput(int analogInput) {
        this.analogInput = analogInput;
    }

    public int getDigitalInput() {
        return digitalInput;
    }

    public void setDigitalInput(int digitalInput) {
        this.digitalInput = digitalInput;
    }

    public int getSerialPortPairs() {
        return serialPortPairs;
    }

    public void setSerialPortPairs(int serialPortPairs) {
        this.serialPortPairs = serialPortPairs;
    }

    public boolean isHas5v() {
        return has5v;
    }

    public void setHas5v(boolean has5v) {
        this.has5v = has5v;
    }

    public boolean isHas3_3v() {
        return has3_3v;
    }

    public void setHas3_3v(boolean has3_3v) {
        this.has3_3v = has3_3v;
    }

    public ArrayList<Pin> getAnalogPins() {
        return analogPins;
    }

    public void setAnalogPins(ArrayList<Pin> analogPins) {
        this.analogPins = analogPins;
    }

    public ArrayList<Pin> getDigitalPins() {
        return digitalPins;
    }

    public void setDigitalPins(ArrayList<Pin> digitalPins) {
        this.digitalPins = digitalPins;
    }

    public ArrayList<Pin> getSerialPins() {
        return serialPins;
    }

    public void setSerialPins(ArrayList<Pin> serialPins) {
        this.serialPins = serialPins;
    }

    public int getAnalogStartingPinNumber() {
        return analogStartingPinNumber;
    }

    public void setAnalogStartingPinNumber(int analogStartingPinNumber) {
        this.analogStartingPinNumber = analogStartingPinNumber;
    }

    public int getDigitalStartingPinNumber() {
        return digitalStartingPinNumber;
    }

    public void setDigitalStartingPinNumber(int digitalStartingPinNumber) {
        this.digitalStartingPinNumber = digitalStartingPinNumber;
    }

    public int getSerialStartingPinNumber() {
        return serialStartingPinNumber;
    }

    public void setSerialStartingPinNumber(int serialStartingPinNumber) {
        this.serialStartingPinNumber = serialStartingPinNumber;
    }

    public ProjectConstants.CONNECTION_TYPE getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ProjectConstants.CONNECTION_TYPE connectionType) {
        this.connectionType = connectionType;
    }

    public boolean checkPinAvailable(Pin.PINTYPE pintype, int pinNumber) {

        boolean result = false;
        ArrayList<Pin> pins = null;
        switch (pintype) {
            case analog: {
                pins = analogPins;
                break;
            }
            case digital: {
                pins = digitalPins;
                break;
            }
            case comm: {
                pins = serialPins;
                break;
            }
        }
        if (pins != null) {
            for (Pin pin : pins) {

                if (pin.getOrder() == pinNumber) {
                    result = pin.isAvailable();
                }
            }


        }
        return result;
    }

    public ProjectConstants.DEVICE_TYPE getDevice_type() {
        return device_type;
    }

    public void setDevice_type(ProjectConstants.DEVICE_TYPE device_type) {
        this.device_type = device_type;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }

    private int AvailablePins(ArrayList<Pin> pinList, int maxPinNumber)
    {
        int totalPinNumber = maxPinNumber;
        for(Pin pin:pinList)
        {
            if(!pin.isAvailable())
                totalPinNumber--;
        }
        return totalPinNumber;
    }

    public int AvailableAnalogPinNumber()
    {
        return AvailablePins(analogPins,analogInput);
    }
    public int AvailableSerialPinNumber()
    {
        return AvailablePins(serialPins,serialPortPairs);
    }
    public int AvailableDigitalPinNumber()
    {
        return AvailablePins(digitalPins,digitalInput);
    }
    public int Availablei2cPinNumber(){return AvailablePins(I2CPins,i2cPinNumber);}
    public int AvailableSPIPinNumber(){return AvailablePins(SPIBusPins,spiBusNumber);}

    public String getAvailablePinsMessage()
    {
        return ("Available Pins: A - "+AvailableAnalogPinNumber()+" | D - "+AvailableDigitalPinNumber()+" | TX/RX - "+AvailableSerialPinNumber());
    }

    public String getCompilerArgument() {
        return compilerArgument;
    }

    public void setCompilerArgument(String compilerArgument) {
        this.compilerArgument = compilerArgument;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public String getUserFriendlyName() {
        return userFriendlyName;
    }

    public void setUserFriendlyName(String userFriendlyName) {
        this.userFriendlyName = userFriendlyName;
    }

    public int getI2cStartingPinNumber() {
        return i2cStartingPinNumber;
    }

    public void setI2cStartingPinNumber(int i2cStartingPinNumber) {
        this.i2cStartingPinNumber = i2cStartingPinNumber;
    }

    public int getSpiBusStartingPinNumber() {
        return spiBusStartingPinNumber;
    }

    public void setSpiBusStartingPinNumber(int spiBusStartingPinNumber) {
        this.spiBusStartingPinNumber = spiBusStartingPinNumber;
    }

    public int getI2cPinNumber() {
        return i2cPinNumber;
    }

    public void setI2cPinNumber(int i2cPinNumber) {
        this.i2cPinNumber = i2cPinNumber;
    }

    public int getSpiBusNumber() {
        return spiBusNumber;
    }

    public void setSpiBusNumber(int spiBusNumber) {
        this.spiBusNumber = spiBusNumber;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public ArrayList<Pin> getI2CPins() {
        return I2CPins;
    }

    public void setI2CPins(ArrayList<Pin> i2CPins) {
        I2CPins = i2CPins;
    }

    public ArrayList<Pin> getSPIBusPins() {
        return SPIBusPins;
    }

    public void setSPIBusPins(ArrayList<Pin> SPIBusPins) {
        this.SPIBusPins = SPIBusPins;
    }

    public DataReceiveListener getDataReceiveListener() {
        return dataReceiveListener;
    }

    public void setDataReceiveListener(DataReceiveListener dataReceiveListener) {
        this.dataReceiveListener = dataReceiveListener;
    }


    public boolean isProgrammed() {
        return isProgrammed;
    }

    public void setProgrammed(boolean programmed) {
        isProgrammed = programmed;
    }

    public IncomingDeviceData getIncomingDeviceData() {
        return incomingDeviceData;
    }

    public void setIncomingDeviceData(IncomingDeviceData incomingDeviceData) {
        this.incomingDeviceData = incomingDeviceData;
    }


}
