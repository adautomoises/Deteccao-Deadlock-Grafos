import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {

    private final boolean isConnected;

    public Edge(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public Edge() {
        this(false);
    }

    @Override
    public String toString() {
        return "(" + getSource() + " : " + getTarget() + ")";
    }
}
