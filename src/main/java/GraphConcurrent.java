import java.util.concurrent.Semaphore;
import org.jgrapht.graph.DefaultEdge;

public class GraphConcurrent {
    private final org.jgrapht.Graph<Vertex<?>, Edge> internalGraph;
    private final Semaphore semaphore = new Semaphore(1);
    private static GraphConcurrent graph;

    private GraphConcurrent(org.jgrapht.Graph<Vertex<?>, Edge> internalGraph) {
        this.internalGraph = internalGraph;
    }

    public static GraphConcurrent getInstance(){
        if (graph ==null) {
            graph = new GraphConcurrent(new org.jgrapht.graph.SimpleDirectedGraph<>(Edge.class));
        }

        return graph;
    }

    public org.jgrapht.Graph<Vertex<?>, Edge> getInternalGraph() {
        return internalGraph;
    }

    public void acquire() {
        semaphore.acquireUninterruptibly();
    }

    public void release() {
        semaphore.release();
    }
}
