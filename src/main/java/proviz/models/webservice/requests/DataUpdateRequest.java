package proviz.models.webservice.requests;

/**
 * Created by Burak on 5/9/17.
 */
public class DataUpdateRequest {
    private String sessionId;

    public DataUpdateRequest() {}

    public String getSessionId() {

        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
