package proviz.library.utilities;

import com.intellij.uiDesigner.core.DimensionInfo;
import com.intellij.uiDesigner.core.GridConstraints;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Burak on 1/18/17.
 */
public class SwipePanelsAnimation {

    private Pane activePane;
    private Pane parent;
    private ButtonBar buttonBar;


    public SwipePanelsAnimation(ButtonBar buttonBar){
        this.buttonBar = buttonBar;
    }


    public void switchScreenB(Pane pane1, Pane pane2, Pane root){

        for(Node b : buttonBar.getButtons()){
            b.setDisable(true);
        }

        root.getChildren().add(pane1);
        KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(pane1.translateXProperty(), -root.getWidth()),
                new KeyValue(pane2.translateXProperty(), 0));
        KeyFrame end = new KeyFrame(Duration.seconds(1),
                new KeyValue(pane1.translateXProperty(), 0),
                new KeyValue(pane2.translateXProperty(), root.getWidth()));
        Timeline slide = new Timeline(start, end);
        slide.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(pane2);
                for(Node b: buttonBar.getButtons()){
                    b.setDisable(false);
                }
            }
        });
        slide.play();


        activePane = pane1;

    }

    public void switchScreenF(Pane pane1, Pane pane2, Pane root) {

        for(Node b : buttonBar.getButtons()){
            b.setDisable(true);
        }

        root.getChildren().add(pane1);

        KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(pane1.translateXProperty(), root.getWidth()),
                new KeyValue(pane2.translateXProperty(), 0));
        KeyFrame end = new KeyFrame(Duration.seconds(1),
                new KeyValue(pane1.translateXProperty(), 0),
                new KeyValue(pane2.translateXProperty(), -root.getWidth()));
        Timeline slide = new Timeline(start, end);
        slide.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(pane2);
                for(Node b : buttonBar.getButtons()){
                    b.setDisable(false);
                }
            }
        });
        slide.play();

        activePane = pane1;

    }

    public void switchScreenFNotLockButton(Pane pane1, Pane pane2, Pane root) {



        root.getChildren().add(pane1);

        KeyFrame start = new KeyFrame(Duration.ZERO,
                new KeyValue(pane1.translateXProperty(), root.getWidth()),
                new KeyValue(pane2.translateXProperty(), 0));
        KeyFrame end = new KeyFrame(Duration.seconds(1),
                new KeyValue(pane1.translateXProperty(), 0),
                new KeyValue(pane2.translateXProperty(), -root.getWidth()));
        Timeline slide = new Timeline(start, end);
        slide.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(pane2);
            }
        });
        slide.play();

        activePane = pane1;

    }

    public Pane getActivePane(){
        return activePane;
    }
}
