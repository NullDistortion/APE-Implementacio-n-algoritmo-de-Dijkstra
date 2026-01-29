package edu.u2.dijkstra.algorithm;

import edu.u2.dijkstra.model.Node;

public class NodeWrapper implements Comparable<NodeWrapper> {
    private final Node node;
    private final int distance;

    public NodeWrapper(Node node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    public Node getNode() {
        return node;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public int compareTo(NodeWrapper other) {
        // Orden natural por distancia (menor a mayor)
        return Integer.compare(this.distance, other.distance);
    }
}