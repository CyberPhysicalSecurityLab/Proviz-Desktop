package proviz.models.uielements;

import java.util.ArrayList;

/**
 * Created by Burak on 10/7/16.
 */
public class PinSelectionModel {
    private String pinName;
    private ArrayList<String> availablePins;

    public PinSelectionModel(){
        availablePins = new ArrayList<>();
    }

    public String getPinName() {
        return pinName;
    }

    public void setPinName(String pinName) {
        this.pinName = pinName;
    }

    public ArrayList<String> getAvailablePins() {
        return availablePins;
    }

    public void setAvailablePins(ArrayList<String> availablePins) {
        this.availablePins = availablePins;
    }
}
