package DSLib.theory.graph;

import DSLib.theory.category.Category;
import DSLib.theory.category.KObject;
import DSLib.theory.category.KObjectBaseImpl;
import DSLib.theory.category.Morphism;

import java.util.Collection;
import java.util.HashMap;

public class NodeImpl extends KObjectBaseImpl implements Node {

    private HashMap<String, Edge> edgeHashMap = new HashMap<>();
    private String nodeName;
    private Graph memberOf;

    @Override
    public boolean addEdge(Node targetNode) {
        return false;
    }

    @Override
    public String getName() {
        return nodeName;
    }

}
