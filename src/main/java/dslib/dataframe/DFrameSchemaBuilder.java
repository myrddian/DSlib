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

package dslib.dataframe;

import dslib.DSLib;
import dslib.dataframe.implementation.DFrameSchemaImpl;

import java.util.HashMap;
import java.util.Map;

public class DFrameSchemaBuilder {

    public static DFrameSchemaBuilder createSchema(String schemaName) {
        return new DFrameSchemaBuilder(schemaName);
    }

    public DFrameSchemaBuilder defineColumn(String colName, DSLib.DataType dataType) {
        schema.put(colName, dataType);
        return this;
    }

    public void modifyExisting(DFrameSchema oldSchema) {
        schema.putAll(oldSchema.getMap());
    }

    public DFrameSchema end() {
        DFrameSchemaImpl returnVal = new DFrameSchemaImpl(schemaName,schema);
        return returnVal;
    }

    private DFrameSchemaBuilder(String schemaName) {
        this.schemaName = schemaName;
    }

    private String schemaName;
    private Map<String, DSLib.DataType> schema = new HashMap<>();

}
