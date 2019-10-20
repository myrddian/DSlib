package DSLib.dataframe;

import java.util.List;
import java.util.Map;

public interface DLDataFrame {

    enum TYPES {FACTOR, REAL, STRING, INTEGER }

    boolean skipInvalid();

    void setSkipFlag(boolean newFlag);

    int getMaxFactorLevel();

    void setMaxFactorLevel(int newMaxFactor);

    DLDataFrame window(String[] fields);

    DLDataFrame select(String index, String value);

    DLDataFrame select(String index, String filterValue, List<String> fields);

    DLDataFrame select(String index, String filterValue, String field);

    DLDataFrame select(String index, String[] filterValue, String[] fields);

    DLDataFrame select(String index, String[] filterValue, List<String> fields);

    DLDataFrame select(String index, List<String> filterValue, List<String> fields);

    DLDataRow createRow();

    DLDataFrame addRow(DLDataRow newRow);

    DLDataFrame addRow(Map<String, String> newRow);

    List<String> getColNames();

    List<String> getFactors(String index);

    List<String> getFactors(String index, int maxFactor);

    Map<String, Integer> getFactorFrequency(int index);

    Map<String, Integer> getFactorFrequency(int index, int maxFactor);

    Map<String, Integer> getFactorFrequency(String index);

    Map<String, Integer> getFactorFrequency(String index, int maxFactor);

    List<String> getFactors(int index);

    List<String> getFactors(int index, int maxFactor);

    boolean isFactor(int index);

    boolean isFactor(int index, int maxFactor);

    boolean isFactor(String index);

    boolean isFactor(String index, int maxFactor);

    DLDataRow loc(int index);

    int size();

    int columns();

    DLDataFrame addColumn(String columnName);

    DLDataFrame addColumn(DLDataColumn newColumn);

    //Returns a copy of the column in memory
    DLDataColumn<String> get(String index);

    DLDataColumn<String> get(int index);

    DLDataColumn<Integer> getAsInt(String index);

    DLDataColumn<Double> getAsDouble(String index);

    DLDataFrame clone(boolean deep);

    DLDataFrame clone();
}
