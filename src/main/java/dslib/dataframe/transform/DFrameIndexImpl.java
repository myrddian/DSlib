package dslib.dataframe.transform;

import java.util.ArrayList;
import java.util.List;

public class DFrameIndexImpl implements DFrameIndex {

    private int indexSize;

    public DFrameIndexImpl(int size) { indexSize = size; }

    @Override
    public int mapToOrigin(int reference) {
        return reference;
    }

    @Override
    public int size() {
        return indexSize;
    }

    @Override
    public List<Integer> indexValues() {
        List<Integer> retVals = new ArrayList<>();
        for(int i=0; i < indexSize; ++i){
            retVals.add(i);
        }
        return retVals;
    }

}
