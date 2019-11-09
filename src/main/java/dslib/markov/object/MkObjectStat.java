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

import dslib.markov.algo.ProbResults;
import dslib.markov.algo.Stats;
import dslib.markov.algo.TrialTree;

import java.util.List;

public class MkObjectStat implements Stats<MkDocument, MkObjectCondition> {

    @Override
    public double getProbGiven(MkDocument inGraph, MkObjectCondition fromState, MkObjectCondition toState) {
        return 0;
    }

    @Override
    public double getProbGiven(MkDocument inGraph, MkObjectCondition fromState, MkObjectCondition toState, int steps) {
        return 0;
    }

    @Override
    public double getProb(MkDocument inGraph, MkObjectCondition toState, int steps) {
        return 0;
    }

    @Override
    public ProbResults<MkObjectCondition> getProbsGiven(MkDocument inGraph, MkObjectCondition fromState, int steps) {
        return null;
    }

    @Override
    public TrialTree<MkObjectCondition> createTrialTree(MkDocument inGraph, int size) {
        List<MkObject> trials = inGraph.generateSamples(size);
        TrialTree retVal = new MkObjectTrialTree(trials,inGraph);
        return retVal;
    }
}
