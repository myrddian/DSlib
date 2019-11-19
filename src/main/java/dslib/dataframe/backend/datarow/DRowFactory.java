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
