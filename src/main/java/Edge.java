import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {
    @Override
    public String toString() {
        return "(" + getSource() + " : " + getTarget() + ")";
    }
}
