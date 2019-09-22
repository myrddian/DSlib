package DSLib.dataframe;

import java.util.*;

class DLDataRowImpl implements DLDataRow {

    private HashMap<String,String> rowValues = new HashMap<>();


    @Override
    public DLDataRowImpl clone(){
        return new DLDataRowImpl(this);
    }

    @Override
    public List<String> getColumns() {
        return new ArrayList<>(rowValues.keySet());
    }

    @Override
    public DLDataRowImpl insertCol(String rowIndex, String rowVal) {
        DLDataRowImpl modified = this.clone();
        modified.rowValues.put(rowIndex,rowVal);
        return modified;
    }

    @Override
    public DLDataRow modify(String rowIndex, String newVal) {
        DLDataRowImpl modified = this.clone();
        modified.rowValues.put(rowIndex,newVal);
        return modified;
    }

    @Override
    public String get(String index){
        return rowValues.get(index);
    }

    public DLDataRowImpl(List<String> rowval, Map<Integer, String> reverDict) {
        for(int counter=0; counter < rowval.size(); ++counter) {
            rowValues.put(reverDict.get(counter), rowval.get(counter));
        }
    }

    public DLDataRowImpl(Map<String, List<String>> columns, int location) {
        for(String colName:columns.keySet()) {
            rowValues.put(colName,columns.get(colName).get(location));
        }
    }

    public DLDataRowImpl() {

    }

    DLDataRowImpl(DLDataRowImpl orig) {
        rowValues.putAll(orig.rowValues);
    }

    DLDataRowImpl(DLDataRow orig) {
        for(String key:orig.getColumns()) {
            rowValues.put(key,orig.get(key));
        }
    }

    public DLDataRowImpl(Map<String, String> values) {
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
