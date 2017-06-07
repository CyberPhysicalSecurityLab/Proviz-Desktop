package proviz.models.tablet;

/**
 * Created by Burak on 5/12/17.
 */
public class ClusterSensorVariable {
    private String variableName;
    private double minThreshold;
    private double maxThreshold;

    public ClusterSensorVariable() {
    }


    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public double getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(double minThreshold) {
        this.minThreshold = minThreshold;
    }

    public double getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(double maxThreshold) {
        this.maxThreshold = maxThreshold;
    }


}
