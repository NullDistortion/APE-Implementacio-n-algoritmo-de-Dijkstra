package edu.u2.dijkstra.model;

public class Node implements Comparable<Node> {
    private final String id;

    public Node(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int compareTo(Node o) {
        return this.id.compareTo(o.id);
    }
}