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

package dslib.dataframe.implementation;

import dslib.DSLib;
import dslib.dataframe.DFrame;
import dslib.dataframe.DQuery;
import dslib.dataframe.DQueryJoin;
import dslib.dataframe.DQueryWhere;

public class DQueryImpl implements DQuery{

    @Override
    public DQuery select(String... fields) {
        return null;
    }

    @Override
    public DQuery filter(String field, String value) {
        return null;
    }

    @Override
    public DQuery filter(String field, long value) {
        return null;
    }

    @Override
    public DQuery filter(String field, double value) {
        return null;
    }

    @Override
    public DQueryWhere where(String field, String value) {
        return null;
    }

    @Override
    public DQuery sort(String field, DSLib.SortType type) {
        return null;
    }

    @Override
    public DQueryJoin join(DFrame joinFrame) {
        return null;
    }

    @Override
    public DQuery end() {
        return null;
    }
}
