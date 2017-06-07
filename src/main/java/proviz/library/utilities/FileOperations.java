package proviz.library.utilities;

import proviz.ProjectConstants;
import proviz.devicedatalibrary.DataManager;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.*;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Burak on 10/11/16.
 */
public class FileOperations {
    private static FileOperations fileOperations;

    public static FileOperations init()
    {
        if(fileOperations == null )
            fileOperations = new FileOperations();
        return fileOperations;
    }

    public ArrayList<String> getBoardTypes(String path)
    {
        ArrayList<String> files = getResourceFoldersName(path);


        return files;

    }

    public ArrayList<String> getSensors(String path)
    {



        return null;
    }

    public ArrayList<String> boardNamesInFolder(String folderResourceName)
    {

        return null;
    }

    public ProjectConstants.OS_TYPE checkOS()
    {
        String osName = System.getProperty("os.name");
        if(osName.indexOf("Mac") >= 0 || osName.indexOf("darwin") >= 0)
        {
            return ProjectConstants.OS_TYPE.OSX;
        }
        else if(osName.indexOf("Win") >= 0)
        {
            return ProjectConstants.OS_TYPE.WINDOWS;
        }
        else if(osName.indexOf("nux") >=0)
        {
            return ProjectConstants.OS_TYPE.LINUX;
        }
        return null;
    }
    private boolean checkProvizFolder()
    {
        File folder = new File(ProjectConstants.init().getProvizFolderPath());

        if(folder.exists() && folder.isFile() == false)
        {
            return true;
        }
        return false;

    }

    public void initializeProvizFolder()
    {
        String provizFolder = ProjectConstants.init().getProvizFolderPath();

        if(!checkProvizFolder())
        {

        }
        File tempFolder= new File(provizFolder+ProjectConstants.init().getFolderSeperator()+"temp");

        if(!tempFolder.exists())
            tempFolder.mkdir();

    }


private ArrayList<String> getResourceFoldersName(String path)
{
    ArrayList<String> folderNames = new ArrayList<>();
    try{
        File file = new File(path);
        String[] names = file.list();

        for(String name : names)
        {
            if (new File(path + name).isDirectory())
            {
                folderNames.add(name);
            }
        }
    }
    catch (Exception ex)
    {
        ex.printStackTrace();
    }
    return folderNames;
}

    private ArrayList<File> getResourceFiles(String folderPath) throws IOException {
        ArrayList<File> files = new ArrayList<>();
            InputStream inputStream = getClass().getResourceAsStream(folderPath);

            try {
                File file = new File(folderPath);
                String[] names = file.list();

                for (String name : names) {
                    File fl =new File(folderPath + ProjectConstants.init().getFolderSeperator() + name);
                    if (fl.isFile()) {
                        files.add(fl);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        return files;
    }

    public void getFiles() throws NullPointerException, IOException
    {
        if(ProjectConstants.isProd == true)
        {
            // JAR file extraction
            CodeSource codeSource = this.getClass().getProtectionDomain().getCodeSource();
            if(codeSource == null)
                throw new NullPointerException("Protection domain is null");

            URL jarPath = codeSource.getLocation();
            JarFile jarfile = new JarFile(codeSource.getLocation().getFile());
            URI uri = URI.create("jar:file:" + jarfile);
            Map<String, String> env = new HashMap<>();
            env.put("create", "true");

            FileSystem fileSystem = FileSystems.newFileSystem(uri, env, null);
            Path generatedClientPath = fileSystem.getPath("/clientapplication");


            ZipInputStream zipInputStream = new ZipInputStream(jarPath.openStream());
            while(true)
            {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if(zipEntry == null)
                    break;
                String name = zipEntry.getName();

                if(name.startsWith("sensors/images/") && !zipEntry.isDirectory() ) {
                    // Sensor Images Load
                    DataManager.getInstance().addSensorImages(ImageLoader.LoadImageIconFromJAR(zipEntry,zipInputStream),zipEntry.getName());


                }
                else
                {
                    if(name.startsWith("sensors/") && !zipEntry.isDirectory() )
                    {
                        DataManager.getInstance().addSensorFromJSONInputStream(jarfile.getInputStream(zipEntry));
                        // JSON Sensor File Images
                    }
                }

                if(name.startsWith("boards/") && !zipEntry.isDirectory())
                {
                    DataManager.getInstance().addBoardFromJSONInputStream(jarfile.getInputStream(zipEntry));
                }

            }


        }
        else
        {
            //not ProdVersion
            DataManager.getInstance().addSensorImages(getResourceFiles(this.getClass().getResource("/sensors/images/").getPath()));
            DataManager.getInstance().addBoards(getResourceFiles(this.getClass().getResource("/boards/arduino").getPath()));
            if(this.getClass().getResource("/boards/beaglebone")!= null)
            DataManager.getInstance().addBoards(getResourceFiles(this.getClass().getResource("/boards/beaglebone").getPath()));
            if(this.getClass().getResource("/boards/raspberrypi")!= null)
            DataManager.getInstance().addBoards(getResourceFiles(this.getClass().getResource("/boards/raspberrypi").getPath()));
            DataManager.getInstance().addSensors(getResourceFiles(this.getClass().getResource("/sensors/").getPath()));
        }
    }
}
