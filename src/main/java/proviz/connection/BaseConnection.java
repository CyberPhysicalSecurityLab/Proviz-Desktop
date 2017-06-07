package proviz.connection;

import proviz.ProjectConstants;
import proviz.codegeneration.CodeGenerationTemplate;
import proviz.models.devices.Board;
import proviz.models.devices.Sensor;

import java.util.ArrayList;

/**
 * Created by Burak on 10/6/16.
 */
public abstract class BaseConnection {
    protected CodeGenerationTemplate codeGenerationTemplate;

    public ProjectConstants.CONNECTION_TYPE getConnection_type() {
        return connection_type;
    }

    public void setConnection_type(ProjectConstants.CONNECTION_TYPE connection_type) {
        this.connection_type = connection_type;
    }

    protected ProjectConstants.CONNECTION_TYPE connection_type;


    public abstract void startConnection();
    public abstract void stopConnection();


    public CodeGenerationTemplate getCodeGenerationTemplate() {
        return codeGenerationTemplate;
    }

    public void setCodeGenerationTemplate(CodeGenerationTemplate codeGenerationTemplate) {
        this.codeGenerationTemplate = codeGenerationTemplate;
    }
}
