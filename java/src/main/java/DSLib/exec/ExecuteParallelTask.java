package DSLib.exec;

import java.util.*;
import java.util.concurrent.*;

public class ExecuteParallelTask {


    public void schedule(Object scheduledObject) {
        int targetJob = jobCount % threads;
        if(scheduleListMapping.containsKey(targetJob)) {
            scheduleListMapping.get(targetJob).add(scheduledObject);
        }
        else {
            scheduleListMapping.put(targetJob, new ArrayList<>());
            scheduleListMapping.get(targetJob).add(scheduledObject);
        }
        jobCount++;
    }

    public void addParam(String paramName, Object value) {
        paramaters.put(paramName, value);
    }

    public void addTasks(ExecTask execTask) {
        execTaskList.add(execTask);
    }

    public ExecuteParallelTask(int numofTasks, ExecutionEngine engine) {
        threads = numofTasks;
        latch = new CountDownLatch(threads);
        executionEngine = engine;
    }

    public void exec() {
        latch = new CountDownLatch(threads);
        for(Integer scheduleGroup: scheduleListMapping.keySet()) {
            ExecutionContext context = new ExecutionContext();
            context.setOutputList(outputs);
            TaskRunner taskRunner = new TaskRunner();
            context.setScheduledObjects(scheduleListMapping.get(scheduleGroup));
            context.setParameters(paramaters);
            taskRunner.setLatch(latch);
            taskRunner.setContext(context);
            taskRunner.setExecTaskList(execTaskList);
            executionEngine.submitJob(taskRunner);
        }
    }

    public void waitForTasks() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Object getOutputItem() {
        try {
            return outputs.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return  null;
        }
    }

    public int outputSize() {
        return outputs.size();
    }

    private Map<Integer, List<Object>> scheduleListMapping = new HashMap<>();
    private Map<String, Object> paramaters = new HashMap<>();
    private List<ExecTask> execTaskList = new ArrayList<>();
    private int threads = 1;
    private int jobCount = 0;
    private ExecutionEngine.SCHEDULE mode = ExecutionEngine.SCHEDULE.ROUND_ROBIN;
    private CountDownLatch latch;
    private ExecutionEngine executionEngine;
    private BlockingQueue<Object> outputs = new LinkedBlockingQueue<>();
}
