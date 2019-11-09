package dslib.dataframe;

import dslib.DSLib;

import java.util.List;
import java.util.Map;

public interface DFrameSchema {
    List<DSLib.DataType> types();
    List<String> columnNames();
    Map<String, DSLib.DataType> getMap();
    DSLib.DataType type(String col);
    String getName();
    boolean contains(String column);
}
