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

package dslib.dataframe.backend.dataframes;

import dslib.dataframe.DFrame;
import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DFrameStore;
import dslib.dataframe.DRow;
import dslib.dataframe.backend.index.*;
import dslib.dataframe.frontend.DFrameAbstract;
import dslib.dataframe.DFrameIndex;

public class DFrameJoinProxy extends DFrameAbstract implements DFrameStore{

    private DFrame parentFrame;
    private DFrame childFrame;
    private DFrameSchema joinSchema;
    private DFrameIndexJoinProxy joinedIndex;

    @Override
    public DRow loc(int index) {
        return null;
    }

    @Override
    public DFrameStore getBackStorage() {
        return this;
    }

    @Override
    public DFrameIndex getIndexProxy() {
        return joinedIndex;
    }

    @Override
    public DFrame apply(DFrameSchema schema) {
        return null;
    }

    @Override
    public DFrameSchema getSchema() {
        return joinSchema;
    }

    @Override
    public DRow getRow(int index) {
        return null;
    }
}
