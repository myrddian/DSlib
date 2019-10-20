package DSLib.exec;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ExecutionEngine {

    private ExecutorService executorService;
    private BlockingQueue messageBox;
    private Map<PROCESS_CONTEXT, Integer> contextConfig = new HashMap<>();

    public enum PROCESS_CONTEXT { DEFAULT, OPTIMISER, INTEGRATION};
    public enum SCHEDULE { ROUND_ROBIN }

    public int getScheduleCapacity() {
        return getScheduleCapacity(PROCESS_CONTEXT.DEFAULT);
    }

    public int getScheduleCapacity(PROCESS_CONTEXT processContext) {
        if (contextConfig.containsKey(processContext)) {
            return contextConfig.get(processContext);
        }
        return Runtime.getRuntime().availableProcessors();
    }

    public void setScheduleCapacity(PROCESS_CONTEXT processContext, int size){
        if (size > Runtime.getRuntime().availableProcessors() || size < 1)
            return;
        contextConfig.put(processContext, size);
    }

    public ExecuteParallelTask createParallelTasks() {
        return createParallelTasks(PROCESS_CONTEXT.DEFAULT, SCHEDULE.ROUND_ROBIN);
    }

    public ExecuteParallelTask createParallelTasks(PROCESS_CONTEXT context, SCHEDULE schedule) {
        ExecuteParallelTask newTask = new ExecuteParallelTask(getScheduleCapacity(context), this);
        return newTask;
    }

    public void submitJob(TaskRunner job) {
        executorService.execute(job);
    }

    public ExecutionEngine() {
        executorService = Executors.newWorkStealingPool();
        int defaultSchedule = getScheduleCapacity();
        setScheduleCapacity(PROCESS_CONTEXT.DEFAULT, defaultSchedule);
    }

}
