package edu.u2.dijkstra.algorithm;

import edu.u2.dijkstra.model.Node;
import java.util.List;
import java.util.Map;

public class DijkstraResult {
    private final Map<Node, Integer> distances;
    private final Map<Node, Node> predecessors;
    private final List<Node> visitOrder; // Nuevo campo

    public DijkstraResult(Map<Node, Integer> distances, Map<Node, Node> predecessors, List<Node> visitOrder) {
        this.distances = distances;
        this.predecessors = predecessors;
        this.visitOrder = visitOrder;
    }

    public Map<Node, Integer> getDistances() {
        return distances;
    }

    public Map<Node, Node> getPredecessors() {
        return predecessors;
    }

    public List<Node> getVisitOrder() {
        return visitOrder;
    }
}