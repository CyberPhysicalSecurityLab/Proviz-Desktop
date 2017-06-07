package proviz.models.codegeneration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import proviz.models.Bound;

/**
 * Created by Burak on 10/3/16.
 */
public class Variable {
    private String type;
    private String preferredName;
    private boolean communicationVariable;
  @JsonIgnore
  private Bound bound;
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Variable()
    {
    }

    public boolean isCommunicationVariable() {
        return communicationVariable;
    }

    public void setCommunicationVariable(boolean communicationVariable) {
        this.communicationVariable = communicationVariable;
        if(communicationVariable == true)
        {
            bound = new Bound();
            bound.setActive(true);
            bound.setUpperValue(100.0);
            bound.setLowerValue(20.0);
        }
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public Bound getBound() {
        return bound;
    }

    public void setBound(Bound bound) {
        this.bound = bound;
    }
}

