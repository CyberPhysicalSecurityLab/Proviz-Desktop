package proviz.models.client.request;

/**
 * Created by Burak on 5/13/17.
 */
public class CreateConnectionRequest {
    public CreateConnectionRequest() {
    }

    public String getSessionId() {

        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    private String sessionId;

}
