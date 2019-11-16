
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
package dslib.dataframe.kernels;

public class IndexValueStringTuple implements Comparable<IndexValueStringTuple> {
    private int index;
    private String value;

    public IndexValueStringTuple(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() { return index; }
    public String getValue() { return value; }

    @Override
    public int compareTo(IndexValueStringTuple o) {
        return this.value.length() - o.value.length();
    }
}
