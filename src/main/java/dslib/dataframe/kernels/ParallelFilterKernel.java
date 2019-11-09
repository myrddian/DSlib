package dslib.dataframe.kernels;

import dslib.dataframe.backend.DRowImplString;
import dslib.exec.ExecutionContext;
import dslib.exec.ExecTask;

import java.util.List;

public class ParallelFilterKernel implements ExecTask {

    @Override
    public void run(ExecutionContext context) {
        List<String> filterValue = (List<String>) context.get("filterValues");
        List<String> fields = (List<String>) context.get("fields");
        String index = (String) context.get("index");
        for(Object rowObject: context.getSchedule()) {
            DRowImplString row = (DRowImplString) rowObject;
            if(filterValue.contains(row.get(index))){
                DRowImplString newRow = new DRowImplString();
                for (String matchedField: fields) {
                    newRow.insertData(matchedField,row.get(matchedField));
                }
                context.addResult(newRow);
            }
        }
    }
}
