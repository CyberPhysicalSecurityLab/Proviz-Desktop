package proviz.connection.restwebservice.controllers;

import proviz.models.webservice.requests.DataUpdateRequest;
import proviz.models.webservice.responses.DataUpdateResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Burak on 5/9/17.
 */
@Path("/updatedata")
public class PostDataUpdateWithTablet {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public DataUpdateResponse updateBoardDataToTablet(DataUpdateRequest dataUpdateRequest)
    {
        DataUpdateResponse dataUpdateResponse = new DataUpdateResponse();



        return dataUpdateResponse;
    }


}
