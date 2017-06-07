package proviz.connection.client;

import proviz.models.client.request.CreateConnectionRequest;
import proviz.models.client.request.TopologyRequest;
import proviz.models.client.response.CreateConnectionResponse;
import proviz.models.client.response.TopologyResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

import javax.ws.rs.Consumes;

/**
 * Created by Burak on 5/12/17.
 */
public interface TabletConnectionMethods {

    @PUT("sendtopology")
    Call<TopologyResponse> sendTopology(@Body TopologyRequest topologyRequest);

    @PUT("createconnection")
    Call<CreateConnectionResponse> createConnectionBySendingSessionID(@Body CreateConnectionRequest createConnectionRequest);

}
