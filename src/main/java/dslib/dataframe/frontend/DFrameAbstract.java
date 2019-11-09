package dslib.dataframe.frontend;

import dslib.DSLib;
import dslib.dataframe.*;
import dslib.dataframe.DFrameStore;
import dslib.dataframe.backend.DFrameAddRowTransformStore;
import dslib.dataframe.backend.DStoreFrameProxy;
import dslib.dataframe.transform.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DFrameAbstract implements DFrame {

    private int factorLevel = 10;

    public abstract DFrameStore getBackStorage();
    public abstract DFrameIndex getIndexProxy();
    public abstract DFrameSchema getSchema();

    @Override
    public int size() {
        return getIndexProxy().size();
    }

    @Override
    public int columns() {
        return getSchema().columnNames().size();
    }

    @Override
    public DRow loc(int index) {
        int i = getIndexProxy().mapToOrigin(index);
        DFrameSchema schema = getSchema();
        DRow original = getBackStorage().getRow(i);
        return original.apply(schema);
    }

    @Override
    public int getMaxFactorLevel() {
        return factorLevel;
    }

    @Override
    public void setMaxFactorLevel(int newMaxFactor) {
        factorLevel = newMaxFactor;
    }

    @Override
    public boolean isFactor(String index) {
        return isFactor(index, factorLevel);
    }

    @Override
    public boolean isFactor(String index, int maxFactor) {
        if(aggregateColumn(index).size() > maxFactor) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getFactors(String index) {
        return getFactors(index,factorLevel);
    }

    @Override
    public List<String> getFactors(String index, int maxFactor) {
        if(!isFactor(index,maxFactor)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(aggregateColumn(index).keySet());
    }

    @Override
    public Map<String, Long> getFactorFrequency(String index) {
        return getFactorFrequency(index,factorLevel);
    }

    @Override
    public Map<String, Long> getFactorFrequency(String index, int maxFactor) {
        if(!isFactor(index,maxFactor)) {
            return null;
        }
        return aggregateColumn(index);
    }

    @Override
    public DColumn<String> get(String index) {
        DColumn<String> retVal = new DColumn<>(index);
        List<Integer> indexValues = getIndexProxy().indexValues();
        for(int indexMap: indexValues) {
            retVal.add(getBackStorage().getRow(indexMap).get(index));
        }
        return retVal;
    }

    @Override
    public DColumn<Long> getAsInt(String index) {
        DColumn<Long> retVal = new DColumn<>(index);
        if(getSchema().type(index) == DSLib.DataType.INTEGER) {
            List<Integer> indexValues = getIndexProxy().indexValues();
            for(int indexMap: indexValues) {
                DRow fetchedRow = getBackStorage().getRow(indexMap).apply(getSchema());
                retVal.add(fetchedRow.get(index));
            }
        }
        return retVal;
    }

    @Override
    public DColumn<Double> getAsDouble(String index) {
        DColumn<Double> retVal = new DColumn<>(index);
        if(getSchema().type(index) == DSLib.DataType.FLOAT) {
            List<Integer> indexValues = getIndexProxy().indexValues();
            for(int indexMap: indexValues) {
                DRow fetchedRow = getBackStorage().getRow(indexMap).apply(getSchema());
                retVal.add(fetchedRow.get(index));
            }
        }
        return retVal;
    }

    @Override
    public List<String> getColNames() {
        return getSchema().columnNames();
    }

    @Override
    public DFrame apply(DQuery query) {
        return null;
    }

    @Override
    public DFrame apply(DFrameColumnTransform columnTransform) {
        return null;
    }

    @Override
    public DFrame select(String... fields) {
        DFrameSchemaBuilder newSchemaBuilder = DFrameSchemaBuilder.createSchema(this.getSchema().getName());
        for(String fieldSelection: fields){
            if(getSchema().type(fieldSelection) == DSLib.DataType.INVALID) {
                return null;
            }
            newSchemaBuilder.defineColumn(fieldSelection,getSchema().type(fieldSelection));

        }
        DFrameSelectTransform selectTransform = new DFrameSelectTransform();
        selectTransform.setSchema(this.getSchema());
        selectTransform.setStore(new DStoreFrameProxy(this));
        selectTransform.setIndex(this.getIndexProxy());
        return selectTransform.apply(newSchemaBuilder.end());
    }

    @Override
    public DRow createRow() {
        return null;
    }

    @Override
    public DFrame addRow(DRow newRow) {
        List<DRow> nRow = new ArrayList<>();
        nRow.add(newRow);
        return addRows(nRow);
    }

    @Override
    public DFrame addRows(List<DRow> newRows) {
        DFrameAddRowTransformStore newStore = new DFrameAddRowTransformStore();
        DFrameAddRowTransform newDf = new DFrameAddRowTransform();
        newStore.setBackFrame(this);
        newStore.addRows(newRows);
        newDf.setSchema(getSchema());
        DFrameIndexImpl newIndex = new DFrameIndexImpl(size()+ newRows.size());
        newDf.setBackStorage(newStore);
        newDf.setIndex(newIndex);
        return newDf;
    }


    private Map<String, Long> aggregateColumn(String index) {
        HashMap<String, Long> tableTrack = new HashMap<>();
        List<Integer> indexValues = getIndexProxy().indexValues();
        for(int indexMap: indexValues){
            DRow row = getBackStorage().getRow(indexMap);
            String value = row.get(index);
            if(!tableTrack.containsKey(value)){
                tableTrack.put(value, 1l);
            }
            else {
                long val = tableTrack.get(value);
                tableTrack.put(value,val + 1);
            }
        }
        return tableTrack;
    }

}