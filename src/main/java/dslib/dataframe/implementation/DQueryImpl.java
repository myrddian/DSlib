package dslib.dataframe.implementation;

import dslib.dataframe.DQuery;
import dslib.dataframe.DQueryWhere;

public class DQueryImpl implements DQuery, DQueryWhere {

    @Override
    public DQuery select(String... fields) {
        return this;
    }

    @Override
    public DQuery filter(String field, String value) {
        return this;
    }

    @Override
    public DQuery filter(String field, long value) {
        return null;
    }

    @Override
    public DQuery filter(String field, double value) {
        return null;
    }

    @Override
    public DQueryWhere where(String field, String value){
        return this;
    }

    @Override
    public DQueryWhere And(String value) {
        return this;
    }

    @Override
    public DQueryWhere Or(String value) {
        return this;
    }

    @Override
    public DQueryWhere And(String field, String value) {
        return this;
    }

    @Override
    public DQueryWhere Or(String field, String value) {
        return this;
    }

    @Override
    public DQuery end() {
        return this;
    }
}
