package edu.u2.dijkstra.model;

import java.util.*;

public class Graph {
    private final Map<Node, List<Edge>> adjacencyMap = new HashMap<>();

    public void addNode(Node node) {
        adjacencyMap.putIfAbsent(node, new ArrayList<>());
    }

    public void addEdge(Node source, Node destination, int weight) {
        addNode(source);
        addNode(destination);
        adjacencyMap.get(source).add(new Edge(source, destination, weight));
    }

    public List<Edge> getNeighbors(Node node) {
        return adjacencyMap.getOrDefault(node, Collections.emptyList());
    }

    public Set<Node> getNodes() {
        return adjacencyMap.keySet();
    }
}