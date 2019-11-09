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

package dslib.dataframe.transform;

import java.util.ArrayList;
import java.util.List;

public class DFrameIndexImpl implements DFrameIndex {

    private int indexSize;

    public DFrameIndexImpl(int size) { indexSize = size; }

    @Override
    public int mapToOrigin(int reference) {
        return reference;
    }

    @Override
    public int size() {
        return indexSize;
    }

    @Override
    public List<Integer> indexValues() {
        List<Integer> retVals = new ArrayList<>();
        for(int i=0; i < indexSize; ++i){
            retVals.add(i);
        }
        return retVals;
    }

}
