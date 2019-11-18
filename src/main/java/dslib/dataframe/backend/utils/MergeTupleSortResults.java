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

package dslib.dataframe.backend.utils;

import dslib.DSLib;
import dslib.dataframe.DFrame;
import dslib.dataframe.kernels.IndexTupleResult;


import java.util.ArrayList;
import java.util.List;

public class MergeTupleSortResults {

    private List<TupleResultBuffer> buffers = new ArrayList<>();
    private long sizeOfMerge = 0;
    private long currentPointer = 0;
    private DSLib.DataType dataType;
    private DFrame baseDataFrame;
    private DSLib.SortType sortOrder = DSLib.SortType.ASC;
    private String sortCol;

    public void setSortCol(String newCol) {
        sortCol = newCol;
    }

    public void setSortOrder(DSLib.SortType newSortOrder) {
        sortOrder = newSortOrder;
    }

    public void setDataType(DSLib.DataType newDataType) {
        dataType = newDataType;
    }

    public void setBaseDataFrame(DFrame baseData) {
        baseDataFrame = baseData;
    }

    public void addResultList(List<IndexTupleResult> results) {
            buffers.add(new TupleResultBuffer(results));
            sizeOfMerge = sizeOfMerge + results.size();
    }

    public boolean isExhausted() {
        if(currentPointer>= sizeOfMerge)
            return true;
        return false;
    }

    public IndexTupleResult getNext() {
        if(dataType == DSLib.DataType.FLOAT)
            return getMinDouble();
        else if(dataType == DSLib.DataType.INTEGER)
            return getMinLong();
        return getMinString();
    }

    private IndexTupleResult getMinString() {
        TupleResultBuffer bestCandidate = null;
        for(TupleResultBuffer buff: buffers) {
            if(!buff.isExhausted()){
                if (bestCandidate == null)
                    bestCandidate = buff;
                String bestCanLen = baseDataFrame.loc(bestCandidate.peek().getOldIndex()).get(sortCol);
                String currentBuff = baseDataFrame.loc(buff.peek().getOldIndex()).get(sortCol);
                if(sortOrder == DSLib.SortType.ASC) {
                    if (bestCanLen.length() > currentBuff.length()) {
                        bestCandidate = buff;
                    }
                }
                else {
                    if (bestCanLen.length() < currentBuff.length()) {
                        bestCandidate = buff;
                    }
                }

            }
        }
        IndexTupleResult result = bestCandidate.peek();
        bestCandidate.take();
        currentPointer++;
        return result;
    }

    private IndexTupleResult getMinDouble() {
        TupleResultBuffer bestCandidate = null;
        for(TupleResultBuffer buff: buffers) {
            if(!buff.isExhausted()){
                if (bestCandidate == null)
                    bestCandidate = buff;
                double bestCanLen = baseDataFrame.loc(bestCandidate.peek().getOldIndex()).get(sortCol);
                double currentBuff = baseDataFrame.loc(buff.peek().getOldIndex()).get(sortCol);
                if(sortOrder == DSLib.SortType.ASC) {
                    if (bestCanLen > currentBuff) {
                        bestCandidate = buff;
                    }
                }
                else {
                    if (bestCanLen < currentBuff) {
                        bestCandidate = buff;
                    }
                }
            }
        }
        IndexTupleResult result = bestCandidate.peek();
        bestCandidate.take();
        currentPointer++;
        return result;
    }

    private IndexTupleResult getMinLong() {
        TupleResultBuffer bestCandidate = null;
        for(TupleResultBuffer buff: buffers) {
            if(!buff.isExhausted()){
                if (bestCandidate == null)
                    bestCandidate = buff;
                long bestCanLen = baseDataFrame.loc(bestCandidate.peek().getOldIndex()).get(sortCol);
                long currentBuff = baseDataFrame.loc(buff.peek().getOldIndex()).get(sortCol);
                if(sortOrder == DSLib.SortType.ASC) {
                    if (bestCanLen > currentBuff) {
                        bestCandidate = buff;
                    }
                }
                else {
                    if (bestCanLen < currentBuff) {
                        bestCandidate = buff;
                    }
                }
            }
        }
        IndexTupleResult result = bestCandidate.peek();
        bestCandidate.take();
        currentPointer++;
        return result;
    }


}
