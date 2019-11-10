package dslib.dataframe;

public interface DQueryJoin {
    DQueryJoin on(String fieldOne, String fieldTwo);
    DQuery end();
}
