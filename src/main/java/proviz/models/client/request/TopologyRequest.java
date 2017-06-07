package proviz.models.client.request;

import proviz.models.tablet.Cluster;

/**
 * Created by Burak on 5/13/17.
 */
public class TopologyRequest {
    private String sessionId;

    public TopologyRequest() {}

    public String getSessionId() {

        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    private Cluster cluster;
}
