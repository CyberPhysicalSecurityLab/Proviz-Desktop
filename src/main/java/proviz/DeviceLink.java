package proviz;

import javafx.beans.binding.Bindings;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.io.Serializable;


/**
 * Created by Fran Callejas on 7/4/2017.
 */
public class DeviceLink extends Pane {

    private Line line = new Line();
    private static int linkID = 10;

    public DeviceLink(){

        linkID++;
        getChildren().add(line);
    }


    public void bindEnds(VBox start, Pane end){
        line.startXProperty().bind(
                Bindings.add(start.translateXProperty(), 40));
        System.out.println(String.valueOf(start.translateXProperty()));
        line.startYProperty().bind(
                Bindings.add(start.translateYProperty(), 20));
        line.endXProperty().bind(
                Bindings.add(end.translateXProperty(), 50));
        line.endYProperty().bind(
                Bindings.add(end.translateYProperty(), 55));
    }

    public int getLinkID(){
        return linkID;
    }
}
