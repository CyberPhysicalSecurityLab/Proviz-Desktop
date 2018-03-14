package proviz;

import javafx.beans.property.BooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import proviz.BoardView;
import proviz.DeviceLink;
import proviz.DottedBox;
import proviz.models.devices.Board;

import java.util.ArrayList;

/**
 * Created by Fran Callejas on 6/26/2017.
 */
public class PView extends Pane {

    private DottedBox dottedBox;
    private ArrayList<BoardView> allDevices;
    private ImageView computerView;
    private VBox computerBox;
    private BoardView selectedBoardView;
    private MainEntrance mainEntrance;


    public PView(MainEntrance mainEntrance){
        this.mainEntrance = mainEntrance; initUI();
    }

    private void initUI(){
        dottedBox = new DottedBox();

        Image computer = new javafx.scene.image.Image
                (this.getClass().getResourceAsStream("/notebook.png"));
        Label computerLabel = new javafx.scene.control.Label("Computer");


        computerLabel.setMinWidth(100);
        computerView = new ImageView(computer);
        computerBox = new VBox();
        computerBox.setTranslateX(440);
        computerBox.setTranslateY(225);
        computerBox.getChildren().addAll(computerView, computerLabel);
        computerBox.setMaxWidth(computerLabel.getWidth());
        computerBox.setMaxHeight(computerView.getFitHeight() + 60);


        allDevices = new ArrayList<>();


        getChildren().addAll(dottedBox, computerBox);

        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getSource() instanceof BoardView){

                    /*if (((BoardView) event.getSource()).getIsClicked()) {
                        ((BoardView) event.getSource()).buildBorder(false);
                        System.out.println("PView take off border");
                    }
                    else{
                        setSelectedBoardView((BoardView) event.getSource());
                        System.out.println("PView put border");

                    }*/

                    setSelectedBoardView((BoardView) event.getSource());

                }
                else{
                    for(BoardView bV : allDevices){
                        bV.buildBorder(false);
                    }
                    mainEntrance.hideRightSideBar();
                }
            }
        });


    }

    public void setSelectedBoardView(BoardView boardView){
        selectedBoardView = boardView;
        for(BoardView bV : allDevices) {
            if (bV != selectedBoardView) {
                bV.buildBorder(false);
            } else if (bV == selectedBoardView) {
                bV.buildBorder(true);
                bV.setIsClicked(true);
            }
        }
    }


    public void createBoardView(Board board){

        BoardView boardView = new BoardView(mainEntrance,board);


        DeviceLink deviceLink = new DeviceLink();
        deviceLink.bindEnds(computerBox, boardView);
        deviceLink.setId(String.valueOf(deviceLink.getLinkID()));
        System.out.println(String.valueOf(deviceLink.getLinkID()));
        boardView.registerLinkID(deviceLink.getLinkID());

        getChildren().add(0, deviceLink);
        getChildren().add(boardView);

        boardView.setTranslateX(board.getX());
        boardView.setTranslateY(board.getY());
        boardView.toFront();

        allDevices.add(boardView);
        System.out.println(allDevices.size());

        computerBox.toFront();

    }



    public void setComputerViewEffect(boolean t){
        if(t == true){
            computerView.setEffect(new DropShadow());
        }
        else if(t == false){
            computerView.setEffect(null);
        }
    }


    public ArrayList<BoardView> getAllDevices() {
        return allDevices;
    }

}
