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

package dslib.dataframe.backend;

import dslib.DSLib;
import dslib.dataframe.DFrame;
import dslib.dataframe.DFrameSchemaBuilder;
import dslib.dataframe.transform.DFrameIndexImpl;

public class DFrameMemoryCSVFactory {

    public static DFrame ReadCSVToMemory(String csvFile, boolean header, char seperator) {
        var dataFrame = new DFrameStringImpl();
        var dataBackEnd = new DStoreStringImpl();
        dataBackEnd.parseCSV(csvFile, header, seperator);
        dataFrame.setStore(dataBackEnd);
        dataFrame.setIndex(new DFrameIndexImpl(dataBackEnd.size()));
        DFrameSchemaBuilder schemaBuilder = DFrameSchemaBuilder.createSchema("default");
        for(String colNames: dataBackEnd.getColNames()) {
            schemaBuilder.defineColumn(colNames, DSLib.DataType.STRING);
        }
        dataFrame.setSchema(schemaBuilder.end());
        return dataFrame;
    }
}
