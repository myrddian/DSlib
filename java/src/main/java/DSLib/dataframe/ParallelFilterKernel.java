package DSLib.dataframe;

import DSLib.exec.ExecutionContext;
import DSLib.exec.ExecTask;

import java.util.List;

public class ParallelFilterKernel implements ExecTask {

    @Override
    public void run(ExecutionContext context) {
        List<String> filterValue = (List<String>) context.get("filterValues");
        List<String> fields = (List<String>) context.get("fields");
        String index = (String) context.get("index");
        for(Object rowObject: context.getSchedule()) {
            DLDataRowImpl row = (DLDataRowImpl) rowObject;
            if(filterValue.contains(row.get(index))){
                DLDataRowImpl newRow = new DLDataRowImpl();
                for (String matchedField: fields) {
                    newRow.insertData(matchedField,row.get(matchedField));
                }
                context.addResult(newRow);
            }
        }
    }
}
