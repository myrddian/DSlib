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

package dslib.markov.object;

import dslib.markov.algo.TrialTree;

import java.util.List;

public class MkObjectTrialTree implements TrialTree<MkObjectCondition> {

    private List<MkObject> objectList;
    private MkDocument mkDocument;

    public MkObjectTrialTree(List<MkObject> samples, MkDocument schema) {
        objectList = samples;
        mkDocument = schema;
    }

    private boolean match(MkObject obj, List<MkObjectCondition> conditions) {
        for(MkObjectCondition cond: conditions){
            if(!cond.conditionSatisfied(obj)){
                return false;
            }
        }
        return true;
    }

    @Override
    public double getCondProb(List<MkObjectCondition> conditions) {
        long success = 0;
        for(MkObject testedObject: objectList) {
            if(match(testedObject, conditions)) {
                success++;
            }
        }
        return Double.valueOf(Double.valueOf(success) / Double.valueOf(getTrials()));
    }

    @Override
    public int getTrials() {
        return objectList.size();
    }

    @Override
    public int getTreeSize() {
        return mkDocument.schemaSize();
    }

}
