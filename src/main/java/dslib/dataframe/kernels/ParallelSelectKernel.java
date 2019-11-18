/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the Affero General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the Affero GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/
package dslib.dataframe.kernels;

import dslib.exec.ExecTask;
import dslib.exec.ExecutionContext;

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
