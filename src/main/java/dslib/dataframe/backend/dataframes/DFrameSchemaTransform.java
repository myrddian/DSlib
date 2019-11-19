package dslib.dataframe.backend.dataframes;

import dslib.dataframe.*;
import dslib.dataframe.backend.datarow.*;
import dslib.dataframe.frontend.*;

public class DFrameSchemaTransform extends DFrameProxy {
    @Override
    public DRow loc(int index) {
        int i = getIndexProxy().mapToOrigin(index);
        DFrameSchema schema = getSchema();
        DRow original = getBackStorage().getRow(i);
        return DRowFactory.getInstance().transform((DRowImplAbstract) original, schema);
    }
}
