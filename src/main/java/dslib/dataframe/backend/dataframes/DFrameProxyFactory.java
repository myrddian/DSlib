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

package dslib.dataframe.backend.dataframes;


public class DFrameProxyFactory {

    public enum TYPE { DFRAME_PROXY, DFRAME_SCHEMA, DFRAME_SELECT, DFRAME_ADD_ROW}

    public DFrameProxy create(TYPE frameType) {
        switch (frameType) {
            case DFRAME_SELECT:
            case DFRAME_PROXY:
            case DFRAME_ADD_ROW:
                return new DFrameProxy();
            case DFRAME_SCHEMA:
                return new DFrameSchemaTransform();
        }
        return null;
    }

    public static synchronized DFrameProxyFactory getInstance(){
        if(instance == null){
            instance = new DFrameProxyFactory();
        }
        return instance;
    }

    private DFrameProxyFactory() {}
    private static DFrameProxyFactory instance;

}
