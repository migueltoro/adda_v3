package us.lsi.alg.caballo2;


import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;

public class KnightTour {

    private static final int BOARD_SIZE = 4;
    private static final int[][] MOVES = {
            {-2, -1}, {-1, -2}, {1, -2}, {2, -1},
            {2, 1}, {1, 2}, {-1, 2}, {-2, 1}
    };

    public static void main(String[] args) {
        Graph<String, DefaultEdge> chessGraph = new SimpleGraph<>(DefaultEdge.class);

        // Add vertices
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                chessGraph.addVertex(encodePosition(row, col));
            }
        }

        // Add edges
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                for (int[] move : MOVES) {
                    int newRow = row + move[0];
                    int newCol = col + move[1];
                    if (isValidPosition(newRow, newCol)) {
                        chessGraph.addEdge(encodePosition(row, col), encodePosition(newRow, newCol));
                    }
                }
            }
        }

        // Find Hamiltonian Path (custom implementation or heuristic)
        List<String> path = new ArrayList<>();
        if (findHamiltonianPath(chessGraph, encodePosition(0, 0), path, new boolean[chessGraph.vertexSet().size()])) {
            System.out.println("Hamiltonian Path found:");
            System.out.println(path);
        } else {
            System.out.println("No Hamiltonian Path exists.");
        }
    }

    private static boolean findHamiltonianPath(Graph<String, DefaultEdge> graph, String current, List<String> path, boolean[] visited) {
        path.add(current);
        visited[Integer.parseInt(current)] = true;

        if (path.size() == graph.vertexSet().size()) {
            return true;
        }

        for (DefaultEdge edge : graph.edgesOf(current)) {
            String neighbor = graph.getEdgeTarget(edge).equals(current) ? graph.getEdgeSource(edge) : graph.getEdgeTarget(edge);
            if (!visited[Integer.parseInt(neighbor)]) {
                if (findHamiltonianPath(graph, neighbor, path, visited)) {
                    return true;
                }
            }
        }

        path.remove(path.size() - 1);
        visited[Integer.parseInt(current)] = false;
        return false;
    }

    private static String encodePosition(int row, int col) {
        return String.valueOf(row * BOARD_SIZE + col);
    }

    private static boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
}