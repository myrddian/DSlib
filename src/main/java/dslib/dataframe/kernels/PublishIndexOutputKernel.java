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
