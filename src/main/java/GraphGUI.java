import javax.swing.*;
import java.awt.*;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.alg.cycle.CycleDetector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

public class GraphGUI extends JFrame {
    private static Graph<JLabel, DefaultEdge> graph;
    private JPanel panel;

    private final int V = 0;
    private final List<List<Integer>> adj = null;

    public GraphGUI() {
        graph = new SimpleGraph<>(DefaultEdge.class);

        // Componentes JLabel representando Processos e Recursos
        // TO-DO: Adicionar um ícone para cada um, para melhor visualização
        JLabel P1 = new JLabel("Processo 1");
        JLabel P2 = new JLabel("Processo 2");
        JLabel P3 = new JLabel("Processo 3");
        JLabel R1 = new JLabel("Recurso 1");
        JLabel R2 = new JLabel("Recurso 2");
        JLabel R3 = new JLabel("Recurso 3");

        // Criando os vértices (Pontos de Conexões)
        graph.addVertex(P1);
        graph.addVertex(P2);
        graph.addVertex(P3);
        graph.addVertex(R1);
        graph.addVertex(R2);
        graph.addVertex(R3);

        // Adicionando Conexão entre Processo e Recurso
        graph.addEdge(P1, R1);
        graph.addEdge(P1, R2);
        graph.addEdge(P1, R3);

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (DefaultEdge edge : graph.edgeSet()) {
                    JLabel source = graph.getEdgeSource(edge);
                    JLabel target = graph.getEdgeTarget(edge);

                    Point sourceCenter = getCenterLocation(source);
                    Point targetCenter = getCenterLocation(target);

                    // Desenha uma linha do ponto de origem (Processo) ao ponto de destino (Recurso)
                    // TO-DO: Adicionar uma seta ou não.
                    g.drawLine(sourceCenter.x, sourceCenter.y, targetCenter.x, targetCenter.y);
                }
            }
        };

        // Adicionando os componentes ao painel
        panel.setLayout(null);
        panel.add(P1);
        panel.add(P2);
        panel.add(P3);
        panel.add(R1);
        panel.add(R2);
        panel.add(R3);

        // Posições dos componentes no painel
        P1.setBounds(50, 50, 100, 30);
        P2.setBounds(200, 50, 100, 30);
        P3.setBounds(350, 50, 100, 30);
        R1.setBounds(50, 200, 100, 30);
        R2.setBounds(200, 200, 100, 30);
        R3.setBounds(350, 200, 100, 30);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private Point getCenterLocation(Component component) {
        int x = component.getX() + component.getWidth() / 2;
        int y = component.getY() + component.getHeight() / 2;
        return new Point(x, y);
    }

    public static void main(String[] args) {
        new GraphGUI();
    }
}
