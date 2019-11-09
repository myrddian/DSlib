package dslib.dataframe;

import java.util.List;

public interface DFrameStore {
    int size();
    DRow getRow(int index);
    List<String> getColNames();
}
