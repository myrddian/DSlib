package DSLib.dataframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


public class LightWeightDataFrame {

    public static LightWeightDataFrame read_csv(String csvFile) {
        var tmp = new LightWeightDataFrame();
        tmp.parseCSV(csvFile);
        return tmp;
    }

    public static LightWeightDataFrame read_csv(String csvFile, boolean header) {
        var tmp = new LightWeightDataFrame();
        tmp.parseCSV(csvFile, header);
        return tmp;
    }

    public static LightWeightDataFrame read_csv(String csvFile, boolean header, char seperator) {
        var tmp = new LightWeightDataFrame();
        tmp.parseCSV(csvFile, header, seperator);
        return tmp;
    }

    private ArrayList<LWDataRow> rows = new ArrayList<>();
    private HashMap<String, List<String>> columns = new HashMap<>();
    private HashMap<Integer, String> reverseMap = new HashMap<>();
    private boolean skipBad = true;

    public LWDataRow loc(int index){
        if(rows.size() > index ){
            return rows.get(index);
        }
        return null;
    }

    public int size() {
        return rows.size();
    }

    public boolean skipInvalid() {
        return skipBad;
    }

    public void setSkipFlag(boolean newFlag) {
        skipBad = newFlag;
    }

    //Returns a copy of the column in memory
    public LWDataColumn<String> get(String index){
        LWDataColumn<String> retVal = new LWDataColumn<>(index,columns.get(index));
        return retVal;
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



    public void parseCSV(String csvFile) {
        parseCSV(csvFile,true, CSVUtil.DEFAULT_SEPARATOR);
    }

    public void parseCSV(String csvFile, boolean header) {
        parseCSV(csvFile,header, CSVUtil.DEFAULT_SEPARATOR);
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
