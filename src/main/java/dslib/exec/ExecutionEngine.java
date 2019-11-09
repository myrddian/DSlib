/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the Affero GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/


package dslib.exec;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class ExecutionEngine {

    private ExecutorService executorService;
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

    public static synchronized ExecutionEngine getInstance(){
        if(instance == null){
            instance = new ExecutionEngine();
        }
        return instance;
    }

    private ExecutionEngine() {
        executorService = Executors.newWorkStealingPool();
        int defaultSchedule = getScheduleCapacity();
        setScheduleCapacity(PROCESS_CONTEXT.DEFAULT, defaultSchedule);
    }
    private static ExecutionEngine instance;


}
