package proviz.connection.restwebservice;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import proviz.ProjectConstants;
import proviz.connection.restwebservice.controllers.GetValueFromDeviceController;

import javax.ws.rs.Path;

/**
 * Created by Burak on 1/11/17.
 */
@Path("/")
public class WebServiceManager {
    // The Java method will process HTTP GET requests

    private  final URI BASE_URI = URI.create("http://"+ ProjectConstants.init().getServerIpAddress()+":5867/");

    private static WebServiceManager self;
    private boolean isAlreadyStarted;
    private  HttpServer server;

    public static WebServiceManager getInstance()
    {
        if(self == null)
            self = new WebServiceManager();
        return self;
    }


    public void start() throws Exception {   try {

        if(isAlreadyStarted == true)
            throw new Exception("Webservice deamon has been already invoked.");
        isAlreadyStarted = true;
        Map<String, String> initParams = new HashMap<>();
        initParams.put(
                ServerProperties.PROVIDER_PACKAGES,
                GetValueFromDeviceController.class.getPackage().getName());
        initParams.put("com.sun.jersey.api.json.POJOMappingFeature", "true");

          server = GrizzlyWebContainerFactory.create(BASE_URI, ServletContainer.class, initParams);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                server.shutdownNow();
            }
        }));

        System.out.println("WebService Started ");

        Thread.currentThread().join();
    } catch (IOException | InterruptedException ex) {
        ex.printStackTrace();
    }
    }

    public void stopWebService()
    {

    }

    public boolean isAlreadyStarted() {
        return isAlreadyStarted;
    }
}
