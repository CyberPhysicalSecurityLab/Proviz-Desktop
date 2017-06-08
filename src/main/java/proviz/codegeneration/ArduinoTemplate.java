package proviz.codegeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.BoardView;
import proviz.LiveDataTableModel;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Burak on 10/6/16.
 */
public class ArduinoTemplate extends CodeGenerationTemplate {

    public ArduinoTemplate(BoardView boardView, LiveDataTableModel liveDataTable)
    {

        super(boardView,liveDataTable);
    }

    public ArduinoTemplate(BoardView boardView)
    {

        super(boardView);
    }



    private void arduinoInit(String modelCode)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String resourceAddress = "boards/arduino/"+modelCode+".json";
        Board arduinoBoard = null;
        try {
            arduinoBoard = objectMapper.readValue(ClassLoader.getSystemResource(resourceAddress).toURI().toURL(),Board.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        if(arduinoBoard.getDigitalPins() == null && arduinoBoard.getAnalogPins() == null && arduinoBoard.getSerialPins() == null)
            arduinoBoard.initializeRequirements();

        arduinoBoard.setUniqueId(boardUniqueCode.toString().replace("-","_"));

        setBoard(arduinoBoard);
    }

    @Override
    public void addSensor(Sensor sensor) {
        addSensorEntry2List(sensor);

    }



}
