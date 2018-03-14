package proviz.uicomponents.rightsidebar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Created by Burak on 8/13/17.
 */
public class SidebarSection extends BorderPane {
    private HBox titlePanel;
    private Text titleText;
    private boolean showArrow;

    public SidebarSection(){

        initUI();

    }

    private void initUI(){
        this.setMaxWidth(Double.MAX_VALUE);

        titlePanel = new HBox();
        titlePanel.setBackground(Background.EMPTY);
        titlePanel.setAlignment(Pos.TOP_LEFT);
        titlePanel.setStyle("-fx-background-color: lightblue");
        titlePanel.setPadding(new Insets(5, 0, 5,10 ));
        titlePanel.setPrefWidth(this.getWidth());


    }


    public void addSectionTitle(String title){

        titleText = new Text();
        titleText.setText(title);
        titlePanel.getChildren().add(titleText);

        this.setTop(titlePanel);


    }
}
