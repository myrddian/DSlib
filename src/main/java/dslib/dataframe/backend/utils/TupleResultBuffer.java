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

package dslib.dataframe.backend.utils;

import dslib.dataframe.kernels.IndexTupleResult;

import java.util.List;

public class TupleResultBuffer {
    private int size = 0;
    private int location = 0 ;
    private List<IndexTupleResult> results;

    public boolean isExhausted() {
        if (location >= size )
            return true;
        return false;
    }

    public TupleResultBuffer(List<IndexTupleResult> res) {
        size = res.size();
        results = res;
    }

    public IndexTupleResult peek() {
        return results.get(location);
    }

    public void take() {
        location++;
    }

}
