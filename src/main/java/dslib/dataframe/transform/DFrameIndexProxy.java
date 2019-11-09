/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFrameIndexProxy implements DFrameIndex {

    private Map<Integer, Integer> indexMap = new HashMap<>();

    @Override
    public int mapToOrigin(int reference) {
        return indexMap.get(reference);
    }
    @Override
    public int size() {
        return indexMap.size();
    }

    @Override
    public List<Integer> indexValues() {
        return new ArrayList<Integer>(indexMap.values());
    }

    public void addMap(int reference, int origin) {
        indexMap.put(reference,origin);
    }
    public DFrameIndexProxy(Map<Integer, Integer> bulkIndex) {
        indexMap.putAll(bulkIndex);
    }
    public DFrameIndexProxy(){}
}
