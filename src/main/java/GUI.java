import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class GUI extends JFrame {
    public JFrame frame;
    public JPanel panelLeft, panelRight, panelR, panelP;
    public JLabel title;
    public JButton button;
    public int count = 1, tempoUsoRecurso, tempoSolicitacaoRecurso;
    private List<Processo> processos = new ArrayList<>();

    public void criaRecursos(List<Recurso> recursos) {
        panelR = new JPanel(new GridLayout(1, 10));
        for (Recurso recurso : recursos) {
//            JLabel label = new JLabel(r.getNome());
//            label.setIcon(new ImageIcon("/image/Teclado.png"));
//            panelR.add(label);
            // TODO: Add Graph structure here...
//            panelR.add(new JLabel(recurso.getNome()));
        }
        panelRight.add(panelR, BorderLayout.NORTH);
    }
    public void criaProcesso(List<Recurso> recursos) {
        if(count<10){
            guiTempoUso();
            guiTempoSolicitacaoRecurso();
//            JLabel processo = new JLabel("Processo "+ count);
//            panelP.add(processo);


            Processo processoObjeto = new Processo(count, tempoUsoRecurso, tempoSolicitacaoRecurso, recursos);
            processoObjeto.start();
            processos.add(processoObjeto);
            panelRight.add(panelP, BorderLayout.SOUTH);
//            panelRight.repaint();
//            panelRight.revalidate();
            count++;

            Vertex<Processo> v = new Vertex<>("P" + count, false, processoObjeto);
            GraphConcurrent graphConcurrent = GraphConcurrent.getInstance();
            Graph<Vertex<?>, Edge> graph = graphConcurrent.getInternalGraph();
            graphConcurrent.acquire();
            graph.addVertex(v);
            graphConcurrent.release();

            processoObjeto.setVertexRepresentation(v);

            // TODO: How the fuck?
//            Point source = getCenterLocation(processo);
//            Point target = getCenterLocation(panelP);
//
//            JPanel panel = new JPanel() {
//                @Override
//                protected void paintComponent(Graphics g) {
//                    super.paintComponent(g);
//
//                    // Desenha uma linha do ponto de origem (Processo) ao ponto de destino (Recurso)
//                    // TO-DO: Adicionar uma seta ou não. // SIM :(
//                    g.drawLine(source.x, source.y, target.x, target.y);
//                }
//            };
//
//            panelP.add(panel);
        } else {
            JOptionPane.showMessageDialog(this, "Já chegou ao limite de processos.");
        }
    }
    public void criaLog(){
        panelLeft.setBounds(0 ,0 , Integer.MAX_VALUE, 100);
        panelLeft.setBackground(Color.lightGray);
        title = new JLabel("Detecção de Deadlock");
        title.setBackground(Color.LIGHT_GRAY);
        title.setFont(new Font("Tahoma", Font.PLAIN, 30));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panelLeft.add(title, BorderLayout.NORTH);
    }
    public void criaBotao () {
        button = new JButton("Criar Processo");
        panelLeft.add(button, BorderLayout. SOUTH);
    }
    public void guiTempoUso(){
        tempoUsoRecurso = Integer.parseInt(JOptionPane.showInputDialog("ΔTu do processo "+ count +":"));
    }
    public void guiTempoSolicitacaoRecurso (){
        tempoSolicitacaoRecurso = Integer.parseInt(JOptionPane.showInputDialog("ΔTs do processo "+ count +":"));
    }
    public GUI(List<Recurso> recursos) {
        panelLeft = new JPanel(new BorderLayout());
        panelRight = new JPanel(new BorderLayout());
        panelP = new JPanel(new GridLayout(1, 10));

        criaRecursos(recursos);
        criaLog();
        criaBotao();

        frame = new JFrame("Janela da Aplicação");
        frame.getContentPane().setBackground(Color.WHITE);

        button.addActionListener(e-> criaProcesso(recursos));

        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(panelLeft, BorderLayout.WEST);
        frame.getContentPane().add(panelRight, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    class JairLulonaro {
        Vertex<?> vertex;
        JLabel comp;

        public JairLulonaro(Vertex<?> vertex, JLabel comp) {
            this.vertex = vertex;
            this.comp = comp;
        }
    }

    int offset = 0;
    public void updateGraphGUI() {
        panelP.removeAll();
        panelR.removeAll();

        GraphConcurrent graphConcurrent = GraphConcurrent.getInstance();
        Graph<Vertex<?>, Edge> graph = graphConcurrent.getInternalGraph();
        graphConcurrent.acquire();

        Set<Vertex<?>> vertices = graph.vertexSet();

        List<Vertex<?>> collect = vertices.stream()
            .filter(Vertex::isResource)
            .toList();

        List<JairLulonaro> jairLulonaros = new ArrayList<>();
        for (Vertex<?> recurso : collect) {
            JLabel comp = new JLabel(((Recurso) recurso.getItem()).getNome());
            panelR.add(comp);

            JairLulonaro jairLulonaro = new JairLulonaro(recurso, comp);
            jairLulonaros.add(jairLulonaro);
        }

        List<Vertex<?>> proc = vertices.stream()
            .filter(v -> !v.isResource())
            .toList();

        for (Vertex<?> processo : proc) {
            JLabel procs = new JLabel(((Processo) processo.getItem()).getName());
            panelP.add(procs);

            JairLulonaro jairLulonaro = new JairLulonaro(processo, procs);
            jairLulonaros.add(jairLulonaro);
        }

        panelRight.repaint();
        panelRight.revalidate();

        Set<Edge> edges = graph.edgeSet();
        for (Edge edge : edges) {
            Vertex<?> edgeSource = graph.getEdgeSource(edge);
            Vertex<?> edgeTarget = graph.getEdgeTarget(edge);

            JairLulonaro jairLulonaro = jairLulonaros
                .stream()
                .filter(i->i.vertex.equals(edgeSource))
                .findFirst().orElseThrow();

            JairLulonaro jairLulonaro2 = jairLulonaros
                .stream()
                .filter(i->i.vertex.equals(edgeTarget))
                .findFirst().orElseThrow();

            Point source = getCenterLocation(jairLulonaro.comp);
            Point target = getCenterLocation(jairLulonaro2.comp);

            // TODO: Remember to draw what you want
            if (edge.isConnected()) {
                panelRight.getGraphics().drawLine(offset, offset, 100, 100);
                offset+=15;
            }

        }

        graphConcurrent.release();

    }

    private Point getCenterLocation(Component component) {
        int x = component.getX();
        int y = component.getY();
        return new Point(x, y);
    }
}
