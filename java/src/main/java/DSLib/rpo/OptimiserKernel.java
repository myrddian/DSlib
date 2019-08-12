package DSLib.rpo;

public interface OptimiserKernel<TYPE, PERMUTE> {

    double getInitialStartingScore();
    boolean isMax();
    double score(TYPE samples);
    PERMUTE modify(RandomPointOptimiserContext optimiserContext);
}
