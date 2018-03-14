package proviz.models.devices;

import com.fasterxml.jackson.annotation.JsonIgnore;
import proviz.models.codegeneration.Code;
import proviz.ProjectConstants;
import proviz.models.Bound;
import proviz.models.codegeneration.Library;
import proviz.models.codegeneration.Variable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

/**
 * Created by Burak on 10/3/16.
 */
public class Sensor  {
    private ArrayList<Pin> pins;
    private int baudrate;
    private String description;
    @JsonIgnore
    private Board parentBoard;
    private String name;
    private String variableTypeName;
    private ArrayList<Code> codes;
    private ArrayList<Variable> variables;
    private ArrayList<Library> libraries;
    private Bound bound;
    private double timeOut;
    private String uniqueIdWithUnderScore;
    private ImageIcon image;



    public Sensor()
    {
        clearPins();
        uniqueIdWithUnderScore = UUID.randomUUID().toString().replace("-","_");

    }

    @Override
    public String toString() {
        return name;
    }

    public Sensor(String uuid)
    {
        clearPins();
        uniqueIdWithUnderScore = uuid;

    }
    public void clearPins(){pins = new ArrayList<>();}
public void addPin(Pin pin)
{
    Pin pin1 = new Pin();
    pin1.setAvailable(pin.isAvailable());
    pin1.setDataDirection(ProjectConstants.init().getOppositePin(pin.getDataDirection()));
    pin1.setIsrequired(pin.isrequired());
pin1.setName(pin.getName());
pin1.setOrder(pin.getOrder());
pin1.setPreferredVariableName(pin.getPreferredVariableName());
pin1.setType(pin.getType());
pins.add(pin);
}

    public void setVariableBounds()
    {
        for(Variable variable:variables)
        {
            if(variable == null)
                variable = new Variable();

            if(variable.isCommunicationVariable())
            {
                bound = new Bound();
                bound.setActive(true);
                bound.setUpperValue(100.0);
                bound.setLowerValue(20.0);
                variable.setBound(new Bound());
            }


        }
    }


    public ArrayList<Pin> getPins() {
        return pins;
    }

//    public void setPins(ArrayList<Pin> pins) {
//        this.pins = pins;
//    }

    public int getBaudrate() {
        return baudrate;
    }

    public void setBaudrate(int baudrate) {
        this.baudrate = baudrate;
    }

    public ArrayList<Code> getCodes() {
        return codes;
    }

    public void setCodes(ArrayList<Code> codes) {
        this.codes = codes;
    }

    public ArrayList<Variable> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Variable> variables) {
        this.variables = variables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public double getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(double timeOut) {
        this.timeOut = timeOut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUniqueIdWithUnderScore() {
        return uniqueIdWithUnderScore;
    }

    public void setUniqueIdWithUnderScore(String uniqueIdWithUnderScore) {
        this.uniqueIdWithUnderScore = uniqueIdWithUnderScore;
    }


    public Bound getBound() {
        return bound;
    }

    public void setBound(Bound bound) {
        this.bound = bound;
    }

    public Board getParentBoard() {
        return parentBoard;
    }

    public void setParentBoard(Board parentBoard) {
        this.parentBoard = parentBoard;
    }

    public String getVariableTypeName() {
        return variableTypeName;
    }

    public void setVariableTypeName(String variableTypeName) {
        this.variableTypeName = variableTypeName;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public ArrayList<Library> getLibraries() {
        return libraries;
    }

    public void setLibraries(ArrayList<Library> libraries) {
        this.libraries = libraries;
    }
}
