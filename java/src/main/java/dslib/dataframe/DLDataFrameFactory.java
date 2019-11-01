package dslib.dataframe;

public class DLDataFrameFactory {


    public static DLDataFrame read_csv(String csvFile) {
        var tmp = new DLDataFrameImpl();
        tmp.parseCSV(csvFile, true, CSVUtil.DEFAULT_SEPARATOR);
        return tmp;
    }

    public static DLDataFrame read_csv(String csvFile, boolean header) {
        var tmp = new DLDataFrameImpl();
        tmp.parseCSV(csvFile, header, CSVUtil.DEFAULT_SEPARATOR);
        return tmp;
    }

    public static DLDataFrame read_csv(String csvFile, boolean header, char seperator) {
        var tmp = new DLDataFrameImpl();
        tmp.parseCSV(csvFile, header, seperator);
        return tmp;
    }

}
