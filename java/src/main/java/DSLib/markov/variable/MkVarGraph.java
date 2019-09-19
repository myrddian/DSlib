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

package DSLib.markov.variable;

import java.util.*;

public class MkVarGraph {

    public static MkVarGraph createInstance() {
        return new MkVarGraph();
    }

    private List<MkVarEdge> edgeList = new ArrayList<>();
    private List<MkVarNode> startingList = new ArrayList<>();
    private Map<String, MkVarNode> nodeMap = new HashMap<>();

    public void clear() {
        edgeList.clear();
        startingList.clear();
    }

    public Map<String,MkVarNode> getNodeMap() {
        return nodeMap;
    }

    public void parseTransitionList(List<String> transitionList) {
        int listSize = transitionList.size();
        for(int counter = 1; counter < listSize; ++counter) {
            String currentNode = transitionList.get(counter);
            String prevNode = transitionList.get(counter-1);

            MkVarNode pNode, cNode;

            if(!nodeMap.containsKey(currentNode)) {
                cNode = createNode(currentNode, MkVarNode.MODE.NODE);
                nodeMap.put(currentNode,cNode);
            }
            else {
                cNode = nodeMap.get(currentNode);
            }
            if(!nodeMap.containsKey(prevNode)) {
                pNode = createNode(prevNode, MkVarNode.MODE.NODE);
                nodeMap.put(prevNode,pNode);
                pNode.increment();
            }
            else  {
                pNode = nodeMap.get(prevNode);
            }
            cNode.increment();
            MkVarEdge targetEdge = pNode.getEdge(cNode);
            if(targetEdge == null ){
                targetEdge = pNode.addEdge(cNode);
                addEdge(targetEdge);
            } else {
                targetEdge.increment();
            }
        }
    }

    public MkVarEdge createEdge(MkVarNode fromNode, MkVarNode toNode) {
        MkVarEdge retVal = new MkVarEdge();
        retVal.setNodes(fromNode,toNode);
        fromNode.addEdge(retVal);
        return retVal;
    }

    public MkVarNode createNode(String nodeName, MkVarNode.MODE mode){
        MkVarNode retVal = new MkVarNode();
        retVal.setValue(nodeName);
        retVal.setNodeMode(mode);
        return retVal;
    }

    public List<MkVarNode> getStartingList() {
        if( startingList.size() == 0 ) {
            return new ArrayList<MkVarNode>(nodeMap.values());
        }
        return startingList;
    }

    public void addEdge(MkVarEdge newEdge) {
        if(edgeList.contains(newEdge)) {
            return;
        }
        edgeList.add(newEdge);
        addNode(newEdge.getToNode());
        addNode(newEdge.getFromNode());
    }

    public MkVarNode getVarNode(String nodeName) {
        return nodeMap.get(nodeName);
    }

    public void addNode(MkVarNode newNode) {
        if(nodeMap.containsKey(newNode.getValue())) {
            return;
        }
        nodeMap.put(newNode.getValue(), newNode);
        if(newNode.getMode() == MkVarNode.MODE.START_NODE) {
            startingList.add(newNode);
        }
    }

    public List<String> edgeNames() {
        List<String> retVal = new ArrayList<>();

        for(MkVarEdge edge: edgeList) {
            retVal.add(edge.getName());
        }
        return retVal;
    }

}
