package dslib.dataframe;

import dslib.DSLib;
import dslib.dataframe.backend.DFrameMemoryCSVFactory;
import dslib.dataframe.backend.DStoreStringImpl;

import java.util.Collection;

public class DFrameFactory {

    public static DFrame read_csv(String csvFile) {
        return read_csv(csvFile,true);
    }

    public static DFrame read_csv(String csvFile, boolean header) {
        return read_csv(csvFile, header, DSLib.DEFAULT_SEPARATOR);
    }

    public static DFrame read_csv(String csvFile, boolean header, char seperator) {
        return read_csv(csvFile, DSLib.FileMode.MEMORY, header, seperator);
    }


    public static DFrame read_csv(String csvFile, DSLib.FileMode mode, boolean header, char seperator) {
        if(mode == DSLib.FileMode.MEMORY) {
            return DFrameMemoryCSVFactory.ReadCSVToMemory(csvFile,header,seperator);
        }
        return null;
    }

}
