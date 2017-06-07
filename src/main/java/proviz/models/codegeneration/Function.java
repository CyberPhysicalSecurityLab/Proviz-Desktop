package proviz.models.codegeneration;

import java.util.ArrayList;

/**
 * Created by Burak on 10/3/16.
 */
public class Function {
    private String type;
    private String name;
    private ArrayList<Argument> arguments;
    private String innerCode;

    public Function()
    {
        arguments = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Argument> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<Argument> arguments) {
        this.arguments = arguments;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }
}
