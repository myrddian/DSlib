package dslib;

import dslib.markov.algo.TrialTree;
import dslib.markov.object.MkObjectCondition;
import dslib.markov.object.MkDocument;
import dslib.markov.object.MkObject;
import dslib.markov.variable.MkVarNode;
import dslib.markov.variable.MkVariable;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class MkObjDocTest {
    @Test
    public void mkDocTest() {
        MkVariable weather = new MkVariable();
        weather.setVariableName("weather");
        weather.createNode("sunny", MkVarNode.MODE.NODE);
        weather.createNode("rainy", MkVarNode.MODE.NODE);

        weather.addEdge("sunny", "rainy", 25);
        weather.addEdge("sunny", "sunny", 75);
        weather.addEdge("rainy", "sunny", 40);
        weather.addEdge("rainy", "rainy", 60);

        MkVariable coin = new MkVariable();
        coin.setVariableName("coin");
        coin.createNode("heads", MkVarNode.MODE.NODE);
        coin.createNode("tails", MkVarNode.MODE.NODE);
        coin.addEdge("heads", "heads", 50);
        coin.addEdge("heads", "tails", 50);
        coin.addEdge("tails", "heads", 50);
        coin.addEdge("tails", "tails", 50);

        MkVariable drink = new MkVariable();
        drink.setVariableName("drink");
        drink.createNode("coffee", MkVarNode.MODE.NODE);
        drink.createNode("tea", MkVarNode.MODE.NODE);
        drink.addEdge("coffee","coffee", 60);
        drink.addEdge("coffee","tea", 40);
        drink.addEdge("tea","tea", 75);
        drink.addEdge("tea","coffee", 25);

        MkDocument document = new MkDocument();
        document.addVariableToSchema(weather);
        document.addVariableToSchema(coin);
        document.addVariableToSchema(drink);

        System.out.println("=========================================================================================================");
        List<MkObject> samples = document.generateSamples(8);
        for(MkObject object:samples) {
            System.out.println(" OBJECT -  " + object.toJson());
        }
        System.out.println("=========================================================================================================");

        TrialTree<MkObjectCondition> trialTree = document.createTrialTree(1000);
        List<MkObjectCondition> conditionOne = new Vector<>();
        conditionOne.add(document.createCondition("weather","sunny"));
        conditionOne.add(document.createCondition("coin", "heads"));

        List<MkObjectCondition> conditionTwo = new Vector<>();
        conditionTwo.add(document.createCondition("weather","rainy"));
        conditionTwo.add(document.createCondition("drink", "coffee"));

        List<MkObjectCondition> conditionThree = new Vector<>();
        conditionThree.add(document.createCondition("weather","sunny"));
        conditionThree.add(document.createCondition("drink", "coffee"));
        conditionThree.add(document.createCondition("coin", "heads"));

        List<MkObjectCondition> conditionSimple = new Vector<>();
        conditionSimple.add(document.createCondition("weather","sunny"));

        System.out.println("=========================================================================================================");
        System.out.println("P(SUNNY AND HEADS) = " + Double.toString(trialTree.getCondProb(conditionOne)));
        System.out.println("P(RAINY AND COFFEE) = " + Double.toString(trialTree.getCondProb(conditionTwo)));
        System.out.println("P(SUNNY AND HEADS AND COFFEE) = " + Double.toString(trialTree.getCondProb(conditionThree)));
        System.out.println("P(SUNNY) = " + Double.toString(trialTree.getCondProb(conditionSimple)));
        System.out.println("=========================================================================================================");

    }

    @Test
    public void parseTest() {
        String simpleMarkov = "B,A,B,A,C,B,C,C,A,C,C,C,B,C,A,B,A,C,A,C,A,A,C,C,C,A,B,A,C,A,A,B,A,C,B,A,B,A,B,C,A,A,C,C,C,C,A,C,A,B";
        String testStringThree ="D,E,C,A,B,C,C,A,B,A,A,C,B,D,E,B,A,C,A,C,A,C,C,A,C,A,C,A,C,A,C,C,B,C,C,C,C,A,C,B,A,C,A,C,A,A,B,D,C,C,B";
        MkDocument document = new MkDocument();
        document.parseAddVariable("testOne", new Vector<String>(Arrays.asList(simpleMarkov.split(","))));
        document.parseAddVariable("testTwo", new Vector<String>(Arrays.asList(testStringThree.split(","))));
        System.out.println("=========================================================================================================");
        List<MkObject> samples = document.generateSamples(10);
        for(MkObject object:samples) {
            System.out.println(" OBJECT -  " + object.toJson());
        }
        System.out.println("=========================================================================================================");

        TrialTree<MkObjectCondition> trialTree = document.createTrialTree(1000);
        List<MkObjectCondition> conditionOne = new Vector<>();
        conditionOne.add(document.createCondition("testOne","A"));
        conditionOne.add(document.createCondition("testTwo", "B"));

        List<MkObjectCondition> conditionA = new Vector<>();
        conditionA.add(document.createCondition("testOne","A"));

        System.out.println("=========================================================================================================");
        System.out.println("P(V1:A AND V2:B) = " + Double.toString(trialTree.getCondProb(conditionOne)));
        System.out.println("P(V1:A) = " + Double.toString(trialTree.getCondProb(conditionA)));
        System.out.println("=========================================================================================================");

    }
}
