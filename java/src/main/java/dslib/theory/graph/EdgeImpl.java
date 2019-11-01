package dslib.theory.graph;

import dslib.theory.category.MorphismBaseImpl;

public class EdgeImpl extends MorphismBaseImpl implements Edge {

    private Node leftNode;
    private Node rightNode;
    private String edgeName;

    public String getEdgeName() {
        return edgeName;
    }

    public void setEdgeName(String edgeName) {
        this.edgeName = edgeName;
    }

    @Override
    public Node getFromNode() {
        return leftNode;
    }

    @Override
    public Node getTarget() {
        return rightNode;
    }

}
