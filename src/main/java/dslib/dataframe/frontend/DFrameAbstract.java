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

package dslib.dataframe.frontend;

import dslib.DSLib;
import dslib.dataframe.*;
import dslib.dataframe.DFrameStore;
import dslib.dataframe.backend.dataframes.*;
import dslib.dataframe.backend.dataframes.DFrameProxyFactory;
import dslib.dataframe.backend.index.*;
import dslib.dataframe.backend.datarow.*;
import dslib.dataframe.backend.store.*;
import dslib.dataframe.backend.utils.*;
import dslib.dataframe.kernels.IndexTupleResult;
import dslib.dataframe.kernels.KernelStaticInfo;
import dslib.dataframe.kernels.ParallelSortKernel;
import dslib.dataframe.transform.*;
import dslib.exec.ExecuteParallelTask;
import dslib.exec.ExecutionEngine;

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
                DRow fetchedRow = getBackStorage().getRow(indexMap);
                retVal.add(DRowFactory.getInstance().transform(fetchedRow,getSchema()).get(index));
                //retVal.add(fetchedRow.get(index));
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
                DRow fetchedRow = getBackStorage().getRow(indexMap);
                retVal.add(DRowFactory.getInstance().transform(fetchedRow,getSchema()).get(index));
               //retVal.add(fetchedRow.get(index));
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
    public DFrame apply(DFrameSchema schema) {
        DFrameProxy newFrame = DFrameProxyFactory.getInstance().create(DFrameProxyFactory.TYPE.DFRAME_SCHEMA);
        DStoreFrameProxy proxy = new DStoreFrameProxy();
        proxy.setBackFrame(this);
        newFrame.setIndex(this.getIndexProxy());
        newFrame.setSchema(schema);
        newFrame.setStore(proxy);
        return newFrame;
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
        DFrameProxy selectTransform = DFrameProxyFactory.getInstance().create(DFrameProxyFactory.TYPE.DFRAME_SCHEMA);
        selectTransform.setSchema(newSchemaBuilder.end());
        selectTransform.setStore(new DStoreFrameProxy(this));
        selectTransform.setIndex(this.getIndexProxy());
        return selectTransform;
    }

    @Override
    public DFrame sort(String column, DSLib.SortType sortType) {
        ExecutionEngine executionEngine = DSLib.getEE();
        ExecuteParallelTask sortTask = executionEngine.createParallelTasks();
        sortTask.addParam(KernelStaticInfo.sortField, column);
        sortTask.addParam(KernelStaticInfo.dataFrame, this);
        sortTask.addParam(KernelStaticInfo.sortOrder, sortType);
        sortTask.addTasks(new ParallelSortKernel());
        for(int counter=0; counter < this.size(); ++counter) {
            sortTask.schedule(counter);
        }
        sortTask.exec();
        sortTask.waitForTasks();

        MergeTupleSortResults merger = new MergeTupleSortResults();
        merger.setBaseDataFrame(this);
        merger.setDataType(getSchema().type(column));
        merger.setSortOrder(sortType);
        merger.setSortCol(column);
        for(int resSize=0; resSize < sortTask.contextSize(); ++resSize) {
            List<IndexTupleResult> resultList = (List<IndexTupleResult>)sortTask.getContext(resSize).get(KernelStaticInfo.sortedIndex);
            merger.addResultList(resultList);
        }
        DFrameIndexProxy indexProxy = new DFrameIndexProxy();
        while(!merger.isExhausted()) {
            IndexTupleResult bestTuple = merger.getNext();
            indexProxy.addMap(bestTuple.getNewIndex(), bestTuple.getOldIndex());
        }

        DFrameProxy retFrame = DFrameProxyFactory.getInstance().create(DFrameProxyFactory.TYPE.DFRAME_PROXY);
        retFrame.setIndex(indexProxy);
        retFrame.setSchema(this.getSchema());
        retFrame.setStore(new DStoreFrameProxy(this));
        return  retFrame;
    }

    @Override
    public DRow createRow() {
        return DRowFactory.getInstance().createRow(getSchema());
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
        DFrameProxy newDf = DFrameProxyFactory.getInstance().create(DFrameProxyFactory.TYPE.DFRAME_PROXY);
        newStore.setBackFrame(this);
        newStore.addRows(newRows);
        newDf.setSchema(getSchema());
        DFrameIndexImpl newIndex = new DFrameIndexImpl(size()+ newRows.size());
        newDf.setStore(newStore);
        newDf.setIndex(newIndex);
        return newDf;
    }
    @Override
    public DFrame removeRow(int location) {
        List<Integer> removeList = new ArrayList<>();
        removeList.add(location);
        return removeRow(removeList);
    }

    @Override
    public DFrame removeRow(List<Integer> locations) {
        DFrameProxy newDF = DFrameProxyFactory.getInstance().create(DFrameProxyFactory.TYPE.DFRAME_PROXY);
        newDF.setSchema(getSchema());
        newDF.setStore(new DStoreFrameProxy(this));
        DFrameIndexProxy newIndex = new DFrameIndexProxy();
        int indexRemap = 0;
        for(int counter=0; counter < size(); ++counter){
            if(!locations.contains(counter)) {
                newIndex.addMap(indexRemap,counter);
                ++indexRemap;
            }
        }
        newDF.setIndex(newIndex);
        return newDF;
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
