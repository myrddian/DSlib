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

package dslib.dataframe.backend;

import dslib.dataframe.*;

import java.util.List;

public class DStoreFrameProxy implements DFrameStore {

    private DFrame backFrame;

    @Override
    public int size() {
        return backFrame.size();
    }


    @Override
    public DRow getRow(int index) {
        DRow tmp = backFrame.loc(index);
        return  tmp;
    }

    @Override
    public List<String> getColNames() {
        return backFrame.getColNames();
    }

    public void setBackFrame(DFrame backFrame) {
        this.backFrame = backFrame;
    }
    public DStoreFrameProxy() {}
    public DStoreFrameProxy(DFrame newBackFrame) {backFrame = newBackFrame;}

}
