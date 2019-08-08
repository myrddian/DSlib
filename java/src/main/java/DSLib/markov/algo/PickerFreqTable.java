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

package DSLib.markov.algo;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class PickerFreqTable<NODE> {

    private long totalVal = 0;
    private List<NODE> nodeList = new Vector<>();
    private HashMap<NODE, Long> revMap = new HashMap<>();

    public void addNode(NODE inNode, long freq) {
        totalVal = totalVal + freq;
        nodeList.add(inNode);
        revMap.put(inNode, freq);
    }

    public long getTotalFreq() { return totalVal; }

    public NODE pickNode(long number) {
        long totalCount = 0;
        long oldCount = 0;



        for(NODE node: nodeList) {
            totalCount = totalCount + revMap.get(node);
            if(number < totalCount && number >= oldCount) {
                return node;
            }
            oldCount = totalCount;
        }
        //Deal with the case of the LAST item being picked
        if(number == totalCount) {
            return nodeList.get(nodeList.size()-1);
        }
        return null;
    }

}
