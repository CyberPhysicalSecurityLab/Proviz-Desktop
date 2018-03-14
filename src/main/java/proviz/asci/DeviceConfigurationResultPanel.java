package proviz.asci;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by Burak on 8/10/17.
 */
public class DeviceConfigurationResultPanel extends HBox {
    Image status;
    Text statusTextLabel;

    public DeviceConfigurationResultPanel(){
        initUI();
    }

    private void initUI(){
        status = new Image(DeviceConfigurationResultPanel.class.getResourceAsStream("/icons/checked.png"));
        Text text = new Text("Device Configuration Successfully Completed");
        setAlignment(Pos.CENTER);
        getChildren().addAll(new ImageView(status), text);
    }
}
