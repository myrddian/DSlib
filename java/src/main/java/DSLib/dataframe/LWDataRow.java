package DSLib.dataframe;

import java.util.List;

public interface LWDataRow {
    String get(String index);
    LWDataRow insertCol(String rowIndex, String rowVal);
    LWDataRow modify(String rowIndex, String newVal);
    LWDataRow clone();
    List<String> getColumns();
}
