package dslib.theory.graph;

import dslib.theory.category.KObject;

public interface Node extends KObject {
    boolean addEdge(Node targetNode);

}
