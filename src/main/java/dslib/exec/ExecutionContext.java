/*      DSLib - Collection of Data Science Libraries
        Copyright (C) 2019  Enzo Reyes

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the Affero GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package dslib.exec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ExecutionContext {

    List<Object> scheduledObjects = new ArrayList<>();
    Map<String, Object> parameters = new HashMap<>();

    public BlockingQueue getOutputList() {
        return outputList;
    }

    public void setOutputList(BlockingQueue outputList) {
        this.outputList = outputList;
    }

    BlockingQueue outputList;

    public List<Object> getSchedule() {
        return scheduledObjects;
    }

    public void set(String key, Object value) {
        parameters.put(key,value);
    }

    public Object get(String key) {
        if(parameters.containsKey(key)) {
            return parameters.get(key);
        }
        return null;
    }

    public void addResult(Object res) {
        outputList.add(res);
    }


    public void setScheduledObjects(List<Object> objects) {
        scheduledObjects.addAll(objects);
    }

    public void setParameters(Map<String,  Object> params) {
        parameters.putAll(params);
    }

}
