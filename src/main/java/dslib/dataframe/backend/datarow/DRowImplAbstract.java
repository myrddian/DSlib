package dslib.dataframe.backend.datarow;

import dslib.dataframe.*;

public abstract class DRowImplAbstract implements DRow {
    public abstract DRow apply(DFrameSchema schema);
}
