package DSLib.dataframe;

import DSLib.exec.ExecuteParallelTask;
import DSLib.exec.ExecutionEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


public class DLDataFrameImpl implements DLDataFrame {

    //Getter/setters

    @Override
    public boolean skipInvalid() {
        return skipBad;
    }

    @Override
    public void setSkipFlag(boolean newFlag) {
        skipBad = newFlag;
    }

    @Override
    public int getMaxFactorLevel() {
        return maxFactorLevel;
    }

    @Override
    public void setMaxFactorLevel(int newMaxFactor) {
        maxFactorLevel = newMaxFactor;
    }

    //Main functionality


    @Override
    public DLDataFrame window(String[] fields) {
        DLDataFrameImpl newDataFrame = new DLDataFrameImpl();
        for(DLDataRowImpl row: rows) {
            DLDataRowImpl newRow = new DLDataRowImpl();
            for (String matchedField: fields) {
                newRow.insertData(matchedField,row.get(matchedField));
            }
            newDataFrame.rows.add(newRow);
        }
        newDataFrame.reparseColumns(fields);
        return newDataFrame;
    }

    @Override
    public DLDataFrame select(String index, String value) {
        return select(index, value,new ArrayList<String>(columns.keySet()));
    }

    @Override
    public DLDataFrame select(String index, String filterValue, List<String> fields) {
        List<String> addString = new ArrayList<>();
        addString.add(filterValue);
        return select(index, addString, fields);
    }

    @Override
    public DLDataFrame select(String index, String filterValue, String field) {
        List<String> addString = new ArrayList<>();
        List<String> addFields = new ArrayList<>();
        addString.add(filterValue);
        addFields.add(field);
        return select(index, addString, addFields);
    }

    @Override
    public DLDataFrame select(String index, String[] filterValue, String[] fields){
        ArrayList<String> fieldList = Arrays.stream(fields).collect(Collectors.toCollection(ArrayList::new));
        return select(index, filterValue, fields);
    }

    @Override
    public DLDataFrame select(String index, String[] filterValue, List<String> fields){
        ArrayList<String> filterList = Arrays.stream(filterValue).collect(Collectors.toCollection(ArrayList::new));
        return select(index, filterList, fields );
    }



    @Override
    public DLDataFrame select(String index, List<String> filterValue, List<String> fields) {
        /*DLDataFrameImpl newDataFrame = new DLDataFrameImpl();
        for(DLDataRowImpl row: rows) {
            if(filterValue.contains(row.get(index))){
                DLDataRowImpl newRow = new DLDataRowImpl();
                for (String matchedField: fields) {
                    newRow.insertData(matchedField,row.get(matchedField));
                }
                newDataFrame.rows.add(newRow);
            }
        }
        newDataFrame.reparseColumns(fields.toArray(new String[fields.size()]));
        return newDataFrame;*/
       return parallelSelect(index,filterValue,fields);
    }

    @Override
    public DLDataRow createRow() {
        DLDataRowImpl newRow = new DLDataRowImpl();
        newRow.insertColumns(columns.keySet());
        return newRow;
    }

    @Override
    public DLDataFrame addRow(DLDataRow newRow) {
        DLDataFrameImpl newDataFrame = this.clone();
        if(!validCols(columns.keySet(),newRow.getColumns())) {
            return null;
        }
        newDataFrame.rows.add(new DLDataRowImpl(newRow));
        newDataFrame.reparseColumns(columns.keySet().toArray(new String[columns()]));
        return newDataFrame;
    }

    @Override
    public DLDataFrame addRow(Map<String, String> newRow) {
        DLDataFrameImpl newDataFrame = this.clone();
        if(!validCols(columns.keySet(),newRow.keySet())) {
            return null;
        }
        newDataFrame.rows.add(new DLDataRowImpl(newRow));
        newDataFrame.reparseColumns(columns.keySet().toArray(new String[columns()]));
        return newDataFrame;
    }

    @Override
    public List<String> getColNames() {
        ArrayList<String> retcolNames = new ArrayList<>();
        retcolNames.addAll(columns.keySet());
        return retcolNames;
    }

    @Override
    public List<String> getFactors(String index) {
        return getFactors(index,this.maxFactorLevel);
    }

