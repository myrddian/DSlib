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

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MkVarNode {

    private String varValue;
    private long varFreq = 0l;
    List<MkVarEdge> edgesOut = new Vector<>();
    Map<MkVarNode, MkVarEdge> nodeEdge = new Hashtable<>();
    private MODE mode = MODE.NODE;

    public enum MODE {START_NODE, END_NODE, NODE }

    public long getFrequency() { return varFreq;}

    public void increment() { varFreq++; }
    public String getValue() { return varValue; }
    public MODE getMode() { return mode; }
    public void setNodeMode(MODE newMode) { mode = newMode; }
    public void setValue(String newValue) { varValue = newValue; }
    public void setFrequency(long newFreq) { varFreq = newFreq; }
    public List<MkVarEdge> getEdgesOut() { return edgesOut; }

    public void addEdge( MkVarEdge newEdge ) {

        if(nodeEdge.containsKey(newEdge)) {
            return;
        }

        if(edgesOut.contains(newEdge)) {
            return;
        }
        edgesOut.add(newEdge);
        nodeEdge.put(newEdge.getToNode(), newEdge);
    }

    public MkVarEdge addEdge(MkVarNode toNode, int varFreq) {
        MkVarEdge newEdge = new MkVarEdge();
        newEdge.setNodes(this,toNode);
        newEdge.setFrequencyValues(varFreq);
        addEdge(newEdge);
        return newEdge;
    }

    public MkVarEdge addEdge(MkVarNode toNode) {
        if(nodeEdge.containsKey(toNode)) {
            nodeEdge.get(toNode).increment();
            return nodeEdge.get(toNode);
        }
        return addEdge(toNode,1);
    }

    public MkVarEdge getEdge(MkVarNode toNode) {
        if(nodeEdge.containsKey(toNode)) {
            return nodeEdge.get(toNode);
        }
        return null;
    }

    public long getTotalEdgeFrequency() {
        long retVal = 0;
        for(MkVarEdge edge: edgesOut ){
            retVal = retVal + edge.getFrequency();
        }
        return retVal;
    }

}
