package dslib.dataframe.kernels;

import dslib.DSLib;
import dslib.dataframe.DFrame;
import dslib.exec.ExecTask;
import dslib.exec.ExecutionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParallelSortKernel implements ExecTask {

    @Override
    public void run(ExecutionContext context) {
        DFrame dataFrame = (DFrame) context.get(KernelStaticInfo.dataFrame);
        String sortField = (String) context.get(KernelStaticInfo.sortField);
        DSLib.SortType sortType = (DSLib.SortType) context.get(KernelStaticInfo.sortOrder);
        List<Object> indexList = context.getSchedule();
        List<IndexTupleResult> indexTupleResults;
        if(dataFrame.getSchema().type(sortField) == DSLib.DataType.FLOAT) {
            indexTupleResults = processDouble(indexList,dataFrame, sortField, sortType);
        }
        else if(dataFrame.getSchema().type(sortField) == DSLib.DataType.INTEGER) {
            indexTupleResults = processInt(indexList,dataFrame, sortField, sortType);
        }
        else {
            indexTupleResults = processString(indexList,dataFrame, sortField, sortType);
        }
        context.set(KernelStaticInfo.sortedIndex, indexTupleResults);
    }

    private List<IndexTupleResult> processString(List<Object> indexList, DFrame referenceFrame, String sortField, DSLib.SortType type) {
        List<IndexValueStringTuple> valueStringTuples = new ArrayList<>();
        for(Object index: indexList) {
            int idx = (Integer)index;
            String value = referenceFrame.loc(idx).get(sortField);
            IndexValueStringTuple tuple = new IndexValueStringTuple(idx,value);
            valueStringTuples.add(tuple);
        }
        if(DSLib.SortType.ASC == type) {
            Collections.sort(valueStringTuples);
        }
        else {
            Collections.sort(valueStringTuples, Collections.reverseOrder());
        }
        int remap = 0;
        List<IndexTupleResult> returnList = new ArrayList<>();
        for(IndexValueStringTuple sorted: valueStringTuples) {
            IndexTupleResult newMap = new IndexTupleResult();
            newMap.setOldIndex(sorted.getIndex());
            newMap.setNewIndex(remap);
            ++remap;
        }
        return returnList;
    }


    private List<IndexTupleResult> processDouble(List<Object> indexList, DFrame referenceFrame, String sortField, DSLib.SortType type) {
        List<IndexValueDoubleTuple> valueDoubleTuples = new ArrayList<>();
        for(Object index: indexList) {
            int idx = (Integer)index;
            double value = referenceFrame.loc(idx).get(sortField);
            IndexValueDoubleTuple tuple = new IndexValueDoubleTuple(idx,value);
            valueDoubleTuples.add(tuple);
        }
        if(DSLib.SortType.ASC == type) {
            Collections.sort(valueDoubleTuples);
        }
        else {
            Collections.sort(valueDoubleTuples, Collections.reverseOrder());
        }
        int remap = 0;
        List<IndexTupleResult> returnList = new ArrayList<>();
        for(IndexValueDoubleTuple sorted: valueDoubleTuples) {
            IndexTupleResult newMap = new IndexTupleResult();
            newMap.setOldIndex(sorted.getIndex());
            newMap.setNewIndex(remap);
            ++remap;
        }
        return returnList;
    }

    private List<IndexTupleResult> processInt(List<Object> indexList, DFrame referenceFrame, String sortField, DSLib.SortType type) {
        List<IndexValueIntTuple> valueIntTuplesTuples = new ArrayList<>();
        for(Object index: indexList) {
            int idx = (Integer)index;
            int value = referenceFrame.loc(idx).get(sortField);
            IndexValueIntTuple tuple = new IndexValueIntTuple(idx,value);
            valueIntTuplesTuples.add(tuple);
        }
        if(DSLib.SortType.ASC == type) {
            Collections.sort(valueIntTuplesTuples);
        }
        else {
            Collections.sort(valueIntTuplesTuples, Collections.reverseOrder());
        }
        int remap = 0;
        List<IndexTupleResult> returnList = new ArrayList<>();
        for(IndexValueIntTuple sorted: valueIntTuplesTuples) {
            IndexTupleResult newMap = new IndexTupleResult();
            newMap.setOldIndex(sorted.getIndex());
            newMap.setNewIndex(remap);
            ++remap;
        }
        return returnList;
    }

}
