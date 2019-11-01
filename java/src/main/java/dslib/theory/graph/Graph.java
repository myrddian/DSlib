package dslib.theory.graph;

import dslib.theory.category.Category;

import java.util.List;

public interface Graph extends Category {
    List<Node> getNodes();
    List<Edge> getEdges();
}
