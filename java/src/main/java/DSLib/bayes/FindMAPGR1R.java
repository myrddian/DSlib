package DSLib.bayes;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import java.util.Random;

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

    private double optimiserJump = 5;

    public void setPriorDistribution(double priorMean, double priorSd) {
        this.priorMean = priorMean;
        this.priorSd = priorSd;
    }

    public void setHyperParameter(double alpha, double beta) {
        hyperAlpha = alpha;
        hyperBeta = beta;
    }

    public double [] optimiseMAP(double []dataSamples, double priorMean, double priorSd){
        var uniform = new UniformRealDistribution(0,optimiserJump);
        Random cointoss = new Random();
        int optCount = 100;
        //Start with the initial condition

        double bestMean = uniform.sample();
        double bestSd = uniform.sample();
        double bestScore = FindMAPGR1R.meanLogLikelyhood(dataSamples,
                priorMean, priorSd, bestMean, bestSd);

        for(int x=0; x < optCount ; ++x){
            int bias = 1;
            if(cointoss.nextBoolean()) {
                bias = -1;
            }
            double newMean = bestMean + (uniform.sample() * bias);
            var newVals = findNewSd(dataSamples, newMean,
                    this.hyperAlpha, this.hyperBeta);
            double score = meanLogLikelyhood(dataSamples,
                    priorMean, priorSd, newVals[0], newVals[1]);
            if(score > bestScore) {
                bestMean = newVals[0];
                bestSd = newVals[1];
                bestScore  = score;
            }
        }
        double []retVal = new double[2];
        retVal[0] = bestMean;
        retVal[1] = bestSd;
        return retVal;
    }

    public double [] findMAP(double[] dataSamples) {
        int optCount = 20;

        var bestValues = optimiseMAP(dataSamples, priorMean, priorSd);
        var bestScore = meanLogLikelyhood(dataSamples,priorMean, priorSd, bestValues[0], bestValues[1]);

        for(int x=0; x< optCount; ++x) {
            var newValues = optimiseMAP(dataSamples, bestValues[0], bestValues[1]);
            var newScore = meanLogLikelyhood(dataSamples,priorMean, priorSd, newValues[0], newValues[1]);
            if(newScore > bestScore) {
                bestValues = newValues;
                bestScore = newScore;
            }
        }

        return bestValues;
    }

    public double [] findMAP(double[] dataSamples,
                             double pMean,
                             double pSd) {
        setPriorDistribution(pMean, pSd);
        return findMAP(dataSamples);
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
                                     double targetMean,
                                     double alpha,
                                     double beta) {
        double meanSum = 0.0;
        for(double value:dataSamples) {
            meanSum = meanSum + (value - targetMean);
        }
        meanSum = meanSum/2;
        double newAlpha = alpha + (dataSamples.length/2);
        double newBeta = beta + meanSum;
        double retMean = 0.0;
        double retSd = 0;
        if(newBeta < 1) {
            retSd = 1;

        }
        else {
            retSd = 1/(new GammaDistribution(newAlpha,newBeta).sample());
        }

        double []retVal = new double[2];
        retVal[0] = retMean + retSd;
        retVal[1] = retSd;

        return retVal;
    }



}
