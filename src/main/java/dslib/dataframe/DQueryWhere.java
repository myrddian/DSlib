package dslib.dataframe;

public interface DQueryWhere {
    DQueryWhere And(String value);
    DQueryWhere Or(String value);
    DQueryWhere And(String field, String value);
    DQueryWhere Or(String field, String value);
    DQuery end();
}
