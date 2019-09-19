package DSLib;
import DSLib.dataframe.LWDataRow;
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

    @Test
    public void cloneTest() {
        LightWeightDataFrame dataFrame = LightWeightDataFrame.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        System.out.println(dataFrame.size());
        System.out.println(dataFrame.loc(0).get("REPORT_ID"));
        LightWeightDataFrame newDataFrame = dataFrame.clone();
        LightWeightDataFrame newColumn =  newDataFrame.addColumn("TEST");
        System.out.println(newColumn.get("TEST"));
        System.out.println(dataFrame.isFactor("REPORT_ID"));
        System.out.println(dataFrame.isFactor("Position Type"));
        System.out.println(dataFrame.isFactor("Position Type",20));
        LightWeightDataFrame germanData = LightWeightDataFrame.read_csv(classLoader.getResource("german.data").getFile(), false, ' ');
        System.out.println(germanData.isFactor(0,20));

    }

    @Test
    public void selectTest() {
        LightWeightDataFrame dataFrame = LightWeightDataFrame.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        LightWeightDataFrame adelaideData = dataFrame.select("Suburb", "ADELAIDE");
        LightWeightDataFrame reducedFields = dataFrame.select("Suburb", "ADELAIDE", "Total Units");
        String [] fields = {"Suburb","Total Units"};
        LightWeightDataFrame windowTest = dataFrame.window(fields);
        System.out.println(adelaideData.size());
        System.out.println(reducedFields.columns());
        System.out.println(windowTest.columns());
        LWDataRow newRow = reducedFields.createRow();
        newRow = newRow.modify("Total Units","12");
        LightWeightDataFrame newRowFrame = reducedFields.addRow(newRow);
        System.out.println(reducedFields.size());
        System.out.println(newRowFrame.size());
    }
}
