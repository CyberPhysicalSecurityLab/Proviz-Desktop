package proviz;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by Burak on 9/26/17.
 */
public class StartupLoadingScreen extends Pane {

    private VBox mainBox;
    private Image logo;
    private Text progressText;
    private ProgressBar progressBar;

    private void initUI()
    {
        mainBox = new VBox();
        logo = new Image("/images/proviz_logo.png");
        progressText = new Text();
        progressBar = new ProgressBar();

    }
}
