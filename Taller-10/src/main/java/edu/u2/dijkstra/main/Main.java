package edu.u2.dijkstra.main;

import edu.u2.dijkstra.algorithm.DijkstraSolver;
import edu.u2.dijkstra.algorithm.DijkstraResult;
import edu.u2.dijkstra.benchmark.GraphLoader;
import edu.u2.dijkstra.benchmark.printer.ResultPrinter;
import edu.u2.dijkstra.model.Graph;
import edu.u2.dijkstra.model.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GraphLoader loader = new GraphLoader();
        ResultPrinter printer = new ResultPrinter();
        DijkstraSolver solver = new DijkstraSolver();
        Scanner sc = new Scanner(System.in);

        Graph graph = null;
        boolean exitProgram = false;

        System.out.println("SISTEMA DE CAMINOS MINIMOS - DIJKSTRA");

        while (!exitProgram) {
            if (graph == null) {
                File selectedFile = loader.selectFile();
                if (selectedFile == null) {
                    exitProgram = true;
                    continue;
                }
                graph = loader.loadGraphFromFile(selectedFile);

                if (graph.getNodes().isEmpty()) {
                    System.out.println("Error: El grafo esta vacio.");
                    graph = null;
                    continue;
                }
                performWarmup(graph, solver);
                System.out.println("\n");
            }

            System.out.println("--- Menu de Operaciones ---");
            System.out.println("1. Recorrer todo (Tabla Completa)");
            System.out.println("2. Calcular Ruta Especifica");
            System.out.println("3. Cargar otro archivo");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            String option = sc.nextLine();

            switch (option) {
                case "1":
                    handleOneToAll(sc, graph, loader, solver, printer);
                    break;
                case "2":
                    handleOneToOne(sc, graph, loader, solver, printer);
                    break;
                case "3":
                    graph = null;
                    break;
                case "0":
                    exitProgram = true;
                    break;
                default:
                    System.out.println("Opcion incorrecta.");
            }
        }
        sc.close();
    }

    private static void performWarmup(Graph graph, DijkstraSolver solver) {
        if (graph.getNodes().isEmpty()) return;
        Node sampleNode = graph.getNodes().iterator().next();
        for (int i = 0; i < 500; i++) {
            solver.solve(graph, sampleNode, null);
        }
    }

    private static void handleOneToAll(Scanner sc, Graph graph, GraphLoader loader,
                                       DijkstraSolver solver, ResultPrinter printer) {
        showNodes(graph);
        System.out.print("Ingrese ID Origen: ");
        Node source = getNodeInput(sc, graph, loader);
        if (source == null) return;

        System.out.println("Calculando todas las rutas...");

        long startTime = System.nanoTime();
        DijkstraResult result = solver.solve(graph, source, null);
        long endTime = System.nanoTime();

        printer.printSolution(result, source);
        System.out.printf("[Tiempo de computo: %.4f ms]%n\n", (endTime - startTime) / 1_000_000.0);
    }

    private static void handleOneToOne(Scanner sc, Graph graph, GraphLoader loader,
                                       DijkstraSolver solver, ResultPrinter printer) {
        showNodes(graph);
        System.out.print("Ingrese ID Origen: ");
        Node source = getNodeInput(sc, graph, loader);
        if (source == null) return;

        System.out.print("Ingrese ID Destino: ");
        Node target = getNodeInput(sc, graph, loader);
        if (target == null) return;

        System.out.println("Buscando ruta optima...");

        long startTime = System.nanoTime();
        DijkstraResult result = solver.solve(graph, source, target);
        long endTime = System.nanoTime();

        printer.printSinglePath(result, source, target);
        System.out.printf("[Tiempo de computo: %.4f ms]%n\n", (endTime - startTime) / 1_000_000.0);
    }

    private static void showNodes(Graph graph) {
        List<Node> nodes = new ArrayList<>(graph.getNodes());
        Collections.sort(nodes);
        System.out.println("Nodos: " + nodes);
    }

    private static Node getNodeInput(Scanner sc, Graph graph, GraphLoader loader) {
        String id = sc.nextLine().trim();
        Node node = loader.findNodeById(graph, id);
        if (node == null) {
            System.out.println("Error: El nodo '" + id + "' no existe.");
        }
        return node;
    }
}