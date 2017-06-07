package proviz.codedistribution;

import com.apple.eio.FileManager;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import proviz.ProjectConstants;
import proviz.codegeneration.RaspberryPiTemplate;
import proviz.connection.firwaredistribution.linux.ProvizProperties;
import proviz.connection.firwaredistribution.linux.ssh.SshHandler;
import proviz.library.utilities.FileOperations;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

/**
 * Created by Burak on 2/3/17.
 */
public class RaspberryPiFlasher extends CodeFlasher {

    private CodeDistributionManager codeDistributionManager;
    private String code;
    private RaspberryPiTemplate raspberryPiTemplate;
    private SshHandler sshHandler;
    private String provizRaspberryPiCodeGeneratorPath;

    public RaspberryPiFlasher(CodeDistributionManager codeDistributionManager, String code, RaspberryPiTemplate raspberryPiTemplate)
    {
        this.codeDistributionManager = codeDistributionManager;
        this.code = code;
        this.raspberryPiTemplate = raspberryPiTemplate;
        provizRaspberryPiCodeGeneratorPath = System.getProperty("user.home")+"/.proviz/.raspcodegen/";
        try {
            createMainJavaFile(code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String createMainJavaFile(String code) throws IOException
    {
        File mainTester = new File(provizRaspberryPiCodeGeneratorPath+"src/main/java/MainTester.java");
        if(!mainTester.exists())
            mainTester.createNewFile();
        PrintWriter printWriter = new PrintWriter((mainTester));
        printWriter.print(code);
        printWriter.close();

        return mainTester.toPath().toString();
    }

    private void compileAndMakeReady2Uplaod() {
        ProcessBuilder processBuilder = new ProcessBuilder(new String[]{"/Users/Burak/.proviz/.raspcodegen/test.sh"});
        try {

            Process process = processBuilder.start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String errMessage = null;
            while ((errMessage = bufferedReader.readLine()) != null)
                System.out.println(errMessage);
            String errMessage2 = null;
            while ((errMessage2 = bufferedReader2.readLine()) != null)
                System.out.println(errMessage2);
            process.waitFor();
            File outputJAR = new File(provizRaspberryPiCodeGeneratorPath + "build/libs/provizclient.pi.sensors.jar");
            if (outputJAR.exists())
                Files.copy(outputJAR.toPath(), new File(System.getProperty("user.home") + "/.proviz/app/provizclient.pi.sensors.jar").toPath(), StandardCopyOption.REPLACE_EXISTING);
            else
                throw new FileNotFoundException("Generated client app file not generated.");
            raspberryPiTemplate.getBoard().setProgrammed(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void sendCode2Device()
    {
        try {
            ProvizProperties.getInstance().writeOnlyDeviceIdandConnetionTYPE2Properties(raspberryPiTemplate.getBoard().getUniqueId(),raspberryPiTemplate.getConnectionType());
            sshHandler = new SshHandler();
            sshHandler.connect();
            sshHandler.transferApplication();
            sshHandler.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public int FlashCode() {
        compileAndMakeReady2Uplaod();
        sendCode2Device();
        return 0;
    }

    @Override
    protected String createTempFile(String code) {
        try{
            return createMainJavaFile(code);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
}
