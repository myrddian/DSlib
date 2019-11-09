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

package dslib.dataframe.implementation;

import dslib.DSLib;
import dslib.dataframe.DFrameSchema;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DFrameSchemaImpl implements DFrameSchema {

    private String schemaName;
    private Map<String, DSLib.DataType> frameSchema;

    @Override
    public List<DSLib.DataType> types() {
        List<DSLib.DataType> typeList = new ArrayList<>();
        for(DSLib.DataType type:frameSchema.values()) {
            if(!typeList.contains(type))
                typeList.add(type);
        }
        return typeList;
    }

    @Override
    public List<String> columnNames() {
        return new ArrayList<String>(frameSchema.keySet());
    }

    @Override
    public Map<String, DSLib.DataType> getMap() {
        return frameSchema;
    }

    @Override
    public DSLib.DataType type(String col) {
        if(!frameSchema.containsKey(col))
            return DSLib.DataType.INVALID;
        return frameSchema.get(col);
    }

    @Override
    public String getName() {
        return schemaName;
    }

    @Override
    public boolean contains(String column) {
        return frameSchema.containsKey(column);
    }

    public  DFrameSchemaImpl(String name, Map<String, DSLib.DataType> schema) {
        schemaName = name;
        frameSchema = schema;
    }

}
