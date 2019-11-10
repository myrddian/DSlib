/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU Affero General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the Affero GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package dslib;

import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DRow;
import dslib.exec.ExecutionEngine;

public class DSLib {

    public enum DataType {FLOAT, INTEGER, STRING, FACTOR, NA, INVALID}
    public static final char DEFAULT_SEPARATOR = ',';
    public static final char DEFAULT_QUOTE = '"';
    public enum FileMode {MEMORY, OFF_DISK}

    public static boolean validInt(String item) {
        try {
            Long.valueOf(item);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean validDouble(String item) {
        try {
            Double.valueOf(item);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean validRow(DRow testRow, DFrameSchema testSchema) {
        for(String col: testRow.getColumns()) {
            DSLib.DataType colType = testSchema.type(col);
            if(colType == DataType.INTEGER) {
                if(!validInt(testRow.get(col))) {
                    return false;
                }
            } else if (colType == DataType.FLOAT) {
                if(!validDouble(testRow.get(col))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static ExecutionEngine getEE() {
        return ExecutionEngine.getInstance();
    }

}
