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
import dslib.DSLib;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DRowGenericInMem extends DRowImplAbstract {

    private DFrameSchema rowSchema;
    private Map<String, String> stringStorage = new HashMap<>();
    private Map<String, Double> doubleStorage = new HashMap<>();
    private Map<String, Long>   longStorage = new HashMap<>();

    private DRowGenericInMem createCopy() {
        DRowGenericInMem newCopy = new DRowGenericInMem();
        newCopy.rowSchema = this.rowSchema;
        newCopy.stringStorage.putAll(this.stringStorage);
        newCopy.doubleStorage.putAll(this.doubleStorage);
        newCopy.longStorage.putAll(this.longStorage);
        return newCopy;
    }

    public void setRowSchema(DFrameSchema newSchema) { rowSchema = newSchema;}

    @Override
    public DRow modify(String rowIndex, String newVal) {
        if(rowSchema.type(rowIndex)== DSLib.DataType.STRING) {
            DRowGenericInMem newItem = createCopy();
            newItem.stringStorage.put(rowIndex,newVal);
            return newItem;
        }
        return null;
    }

    @Override
    public DRow modify(String rowIndex, long newVal) {
        if(rowSchema.type(rowIndex)== DSLib.DataType.INTEGER) {
            DRowGenericInMem newItem = createCopy();
            newItem.longStorage.put(rowIndex,newVal);
            return newItem;
        }
        return null;
    }

    @Override
    public DRow modify(String rowIndex, double newVal) {
        if(rowSchema.type(rowIndex)== DSLib.DataType.FLOAT) {
            DRowGenericInMem newItem = createCopy();
            newItem.doubleStorage.put(rowIndex,newVal);
            return newItem;
        }
        return null;
    }

    @Override
    public List<String> getColumns() {
        return rowSchema.columnNames();
    }

    @Override
    public <Any> Any get(String index) {
        if(!rowSchema.contains(index)) {
            return null;
        }
        switch (rowSchema.type(index)) {
            case FLOAT:
                return (Any)((Double)(double)(doubleStorage.get(index)));
            case INTEGER:
                return (Any)((Long)(long)(longStorage.get(index)));
            default:
                return (Any)((String)stringStorage.get(index));
        }
    }

    @Override
    public DRow apply(DFrameSchema schema) {
        DRowGenericInMem copy = createCopy();
        copy.rowSchema = schema;
        return copy;
    }
}
