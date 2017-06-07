package proviz.models.webservice.responses;

import proviz.ProjectConstants;

/**
 * Created by Burak on 1/12/17.
 */
public class GetValueFromDeviceResponse {

    private ProjectConstants.OPERATION_RESULT result;

    public ProjectConstants.OPERATION_RESULT getResult() {
        return result;
    }

    public void setResult(ProjectConstants.OPERATION_RESULT result) {
        this.result = result;
    }
}
