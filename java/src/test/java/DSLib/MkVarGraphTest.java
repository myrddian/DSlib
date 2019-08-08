package DSLib;

import DSLib.markov.algo.TrialTree;
import DSLib.markov.algo.variables.MkVarProbRes;
import DSLib.markov.algo.variables.MkVarStatsNum;
import DSLib.markov.algo.variables.MkVarWalker;
import DSLib.markov.datatypes.variables.MkVarEdge;
import DSLib.markov.datatypes.variables.MkVarGraph;
import DSLib.markov.datatypes.variables.MkVarNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MkVarGraphTest {
    @Test
    public void mkVarTestsStartNodes() {
        MkVarWalker walker = new MkVarWalker();
        MkVarGraph graph = MkVarGraph.createInstance();

        MkVarNode nodaA = graph.createNode("A", MkVarNode.MODE.NODE);
        MkVarNode nodeB = graph.createNode("B", MkVarNode.MODE.NODE);
        MkVarNode nodeC = graph.createNode("C", MkVarNode.MODE.NODE);
        MkVarNode nodeD = graph.createNode("D", MkVarNode.MODE.START_NODE);
        MkVarNode nodeE = graph.createNode("E", MkVarNode.MODE.START_NODE);

        MkVarEdge aToB = graph.createEdge(nodaA, nodeB);
        MkVarEdge aToA = graph.createEdge(nodaA, nodaA);
        MkVarEdge aToC = graph.createEdge(nodaA, nodeC);

        //Should be 1/3 transition state
        aToA.setFrequencyValues(1);
        aToB.setFrequencyValues(1);
        aToC.setFrequencyValues(1);

        //Add A to the graph
        graph.addEdge(aToB);
        graph.addEdge(aToA);
        graph.addEdge(aToC);

        //B - Transition
        graph.addEdge(nodeB.addEdge(nodeC, 2));
        graph.addEdge(nodeB.addEdge(nodaA, 2));
        graph.addEdge(nodeB.addEdge(nodeD, 3));

        //C - Transition
        graph.addEdge(nodeC.addEdge(nodeB, 2));
        graph.addEdge(nodeC.addEdge(nodeC, 1));
        graph.addEdge(nodeC.addEdge(nodaA, 2));

        //D - Starting State
        graph.addEdge(nodeD.addEdge(nodaA, 3));
        graph.addEdge(nodeD.addEdge(nodeC, 2));
        graph.addEdge(nodeD.addEdge(nodeB, 1));
        graph.addEdge(nodeD.addEdge(nodeE, 3));

        //E - Starting State
        graph.addEdge(nodeE.addEdge(nodaA, 2));
        graph.addEdge(nodeE.addEdge(nodeC, 1));
        graph.addEdge(nodeE.addEdge(nodeB, 2));

        List<String> res = walker.walk(graph, 50);
        System.out.println("\nRandom Markov Walk of Graph: ");
        System.out.println(String.join(",", res));
        res = graph.edgeNames();
        System.out.println("\nEdge names");
        System.out.println(String.join("\n", res));
        List<MkVarNode> walkedList  = walker.walkNode(graph,true,50);
        System.out.println("\nUNIQUE WALKER TEST");
        for(MkVarNode node: walkedList) {
            System.out.print(node.getValue() +  ", ");
        }

        System.out.println("\n\n=================================================");
        MkVarStatsNum statsNum = new MkVarStatsNum();
        double probA  =  statsNum.getProb(graph,nodaA, 10);
        double probB  =  statsNum.getProb(graph,nodeB, 10);
        double probC  =  statsNum.getProb(graph,nodeC, 10);
        double probD  =  statsNum.getProb(graph,nodeD, 10);
        double probE  =  statsNum.getProb(graph,nodeE, 10);
        System.out.println("Probabilities that this graph ends in a given node in 10 steps:\n");
        System.out.println("P(NODE-A) = " + Double.toString(probA));
        System.out.println("P(NODE-B) = " + Double.toString(probB));
        System.out.println("P(NODE-C) = " + Double.toString(probC));
        System.out.println("P(NODE-D) = " + Double.toString(probD));
        System.out.println("P(NODE-E) = " + Double.toString(probE));
        System.out.println("=================================================");
        System.out.println("\n\n=================================================");
        MkVarProbRes results = statsNum.getProbsGiven(graph,nodeE, 30);
        System.out.println("P(A|E) -> " + Double.toString(results.getProb(nodaA)));
        System.out.println("P(B|E) -> " + Double.toString(results.getProb(nodeB)));
        System.out.println("P(C|E) -> " + Double.toString(results.getProb(nodeC)));
        System.out.println("P(D|E) -> " + Double.toString(results.getProb(nodeD)));
        System.out.println("P(E|E) -> " + Double.toString(results.getProb(nodeE)));
        System.out.println("\n\n=================================================");
        results = statsNum.getProbsGiven(graph,nodeD, 30);
        System.out.println("P(A|D) -> " + Double.toString(results.getProb(nodaA)));
        System.out.println("P(B|D) -> " + Double.toString(results.getProb(nodeB)));
        System.out.println("P(C|D) -> " + Double.toString(results.getProb(nodeC)));
        System.out.println("P(D|D) -> " + Double.toString(results.getProb(nodeD)));
        System.out.println("P(E|D) -> " + Double.toString(results.getProb(nodeE)));

    }

    @Test
    public void mkGenGraph() {
        String testStringOne = "D,E,B,D,A,B,C,B,A,A,B,C,C,A,A,C,B,A,C,A,C,B,A,C,B,A,B,A,A,A,B,A,A,A,A,A,B,D,A,B,D,A,C,C,A,C,A,B,D,C,A";
        String testStringTwo = "A,C,B,A,A,A,C,B,C,C,A,A,C,B,A,A,A,C,A,B,A,C,B,A,C,A,B,C,A,C,A,C,B,A,B,A,C,A,A,C,A,B,A,B,A,A,A,C,A,C,A";
        String testStringThree ="D,E,C,A,B,C,C,A,B,A,A,C,B,D,E,B,A,C,A,C,A,C,C,A,C,A,C,A,C,A,C,C,B,C,C,C,C,A,C,B,A,C,A,C,A,A,B,D,C,C,B";

        Vector<String> split = new Vector<String>(Arrays.asList(testStringOne.split(",")));
        MkVarWalker walker = new MkVarWalker();
        MkVarGraph graph = MkVarGraph.createInstance();
        MkVarGraph graphTwo = MkVarGraph.createInstance();
        MkVarGraph graphThree = MkVarGraph.createInstance();

        graph.parseTransitionList(split);
        graphTwo.parseTransitionList(new Vector<String>(Arrays.asList(testStringTwo.split(","))));
        graphThree.parseTransitionList(new Vector<String>(Arrays.asList(testStringThree.split(","))));
        List<String> res = walker.walk(graph, 50);
        System.out.println("\nRandom Markov Walk of Graph [TEST ONE]: ");
        System.out.println(String.join(",", res));

        res = walker.walk(graphTwo, 50);
        System.out.println("\nRandom Markov Walk of Graph [TEST TWO]: ");
        System.out.println(String.join(",", res));

        res = walker.walk(graphThree, 50);
        System.out.println("\nRandom Markov Walk of Graph [TEST THREE]: ");
        System.out.println(String.join(",", res));

    }


    @Test
    public void mkVarTests() {
        MkVarWalker walker = new MkVarWalker();
        MkVarGraph graph = MkVarGraph.createInstance();

        MkVarNode nodaA = graph.createNode("A", MkVarNode.MODE.NODE);
        MkVarNode nodeB = graph.createNode("B", MkVarNode.MODE.NODE);
        MkVarNode nodeC = graph.createNode("C", MkVarNode.MODE.NODE);

        MkVarEdge aToB = graph.createEdge(nodaA, nodeB);
        MkVarEdge aToA = graph.createEdge(nodaA, nodaA);
        MkVarEdge aToC = graph.createEdge(nodaA, nodeC);

        //Should be 1/3 transition state
        aToA.setFrequencyValues(1);
        aToB.setFrequencyValues(1);
        aToC.setFrequencyValues(1);

        //Add A to the graph
        graph.addEdge(aToB);
        graph.addEdge(aToA);
        graph.addEdge(aToC);

        //B - Transition
        graph.addEdge(nodeB.addEdge(nodeC, 1));
        graph.addEdge(nodeB.addEdge(nodaA, 1));

        //C - Transition
        graph.addEdge(nodeC.addEdge(nodeB, 1));
        graph.addEdge(nodeC.addEdge(nodeC, 1));
        graph.addEdge(nodeC.addEdge(nodaA, 1));

        List<String> res = walker.walk(graph, 50);
        System.out.println(String.join(",", res));
        res = graph.edgeNames();
        System.out.println(String.join(", ", res));
    }

    @Test
    public void sunnyRainyTest() {
        MkVarWalker walker = new MkVarWalker();
        MkVarGraph graph = MkVarGraph.createInstance();
        MkVarStatsNum statsNum = new MkVarStatsNum();

        MkVarNode sunny = graph.createNode("sunny", MkVarNode.MODE.NODE);
        MkVarNode rainy = graph.createNode("rainy", MkVarNode.MODE.NODE);

        //75% to sunny, 25% to rainy
        graph.addEdge(sunny.addEdge(sunny,75));
        graph.addEdge(sunny.addEdge(rainy, 25));

        //40/60 split
        graph.addEdge(rainy.addEdge(sunny, 40));
        graph.addEdge(rainy.addEdge(rainy, 60));

        MkVarProbRes results = statsNum.getProbsGiven(graph,sunny, 10);
        System.out.println("\n=========================================================================================================");
        System.out.println("P(SUNNY|SUNNY) in 10 Transitions(PROBRES) = " + Double.toString(results.getProb(sunny)));
        System.out.println("P(RAINY|SUNNY) in 10 Transitions(PROBRES) = " + Double.toString(results.getProb(rainy)));
        System.out.println("=========================================================================================================");
        System.out.println("P(SUNNY|SUNNY) in 2 Transitions = " + Double.toString(statsNum.getProbGiven(graph,sunny,sunny,2)));
        System.out.println("P(RAINY|SUNNY) in 2 Transitions = " + Double.toString(statsNum.getProbGiven(graph,sunny,rainy,2)));
        System.out.println("=========================================================================================================");
        System.out.println("P(SUNNY|SUNNY) in 10 Transitions = " + Double.toString(statsNum.getProbGiven(graph,sunny,sunny,10)));
        System.out.println("P(RAINY|SUNNY) in 10 Transitions = " + Double.toString(statsNum.getProbGiven(graph,sunny,rainy,10)));
        System.out.println("P(RAINY|RAINY) in 10 Transitions = " + Double.toString(statsNum.getProbGiven(graph,rainy,rainy,10)));
        System.out.println("P(SUNNY|RAINY) in 10 Transitions = " + Double.toString(statsNum.getProbGiven(graph,rainy,sunny,10)));
        System.out.println("=========================================================================================================");
        System.out.println("P(SUNNY) in 10 Transitions = " + Double.toString(statsNum.getProb(graph,sunny,10)));
        System.out.println("P(RAINY) in 10 Transitions = " + Double.toString(statsNum.getProb(graph,rainy,10)));
    }

    @Test
    public void headTailsTest() {
        MkVarWalker walker = new MkVarWalker();
        MkVarGraph graph = MkVarGraph.createInstance();
        MkVarStatsNum statsNum = new MkVarStatsNum();

        MkVarNode heads = graph.createNode("heads", MkVarNode.MODE.NODE);
        MkVarNode tails = graph.createNode("tails", MkVarNode.MODE.NODE);

        graph.addEdge(heads.addEdge(heads,2));
        graph.addEdge(heads.addEdge(tails, 2));
        graph.addEdge(tails.addEdge(heads, 2));
        graph.addEdge(tails.addEdge(tails, 2));

        System.out.println("=========================================================================================================");
        System.out.println("Random Markov Walk of Graph: ");
        List<String> res = walker.walk(graph, 3);
        System.out.println(String.join(",", res));
        System.out.println("=========================================================================================================");

        MkVarProbRes results = statsNum.getProbsGiven(graph,heads, 10);
        System.out.println("=========================================================================================================");
        System.out.println("P(HEADS|HEADS) in 10 Transitions(PROBRES) = " + Double.toString(results.getProb(heads)));
        System.out.println("P(TAILS|HEADS) in 10 Transitions(PROBRES) = " + Double.toString(results.getProb(tails)));
        System.out.println("=========================================================================================================");

        statsNum.setTrialRuns(1000);
        TrialTree<MkVarNode> trialTree = statsNum.createTrialTree(graph,3);
        List<MkVarNode> conditionOne = new Vector<>();
        conditionOne.add(heads);
        conditionOne.add(heads);
        conditionOne.add(heads);

        List<MkVarNode> conditionTwo = new Vector<>();
        conditionTwo.add(tails);
        conditionTwo.add(heads);
        conditionTwo.add(tails);

        System.out.println("=========================================================================================================");
        System.out.println("P(HEADS|HEADS|HEADS) = " + Double.toString(trialTree.getCondProb(conditionOne)));
        System.out.println("P(TAILS|HEADS|TAILS) = " + Double.toString(trialTree.getCondProb(conditionTwo)));
        System.out.println("=========================================================================================================");



    }
}
