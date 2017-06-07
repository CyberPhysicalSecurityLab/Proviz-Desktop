package proviz.models.configuration;

/**
 * Created by Burak on 9/30/16.
 */
public class Compiler {
    private String type;
    private Enviroments[] enviroments;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Enviroments[] getEnviroments() {
        return enviroments;
    }

    public void setEnviroments(Enviroments[] enviroments) {
        this.enviroments = enviroments;
    }
}
