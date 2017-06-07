package proviz.models;

/**
 * Created by Burak on 10/3/16.
 */
public class Bound {
    private double upperValue;
    private double lowerValue;
    private boolean isActive;





    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getUpperValue() {
        return upperValue;
    }

    public void setUpperValue(double upperValue) {
        this.upperValue = upperValue;
    }

    public double getLowerValue() {
        return lowerValue;
    }

    public void setLowerValue(double lowerValue) {
        this.lowerValue = lowerValue;
    }
}
