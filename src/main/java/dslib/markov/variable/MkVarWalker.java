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


package dslib.markov.variable;

import dslib.markov.algo.Walk;

import java.util.List;
import java.util.Vector;

public class MkVarWalker implements Walk<MkVarGraph, MkVarNode> {


    @Override
    public List<String> walk(MkVarGraph graph, int iterations) {

        MkVarPicker picker = new MkVarPicker();
        List<String> retList = new Vector<>();
        List<MkVarNode> startingList = graph.getStartingList();
        MkVarNode startingNode = picker.pickNode(startingList);
        retList.add(startingNode.getValue());
        MkVarNode pickedNode = startingNode;

        for(int counter=0; counter < iterations-1; ++counter) {
            pickedNode = picker.pickNode(pickedNode);
            retList.add(pickedNode.getValue());
        }

        return retList;
    }

    @Override
    public List<MkVarNode> walkNode(MkVarGraph inGraph, int walkCount) {
        return walkNode(inGraph,false,walkCount);
    }

    @Override
    public List<MkVarNode> walkNode(MkVarGraph inGraph, boolean unique, int walkCount) {
        MkVarPicker picker = new MkVarPicker();
        List<MkVarNode> startingList = inGraph.getStartingList();
        MkVarNode startingNode = picker.pickNode(startingList);
        return walkNode(inGraph, startingNode,unique, walkCount);
    }

    @Override
    public List<MkVarNode> walkNode(MkVarGraph inGraph, MkVarNode startingNode, boolean unique, int walkCount) {
        MkVarPicker picker = new MkVarPicker();
        List<MkVarNode> retList = new Vector<>();
        retList.add(startingNode);
        MkVarNode pickedNode = startingNode;

        //Substract 1 because we have picked a starting node already
        for(int counter=0; counter < walkCount-1; ++counter) {
            if(unique) {
                MkVarNode temp = picker.pickNode(pickedNode);
                if(retList.contains(temp)) {
                    continue;
                }  else {
                    pickedNode = temp;
                    retList.add(temp);
                }
            }  else {
                pickedNode = picker.pickNode(pickedNode);
                retList.add(pickedNode);
            }
        }
        return retList;
    }

    @Override
    public List<MkVarNode> walkNode(MkVarGraph inGraph, MkVarNode startingNode, int walkCount) {
        return walkNode(inGraph, startingNode, false, walkCount);
    }
}
