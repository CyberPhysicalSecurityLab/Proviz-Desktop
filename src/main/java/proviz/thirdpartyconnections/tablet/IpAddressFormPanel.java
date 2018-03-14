package proviz.thirdpartyconnections.tablet;


import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


/**
 * Created by Burak on 5/13/17.
 */

    public class IpAddressFormPanel extends Pane {

        private javafx.scene.control.TextField ipAddressField;

        public IpAddressFormPanel(){
            initUI();
        }

        private void initUI(){
            javafx.scene.control.Label label1 = new javafx.scene.control.Label();
            ImageView designer = new ImageView(new javafx.scene.image.Image("/images/designer.png"));
            designer.setPreserveRatio(true);
            designer.setFitWidth(300);
            label1.setGraphic(designer);
            label1.setAlignment(Pos.CENTER);
            VBox vBox1 = new VBox();
            vBox1.getChildren().add(label1);
            vBox1.setAlignment(Pos.CENTER);
            vBox1.setPadding(new javafx.geometry.Insets(10));

            javafx.scene.control.Label label2 = new javafx.scene.control.Label();
            label2.setText("Hi.\n" +
                    "My name is Berke.\n" +
                    "\n" +
                    "I am going to help you create a connection between\n" +
                    "this application and your Android tablet application.\n" +
                    "\n" +
                    "Please enter your tablet IP address into the field below\n" +
                    "then hit the Next button.\n" +
                    "\n" +
                    "You can find your IP address on the Proviz tablet application.\n" +
                    "\n");

            label2.setAlignment(Pos.CENTER_LEFT);

            ipAddressField = new javafx.scene.control.TextField();
            ipAddressField.setPromptText("Enter IP Address");
            ipAddressField.setAlignment(Pos.CENTER_LEFT);

            VBox vBox2 = new VBox();
            vBox2.getChildren().addAll(label2, ipAddressField);
            vBox2.setAlignment(Pos.CENTER);
            vBox2.setPadding(new javafx.geometry.Insets(10));

            HBox center = new HBox();
            center.getChildren().addAll(vBox1, vBox2);
            center.setAlignment(Pos.CENTER);
            center.setStyle("-fx-border-radius: 2; -fx-border-color: darkgray; -fx-border-insets: 10;" +
                    " -fx-pref-height: 450; -fx-pref-width: 920");
            center.setPadding(new javafx.geometry.Insets(20));
            getChildren().add(center);
        }

        public String getIpAddress(){
            return ipAddressField.getText();
        }
    }





