package DSLib.dataframe;

import java.util.*;

class LWDataRowImpl implements LWDataRow {

    private HashMap<String,String> rowValues = new HashMap<>();


    @Override
    public LWDataRowImpl clone(){
        return new LWDataRowImpl(this);
    }

    @Override
    public List<String> getColumns() {
        return new ArrayList<>(rowValues.keySet());
    }

    @Override
    public LWDataRowImpl insertCol(String rowIndex, String rowVal) {
        LWDataRowImpl modified = this.clone();
        modified.rowValues.put(rowIndex,rowVal);
        return modified;
    }

    @Override
    public LWDataRow modify(String rowIndex, String newVal) {
        LWDataRowImpl modified = this.clone();
        modified.rowValues.put(rowIndex,newVal);
        return modified;
    }

    @Override
    public String get(String index){
        return rowValues.get(index);
    }

    public LWDataRowImpl(List<String> rowval, Map<Integer, String> reverDict) {
        for(int counter=0; counter < rowval.size(); ++counter) {
            rowValues.put(reverDict.get(counter), rowval.get(counter));
        }
    }

    public LWDataRowImpl(Map<String, List<String>> columns, int location) {
        for(String colName:columns.keySet()) {
            rowValues.put(colName,columns.get(colName).get(location));
        }
    }

    public LWDataRowImpl() {

    }

    LWDataRowImpl(LWDataRowImpl orig) {
        rowValues.putAll(orig.rowValues);
    }

    LWDataRowImpl(LWDataRow orig) {
        for(String key:orig.getColumns()) {
            rowValues.put(key,orig.get(key));
        }
    }

    public LWDataRowImpl(Map<String, String> values) {
        rowValues.putAll(values);
    }


    public void insertData(String rowIndex, String rowVal){
        rowValues.put(rowIndex,rowVal);
    }

    public void insertColumns(Collection<String> columns) {
        for(String key:columns) {
            rowValues.put(key,"");
        }
    }

}
