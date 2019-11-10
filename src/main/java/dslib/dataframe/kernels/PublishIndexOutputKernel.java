package dslib.dataframe.kernels;

import dslib.exec.ExecTask;
import dslib.exec.ExecutionContext;

import java.util.List;

public class PublishIndexOutputKernel implements ExecTask {
    @Override
    public void run(ExecutionContext context) {
        List<Integer> index = (List<Integer>) context.get(KernelStaticInfo.indexList);
        for(int indexValue: index) {
            context.addResult(indexValue);
        }
    }
}
