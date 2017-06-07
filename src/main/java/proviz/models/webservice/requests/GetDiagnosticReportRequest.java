package proviz.models.webservice.requests;

/**
 * Created by Burak on 1/12/17.
 */
public class GetDiagnosticReportRequest {
    private String sensorUniqueId;
    private String boardUniqueId;
    private String status;

    public String getSensorUniqueId() {
        return sensorUniqueId;
    }

    public void setSensorUniqueId(String sensorUniqueId) {
        this.sensorUniqueId = sensorUniqueId;
    }

    public String getBoardUniqueId() {
        return boardUniqueId;
    }

    public void setBoardUniqueId(String boardUniqueId) {
        this.boardUniqueId = boardUniqueId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
