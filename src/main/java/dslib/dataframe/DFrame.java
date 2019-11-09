/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the Affero General Public License as published by
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
