package proviz.models.devices;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Burak on 10/3/16.
 */
public class Pin {
    private String name;
    private String preferredVariableName;
    private boolean isrequired;
    private PINTYPE type;
    private int order;
    private DATA_DIRECTION dataDirection;
    @JsonProperty("isAvailable")
    private boolean isAvailable;

    public DATA_DIRECTION getDataDirection() {
        return dataDirection;
    }

    public void setDataDirection(DATA_DIRECTION dataDirection) {
        this.dataDirection = dataDirection;
    }

    public enum DATA_DIRECTION
    {
        UNDEFINED,
        INPUT,
        OUTPUT,
        SERIAL
    }
    @Override
    public String toString() {
        String returnString = "undefined";

        switch (type)
        {
            case analog:
            {
                returnString =  "A_"+order;
                break;
            }
            case digital:
            {
                returnString =  "D_"+order;

                break;
            }
            case vcc:
            {
                break;
            }
            case comm:
            {
                returnString =  "TX/RX_"+order;

                break;
            }
            case ground:
            {
                break;
            }
            case i2c:
            {
                returnString  = "I2C_"+order;
                break;
            }
            case spibus:
            {
                returnString = "SPI_"+order;
                break;
            }
        }
        return returnString;
    }

    public Pin()
    {
        order = 0;
        isAvailable = true;
    }

    public PINTYPE getType() {
        return type;
    }

    public void setType(PINTYPE type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPreferredVariableName() {
        return preferredVariableName;
    }

    public void setPreferredVariableName(String preferredVariableName) {
        this.preferredVariableName = preferredVariableName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public enum PINTYPE{
        vcc,
        ground,
        analog,
        comm,
        i2c,
        spibus,
        digital
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isrequired() {
        return isrequired;
    }

    public void setIsrequired(boolean isrequired) {
        this.isrequired = isrequired;
    }
}
