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

package dslib.dataframe.transform;

import dslib.dataframe.DFrame;
import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DFrameStore;
import dslib.dataframe.backend.DStoreFrameProxy;
import dslib.dataframe.frontend.DFrameAbstract;

public class DFrameAddRowTransform extends DFrameAbstract {

    private DFrameStore backStorage;
    private DFrameIndex index;
    private DFrameSchema schema;

    public void setBackStorage(DFrameStore backStorage) {
        this.backStorage = backStorage;
    }

    public DFrameIndex getIndex() {
        return index;
    }

    public void setIndex(DFrameIndex index) {
        this.index = index;
    }

    public void setSchema(DFrameSchema schema) {
        this.schema = schema;
    }

    @Override
    public DFrameStore getBackStorage() {
        return backStorage;
    }

    @Override
    public DFrameIndex getIndexProxy() {
        return index;
    }

    @Override
    public DFrame apply(DFrameSchema schema) {
        DFrameSelectTransform newFrame = new DFrameSelectTransform();
        DStoreFrameProxy proxy = new DStoreFrameProxy();
        proxy.setBackFrame(this);
        newFrame.setStore(this.backStorage);
        newFrame.setSchema(this.schema);
        newFrame.setIndex(this.index);
        return newFrame;
    }

    @Override
    public DFrameSchema getSchema() {
        return schema;
    }
}
