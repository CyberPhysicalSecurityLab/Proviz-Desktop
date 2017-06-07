package proviz.devicedatalibrary;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.library.utilities.ImageLoader;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Burak on 1/16/17.
 */

public class DataManager {

    public  ArrayList<Sensor> activeSensors;
    private ArrayList<Sensor> sensors;
    private ArrayList<Board> boards;

    private ArrayList<Board> activeBoards;
    private ObjectMapper objectMapper;

    private static DataManager ourInstance = new DataManager();

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        sensors = new ArrayList<>();
        activeSensors = new ArrayList<Sensor>();
        boards = new ArrayList<>();
        objectMapper = new ObjectMapper();
        activeBoards = new ArrayList<>();
    }

    public Sensor getActiveSensor(String sensorId)
    {
        for(Sensor sensor: activeSensors)
        {
            if(sensor.getUniqueIdWithUnderScore().compareTo(sensorId) == 0)
                return sensor;
        }
        return null;
    }

    public Board getBoardWithBoardId(String boardId)
    {
        for(Board board: boards)
        {
            if(board.getUniqueId().compareTo(boardId) == 0)
                return board;
        }
        return null;
    }

    public void addSensor(Sensor sensor)
    {
        sensors.add(sensor);
    }
    public void addSensorFromJSONInputStream(InputStream inputStream){
        try {
            Sensor sensor =  objectMapper.readValue(inputStream,Sensor.class);
            sensors.add(sensor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addBoardFromJSONInputStream(InputStream inputStream)
    {
        try {
            Board board = objectMapper.readValue(inputStream,Board.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addSensors(ArrayList<File> sensorFiles)
    {

            for(File file: sensorFiles)
            {
                try {
                    Sensor sensor =objectMapper.readValue(file,Sensor.class);
                    sensors.add(sensor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


    }
    public void addBoards(ArrayList<File> boardFiles) {

        for (File file : boardFiles) {
            try {
                Board board = objectMapper.readValue(file, Board.class);
                boards.add(board);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Board> getActiveBoards() {
        return activeBoards;
    }

    public void setActiveBoards(ArrayList<Board> activeBoards) {
        this.activeBoards = activeBoards;
    }


    public void addSensorImages(ImageIcon imageIcon, String sensorName)
    {
        for(Sensor sensor: sensors)
        {
            if(sensorName.contains(sensor.getName()))
            {
                sensor.setImage(imageIcon);
                break;
            }
        }
    }
    public void addSensorImages(ArrayList<File> imageIconFiles)
    {

        for(File file: imageIconFiles) {
            for(Sensor sensor: sensors)
            {
                if(file.getName().contains(sensor.getName()))
                {
                    ImageIcon imageIcon = ImageLoader.LoadImageIcon(file.getPath());
                    sensor.setImage(imageIcon);
                }
            }

        }

    }

    public void addBoards(Board board)
    {
        boards.add(board);
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }



    public ArrayList<Board> getBoards() {
        return boards;
    }

    public void setBoards(ArrayList<Board> boards) {
        this.boards = boards;
    }
}