    @Override
    public List<String> getFactors(String index, int maxFactor) {

        if(!isFactor(index,maxFactor)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(aggregateColumn(index).keySet());
    }

    @Override
    public Map<String, Integer> getFactorFrequency(int index){
        return getFactorFrequency(reverseMap.get(index),maxFactorLevel);
    }

    @Override
    public Map<String, Integer> getFactorFrequency(int index, int maxFactor){
        return getFactorFrequency(reverseMap.get(index),maxFactor);
    }

    @Override
    public Map<String, Integer> getFactorFrequency(String index){
        return getFactorFrequency(index,maxFactorLevel);
    }

    @Override
    public Map<String, Integer> getFactorFrequency(String index, int maxFactor) {
        if(!isFactor(index,maxFactor)) {
            return null;
        }
        return aggregateColumn(index);
    }

    @Override
    public List<String> getFactors(int index) {
        return getFactors(reverseMap.get(index), maxFactorLevel);
    }

    @Override
    public List<String> getFactors(int index, int maxFactor) {
        return getFactors(reverseMap.get(index), maxFactor);
    }


    @Override
    public boolean isFactor(int index) {
        return isFactor(reverseMap.get(index), maxFactorLevel);
    }

    @Override
    public boolean isFactor(int index, int maxFactor) {
        return isFactor(reverseMap.get(index), maxFactor);
    }

    @Override
    public boolean isFactor(String index) {
        return isFactor(index, maxFactorLevel);
    }

    @Override
    public boolean isFactor(String index, int maxFactor) {
        if(aggregateColumn(index).size() > maxFactor) {
            return false;
        }
        return true;
    }

    @Override
    public DLDataRow loc(int index){
        if(rows.size() > index ){
            return rows.get(index);
        }
        return null;
    }

    @Override
    public int size() {
        return rows.size();
    }

    @Override
    public int columns(){
        return reverseMap.keySet().size();
    }

    @Override
    public DLDataFrame addColumn(String columnName) {
        if(columnName.length() == 0 ) {
            return null;
        }
        if(columns.containsKey(columnName)){
            return null;
        }
        DLDataFrameImpl newDataFrame = this.clone();
        newDataFrame.columns.put(columnName, new ArrayList<>());
        newDataFrame.reverseMap.put(columns() +1, columnName);
        for(int counter=0; counter < size(); ++counter) {
            newDataFrame.columns.get(columnName).add("");
        }
        newDataFrame.reparseRows();
        return newDataFrame;
    }

    @Override
    public DLDataFrame addColumn(DLDataColumn newColumn) {
        if (newColumn.size() != this.size()) {
            return null;
        }
        if(columns.containsKey(newColumn.name())){
            return null;
        }
        DLDataFrameImpl newDataFrame = this.clone();
        newDataFrame.columns.put(newColumn.name(), new ArrayList<>());
        List<String> values = newColumn.getStrings();
        newDataFrame.columns.get(newColumn.name()).addAll(values);
        newDataFrame.reverseMap.put(columns() +1, newColumn.name());
        newDataFrame.reparseRows();
        return newDataFrame;
    }

    //Returns a copy of the column in memory
    @Override
    public DLDataColumn<String> get(String index){
        DLDataColumn<String> retVal = new DLDataColumn<>(index,columns.get(index));
        return retVal;
    }

    @Override
    public DLDataColumn<String> get(int index){
       return get(reverseMap.get(index));
    }


    @Override
    public DLDataColumn<Integer> getAsInt(String index){
        List<String> intervalRep = columns.get(index);
        ArrayList<Integer> retVal = new ArrayList<>();
        for(String value:intervalRep){
            if(validInt(value) && skipBad ) {
                int converted = Integer.valueOf(value);
                retVal.add(converted);
            }
        }
        return new DLDataColumn<>(index,retVal);
    }

    @Override
    public DLDataColumn<Double> getAsDouble(String index){
        List<String> intervalRep = columns.get(index);
        ArrayList<Double> retVal = new ArrayList<>();
        for(String value:intervalRep){
            if(validDouble(value) && skipBad ) {
                double converted = Double.valueOf(value);
                retVal.add(converted);
            }
        }
        return new DLDataColumn<>(index,retVal);
    }

    @Override
    public DLDataFrameImpl clone(boolean deep) {
        DLDataFrameImpl newdf = new DLDataFrameImpl();
        //Copy the stuff over
        newdf.skipBad = skipBad;
        newdf.maxFactorLevel = maxFactorLevel;

        if(deep) {
            for(Integer key: reverseMap.keySet()) {
                newdf.reverseMap.put(key, reverseMap.get(key));
                newdf.columns.put(reverseMap.get(key),new ArrayList<>());
            }
            for(int counter=0 ; counter < this.size(); ++counter) {
                DLDataRowImpl newRow = (DLDataRowImpl) this.loc(counter).clone();
                for(String key: columns.keySet()) {
                    newdf.columns.get(key).add(newRow.get(key));
                }
                newdf.rows.add(newRow);
            }
        }
        else {
            newdf.reverseMap.putAll(reverseMap);
            newdf.columns.putAll(columns);
            newdf.rows.addAll(rows);
        }
        return newdf;
    }

    @Override
    public DLDataFrameImpl clone() {
        return clone(false);
    }


    //Private Methods

    private ArrayList<DLDataRowImpl> rows = new ArrayList<>();
    private HashMap<String, List<String>> columns = new HashMap<>();
    private HashMap<Integer, String> reverseMap = new HashMap<>();
    private boolean skipBad = true;
    private int maxFactorLevel = 10;


    private boolean validCols(Collection<String> colNames, Collection<String> other) {
        if(other.size() < columns() || other.size()  > columns()) {
            return false;
        }
        for(String key:colNames) {
            if(!other.contains(key)) {
                return false;
            }
        }
        return true;
    }

    private void reparseRows() {
        int origSize = size();
        rows.clear();
        for(int counter=0; counter < origSize; ++counter) {
            rows.add(new DLDataRowImpl(columns,counter));
        }
    }

    private void reparseColumns(String [] fields) {
        int colId = 0;
        columns.clear();
        reverseMap.clear();
        for(String key:fields) {
            columns.put(key, new ArrayList<>());
            reverseMap.put(colId, key);
            colId++;
        }

        for(DLDataRowImpl row:rows) {
            for(String colKey:columns.keySet()) {
                columns.get(colKey).add(row.get(colKey));
            }
        }

    }

    private boolean validInt(String item) {
        try {
            Integer.valueOf(item);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private boolean validDouble(String item) {
        try {
            Double.valueOf(item);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    private Map<String, Integer> aggregateColumn(String index) {
        HashMap<String, Integer> tableTrack = new HashMap<>();
        for(String colVal:columns.get(index)) {
            if(!tableTrack.containsKey(colVal)) {
                tableTrack.put(colVal, 1);
            }
            else {
                int val = tableTrack.get(colVal);
                tableTrack.put(colVal,val + 1);
            }

        }
        return tableTrack;
    }

    private DLDataFrame parallelSelect(String index, List<String> filterValue, List<String> fields) {
        ExecuteParallelTask parallelTask = ExecutionEngine.getInstance().createParallelTasks();
        parallelTask.addParam("index", index);
        parallelTask.addParam("filterValues", filterValue);
        parallelTask.addParam("fields", fields);
        parallelTask.addTasks(new ParallelFilterKernel());
        for(DLDataRowImpl row: rows) {
            parallelTask.schedule(row);
        }
        parallelTask.exec();
        parallelTask.waitForTasks();
        DLDataFrameImpl newDataFrame = new DLDataFrameImpl();
        int outputSize = parallelTask.outputSize();
        for(int counter=0 ; counter < outputSize; ++counter) {
            newDataFrame.rows.add((DLDataRowImpl)parallelTask.getOutputItem());
        }
        newDataFrame.reparseColumns(fields.toArray(new String[fields.size()]));
        return newDataFrame;
    }

    public void parseCSV(String csvFile, boolean header, char seperator){

        rows.clear();
        columns.clear();
        reverseMap.clear();

        int lineCount = 0;
        try(Scanner scanner = new Scanner(new File(csvFile))) {
            while (scanner.hasNext()) {
                List<String> row = CSVUtil.parseLine(scanner.nextLine(), seperator);
                if(lineCount == 0 ){
                    if(header == true ) {
                        int row_count = 0;
                        for(String header_name: row){
                            String newHeader = header_name.replaceAll("^\"+|\"+$", "").trim();
                            reverseMap.put(row_count,newHeader);
                            columns.put(newHeader, new ArrayList<>());
                            row_count++;
                        }
                        ++lineCount;
                        continue;
                    }
                    else {
                        //We have to generate a default header name
                        for(int counter = 0; counter < row.size(); ++counter){
                            String colName = "column-" + Integer.toString(counter);
                            reverseMap.put(counter,colName);
                            columns.put(colName, new ArrayList<>());
                        }
                    }
                }
                DLDataRowImpl dataRow = new DLDataRowImpl(row, reverseMap);
                rows.add(dataRow);
                //Add the column
                for(int counter=0; counter < row.size(); ++counter) {
                    List<String> col = columns.get(reverseMap.get(counter));
                    col.add(row.get(counter));
                }
                ++lineCount;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
