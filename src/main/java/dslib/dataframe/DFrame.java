package dslib.dataframe;

import dslib.dataframe.transform.DFrameColumnTransform;

import java.util.List;
import java.util.Map;

public interface DFrame {

    //DFrame main functionality
    int size();
    int columns();
    DRow loc(int index);

    int getMaxFactorLevel();
    void setMaxFactorLevel(int newMaxFactor);
    boolean isFactor(String index);
    boolean isFactor(String index, int maxFactor);

    List<String> getFactors(String index);
    List<String> getFactors(String index, int maxFactor);

    Map<String, Long> getFactorFrequency(String index);
    Map<String, Long> getFactorFrequency(String index, int maxFactor);

    //Returns a copy of the column in memory
    DColumn<String> get(String index);
    DColumn<Long> getAsInt(String index);
    DColumn<Double> getAsDouble(String index);
    List<String> getColNames();

    DFrame apply(DQuery query);
    DFrame apply(DFrameColumnTransform columnTransform);
    DFrame apply(DFrameSchema schema);
    DFrame select(String ... fields);

    DRow createRow();
    DFrame addRow(DRow newRow);
    DFrame addRows(List<DRow> newRows);
    DFrameSchema getSchema();

}
