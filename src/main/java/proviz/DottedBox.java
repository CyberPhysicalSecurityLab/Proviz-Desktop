package proviz;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by Burak on 8/10/17.
 */
public class DottedBox extends Pane {


        private Canvas canvas;
        private GraphicsContext gc;

        public DottedBox(){
            initUI();
        }

        private void initUI(){
            this.setMaxWidth(Double.MAX_VALUE);
            this.setMaxHeight(Double.MAX_VALUE);

            canvas = new Canvas(2000, 2000);

            gc = canvas.getGraphicsContext2D();

            for(int w = 5; w < canvas.getWidth(); w = w+20){
                for(int h = 5; h < canvas.getHeight(); h = h+20){
                    gc.setFill(Color.GRAY);
                    gc.fillOval(w, h , 2,2);

                }
            }

            getChildren().addAll(canvas);
        }

    }
