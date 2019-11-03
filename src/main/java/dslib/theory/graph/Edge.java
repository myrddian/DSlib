package dslib.theory.graph;

import dslib.theory.category.Morphism;

public interface Edge extends Morphism {
    Node getFromNode();
    Node getTarget();
    String getName();
}
