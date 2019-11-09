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
