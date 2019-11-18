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

import dslib.dataframe.backend.datarow.DRowImplString;
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
