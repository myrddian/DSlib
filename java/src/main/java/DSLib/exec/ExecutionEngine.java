package DSLib.exec;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

public class ExecutionEngine {

    private ExecutorService executorService;
    private BlockingQueue messageBox;

    public enum PROCESS_CONTEXT { DEFAULT, OPTIMISER, INTEGRATION};

    public ExecutionContext createContext(int numJobs) {
        return new ExecutionContext();
    }


    public int getScheduleCapacity() {
        return getScheduleCapacity(PROCESS_CONTEXT.DEFAULT);
    }

    public int getScheduleCapacity(PROCESS_CONTEXT processContext) {
        return 1;
    }


}
