package dslib.dataframe;

import dslib.DSLib;
import dslib.dataframe.implementation.DFrameSchemaImpl;

import java.util.HashMap;
import java.util.Map;

public class DFrameSchemaBuilder {

    public static DFrameSchemaBuilder createSchema(String schemaName) {
        return new DFrameSchemaBuilder(schemaName);
    }

    public DFrameSchemaBuilder defineColumn(String colName, DSLib.DataType dataType) {
        schema.put(colName, dataType);
        return this;
    }

    public void modifyExisting(DFrameSchema oldSchema) {
        schema.putAll(oldSchema.getMap());
    }

    public DFrameSchema end() {
        DFrameSchemaImpl returnVal = new DFrameSchemaImpl(schemaName,schema);
        return returnVal;
    }

    private DFrameSchemaBuilder(String schemaName) {
        this.schemaName = schemaName;
    }

    private String schemaName;
    private Map<String, DSLib.DataType> schema = new HashMap<>();

}
