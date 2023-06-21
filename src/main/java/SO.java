import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.Set;

public class SO extends Thread{
    private int tempoDeadlock, clock = 0;
    private final GUI gui;
    public  SO(int tempoDeadlock, GUI gui){
        this.tempoDeadlock = tempoDeadlock;
        this.gui = gui;
    }

    @Override
    public void run(){
        while (true){
            try {
              gui.updateGraphGUI();
              sleep(500);
//                clock++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            GraphConcurrent instance = GraphConcurrent.getInstance();
            instance.acquire();
            Graph<Vertex<?>, Edge> internalGraph = instance.getInternalGraph();
            /// Calculos e mais calculos...
            instance.release();
            CycleDetector<Vertex, Edge> cycleDetector = new CycleDetector<>((Graph<Vertex, Edge>) ((Object) internalGraph));
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
//            if(tempoDeadlock == clock){
//                System.out.println("Detecção de Deadlock ... tempo: "+tempoDeadlock);
//                clock = 0 ;
//            }
        }
    }
    public static void main(String[] args) {
        // Cria um teste.grafo direcionado
        Graph<Vertex, Edge> graph = new SimpleDirectedGraph<>(Edge.class);

        // Adiciona vértices (processos e recursos)
        Vertex processA = new Vertex("A", false, null);
        Vertex processB = new Vertex("B", false, null);
        Vertex processC = new Vertex("C", false, null);
        Vertex resourceX = new Vertex("X", true, null);
        Vertex resourceY = new Vertex("Y", true, null);

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
//        graph.addEdge(resourceX, processB, new Edge(false));

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