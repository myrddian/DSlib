package dslib.dataframe.backend;

import dslib.dataframe.DFrameStore;
import dslib.dataframe.DRow;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class DStoreStringImpl implements DFrameStore {

    private ArrayList<DRowImplString> rows = new ArrayList<>();
    private HashMap<String, List<String>> columns = new HashMap<>();
    private HashMap<Integer, String> reverseMap = new HashMap<>();

    @Override
    public List<String> getColNames() {
        ArrayList<String> retcolNames = new ArrayList<>();
        retcolNames.addAll(columns.keySet());
        return retcolNames;
    }

    @Override
    public DRow getRow(int index){
        if(rows.size() > index ){
            return rows.get((int)index);
        }
        return null;
    }

    @Override
    public int size() {
        return rows.size();
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
                DRowImplString dataRow = new DRowImplString(row, reverseMap);
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

            /*public DFrame window(String[] fields) {

    }*/
   /*@Override
    public List<String> getFactors(String index) {
        return getFactors(index,this.maxFactorLevel);
    }*/


    /*@Override
    public DFrame addRow(DRow newRow) {
        DFrameImplString newDataFrame = this.clone();
        if(!validCols(columns.keySet(),newRow.getColumns())) {
            return null;
        }
        newDataFrame.rows.add(new DRowImplString(newRow));
        newDataFrame.reparseColumns(columns.keySet().toArray(new String[columns()]));
        return newDataFrame;
    }*/

    /*public DFrame select(String index, List<String> filterValue, List<String> fields) {
       return parallelSelect(index,filterValue,fields);
    }*/

    /*public DFrame addColumn(DColumn newColumn) {
        if (newColumn.size() != this.size()) {
            return null;
        }
        if(columns.containsKey(newColumn.name())){
            return null;
        }
        DFrameImplString newDataFrame = this.clone();
        newDataFrame.columns.put(newColumn.name(), new ArrayList<>());
        List<String> values = newColumn.getStrings();
        newDataFrame.columns.get(newColumn.name()).addAll(values);
        newDataFrame.reverseMap.put(columns() +1, newColumn.name());
        newDataFrame.reparseRows();
        return newDataFrame;
    }*/

    /*private DFrame parallelSelect(String index, List<String> filterValue, List<String> fields) {
        ExecuteParallelTask parallelTask = ExecutionEngine.getInstance().createParallelTasks();
        parallelTask.addParam("index", index);
        parallelTask.addParam("filterValues", filterValue);
        parallelTask.addParam("fields", fields);
        parallelTask.addTasks(new ParallelFilterKernel());
        for(DRowImplString row: rows) {
            parallelTask.schedule(row);
        }
        parallelTask.exec();
        parallelTask.waitForTasks();
        DFrameImplString newDataFrame = new DFrameImplString();
        int outputSize = parallelTask.outputSize();
        for(int counter=0 ; counter < outputSize; ++counter) {
            newDataFrame.rows.add((DRowImplString)parallelTask.getOutputItem());
        }
        newDataFrame.reparseColumns(fields.toArray(new String[fields.size()]));
        return newDataFrame;
    }*/


}
