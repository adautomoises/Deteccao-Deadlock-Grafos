import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class GraphE extends Thread{
    private String name;
    private String id;

    public GraphE(String name, String id){
        this.name = name;
        this.id = id;
    }

//    @Override
//    public void run(){
//
//    }
    public static void main(String[] args) {
        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        // Adicionar vértices
        String vertex1 = "A";
        String vertex2 = "B";
        String vertex3 = "C";
        String vertex4 = "D";
        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);

        // Adicionar arestas
        graph.addEdge(vertex1, vertex2);
        graph.addEdge(vertex2, vertex3);
        graph.addEdge(vertex3, vertex4);
        graph.addEdge(vertex4, vertex1);

        // Imprimir o grafo
        System.out.println("Grafo:");
        System.out.println(graph);
    }
}
