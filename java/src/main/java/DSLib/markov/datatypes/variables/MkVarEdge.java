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

package DSLib.markov.datatypes.variables;

public class MkVarEdge {

    private long frequencyOfEdge = 0;
    private MkVarNode fromNode;
    private MkVarNode toNode;
    private String edgeGeneratedName = "";

    public void setFrequencyValues(int newVal) { frequencyOfEdge = newVal; }

    public void increment() {
        frequencyOfEdge++;
    }

    public long getFrequency() { return frequencyOfEdge; }
    public MkVarNode getFromNode() { return fromNode; }
    public MkVarNode getToNode() { return toNode; }

    public void setNodes(MkVarNode fromNode, MkVarNode toNode) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        generateName();
    }

    public double getNormalisedFreq(long globalFreq) {
        return Double.valueOf( frequencyOfEdge / globalFreq);
    }

    public String getName(){ return edgeGeneratedName; }

    private void generateName(){
        String genVals = "FROM: ";
        if(fromNode != null) {
            genVals = genVals + fromNode.getValue();
        }
        if(toNode != null ) {
            genVals = genVals + " TO: " + toNode.getValue();
        }
        edgeGeneratedName = genVals;
    }

}
