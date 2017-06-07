package proviz.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.models.connection.IncomingDeviceData;
import proviz.models.devices.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Burak on 2/11/17.
 */
public class SerialPortReader implements Runnable {
    InputStream in;
    private Board board;

    public SerialPortReader(Board board, InputStream in) {
        this.board = board; this.in = in;
    }

    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String str= "";

        try {
            if (bufferedReader.ready()) {
                while ((str = bufferedReader.readLine()) != null) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        IncomingDeviceData incomingDeviceData = objectMapper.readValue(str, IncomingDeviceData.class);
                        board.publishReceivedData(incomingDeviceData);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
