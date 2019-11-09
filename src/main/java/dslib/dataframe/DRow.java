package dslib.dataframe;

import java.util.List;

public interface DRow {
    DRow modify(String rowIndex, String newVal);
    DRow modify(String rowIndex, long newVal);
    DRow modify(String rowIndex, double newVal);
    List<String> getColumns();
    <Any> Any get(String index);
    DRow apply(DFrameSchema schema);
}
