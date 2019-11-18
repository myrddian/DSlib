/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the Affero General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the Affero GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package dslib.dataframe;

import dslib.DSLib;
import dslib.dataframe.backend.factories.DFrameMemoryCSVFactory;
import dslib.dataframe.implementation.DQueryImpl;

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

    public static DQuery createQuery() {
        return new DQueryImpl();
    }

}
