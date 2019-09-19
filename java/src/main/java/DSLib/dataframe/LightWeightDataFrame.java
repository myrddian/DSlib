package DSLib.dataframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


public class LightWeightDataFrame {

    //Instance methods

    public static LightWeightDataFrame read_csv(String csvFile) {
        var tmp = new LightWeightDataFrame();
        tmp.parseCSV(csvFile, true, CSVUtil.DEFAULT_SEPARATOR);
        return tmp;
    }

    public static LightWeightDataFrame read_csv(String csvFile, boolean header) {
        var tmp = new LightWeightDataFrame();
        tmp.parseCSV(csvFile, header, CSVUtil.DEFAULT_SEPARATOR);
        return tmp;
    }

    public static LightWeightDataFrame read_csv(String csvFile, boolean header, char seperator) {
        var tmp = new LightWeightDataFrame();
        tmp.parseCSV(csvFile, header, seperator);
        return tmp;
    }

    //Getter/setters

    public boolean skipInvalid() {
        return skipBad;
    }

    public void setSkipFlag(boolean newFlag) {
        skipBad = newFlag;
    }

    public int getMaxFactorLevel() {
        return maxFactorLevel;
    }

    public void setMaxFactorLevel(int newMaxFactor) {
        maxFactorLevel = newMaxFactor;
    }

    //Main functionality


    public LightWeightDataFrame window(String [] fields) {
        LightWeightDataFrame newDataFrame = new LightWeightDataFrame();
        for(LWDataRowImpl row: rows) {
            LWDataRowImpl newRow = new LWDataRowImpl();
            for (String matchedField: fields) {
                newRow.insertData(matchedField,row.get(matchedField));
            }
            newDataFrame.rows.add(newRow);
        }
        newDataFrame.reparseColumns(fields);
        return newDataFrame;
    }

    public LightWeightDataFrame select(String index, String value) {
        return select(index, value,new ArrayList<String>(columns.keySet()));
    }

    public LightWeightDataFrame select(String index, String filterValue, List<String> fields) {
        List<String> addString = new ArrayList<>();
        addString.add(filterValue);
        return select(index, addString, fields);
    }

    public LightWeightDataFrame select(String index, String filterValue, String field) {
        List<String> addString = new ArrayList<>();
        List<String> addFields = new ArrayList<>();
        addString.add(filterValue);
        addFields.add(field);
        return select(index, addString, addFields);
    }

    public LightWeightDataFrame select(String index, String [] filterValue, String [] fields){
        ArrayList<String> fieldList = Arrays.stream(fields).collect(Collectors.toCollection(ArrayList::new));
        return select(index, filterValue, fields);
    }

    public LightWeightDataFrame select(String index, String [] filterValue, List<String> fields){
        ArrayList<String> filterList = Arrays.stream(filterValue).collect(Collectors.toCollection(ArrayList::new));
        return select(index, filterList, fields );
    }

    public LightWeightDataFrame select(String index, List<String> filterValue, List<String> fields) {
        LightWeightDataFrame newDataFrame = new LightWeightDataFrame();
        for(LWDataRowImpl row: rows) {
            if(filterValue.contains(row.get(index))){
                LWDataRowImpl newRow = new LWDataRowImpl();
                for (String matchedField: fields) {
                    newRow.insertData(matchedField,row.get(matchedField));
                }
                newDataFrame.rows.add(newRow);
            }
        }
        newDataFrame.reparseColumns(fields.toArray(new String[fields.size()]));
        return newDataFrame;
    }

    public LWDataRow createRow() {
        LWDataRowImpl newRow = new LWDataRowImpl();
        newRow.insertColumns(columns.keySet());
        return newRow;
    }

    public LightWeightDataFrame addRow(LWDataRow newRow) {
        LightWeightDataFrame newDataFrame = this.clone();
        if(!validCols(columns.keySet(),newRow.getColumns())) {
            return null;
        }
        newDataFrame.rows.add(new LWDataRowImpl(newRow));
        newDataFrame.reparseColumns(columns.keySet().toArray(new String[columns()]));
        return newDataFrame;
    }

    public LightWeightDataFrame addRow(Map<String, String> newRow) {
        LightWeightDataFrame newDataFrame = this.clone();
        if(!validCols(columns.keySet(),newRow.keySet())) {
            return null;
        }
        newDataFrame.rows.add(new LWDataRowImpl(newRow));
        newDataFrame.reparseColumns(columns.keySet().toArray(new String[columns()]));
        return newDataFrame;
    }

    public List<String> getColNames() {
        ArrayList<String> retcolNames = new ArrayList<>();
        retcolNames.addAll(columns.keySet());
        return retcolNames;
    }

    public List<String> getFactors(String index) {
        return getFactors(index,this.maxFactorLevel);
    }

