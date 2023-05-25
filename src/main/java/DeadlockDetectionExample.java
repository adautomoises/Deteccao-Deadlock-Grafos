import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.Set;

class Vertex {
    private final String name;
    private final boolean isResource;

    Vertex(String name, boolean isResource) {
        this.name = name;
        this.isResource = isResource;
    }

    String getName() {
        return name;
    }

    boolean isResource() {
        return isResource;
    }
}

class Edge extends DefaultEdge {
    @Override
    public String toString() {
        return "(" + getSource() + " : " + getTarget() + ")";
    }
}

public class DeadlockDetectionExample {
    public static void main(String[] args) {
        // Cria um grafo direcionado
        Graph<Vertex, Edge> graph = new SimpleDirectedGraph<>(Edge.class);

        // Adiciona vértices (processos e recursos)
        Vertex processA = new Vertex("A", false);
        Vertex processB = new Vertex("B", false);
        Vertex processC = new Vertex("C", false);
        Vertex resourceX = new Vertex("X", true);
        Vertex resourceY = new Vertex("Y", true);

        graph.addVertex(processA);
        graph.addVertex(processB);
        graph.addVertex(processC);
        graph.addVertex(resourceX);
        graph.addVertex(resourceY);

        // Adiciona arestas que representam alocação de recursos
        graph.addEdge(processA, resourceX);
        graph.addEdge(processB, resourceY);
        graph.addEdge(processC, resourceX);
        graph.addEdge(resourceY, processA); // Cria um deadlock
        graph.addEdge(resourceX, processB); // Cria um deadlock

        // Detecta ciclos
        CycleDetector<Vertex, Edge> cycleDetector = new CycleDetector<>(graph);
        Set<Vertex> deadlockVertices = cycleDetector.findCycles();

        if (!deadlockVertices.isEmpty()) {
            System.out.println("Deadlock detectado. Processos em deadlock:");
            for (Vertex vertex : deadlockVertices) {
                if (!vertex.isResource()) {
                    System.out.println("Processo: " + vertex.getName());
                }
            }
        } else {
            System.out.println("Não há deadlock no sistema.");
        }
    }
}