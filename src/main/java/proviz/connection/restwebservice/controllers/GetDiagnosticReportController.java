package proviz.connection.restwebservice.controllers;

import proviz.models.webservice.requests.GetDiagnosticReportRequest;
import proviz.models.webservice.responses.GetDiagnosticReportResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by Burak on 1/12/17.
 */
@Path("/sendreport")
public class GetDiagnosticReportController {
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public GetDiagnosticReportResponse getReportFromDevice(GetDiagnosticReportRequest request)
    {
        GetDiagnosticReportResponse getDiagnosticReportResponse = new GetDiagnosticReportResponse();
        getDiagnosticReportResponse.setResult("Received");
        return  getDiagnosticReportResponse;
    }
}


