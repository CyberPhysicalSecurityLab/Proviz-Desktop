package proviz.models.webservice.responses;

import proviz.models.connection.IncomingDeviceData;

import java.util.ArrayList;

/**
 * Created by Burak on 5/9/17.
 */
public class DataUpdateResponse {
    private ArrayList<IncomingDeviceData> boards;

    public DataUpdateResponse() {
    }

    public ArrayList<IncomingDeviceData> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<IncomingDeviceData> boards) {
        this.boards = boards;
    }
}
