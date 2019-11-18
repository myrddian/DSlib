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

package dslib.dataframe.backend.store;

import dslib.dataframe.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFrameAddRowTransformStore implements DFrameStore {

    private DFrame backFrame;
    private Map<Integer, DRow> addedLines = new HashMap<>();

    public void setBackFrame(DFrame backFrame) {
        this.backFrame = backFrame;
    }

    public void addRows(List<DRow> rows) {
        int startingNumber = backFrame.size();
        for(DRow newRow: rows) {
            addedLines.put(startingNumber, newRow);
            ++startingNumber;
        }
    }

    @Override
    public int size() {
        return backFrame.size() + addedLines.size();
    }

    @Override
    public DRow getRow(int index) {
        if(addedLines.containsKey(index)) {
            return addedLines.get(index);
        }
        if(index < backFrame.size()) {
            return backFrame.loc(index);
        }
        return null;
    }

    @Override
    public List<String> getColNames() {
        return backFrame.getColNames();
    }
}
