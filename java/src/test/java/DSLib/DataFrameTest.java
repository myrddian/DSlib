package DSLib;
import org.junit.jupiter.api.Test;
import DSLib.dataframe.LightWeightDataFrame;

import java.util.List;

public class DataFrameTest {

    private  ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void loadCsv() {
        LightWeightDataFrame dataFrame = LightWeightDataFrame.read_csv(classLoader.getResource("german.data").getFile(), false, ' ');
        System.out.println(dataFrame.size());
    }

    @Test
    public void loadCsvTwo() {
        LightWeightDataFrame dataFrame = LightWeightDataFrame.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        System.out.println(dataFrame.size());
        System.out.println(dataFrame.loc(0).get("REPORT_ID"));
        List<String> testVals = dataFrame.get("REPORT_ID");
        testVals.add(0,"TEST");
        List<String> newTEst = dataFrame.get("REPORT_ID");
        String valtest = testVals.get(0)  + " versus " + newTEst.get(0);
        System.out.println(valtest);
        List<Integer> units = dataFrame.getAsInt("Total Units");
        System.out.println(units);

    }
}
