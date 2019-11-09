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

package dslib.dataframe.frontend;

import dslib.DSLib;
import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DRow;
import dslib.dataframe.backend.DRowImplString;

import java.util.List;

public class DRowImplGeneric implements DRow {

    public void setBackImplementation(DRowImplString backImplementation) {
        this.backImplementation = backImplementation;
    }

    public void setSchema(DFrameSchema schema) {
        this.schema = schema;
    }

    private DRowImplString backImplementation;
    private DFrameSchema schema;

    @Override
    public <Any> Any get(String index) {
        if(!schema.contains(index)) {
            return null;
        }
        switch (schema.type(index)) {
            case FLOAT:
                return (Any)((Double)(double) Double.parseDouble(backImplementation.get(index)));
            case INTEGER:
                return (Any)((Long)(long) Long.parseLong(backImplementation.get(index)));
            default:
                return (Any)((String) backImplementation.get(index));
        }
    }

    @Override
    public DRow apply(DFrameSchema schema) {
        DRowImplGeneric newRow = new DRowImplGeneric();
        newRow.backImplementation = this.backImplementation;
        newRow.schema = schema;
        return newRow;
    }

    @Override
    public DRow modify(String rowIndex, String newVal) {
        if(!schema.contains(rowIndex)) {
            return null;
        }
        if(schema.type(rowIndex) == DSLib.DataType.STRING) {
            DRowImplString newBack = (DRowImplString)backImplementation.modify(rowIndex,newVal);
            DRowImplGeneric newRow = new DRowImplGeneric();
            newRow.backImplementation = newBack;
            newRow.schema = schema;
            return newRow;
        }
        return null;
    }

    @Override
    public DRow modify(String rowIndex, long newVal) {
        if(!schema.contains(rowIndex)) {
            return null;
        }
        if(schema.type(rowIndex) == DSLib.DataType.INTEGER) {
            DRowImplString newBack = (DRowImplString)backImplementation.modify(rowIndex,newVal);
            DRowImplGeneric newRow = new DRowImplGeneric();
            newRow.backImplementation = newBack;
            newRow.schema = schema;
            return newRow;
        }
        return null;
    }

    @Override
    public DRow modify(String rowIndex, double newVal) {
        if(!schema.contains(rowIndex)) {
            return null;
        }
        if(schema.type(rowIndex) == DSLib.DataType.FLOAT) {
            DRowImplString newBack = (DRowImplString)backImplementation.modify(rowIndex,newVal);
            DRowImplGeneric newRow = new DRowImplGeneric();
            newRow.backImplementation = newBack;
            newRow.schema = schema;
            return newRow;
        }
        return null;
    }

    @Override
    public List<String> getColumns() {
        return schema.columnNames();
    }
}
