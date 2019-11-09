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

package dslib.markov.variable;

import java.util.Hashtable;
import java.util.Map;

import dslib.markov.algo.ProbResults;

public class MkVarProbRes implements ProbResults<MkVarNode> {


    private Map<String, MkVarNode> nodeMap = new Hashtable<>();
    private Map<String, Double> results = new Hashtable<>();
    private int runs = 100;

    @Override
    public MkVarNode getNode(String nodeName) {
        return nodeMap.get(nodeName);
    }

    public void addNode(MkVarNode targetNode, long freq) {
        results.put(targetNode.getValue(), Double.valueOf(freq)/ Double.valueOf(runs));
        nodeMap.put(targetNode.getValue(), targetNode);
    }

    @Override
    public double getProb(MkVarNode targetNode) {
       return results.get(targetNode.getValue());
    }


    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }
}
