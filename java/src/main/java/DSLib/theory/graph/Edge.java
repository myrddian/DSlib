package DSLib.theory.graph;

import DSLib.theory.category.Morphism;

public interface Edge extends Morphism {
    Node getFromNode();
    Node getTarget();
    String getName();
}
