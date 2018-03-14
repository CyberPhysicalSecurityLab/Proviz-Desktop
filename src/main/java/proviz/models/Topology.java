package proviz.models;

import proviz.codegeneration.CodeGenerationTemplate;
import proviz.models.devices.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Burak on 8/13/17.
 */
public class Topology implements Serializable {
    public ArrayList<CodeGenerationTemplate> codeGenerationTemplates = new ArrayList<>();

    public Topology()
    {
        codeGenerationTemplates = new ArrayList<>();
    }

    public ArrayList<CodeGenerationTemplate> getCodeGenerationTemplates() {
        return codeGenerationTemplates;
    }

    public void setCodeGenerationTemplates(ArrayList<CodeGenerationTemplate> codeGenerationTemplates) {
        this.codeGenerationTemplates = codeGenerationTemplates;
    }
}
