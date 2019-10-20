package DSLib;
import DSLib.exec.ExecutionEngine;
import org.junit.jupiter.api.Test;

public class ExecutionTest {

    @Test
    public void engineStart() {
        ExecutionEngine executionEngine = new ExecutionEngine();
        System.out.println("Total Capacity: " + Integer.toString(executionEngine.getScheduleCapacity()));
    }

}
