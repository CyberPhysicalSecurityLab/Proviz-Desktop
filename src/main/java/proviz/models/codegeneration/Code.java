package proviz.models.codegeneration;

import proviz.ProjectConstants;

import java.util.ArrayList;

/**
 * Created by Burak on 10/3/16.
 */
public class Code {
    private ArrayList<Function> functions;
    private ProjectConstants.DEVICE_TYPE environment;

    public Code()
    {
        functions = new ArrayList<>();
    }

    public ArrayList<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(ArrayList<Function> function) {
        this.functions = function;
    }


    public ProjectConstants.DEVICE_TYPE getEnvironment() {
        return environment;
    }

    public void setEnvironment(ProjectConstants.DEVICE_TYPE environment) {
        this.environment = environment;
    }
}
