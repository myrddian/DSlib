package DSLib.exec;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class ExecutionEngine {

    private ExecutorService executorService;
    private BlockingQueue messageBox;

    public ExecutionContext createContext(int numJobs) {
        return new ExecutionContext();
    }


}
