package DSLib.theory.graph;

import DSLib.theory.category.KObject;

public interface Node extends KObject {
    boolean addEdge(Node targetNode);

}
