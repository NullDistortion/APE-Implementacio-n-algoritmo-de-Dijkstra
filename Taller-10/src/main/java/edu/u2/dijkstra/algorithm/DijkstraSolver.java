package edu.u2.dijkstra.algorithm;

import edu.u2.dijkstra.model.*;
import java.util.*;

public class DijkstraSolver implements ShortestPathStrategy {

    @Override
    public DijkstraResult solve(Graph graph, Node source, Node target) {
        Map<Node, Integer> distances = new HashMap<>();
        Map<Node, Node> predecessors = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        List<Node> visitOrder = new ArrayList<>();

        PriorityQueue<NodeWrapper> pq = new PriorityQueue<>();

        for (Node node : graph.getNodes()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        pq.add(new NodeWrapper(source, 0));

        while (!pq.isEmpty()) {
            NodeWrapper currentWrapper = pq.poll();
            Node currentNode = currentWrapper.getNode();

            if (visited.contains(currentNode)) continue;

            visited.add(currentNode);
            visitOrder.add(currentNode);

            if (target != null && currentNode.equals(target)) {
                break;
            }

            for (Edge edge : graph.getNeighbors(currentNode)) {
                Node neighbor = edge.getDestination();
                if (visited.contains(neighbor)) continue;

                int newDist = distances.get(currentNode) + edge.getWeight();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    predecessors.put(neighbor, currentNode);
                    pq.add(new NodeWrapper(neighbor, newDist));
                }
            }
        }
        return new DijkstraResult(distances, predecessors, visitOrder);
    }
}