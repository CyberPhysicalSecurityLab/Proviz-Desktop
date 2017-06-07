package proviz.asci;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.apache.commons.io.FileUtils;
import proviz.ProjectConstants;
import proviz.connection.WiFiConnection;
import proviz.library.utilities.FileOperations;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Burak on 1/24/17.
 */
public class WiFiRaspClientSoftwarePanel extends JPanel {

    private JPanel panel1;
    private JButton getClientSoftwareButton;
    private DeviceConfigurationDialog configurationDialog;

    public WiFiRaspClientSoftwarePanel(DeviceConfigurationDialog configurationDialog)
    {
        this.configurationDialog = configurationDialog;
        initUI();
        this.add(panel1);

        getClientSoftwareButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                generateClientApp();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    private void generateClientApp()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String rootFolder = getUSBSystemPath();
        if(rootFolder != null)
        fileChooser.setCurrentDirectory(new File(rootFolder));
        int result =  fileChooser.showDialog(this,"OK");

        if(result == JFileChooser.APPROVE_OPTION)
        {
            File selectedFolder = fileChooser.getSelectedFile();

            if(ProjectConstants.isProd == true)
            {
                try {
                    //todo You need to client code generation part for prod version.
                    CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
                    if (codeSource == null)
                        throw new NullPointerException("Protection domain is null");
                    URL jarPath = codeSource.getLocation();
                    JarFile jarfile = new JarFile(codeSource.getLocation().getFile());
                    URI uri = URI.create("jar:file:" + jarfile);
                    Map<String, String> env = new HashMap<>();
                    env.put("create", "true");
                    FileSystem fileSystem = FileSystems.newFileSystem(uri, env, null);
                    Path generatedClientPath = fileSystem.getPath("/clientapplication");
                    Files.copy(generatedClientPath,new File("/Users/Burak/tst").toPath(), StandardCopyOption.REPLACE_EXISTING);

                    }

                catch (Exception ex)
                {
                    ex.printStackTrace();
                }

            }
            else
            {
                    URL clientApplicationPath = WiFiRaspClientSoftwarePanel.class.getClassLoader().getResource("clientapplication");
                URL configPropertiesFilePath = WiFiRaspClientSoftwarePanel.class.getClassLoader().getResource(".config.properties");
                File configPropertiesFile = new File(configPropertiesFilePath.getFile());
                    File clientApplicationFolder = new File(clientApplicationPath.getFile());
                try {
                    FileUtils.copyDirectory(clientApplicationFolder,selectedFolder);
                    FileUtils.copyFile(configPropertiesFile,selectedFolder);
                    configurationDialog.skip2NextPanel();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

    private String getUSBSystemPath()
    {
        switch (FileOperations.init().checkOS())
        {
            case OSX:
            {
            return "/Volumes";
            }
            case LINUX:
            {
            return "/media/"+System.getProperty("user.name");
            }
            case WINDOWS:
            {
                // todo windows root folder needs to be added.
                break;
            }
        }
        return null;
    }
    private void initUI(){
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setMinimumSize(new Dimension(960, 400));
        panel1.setPreferredSize(new Dimension(960, 400));
        final JLabel label1 = new JLabel();
        label1.setText("RPI Client Software Needs to be Prepared");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel1.add(label1, gbc);
        getClientSoftwareButton = new JButton();
        getClientSoftwareButton.setText("Get Client Software");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel1.add(getClientSoftwareButton, gbc);
    }
}
