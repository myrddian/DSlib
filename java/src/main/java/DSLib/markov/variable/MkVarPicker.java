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


import DSLib.markov.algo.PickerFreqTable;
import DSLib.markov.algo.Picker;

import java.util.Random;
import java.util.List;
import org.apache.commons.math3.random.RandomDataGenerator;

public class MkVarPicker implements Picker<MkVarNode> {

    @Override
    public MkVarNode pickNodeNormalised(MkVarNode inNode) {
        return null;
    }

    @Override
    public MkVarNode pickNode(MkVarNode inNode) {
        List<MkVarEdge> edges = inNode.getEdgesOut();
        PickerFreqTable<MkVarNode> nodePickerFreqTable = new PickerFreqTable<>();

        for(MkVarEdge edge: edges) {
            nodePickerFreqTable.addNode(edge.getToNode(), edge.getFrequency());
        }
        RandomDataGenerator random = new RandomDataGenerator();
        return nodePickerFreqTable.pickNode(random.nextLong(0l,nodePickerFreqTable.getTotalFreq()));
    }

    @Override
    public MkVarNode pickNode(List<MkVarNode> mkVarNodes) {
        Random random = new Random();
        return mkVarNodes.get(random.nextInt(mkVarNodes.size()));
    }
}
