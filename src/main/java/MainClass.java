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
public class MainClass {

    public static void main (String[] args) {


        ProjectConstants.templateConfiguration =  startTemplateEngine(ProjectConstants.templateConfiguration);

        UIManager.put("Label.font", new Font(Font.SANS_SERIF, 0, 12));


        ProjectConstants.init().setFolderSeperator("/");

        try {
            ProjectConstants.init().setServerIpAddress(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


        JFrame window = new JFrame("");
        Toolkit CurrentToolkit = Toolkit.getDefaultToolkit();
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = CurrentToolkit.getScreenSize();
        window.setSize(screenSize.width,screenSize.height);
        window.setResizable(true);
        window.setLocationRelativeTo(null);



        MainEntrance mainPanel = new MainEntrance(window);
        window.setBounds(0,0,(int)screenSize.getWidth(),(int)screenSize.getHeight());

        window.add(mainPanel.getMainPanel());

        window.pack();


        window.setVisible(true);

        try {
            ProjectConstants.init().setIpAddress(InetAddress.getLocalHost().getHostAddress());
            FileOperations.init().initializeProvizFolder();
            FileOperations.init().getFiles();


        } catch (IOException e) {
            e.printStackTrace();
        }
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
