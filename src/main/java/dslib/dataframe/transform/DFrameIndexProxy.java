package dslib.dataframe.transform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFrameIndexProxy implements DFrameIndex {

    private Map<Integer, Integer> indexMap = new HashMap<>();

    @Override
    public int mapToOrigin(int reference) {
        return indexMap.get(reference);
    }
    @Override
    public int size() {
        return indexMap.size();
    }

    @Override
    public List<Integer> indexValues() {
        return new ArrayList<Integer>(indexMap.values());
    }

    public void addMap(int reference, int origin) {
        indexMap.put(reference,origin);
    }
    public DFrameIndexProxy(Map<Integer, Integer> bulkIndex) {
        indexMap.putAll(bulkIndex);
    }
    public DFrameIndexProxy(){}
}