    public List<String> getFactors(String index, int maxFactor) {

        if(!isFactor(index,maxFactor)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(aggregateColumn(index).keySet());
    }

    public Map<String, Integer> getFactorFrequency(int index){
        return getFactorFrequency(reverseMap.get(index),maxFactorLevel);
    }

    public Map<String, Integer> getFactorFrequency(int index, int maxFactor){
        return getFactorFrequency(reverseMap.get(index),maxFactor);
    }

    public Map<String, Integer> getFactorFrequency(String index){
        return getFactorFrequency(index,maxFactorLevel);
    }

    public Map<String, Integer> getFactorFrequency(String index, int maxFactor) {
        if(!isFactor(index,maxFactor)) {
            return null;
        }
        return aggregateColumn(index);
    }

    public List<String> getFactors(int index) {
        return getFactors(reverseMap.get(index), maxFactorLevel);
    }

    public List<String> getFactors(int index, int maxFactor) {
        return getFactors(reverseMap.get(index), maxFactor);
    }


    public boolean isFactor(int index) {
        return isFactor(reverseMap.get(index), maxFactorLevel);
    }

    public boolean isFactor(int index, int maxFactor) {
        return isFactor(reverseMap.get(index), maxFactor);
    }

    public boolean isFactor(String index) {
        return isFactor(index, maxFactorLevel);
    }

    public boolean isFactor(String index, int maxFactor) {
        if(aggregateColumn(index).size() > maxFactor) {
            return false;
        }
        return true;
    }

    public LWDataRow loc(int index){
        if(rows.size() > index ){
            return rows.get(index);
        }
        return null;
    }

    public int size() {
        return rows.size();
    }

    public int columns(){
        return reverseMap.keySet().size();
    }

    public LightWeightDataFrame addColumn(String columnName) {
        if(columnName.length() == 0 ) {
            return null;
        }
        if(columns.containsKey(columnName)){
            return null;
        }
        LightWeightDataFrame newDataFrame = this.clone();
        newDataFrame.columns.put(columnName, new ArrayList<>());
        newDataFrame.reverseMap.put(columns() +1, columnName);
        for(int counter=0; counter < size(); ++counter) {
            newDataFrame.columns.get(columnName).add("");
        }
        newDataFrame.reparseRows();
        return newDataFrame;
    }

    public LightWeightDataFrame addColumn(LWDataColumn newColumn) {
        if (newColumn.size() != this.size()) {
            return null;
        }
        if(columns.containsKey(newColumn.name())){
            return null;
        }
        LightWeightDataFrame newDataFrame = this.clone();
        newDataFrame.columns.put(newColumn.name(), new ArrayList<>());
        List<String> values = newColumn.getStrings();
        newDataFrame.columns.get(newColumn.name()).addAll(values);
        newDataFrame.reverseMap.put(columns() +1, newColumn.name());
        newDataFrame.reparseRows();
        return newDataFrame;
    }

    //Returns a copy of the column in memory
    public LWDataColumn<String> get(String index){
        LWDataColumn<String> retVal = new LWDataColumn<>(index,columns.get(index));
        return retVal;
    }

    public LWDataColumn<String> get(int index){
       return get(reverseMap.get(index));
    }


    public LWDataColumn<Integer> getAsInt(String index){
        List<String> intervalRep = columns.get(index);
        ArrayList<Integer> retVal = new ArrayList<>();
        for(String value:intervalRep){
            if(validInt(value) && skipBad ) {
                int converted = Integer.valueOf(value);
                retVal.add(converted);
            }
        }
        return new LWDataColumn<>(index,retVal);
    }

    public LWDataColumn<Double> getAsDouble(String index){
        List<String> intervalRep = columns.get(index);
        ArrayList<Double> retVal = new ArrayList<>();
        for(String value:intervalRep){
            if(validDouble(value) && skipBad ) {
                double converted = Double.valueOf(value);
                retVal.add(converted);
            }
        }
        return new LWDataColumn<>(index,retVal);
    }

    public LightWeightDataFrame clone(boolean deep) {
        LightWeightDataFrame newdf = new LightWeightDataFrame();
        //Copy the stuff over
        newdf.skipBad = skipBad;
        newdf.maxFactorLevel = maxFactorLevel;

        if(deep) {
            for(Integer key: reverseMap.keySet()) {
                newdf.reverseMap.put(key, reverseMap.get(key));
                newdf.columns.put(reverseMap.get(key),new ArrayList<>());
            }
            for(int counter=0 ; counter < this.size(); ++counter) {
                LWDataRowImpl newRow = (LWDataRowImpl) this.loc(counter).clone();
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

    public LightWeightDataFrame clone() {
        return clone(false);
    }


    //Private Methods

    private ArrayList<LWDataRowImpl> rows = new ArrayList<>();
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
            rows.add(new LWDataRowImpl(columns,counter));
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

        for(LWDataRowImpl row:rows) {
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

    private void parseCSV(String csvFile, boolean header, char seperator){

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
                LWDataRowImpl dataRow = new LWDataRowImpl(row, reverseMap);
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
