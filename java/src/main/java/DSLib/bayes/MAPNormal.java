package DSLib.bayes;

import org.apache.commons.math3.distribution.NormalDistribution;

public class MAPNormal {

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getSd() {
        return sd;
    }

    public void setSd(double sd) {
        this.sd = sd;
    }

    private double mean = 0.0;
    private double sd = 1.0;

}
