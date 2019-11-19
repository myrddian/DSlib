package dslib.dataframe.backend.datarow;

import dslib.dataframe.*;

public class DRowFactory {
    public static synchronized DRowFactory getInstance(){
        if(instance == null){
            instance = new DRowFactory();
        }
        return instance;
    }

    private DRowFactory() {}
    private static DRowFactory instance;

    public DRow transform(DRow original, DFrameSchema targetSchema) {
        return transform((DRowImplAbstract)original,targetSchema);
    }

    public DRow transform(DRowImplAbstract original, DFrameSchema targetSchema) {
        return original.apply(targetSchema);
    }

    public DRow createRow(DFrameSchema targetSchema) {
        DRowGenericInMem newRow = new DRowGenericInMem();
        newRow.setRowSchema(targetSchema);
        return newRow;
    }

}
