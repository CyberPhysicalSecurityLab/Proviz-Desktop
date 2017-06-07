package proviz.models.configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Burak on 9/30/16.
 */
public class ProjectConfiguration {
    private static ProjectConfiguration projectConfiguration;
 private  ArrayList<proviz.models.configuration.Compiler> compilers;
    private ArrayList<String> deviceTypes;

    public static ProjectConfiguration init() {
        if (projectConfiguration != null) {
            ObjectMapper objectMapper = new ObjectMapper();

            try {
                projectConfiguration = objectMapper.readValue(new File(ClassLoader.getSystemResource("/config/configuration.json.json").toURI()), projectConfiguration.getClass());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return projectConfiguration;
        }
        else
            return projectConfiguration;
    }




    public static void setProjectConfiguration(ProjectConfiguration projectConfiguration) {
        ProjectConfiguration.projectConfiguration = projectConfiguration;
    }

    public ArrayList<proviz.models.configuration.Compiler> getCompilers() {
        return compilers;
    }

    public void setCompilers(ArrayList<proviz.models.configuration.Compiler> compilers) {
        this.compilers = compilers;
    }

    public ArrayList<String> getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(ArrayList<String> deviceTypes) {
        this.deviceTypes = deviceTypes;
    }
}
