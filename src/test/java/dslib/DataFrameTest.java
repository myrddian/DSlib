package dslib;

import dslib.dataframe.*;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DataFrameTest {

    private  ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void loadCsv() {
        DFrame dataFrame = DFrameFactory.read_csv(classLoader.getResource("german.data").getFile(), false, ' ');
        System.out.println(dataFrame.size());
    }

    @Test
    public void loadCsvTwo() {
        DFrame dataFrame = DFrameFactory.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        System.out.println(dataFrame.size());
        System.out.println((String)dataFrame.loc(0).get("REPORT_ID"));
        List<String> testVals = dataFrame.get("REPORT_ID");
        testVals.add(0,"TEST");
        List<String> newTEst = dataFrame.get("REPORT_ID");
        String valtest = testVals.get(0)  + " versus " + newTEst.get(0);
        System.out.println(valtest);
        List<Long> units = dataFrame.getAsInt("Total Units");
        System.out.println(units.size());
    }

    @Test
    public void schemaTest() {
        DFrame dataFrame = DFrameFactory.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        System.out.println(dataFrame.size());
        DFrameSchemaBuilder modifySchema = DFrameSchemaBuilder.createSchema("Crash_Schema");
        modifySchema.modifyExisting(dataFrame.getSchema());
        modifySchema.defineColumn("Area Speed", DSLib.DataType.INTEGER);
        modifySchema.defineColumn("ACCLOC_X", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("ACCLOC_Y", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("Total Units", DSLib.DataType.INTEGER);
        DFrame schemaFrame = dataFrame.apply(modifySchema.end());
        System.out.println(schemaFrame.size());
        System.out.println((Long)schemaFrame.loc(1).get("Total Units"));
        List<Long> units = schemaFrame.getAsInt("Total Units");
        System.out.println(units.size());
    }

    @Test
    public void selectTest() {
        DFrame dataFrame = DFrameFactory.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        DFrameSchemaBuilder modifySchema = DFrameSchemaBuilder.createSchema("Crash_Schema");
        modifySchema.modifyExisting(dataFrame.getSchema());
        modifySchema.defineColumn("Area Speed", DSLib.DataType.INTEGER);
        modifySchema.defineColumn("ACCLOC_X", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("ACCLOC_Y", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("Total Units", DSLib.DataType.INTEGER);
        DFrame crashFrame = dataFrame.apply(modifySchema.end());
        DFrame adelaideData = crashFrame.select("Suburb", "Area Speed");
        DFrame reducedFields = crashFrame.select("Suburb", "Month", "Total Units");
        long monthTest = reducedFields.loc(0).get("Total Units");
        double accloc = crashFrame.loc(0).get("ACCLOC_X");
        System.out.println(crashFrame.size());
        System.out.println(adelaideData.size());
        System.out.println(reducedFields.columns());
        System.out.println(monthTest);
        System.out.println(accloc);
    }

    @Test
    public void sortTest() {
        DFrame dataFrame = DFrameFactory.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        DFrameSchemaBuilder modifySchema = DFrameSchemaBuilder.createSchema("Crash_Schema");
        modifySchema.modifyExisting(dataFrame.getSchema());
        modifySchema.defineColumn("Area Speed", DSLib.DataType.INTEGER);
        modifySchema.defineColumn("ACCLOC_X", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("ACCLOC_Y", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("Total Units", DSLib.DataType.INTEGER);
        DFrame crashFrame = dataFrame.apply(modifySchema.end());
        DFrame sorted = crashFrame.sort("Total Units", DSLib.SortType.ASC);
        long totalUnit = sorted.loc(0).get("Total Units");
        System.out.println(totalUnit);
        DFrame desSort = crashFrame.sort("Total Units", DSLib.SortType.DESC);
        totalUnit = desSort.loc(0).get("Total Units");
        System.out.println(totalUnit);
    }

    @Test
    public void modifyTest() {
        DFrame dataFrame = DFrameFactory.read_csv(classLoader.getResource("2018_DATA_SA_Crash.csv").getFile());
        DFrameSchemaBuilder modifySchema = DFrameSchemaBuilder.createSchema("Crash_Schema");
        modifySchema.modifyExisting(dataFrame.getSchema());
        modifySchema.defineColumn("Area Speed", DSLib.DataType.INTEGER);
        modifySchema.defineColumn("ACCLOC_X", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("ACCLOC_Y", DSLib.DataType.FLOAT);
        modifySchema.defineColumn("Total Units", DSLib.DataType.INTEGER);
        DFrame crashFrame = dataFrame.apply(modifySchema.end()).select("Suburb", "Area Speed");
        DRow newRow = crashFrame.createRow();
        newRow = newRow.modify("Area Speed", 80);
        newRow = newRow.modify("Suburb", "Tarneit");
        System.out.println(crashFrame.size());
        crashFrame = crashFrame.addRow(newRow);
        System.out.println(crashFrame.size());
        crashFrame = crashFrame.removeRow(0);
        System.out.println(crashFrame.size());
    }

}
