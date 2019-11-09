package dslib.dataframe.transform;

import java.util.List;

public interface DFrameIndex {
    int mapToOrigin(int reference);
    int size();
    List<Integer> indexValues();
}
