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
        var newSd = FindMAPGR1R.findNewSd(testVal,targetMean,alpha,beta);
        var logl = FindMAPGR1R.meanLogLikelyhood(testVal,3,2,0,1);
        FindMAPGR1R mapgr1R = new FindMAPGR1R();
        mapgr1R.setPriorDistribution(3,2);
        var newOpt = mapgr1R.findMAP(testVal);
        System.out.println(newSd[0]);
        System.out.println(newSd[1]);
        System.out.println(logl);
        System.out.println(newOpt[0]);
        System.out.println(newOpt[1]);
        System.out.println(FindMAPGR1R.meanLogLikelyhood(testVal,3,2,newOpt[0],newOpt[1]));
    }

}
