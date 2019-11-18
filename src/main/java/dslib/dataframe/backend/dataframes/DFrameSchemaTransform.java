package dslib.dataframe.backend.dataframes;

import dslib.dataframe.*;
import dslib.dataframe.frontend.*;

public class DFrameSchemaTransform extends DFrameAbstract {

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
        int i = getIndexProxy().mapToOrigin(index);
        DFrameSchema schema = getSchema();
        DRow original = getBackStorage().getRow(i);
        return original.apply(schema);
    }

    @Override
    public DFrameSchema getSchema() {
        return schema;
    }
}
