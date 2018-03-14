package proviz.codegeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.BoardView;
import proviz.LiveDataTable;
import proviz.LiveDataTableModel;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Burak on 10/6/16.
 */
public class ArduinoTemplate extends CodeGenerationTemplate {

    public ArduinoTemplate(BoardView boardView, LiveDataTable liveDataTable)
    {

        super(boardView,liveDataTable);
    }

    public ArduinoTemplate(BoardView boardView)
    {

        super(boardView);
    }





    @Override
    public void addSensor(Sensor sensor) {
        addSensorEntry2List(sensor);

    }



}
