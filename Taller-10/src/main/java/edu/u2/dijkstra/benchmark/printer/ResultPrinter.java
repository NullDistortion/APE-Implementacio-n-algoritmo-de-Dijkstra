package edu.u2.dijkstra.benchmark.printer;

import edu.u2.dijkstra.algorithm.DijkstraResult;
import edu.u2.dijkstra.model.Node;
import java.util.*;
import java.util.stream.Collectors;

public class ResultPrinter {

    public void printSolution(DijkstraResult result, Node source) {
        printHeader(source);

        Map<Node, Integer> dists = result.getDistances();
        List<Node> targets = new ArrayList<>(dists.keySet());
        targets.sort(Comparator.comparing(Node::getId));

        for (Node target : targets) {
            printTableRow(result, source, target);
        }

        printFooter(result, source);
    }

    public void printSinglePath(DijkstraResult result, Node source, Node target) {
        printHeader(source);
        printTableRow(result, source, target);
        printFooter(result, source);
    }

    private void printHeader(Node source) {
        System.out.println("\n=============================================================");
        System.out.println(" REPORTE DE RUTAS OPTIMAS (DIJKSTRA) ");
        System.out.println(" Origen de partida: [ " + source.getId() + " ]");
        System.out.println("=============================================================");
        System.out.printf("| %-10s | %-15s | %-30s |%n", "DESTINO", "COSTO TOTAL", "RECORRIDO (CAMINO)");
        System.out.println("|------------|-----------------|--------------------------------|");
    }

    private void printTableRow(DijkstraResult result, Node source, Node target) {
        int distance = result.getDistances().getOrDefault(target, Integer.MAX_VALUE);
        String pathStr;
        String distStr;

        if (source.equals(target)) {
            distStr = "0";
            pathStr = source.getId();
        } else if (distance == Integer.MAX_VALUE) {
            distStr = "INF";
            pathStr = "---";
        } else {
            distStr = String.valueOf(distance);
            pathStr = reconstructPathString(result.getPredecessors(), source, target);
        }

        System.out.printf("| %-10s | %-15s | %-30s |%n", target.getId(), distStr, pathStr);
    }

    private void printFooter(DijkstraResult result, Node source) {
        List<Node> order = result.getVisitOrder();
        System.out.println("=============================================================");
        System.out.println(">> Nodos explorados (Orden de Expansion):");

        if (order.isEmpty()) {
            System.out.println("[ Ninguno ]");
            return;
        }

        String sequence = order.stream()
                .map(Node::getId)
                .collect(Collectors.joining(" -> "));
        System.out.println("[ " + sequence + " ]");

        Node lastNode = order.get(order.size() - 1);
        int finalCost = result.getDistances().getOrDefault(lastNode, 0);

        System.out.println("-------------------------------------------------------------");
        System.out.println(">> Ultimo nodo alcanzado: [ " + lastNode.getId() + " ]");
        System.out.println(">> Costo acumulado al final del recorrido: " + finalCost);
        System.out.println("=============================================================");
    }

    private String reconstructPathString(Map<Node, Node> predecessors, Node start, Node end) {
        LinkedList<String> path = new LinkedList<>();
        Node step = end;

        if (!predecessors.containsKey(end) && !end.equals(start)) return "Sin camino";

        while (step != null) {
            path.addFirst(step.getId());
            if (step.equals(start)) break;
            step = predecessors.get(step);
        }
        return String.join(" -> ", path);
    }
}