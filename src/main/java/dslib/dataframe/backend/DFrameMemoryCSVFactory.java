package dslib.dataframe.backend;

import dslib.DSLib;
import dslib.dataframe.DFrame;
import dslib.dataframe.DFrameSchemaBuilder;
import dslib.dataframe.transform.DFrameIndexImpl;

public class DFrameMemoryCSVFactory {

    public static DFrame ReadCSVToMemory(String csvFile, boolean header, char seperator) {
        var dataFrame = new DFrameStringImpl();
        var dataBackEnd = new DStoreStringImpl();
        dataBackEnd.parseCSV(csvFile, header, seperator);
        dataFrame.setStore(dataBackEnd);
        dataFrame.setIndex(new DFrameIndexImpl(dataBackEnd.size()));
        DFrameSchemaBuilder schemaBuilder = DFrameSchemaBuilder.createSchema("default");
        for(String colNames: dataBackEnd.getColNames()) {
            schemaBuilder.defineColumn(colNames, DSLib.DataType.STRING);
        }
        dataFrame.setSchema(schemaBuilder.end());
        return dataFrame;
    }
}
