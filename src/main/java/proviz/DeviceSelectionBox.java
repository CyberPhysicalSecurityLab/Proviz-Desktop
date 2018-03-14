package proviz;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;


/**
 * Created by Burak on 8/10/17.
 */
public class DeviceSelectionBox extends VBox {
    public DeviceSelectionBox(){
        initUI();
    }

    private void initUI(){
        VBox border = new VBox();
//        Image beagle = new Image(DeviceSelectionBox.class.getResourceAsStream("/icons/beagle.png"));
        Image arduino = new Image(DeviceSelectionBox.class.getResourceAsStream("/icons/arduino.png"));
        Image raspberryPi = new Image(DeviceSelectionBox.class.getResourceAsStream("/icons/raspberry-pi.png"));

//        ImageView beagleV = new ImageView(beagle);
//        beagleV.setId(ProjectConstants.DEVICE_TYPE.BEAGLEBONE.toString());
//        beagleV.setOnDragDetected(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                dragAndDrop(beagleV, beagleV.getId(), event);
//            }
//        });

        ImageView arduinoV = new ImageView(arduino);
        arduinoV.setId(ProjectConstants.DEVICE_TYPE.ARDUINO.toString());
        arduinoV.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragAndDrop(arduinoV, arduinoV.getId(), event);
            }
        });

        ImageView raspberryPiV = new ImageView(raspberryPi);
        raspberryPiV.setId(ProjectConstants.DEVICE_TYPE.RASPBERRY_PI.toString());
        raspberryPiV.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dragAndDrop(raspberryPiV, raspberryPiV.getId(), event);
            }
        });


//        border.getChildren().addAll(arduinoV, beagleV, raspberryPiV);
        border.getChildren().addAll(arduinoV, raspberryPiV);


        border.fillWidthProperty();
        border.setAlignment(Pos.TOP_CENTER);
        border.setSpacing(20);
        border.setPadding(new Insets(10, 10, 10, 10));
        border.setStyle("-fx-background-color: lightgray;");

        this.getChildren().add(border);
        this.setPadding(new Insets(20, 5, 0, 10));

    }

    private void dragAndDrop(ImageView i, String id, MouseEvent event){
        Dragboard db = i.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putString(id);
        db.setContent(content);
        getScene().setCursor(Cursor.HAND);
        event.consume();
    }
}
