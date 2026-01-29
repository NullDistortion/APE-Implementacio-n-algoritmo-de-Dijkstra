package edu.u2.dijkstra.algorithm;

import edu.u2.dijkstra.model.Graph;
import edu.u2.dijkstra.model.Node;

public interface ShortestPathStrategy {
    DijkstraResult solve(Graph graph, Node source, Node target);
}