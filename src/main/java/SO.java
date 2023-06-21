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

    public void detectaDeadlock() {
        GraphConcurrent instance = GraphConcurrent.getInstance();
        instance.acquire();
        Graph<Vertex<?>, Edge> internalGraph = instance.getInternalGraph();
        CycleDetector<Vertex<?>, Edge> cycleDetector = new CycleDetector<>(internalGraph);
        Set<Vertex<?>> deadlockVertices = cycleDetector.findCycles();
        if (!deadlockVertices.isEmpty()) {
            System.out.println("Deadlock detectado. Processos em deadlock:");
            for (Vertex vertex : deadlockVertices) {
                if (!vertex.isResource()) {
                    System.out.println("Processo: " + vertex.getName());
                }
            }
        }
        System.out.println("Grafo:");
        for (Vertex<?> vertex : internalGraph.vertexSet()) {
            System.out.println("Vértice: " + vertex.getName());
            for (Edge edge : internalGraph.outgoingEdgesOf(vertex)) {
                System.out.println("  - Aresta: " + edge);
            }
        }
        instance.release();
        clock = 0;
    }

    @Override
    public void run(){
        while (true){
            try {
              gui.updateGraphGUI();
              sleep(1000);
              clock++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if(clock == tempoDeadlock){
                detectaDeadlock();
            }
        }
    }
}