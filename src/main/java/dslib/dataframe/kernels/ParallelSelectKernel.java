package dslib.dataframe.kernels;

import dslib.DSLib;
import dslib.dataframe.DFrameSchemaBuilder;
import dslib.dataframe.backend.DStoreFrameProxy;
import dslib.dataframe.transform.DFrameSelectTransform;
import dslib.exec.ExecTask;
import dslib.exec.ExecutionContext;

import java.util.List;

public class ParallelSelectKernel implements ExecTask {

    @Override
    public void run(ExecutionContext context) {
        /*List<String> filterValue = (List<String>) context.get("filterValues");
        List<String> fields = (List<String>) context.get("fields");
        DFrameSchemaBuilder newSchemaBuilder = DFrameSchemaBuilder.createSchema(this.getSchema().getName());
        for(String fieldSelection: fields){
            if(getSchema().type(fieldSelection) == DSLib.DataType.INVALID) {
                return null;
            }
            newSchemaBuilder.defineColumn(fieldSelection,getSchema().type(fieldSelection));

        }
        DFrameSelectTransform selectTransform = new DFrameSelectTransform();
        selectTransform.setSchema(this.getSchema());
        selectTransform.setStore(new DStoreFrameProxy(this));
        selectTransform.setIndex(this.getIndexProxy());
        return selectTransform.apply(newSchemaBuilder.end());*/
    }

}
