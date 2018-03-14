package proviz.uicomponents.rightsidebar;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Burak on 8/13/17.
 */
public class BoardConnectionView extends VBox {

    private ImageView connectionImageView;
    private SidebarSection connection;
    private HBox connectionContent;

    public BoardConnectionView(){

        initUI();

    }

    private void initUI(){
        connection = new SidebarSection();
        connection.addSectionTitle("Connection");

        connectionContent = new HBox();

        connection.setCenter(connectionContent);

        this.getChildren().add(connection);
    }


}