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

package dslib.dataframe.backend.datarow;

import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DRow;

import javax.xml.validation.Schema;
import java.util.List;

public class DRowJoinProxy extends DRowImplAbstract {

    private Schema joinSchema;
    private DRow   frameOne;
    private DRow   frameTwo;

    public void setJoinRow(DRow parentFrame, DRow childFrame) {
        frameOne = parentFrame;
        frameTwo = childFrame;
    }

    @Override
    public DRow modify(String rowIndex, String newVal) {
        return null;
    }

    @Override
    public DRow modify(String rowIndex, long newVal) {
        return null;
    }

    @Override
    public DRow modify(String rowIndex, double newVal) {
        return null;
    }

    @Override
    public List<String> getColumns() {
        return null;
    }

    @Override
    public <Any> Any get(String index) {
        return null;
    }

    @Override
    public DRow apply(DFrameSchema schema) {
        return null;
    }
}
