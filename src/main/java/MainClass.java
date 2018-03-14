import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import proviz.MainEntrance;
import proviz.ProjectConstants;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import proviz.codedistribution.ArduinoFlasher;
import proviz.connection.firwaredistribution.linux.ProvizProperties;
import proviz.library.utilities.FileOperations;

import javax.management.InvalidApplicationException;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

/**
 * Created by Burak on 8/31/16.
 */
public class MainClass extends Application {
    static long startingTime;
    @Override
    public void start(Stage primaryStage) throws Exception{
        ProjectConstants.templateConfiguration =  startTemplateEngine(ProjectConstants.templateConfiguration);

        MainEntrance mainEntrance = new MainEntrance(primaryStage);
        //DeviceSelectionBox dvs = new DeviceSelectionBox();

        primaryStage.setTitle("Proviz+");
        primaryStage.setScene(new Scene(mainEntrance, 1024, 600));
        primaryStage.show();
        System.out.println("Main Screen Loadin Time "+(System.currentTimeMillis()-startingTime));

    }




    public static void main (String[] args) {


        startingTime = System.currentTimeMillis();


        ProjectConstants.init().setFolderSeperator("/");

        try {
            ProjectConstants.init().setServerIpAddress(InetAddress.getLocalHost().getHostAddress());
            ProjectConstants.init().setIpAddress(InetAddress.getLocalHost().getHostAddress());
            FileOperations.init().initializeProvizFolder();
            FileOperations.init().getFiles();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }




        launch(args);





    }


    private static Configuration startTemplateEngine(Configuration configuration)
    {
        configuration = new Configuration();
        configuration.setClassForTemplateLoading(MainClass.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.US);


        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.DEBUG_HANDLER);
        return configuration;

    }



}
