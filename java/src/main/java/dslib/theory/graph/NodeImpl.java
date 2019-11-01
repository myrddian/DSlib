package dslib.theory.graph;

import dslib.theory.category.KObjectBaseImpl;

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
