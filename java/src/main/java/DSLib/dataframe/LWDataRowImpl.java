package DSLib.dataframe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LWDataRowImpl implements LWDataRow {

    private HashMap<String,String> rowValues = new HashMap<>();

    public String get(String index){
        return rowValues.get(index);
    }

    public LWDataRowImpl(List<String> rowval, Map<Integer, String> reverDict) {
        for(int counter=0; counter < rowval.size(); ++counter) {
            rowValues.put(reverDict.get(counter), rowval.get(counter));
        }
    }


}
