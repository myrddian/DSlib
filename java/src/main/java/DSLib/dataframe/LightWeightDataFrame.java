package DSLib.dataframe;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


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

    public LWDataRow loc(int index){
        if(rows.size() > index ){
            return rows.get(index);
        }
        return null;
    }

    public int size() {
        return rows.size();
    }

    //Returns a copy of the column in memory
    public List<String> get(String index){
        ArrayList<String> clone = new ArrayList<>();
        Iterator<String> iterator = columns.get(index).iterator();
        while (iterator.hasNext()){
            String val = (String) iterator.next();
            clone.add(val);
        }
        return  clone;
    }

    public List<Integer> getAsInt(String index){
        List<String> intervalRep = columns.get(index);
        ArrayList<Integer> retVal = new ArrayList<>();
        for(String value:intervalRep){
            int converted = Integer.valueOf(value);
            retVal.add(converted);
        }
        return retVal;
    }

    public List<Double> getAsDouble(String index){
        List<String> intervalRep = columns.get(index);
        ArrayList<Double> retVal = new ArrayList<>();
        for(String value:intervalRep){
            double converted = Double.valueOf(value);
            retVal.add(converted);
        }
        return retVal;
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
