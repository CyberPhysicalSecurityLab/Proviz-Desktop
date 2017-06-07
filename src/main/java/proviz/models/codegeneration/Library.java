package proviz.models.codegeneration;

import proviz.ProjectConstants;

import java.util.ArrayList;

/**
 * Created by Burak on 1/30/17.
 */
public class Library {

    private ProjectConstants.DEVICE_TYPE environment;
    private ArrayList<String> names;

    public  Library()
    {
        setNames(new ArrayList<>());
    }

    public ProjectConstants.DEVICE_TYPE getEnvironment() {
        return environment;
    }

    public void setEnvironment(ProjectConstants.DEVICE_TYPE environment) {
        this.environment = environment;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
}
