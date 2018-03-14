package proviz.codegeneration;

import com.fasterxml.jackson.databind.ObjectMapper;
import proviz.BoardView;
import proviz.LiveDataTable;
import proviz.LiveDataTableModel;
import proviz.connection.DataReceiveListener;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;
import proviz.ProjectConstants;
import proviz.models.devices.Pin;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Burak on 10/6/16.
 */
public abstract class CodeGenerationTemplate  {
    private String createdDate;
    private Board board;
    private BoardView boardView;
    private ProjectConstants.CONNECTION_TYPE connectionType;
    protected UUID boardUniqueCode;
    private LiveDataTable liveDataTable;






    public CodeGenerationTemplate(ProjectConstants.CONNECTION_TYPE connectionType,BoardView boardView)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        createdDate = dateFormat.format(date);
        this.connectionType = connectionType;
this.boardView = boardView;
        boardUniqueCode = UUID.randomUUID();


    }

    public CodeGenerationTemplate(BoardView boardView)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        Date date = new Date();
        createdDate = dateFormat.format(date);
        this.boardView = boardView;
        boardUniqueCode = UUID.randomUUID();
    }

    public CodeGenerationTemplate(BoardView boardView,LiveDataTable liveDataTableModel)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        this.liveDataTable = liveDataTableModel;

        Date date = new Date();
        createdDate = dateFormat.format(date);
        this.boardView = boardView;
        boardUniqueCode = UUID.randomUUID();
        //boardUniqueCode = UUID.fromString("d7ebafd7-cbc4-443d-a99d-597c09630160");
    }
public void addSensorEntry2List(Sensor sensor)
{
    sensor.setParentBoard(board);
    board.getSensors().add(sensor);

}
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public ArrayList<Sensor> getSensors() {
        return board.getSensors();
    }




    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        this.board.setUniqueId(boardUniqueCode.toString().replace("-","_"));
        board.subscribe(boardView);
        if(liveDataTable!= null)
        liveDataTable.subscribeDataReceiverLister(board);
        if(board == null) {
//            boardView.getParentView().getParentView().setBoardForRightSideBar(board);
//            boardView.getParentView().getParentView().showRightSideBar();
        }
    }

//    public void loadBoardDetails(String modelCode) {
//        this.board.setUniqueId(boardUniqueCode.toString().replace("-","_"));
//        Board newBoard = loadBoardDetailWithModelName(modelCode);
//
//        newBoard.subscribe(boardView);
//        if(liveDataTableModel!= null)
//            liveDataTableModel.subscribeDataReceiverLister(board);
//      this.board = newBoard;
//    }


    public abstract void addSensor(Sensor sensor);


    public boolean checkPinAvailable(Pin.PINTYPE pintype, int pinNumber)
    {
       return board.checkPinAvailable( pintype,  pinNumber);
    }



    public ProjectConstants.CONNECTION_TYPE getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ProjectConstants.CONNECTION_TYPE connectionType) {
        this.connectionType = connectionType;
    }

    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public UUID getBoardUniqueCode() {
        return boardUniqueCode;
    }

    public void setBoardUniqueCode(UUID boardUniqueCode) {
        this.boardUniqueCode = boardUniqueCode;
    }
}
