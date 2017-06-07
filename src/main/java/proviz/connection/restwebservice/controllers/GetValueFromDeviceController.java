package proviz.connection.restwebservice.controllers;

import proviz.ProjectConstants;
import proviz.devicedatalibrary.DataManager;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;
import proviz.models.connection.IncomingDeviceData;
import proviz.models.webservice.responses.GetValueFromDeviceResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;

/**
 * Created by Burak on 1/12/17.
 */
@Path("/sendvalue")
public class GetValueFromDeviceController {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public GetValueFromDeviceResponse getDataFromDevice(IncomingDeviceData request)
    {
        GetValueFromDeviceResponse getValueFromDeviceResponse = new GetValueFromDeviceResponse();
        getValueFromDeviceResponse.setResult(ProjectConstants.OPERATION_RESULT.SUCCESS);

        ArrayList<Sensor> activeSensors = DataManager.getInstance().activeSensors;
        Sensor desiredSensor = null;

        for(Sensor sensor: activeSensors)
        {
            //todo this not good implementation for future dev.
            if(sensor.getParentBoard().getUniqueId().compareTo(request.getDeviceId()) == 0)
            {
                Board board = sensor.getParentBoard();
                board.publishReceivedData(request);

            }
        }

        return getValueFromDeviceResponse;
    }

}
