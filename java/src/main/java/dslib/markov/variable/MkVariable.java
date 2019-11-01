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

package dslib.markov.variable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MkVariable {

    private MkVarGraph graph = new MkVarGraph();
    private String variableName;
    private MkVarWalker walker = new MkVarWalker();
    private Map<String, MkVarNode> nodeMap = new HashMap<>();

    public void setVariableName(String varName) {
        variableName = varName;
    }

    public void setGraph(MkVarGraph inGraph) {
        graph = inGraph;
    }

    public String getVariableName() {
        return variableName;
    }

    public MkVarNode generateSample() {
        return walker.walkNode(graph,false,1).get(0);
    }

    public List<MkVarNode> generateSamples(int samples) {
        return walker.walkNode(graph,false,samples);
    }

    public MkVarGraph getGraph() {
        return graph;
    }

    public void createNode(String nodeName, MkVarNode.MODE mode ) {
        MkVarNode node = graph.createNode(nodeName,mode);
        graph.addNode(node);
        nodeMap.put(nodeName,node);
    }

    public MkVarNode getNode(String nodeName) {
        return nodeMap.get(nodeName);
    }

    public void addEdge(String fromNode, String toNode, int freq) {
        MkVarNode fNode = getNode(fromNode);
        MkVarNode tNode = getNode(toNode);
        graph.addEdge(fNode.addEdge(tNode,freq));
    }

    public void parseTransitionList(List<String> transitionList) {
        graph.parseTransitionList(transitionList);
        nodeMap = graph.getNodeMap();
    }

}
