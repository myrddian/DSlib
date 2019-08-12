package DSLib.bayes;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

public class FindMAPGR1R {

    private double priorMean=0.0;
    private double priorSd=1;
    private double hyperAlpha=1;
    private double hyperBeta=1;

    public double getOptimiserJump() {
        return optimiserJump;
    }

    public void setOptimiserJump(double optimiserJump) {
        this.optimiserJump = optimiserJump;
    }

    private double optimiserJump = 1;

    public void setPriorDistribution(double priorMean, double priorSd) {
        this.priorMean = priorMean;
        this.priorSd = priorSd;
    }

    public void setHyperParameter(double alpha, double beta) {
        hyperAlpha = alpha;
        hyperBeta = beta;
    }

    public static double meanLogLikelyhood(double []dataSamples,
                                           double priorMean, double priorSd,
                                           double targetMean, double targetSd) {
        double logLikelyhood = 0.0;
        var logPrior = new NormalDistribution(priorMean,priorSd).logDensity(targetMean);
        var targetDist = new NormalDistribution(targetMean,targetSd);

        for(var sample: dataSamples) {
            logLikelyhood += targetDist.logDensity(sample);
        }

        return (logLikelyhood + logPrior);
    }

    public static double []findNewSd(double []dataSamples,
                                   double targetMean, double targetSd,
                                   double alpha, double beta) {
        double meanSum = 0.0;
        for(double value:dataSamples) {
            meanSum = meanSum + (value - targetMean);
        }
        meanSum = meanSum/2;
        double newAlpha = alpha + (dataSamples.length/2);
        double newBeta = beta + meanSum;
        double retMean = 0.0;

        if(newBeta < 1) {
            newBeta = 1.0;
        }

        double retSd = 1/(new GammaDistribution(newAlpha,newBeta).sample());

        double []retVal = new double[2];
        retVal[0] = retMean + retSd;
        retVal[1] = retSd;

        return retVal;
    }



}
