package proviz.connection;

import proviz.models.connection.IncomingDeviceData;
import proviz.models.connection.ReceivedDataModel;
import proviz.models.connection.ReceivedSensorData;
import proviz.models.devices.Board;

/**
 * Created by Burak on 10/15/16.
 */
public interface DataReceiveListener {

      void onReceivedData(Board board,ReceivedDataModel receivedData);
      void connectionLost(Board board);
      void connectionEstablished(Board board);

}
