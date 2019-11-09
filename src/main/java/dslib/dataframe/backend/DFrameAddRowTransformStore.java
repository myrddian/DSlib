package dslib.dataframe.backend;

import dslib.dataframe.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DFrameAddRowTransformStore implements DFrameStore {

    private DFrame backFrame;
    private Map<Integer, DRow> addedLines = new HashMap<>();

    public void setBackFrame(DFrame backFrame) {
        this.backFrame = backFrame;
    }

    public void addRows(List<DRow> rows) {
        int startingNumber = backFrame.size();
        for(DRow newRow: rows) {
            addedLines.put(startingNumber, newRow);
            ++startingNumber;
        }
    }

    @Override
    public int size() {
        return backFrame.size() + addedLines.size();
    }

    @Override
    public DRow getRow(int index) {
        if(addedLines.containsKey(index)) {
            return addedLines.get(index);
        }
        if(index < backFrame.size()) {
            return backFrame.loc(index);
        }
        return null;
    }

    @Override
    public List<String> getColNames() {
        return backFrame.getColNames();
    }
}
