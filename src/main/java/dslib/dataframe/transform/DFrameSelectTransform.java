package dslib.dataframe.transform;

import dslib.dataframe.DFrame;
import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DFrameStore;
import dslib.dataframe.backend.DStoreFrameProxy;
import dslib.dataframe.frontend.DFrameAbstract;

public class DFrameSelectTransform extends DFrameAbstract {


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
        DFrameSelectTransform newFrame = new DFrameSelectTransform();
        DStoreFrameProxy proxy = new DStoreFrameProxy();
        proxy.setBackFrame(this);
        newFrame.store = proxy;
        newFrame.schema = schema;
        newFrame.index = this.index;
        return newFrame;
    }

    @Override
    public DFrameSchema getSchema() {
        return schema;
    }
}
