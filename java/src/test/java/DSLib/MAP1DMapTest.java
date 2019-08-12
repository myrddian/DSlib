package DSLib;

import DSLib.bayes.FindMAPGR1R;
import org.junit.jupiter.api.Test;

public class MAP1DMapTest {

    @Test
    public void testMap() {
        double []testVal = new double[]{0.5,0.4,0.1,0.5,0.2,0.3};
        double targetMean = 0;
        double targetSd = 1;
        double alpha=1;
        double beta=1;
        var newSd = FindMAPGR1R.findNewSd(testVal,targetMean,targetSd,alpha,beta);
        var logl = FindMAPGR1R.meanLogLikelyhood(testVal,3,2,0,1);
        System.out.println(newSd[0]);
        System.out.println(newSd[1]);
        System.out.println(logl);
    }

}
