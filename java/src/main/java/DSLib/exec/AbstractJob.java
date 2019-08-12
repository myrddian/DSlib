package DSLib.exec;

public abstract class AbstractJob implements Runnable {

    private ExecutionContext context;

    public abstract void run(ExecutionContext context);

    @Override
    public void run() {
        run(context);
    }
}
