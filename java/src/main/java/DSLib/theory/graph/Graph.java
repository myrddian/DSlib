package DSLib.theory.graph;

import DSLib.theory.category.Category;

import java.util.List;

public interface Graph extends Category {
    List<Node> getNodes();
    List<Edge> getEdges();
}
