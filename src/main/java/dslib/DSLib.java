package dslib;

import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DRow;

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

}
