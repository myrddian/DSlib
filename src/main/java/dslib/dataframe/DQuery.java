package dslib.dataframe;

public interface DQuery {

    DQuery select(String ... fields);
    DQuery filter(String field, String value);
    DQuery filter(String field, long value);
    DQuery filter(String field, double value);
    DQueryWhere where(String field, String value);
    DQuery end();
}
