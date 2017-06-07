package proviz.models.tablet;

import java.util.ArrayList;

/**
 * Created by Burak on 5/12/17.
 */
public class Cluster {
    private String sessionId;
    private ArrayList<ClusterBoard> boards;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ArrayList<ClusterBoard> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<ClusterBoard> boards) {
        this.boards = boards;
    }

    public Cluster() {}
}
