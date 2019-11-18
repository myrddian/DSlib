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

import dslib.DSLib;
import dslib.dataframe.*;
import dslib.dataframe.backend.store.*;
import dslib.dataframe.frontend.DFrameAbstract;
import dslib.dataframe.backend.index.DFrameIndexProxy;

public class DFrameStringImpl extends DFrameAbstract {

    public void setStore(DFrameStore store) {
        this.store = store;
    }

    public void setIndex(DFrameIndex index) {
        this.index = index;
    }

    public void setSchema(DFrameSchema schema) {
        this.schema = schema;
    }

    private DFrameStore store;
    private DFrameIndex index;
    private DFrameSchema schema;

    @Override
    public DFrameStore getBackStorage() {
        return store;
    }

    @Override
    public DFrameIndex getIndexProxy() {
        return index;
    }

    @Override
    public DRow loc(int index) {
        return store.getRow(this.index.mapToOrigin(index));
    }

    @Override
    public DFrame apply(DFrameSchema schema) {
        DFrameSchemaTransform newFrame = new DFrameSchemaTransform();
        DStoreFrameProxy proxy = new DStoreFrameProxy();
        proxy.setBackFrame(this);
        newFrame.setStore(proxy);
        newFrame.setSchema(schema);
        DFrameIndexProxy indexProxy = new DFrameIndexProxy();
        int currentIndex = 0;
        for(int index=0; index < store.size(); ++index) {
            if(DSLib.validRow(store.getRow(index), schema)) {
                indexProxy.addMap(currentIndex,index);
                ++currentIndex;
            }
        }
        newFrame.setIndex(indexProxy);
        return newFrame;
    }

    @Override
    public DFrameSchema getSchema() {
        return schema;
    }

}
