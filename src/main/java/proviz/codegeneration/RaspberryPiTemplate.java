package proviz.codegeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.BoardView;
import proviz.LiveDataTable;
import proviz.LiveDataTableModel;
import proviz.ProjectConstants;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Burak on 1/31/17.
 */
public class RaspberryPiTemplate extends CodeGenerationTemplate {

    public RaspberryPiTemplate(BoardView boardView, LiveDataTable liveDataTable)
    {
        super(boardView,liveDataTable);
    }

    public RaspberryPiTemplate(BoardView boardView)
    {
        super(boardView);
    }


    public void raspberrypiInit(String modelCode)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        String resourceAddress = "boards/raspberrypi/"+modelCode+".json";
        Board raspberrypiBoard = null;
        try {
            raspberrypiBoard = objectMapper.readValue(ClassLoader.getSystemResource(resourceAddress).toURI().toURL(),Board.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        if(raspberrypiBoard.getDigitalPins() == null && raspberrypiBoard.getAnalogPins() == null && raspberrypiBoard.getSerialPins() == null)
            raspberrypiBoard.initializeRequirements();
        raspberrypiBoard.setUniqueId(boardUniqueCode.toString().replace("-","_"));
        setBoard(raspberrypiBoard);
    }
    @Override
    public void addSensor(Sensor sensor) {
        addSensorEntry2List(sensor);
    }


}
