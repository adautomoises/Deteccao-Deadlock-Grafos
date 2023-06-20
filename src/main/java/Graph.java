import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import javax.swing.*;

public class Graph {
    public org.jgrapht.Graph<JLabel, DefaultEdge> graph;

    public Graph (){
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
    }

//    panel = new JPanel() {
//        @Override
//        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            for (DefaultEdge edge : graph.edgeSet()) {
//                JLabel source = graph.getEdgeSource(edge);
//                JLabel target = graph.getEdgeTarget(edge);
//
//                Point sourceCenter = getCenterLocation(source);
//                Point targetCenter = getCenterLocation(target);
//
//                // Desenha uma linha do ponto de origem (Processo) ao ponto de destino (Recurso)
//                // TO-DO: Adicionar uma seta ou não.
//                g.drawLine(sourceCenter.x, sourceCenter.y, targetCenter.x, targetCenter.y);
//            }
//        }
//    };

//    private Point getCenterLocation(Component component) {
//        int x = component.getX() + component.getWidth() / 2;
//        int y = component.getY() + component.getHeight() / 2;
//        return new Point(x, y);
//    }
}
