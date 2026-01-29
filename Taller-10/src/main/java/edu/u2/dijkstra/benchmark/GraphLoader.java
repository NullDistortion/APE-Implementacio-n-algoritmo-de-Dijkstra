package edu.u2.dijkstra.benchmark;

import edu.u2.dijkstra.model.Graph;
import edu.u2.dijkstra.model.Node;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GraphLoader {

    private final String RESOURCES_PATH = "src/main/resources";

    private final Map<String, Node> nodeCache = new HashMap<>();

    public File selectFile() {
        File folder = new File(RESOURCES_PATH);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Error: Carpeta 'resources' no encontrada en: " + folder.getAbsolutePath());
            folder.mkdirs();
            return null;
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files == null || files.length == 0) {
            System.out.println("No hay archivos .txt disponibles en " + RESOURCES_PATH);
            return null;
        }

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Seleccione un archivo de Grafo ---");
            for (int i = 0; i < files.length; i++) {
                System.out.println((i + 1) + ". " + files[i].getName());
            }
            System.out.println("0. Salir / Cancelar");
            System.out.print("Opción: ");

            try {
                String input = sc.nextLine();
                int choice = Integer.parseInt(input);

                if (choice == 0) return null;

                if (choice > 0 && choice <= files.length) {
                    return files[choice - 1];
                } else {
                    System.out.println("Opción fuera de rango.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
    }

    public Graph loadGraphFromFile(File file) {
        Graph graph = new Graph();
        nodeCache.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean headerRead = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");

                if (!headerRead) {
                    if (parts.length == 2) {
                        System.out.println(">> Configuración detectada: " + parts[0] + " Nodos, " + parts[1] + " Aristas.");
                        headerRead = true;
                        continue; // Saltamos a la siguiente línea para leer los datos reales
                    }
                }

                if (parts.length < 3) continue;

                String srcId = parts[0];
                String destId = parts[1];
                int weight = Integer.parseInt(parts[2]);

                Node source = getOrCreateNode(graph, srcId);
                Node destination = getOrCreateNode(graph, destId);

                graph.addEdge(source, destination, weight);
            }
            System.out.println(">> Grafo construido correctamente en memoria.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error crítico leyendo el archivo: " + e.getMessage());
        }

        return graph;
    }

    private Node getOrCreateNode(Graph graph, String id) {
        if (nodeCache.containsKey(id)) {
            return nodeCache.get(id);
        }

        for (Node n : graph.getNodes()) {
            if (n.getId().equals(id)) {
                nodeCache.put(id, n);
                return n;
            }
        }

        Node newNode = new Node(id);
        graph.addNode(newNode);
        nodeCache.put(id, newNode);
        return newNode;
    }

    public Node findNodeById(Graph graph, String id) {
        for(Node n : graph.getNodes()){
            if(n.getId().equals(id)) return n;
        }
        return null;
    }
}