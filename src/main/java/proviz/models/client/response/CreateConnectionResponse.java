package proviz.models.client.response;

import proviz.ProjectConstants;

/**
 * Created by Burak on 5/13/17.
 */
public class CreateConnectionResponse {
    private ProjectConstants.OPERATION_RESULT response;

    public CreateConnectionResponse() {
    }

    public ProjectConstants.OPERATION_RESULT getResponse() {

        return response;
    }

    public void setResponse(ProjectConstants.OPERATION_RESULT response) {
        this.response = response;
    }
}
