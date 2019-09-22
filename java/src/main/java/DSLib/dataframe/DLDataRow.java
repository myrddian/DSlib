package DSLib.dataframe;

import java.util.List;

public interface DLDataRow {
    String get(String index);
    DLDataRow insertCol(String rowIndex, String rowVal);
    DLDataRow modify(String rowIndex, String newVal);
    DLDataRow clone();
    List<String> getColumns();
}
