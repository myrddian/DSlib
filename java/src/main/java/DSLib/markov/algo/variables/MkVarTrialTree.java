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

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package DSLib.markov.algo.variables;

import DSLib.markov.algo.TrialTree;
import DSLib.markov.datatypes.variables.MkVarNode;

import java.util.List;

public class MkVarTrialTree implements TrialTree<MkVarNode> {
    @Override
    public double getCondProb(List<MkVarNode> condition) {
        long successFreq = 0l;
        for(List<MkVarNode> trial: trialResults ) {
            if(match(trial, condition)) {
                successFreq++;
            }
         }
        return Double.valueOf(Double.valueOf(successFreq) / Double.valueOf(getTrials()));
    }

    private boolean match(List<MkVarNode> trial, List<MkVarNode> condition) {
        if(condition.size() > trialSize) {
            return false;
        }
        for(int counter = 0; counter < condition.size(); ++counter ) {
            String trialNodeVal = trial.get(counter).getValue();
            String conditionNodeVal = condition.get(counter).getValue();
            if(!(trialNodeVal.equals(conditionNodeVal))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getTrials() {
        return trialResults.size();
    }

    @Override
    public int getTreeSize() {
        return trialSize;
    }

    public void addTrials(List<List<MkVarNode>> results, int size) {
        trialResults = results;
        trialSize = size;
    }

    private List<List<MkVarNode>> trialResults;
    private int trialSize;
}
