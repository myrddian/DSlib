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
