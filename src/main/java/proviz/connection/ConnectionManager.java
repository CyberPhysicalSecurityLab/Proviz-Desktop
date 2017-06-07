package proviz.connection;

import java.util.ArrayList;

/**
 * Created by Burak on 10/17/16.
 */
public class ConnectionManager {
    static  ConnectionManager self;

    private ArrayList<BaseConnection> aliveConnections;

    public ConnectionManager()
    {
        aliveConnections = new ArrayList<>();
    }
    public void addNewConnection(BaseConnection baseConnection)
    {
        aliveConnections.add(baseConnection);
    }

    public static ConnectionManager getInstance()
    {
        if(self == null)
        {
            self = new ConnectionManager();
        }
        return self;
    }

}
