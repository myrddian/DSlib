package dslib.rpo;

public interface OptimiserKernel<TYPE, PERMUTE> {

    double getInitialStartingScore();
    boolean isMax();
    boolean isMonteCarlo();
    double score(TYPE samples);
    PERMUTE modify(RandomPointOptimiserContext optimiserContext);
}
