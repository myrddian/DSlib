package dslib.dataframe.backend;

import dslib.dataframe.DFrameSchema;
import dslib.dataframe.DRow;
import dslib.dataframe.frontend.DRowImplGeneric;

import java.util.*;

public class DRowImplString implements DRow {

    private HashMap<String,String> rowValues = new HashMap<>();

    public DRowImplString clone(){
        return new DRowImplString(this);
    }

    @Override
    public List<String> getColumns() {
        return new ArrayList<>(rowValues.keySet());
    }

    @Override
    public DRow apply(DFrameSchema schema) {
        DRowImplGeneric genericRow = new DRowImplGeneric();
        genericRow.setBackImplementation(this);
        genericRow.setSchema(schema);
        return genericRow;
    }

    @Override
    public DRow modify(String rowIndex, String newVal) {
        DRowImplString modified = this.clone();
        modified.rowValues.put(rowIndex,newVal);
        return modified;
    }

    @Override
    public DRow modify(String rowIndex, long newVal) {
        return modify(rowIndex, Long.toString(newVal));
    }

    @Override
    public DRow modify(String rowIndex, double newVal) {
        return modify(rowIndex, Double.toString(newVal));
    }

    @Override
    public String get(String index){
        return rowValues.get(index);
    }

    public DRowImplString(List<String> rowval, Map<Integer, String> reverDict) {
        for(int counter=0; counter < rowval.size(); ++counter) {
            rowValues.put(reverDict.get(counter), rowval.get(counter));
        }
    }

    public DRowImplString(Map<String, List<String>> columns, int location) {
        for(String colName:columns.keySet()) {
            rowValues.put(colName,columns.get(colName).get(location));
        }
    }

    public DRowImplString() {

    }

    public DRowImplString(DRowImplString orig) {
        rowValues.putAll(orig.rowValues);
    }

    public DRowImplString(DRow orig) {
        for(String key:orig.getColumns()) {
            rowValues.put(key,orig.get(key));
        }
    }

    public DRowImplString(Map<String, String> values) {
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
