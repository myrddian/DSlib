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

package dslib.dataframe.backend.datarow;

import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DRow;

import java.util.*;

public class DRowImplString extends DRowImplAbstract {

    private HashMap<String,String> rowValues = new HashMap<>();

    @Override
    public List<String> getColumns() {
        return new ArrayList<>(rowValues.keySet());
    }

    @Override
    public DRow apply(DFrameSchema schema) {
        DRowString2GenericProxy genericRow = new DRowString2GenericProxy();
        genericRow.setBackImplementation(this);
        genericRow.setSchema(schema);
        return genericRow;
    }

    @Override
    public DRow modify(String rowIndex, String newVal) {
        DRowImplString modified = this.clone();
        modified.rowValues.put(rowIndex,newVal);
        return modified;
    }

    @Override
    public DRow modify(String rowIndex, long newVal) {
        return modify(rowIndex, Long.toString(newVal));
    }

    @Override
    public DRow modify(String rowIndex, double newVal) {
        return modify(rowIndex, Double.toString(newVal));
    }

    @Override
    public String get(String index){
        return rowValues.get(index);
    }

    public DRowImplString(List<String> rowval, Map<Integer, String> reverDict) {
        for(int counter=0; counter < rowval.size(); ++counter) {
            rowValues.put(reverDict.get(counter), rowval.get(counter));
        }
    }

    public DRowImplString(){}
    public DRowImplString(DRowImplString orig) {
        rowValues.putAll(orig.rowValues);
    }
    public void insertData(String rowIndex, String rowVal){
        rowValues.put(rowIndex,rowVal);
    }
    public DRowImplString clone(){
        return new DRowImplString(this);
    }


}
