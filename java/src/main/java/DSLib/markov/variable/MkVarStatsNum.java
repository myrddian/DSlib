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

import DSLib.markov.algo.Stats;
import DSLib.markov.algo.TrialTree;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class MkVarStatsNum implements Stats<MkVarGraph, MkVarNode> {

    private int trialRuns = 100;


    @Override
    public double getProbGiven(MkVarGraph inGraph, MkVarNode fromState, MkVarNode toState) {
        MkVarEdge edge = fromState.getEdge(toState);
        long totalFreq = fromState.getTotalEdgeFrequency();
        return edge.getNormalisedFreq(totalFreq);
    }

    @Override
    public double getProbGiven(MkVarGraph inGraph, MkVarNode fromState, MkVarNode toState, int steps) {

        List<List<MkVarNode>> trialResults = generateTrialResultsFromState(inGraph, fromState, steps);
        HashMap<String, Long> freqMap = getFreqMap(trialResults);
        if(freqMap.containsKey(toState.getValue())) {
            return (Double.valueOf(freqMap.get(toState.getValue())) / Double.valueOf(trialRuns));
        }
        return 0.0;
    }

    @Override
    public double getProb(MkVarGraph inGraph, MkVarNode toState, int steps) {
        List<List<MkVarNode>> trialResults = generateTrials(inGraph,steps);
        HashMap<String, Long> freqMap = getFreqMap(trialResults);
        if(freqMap.containsKey(toState.getValue())) {
            return (Double.valueOf(freqMap.get(toState.getValue())) / Double.valueOf(trialRuns));
        }
        return 0.0;
    }

    @Override
    public MkVarProbRes getProbsGiven(MkVarGraph inGraph, MkVarNode fromState, int steps) {
        List<List<MkVarNode>> trialResults = generateTrialResultsFromState(inGraph,fromState,steps);
        HashMap<String, Long> freqMap = getFreqMap(trialResults);

        MkVarProbRes probRes = new MkVarProbRes();
        probRes.setRuns(trialRuns);
        for(String key: freqMap.keySet()) {
            probRes.addNode(inGraph.getVarNode(key), freqMap.get(key));
        }

        return probRes;
    }

    @Override
    public TrialTree<MkVarNode> createTrialTree(MkVarGraph inGraph, int size) {
        MkVarTrialTree tree = new MkVarTrialTree();
        tree.addTrials(generateTrials(inGraph,size),size);
        return tree;
    }

    private List<List<MkVarNode>> generateTrials(MkVarGraph graph, int steps) {
        MkVarPicker picker = new MkVarPicker();
        List<MkVarNode> startingList = graph.getStartingList();
        List<List<MkVarNode>> trialResults = new Vector<>();
        MkVarWalker walker = new MkVarWalker();

        for(int counter=0; counter < trialRuns; counter++) {
            trialResults.add(walker.walkNode(graph, picker.pickNode(startingList),steps));
        }

        return trialResults;
    }

    private List<List<MkVarNode>> generateTrialResultsFromState(MkVarGraph graph, MkVarNode fromState, int steps) {
        List<List<MkVarNode>> trialResults = new Vector<>();
        MkVarWalker walker = new MkVarWalker();

        for(int counter=0; counter < trialRuns; counter++) {
            trialResults.add(walker.walkNode(graph,fromState,steps));
        }

        return trialResults;
    }

    private HashMap<String, Long> getFreqMap(List<List<MkVarNode>> trialResults) {
        HashMap<String, Long> freqMap = new HashMap<>();
        for(List<MkVarNode> trial: trialResults){
            if(trial.size() != 0 ) {
                MkVarNode endNode = trial.get(trial.size()-1);
                if(!freqMap.containsKey(endNode.getValue())){
                    freqMap.put(endNode.getValue(),1l);
                }
                else {
                    freqMap.replace(endNode.getValue(), freqMap.get(endNode.getValue()) +1);
                }
            }
        }
        return  freqMap;
    }

    public int getTrialRuns() {
        return trialRuns;
    }

    public void setTrialRuns(int trialRuns) {
        this.trialRuns = trialRuns;
    }
}
