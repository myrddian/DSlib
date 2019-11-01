package dslib.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TaskRunner implements Runnable {

    private ExecutionContext context;
    private List<ExecTask> execTaskList = new ArrayList<>();
    private CountDownLatch countDownLatch;

    @Override
    public void run() {
        for(ExecTask job: execTaskList) {
            job.run(context);
        }
        countDownLatch.countDown();
    }

    public void setContext(ExecutionContext context) {
        this.context = context;
    }

    public void setExecTaskList(List<ExecTask> list) {
        execTaskList.addAll(list);
    }

    public void setLatch(CountDownLatch latch) {
        countDownLatch = latch;
    }
}
