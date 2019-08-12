package DSLib.rpo;

import DSLib.exec.AbstractJob;
import DSLib.exec.ExecutionContext;

public abstract class AbstractOptimiserJob extends AbstractJob {

    private RandomPointOptimiserContext randomPointOptimiserContext;

    @Override
    public void run(ExecutionContext context) {
        run(randomPointOptimiserContext);
    }

    public abstract void run(RandomPointOptimiserContext optimiserContext);
}
