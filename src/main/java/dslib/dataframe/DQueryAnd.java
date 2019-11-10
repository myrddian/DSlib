package dslib.dataframe;

public interface DQueryAnd {
    DQueryAnd And(String field, String value);
    DQuery end();
}
