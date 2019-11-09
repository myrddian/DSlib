package dslib.dataframe.backend;

import dslib.DSLib;
import dslib.dataframe.DFrame;
import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DFrameStore;
import dslib.dataframe.frontend.DFrameAbstract;
import dslib.dataframe.transform.DFrameIndex;
import dslib.dataframe.transform.DFrameIndexProxy;

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
    public DFrame apply(DFrameSchema schema) {
        DFrameStringImpl newFrame = new DFrameStringImpl();
        DStoreFrameProxy proxy = new DStoreFrameProxy();
        proxy.setBackFrame(this);
        newFrame.store = proxy;
        newFrame.schema = schema;
        DFrameIndexProxy indexProxy = new DFrameIndexProxy();
        int currentIndex = 0;
        for(int index=0; index < store.size(); ++index) {
            if(DSLib.validRow(store.getRow(index), schema)) {
                indexProxy.addMap(currentIndex,index);
                ++currentIndex;
            }
        }
        newFrame.index = indexProxy;
        return newFrame;
    }

    @Override
    public DFrameSchema getSchema() {
        return schema;
    }

}
