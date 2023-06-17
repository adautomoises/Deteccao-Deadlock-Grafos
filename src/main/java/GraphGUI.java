import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

public class GraphGUI extends JFrame {
    public Graph<JLabel, DefaultEdge> graph;
    public JFrame frame;
    public JPanel panelLeft, panelRight, panelR, panelP, panel;
    public JLabel title;
    public JButton button;
    public int count = 1;
    public int tempoUsoRecurso;
    public int tempoSolicitacaoRecurso;
    private  List<Processo> processos = new ArrayList<>();

    public List<JLabel> criaRecursos (List<Recurso> recursos) {
        panelR = new JPanel(new GridLayout(1,10));
        for (Recurso r : recursos){
            JLabel recurso = new JLabel(r.getNome());
            graph.addVertex(recurso);
            panelR.add(recurso);
        }
        panelRight.add(panelR, BorderLayout.NORTH);
        return recursos.stream().map(e -> new JLabel(e.getNome())).collect(Collectors.toList());
    }

    public void criaProcesso () {
        if(count<10){
            guiTempoUso();
            guiTempoSolicitacaoRecurso();
            JLabel processo = new JLabel("Processo "+ count);
            graph.addVertex(processo);
            panelP.add(processo);
            processos.add(new Processo(count, tempoUsoRecurso, tempoSolicitacaoRecurso));
            panelRight.add(panelP, BorderLayout.SOUTH);
            panelRight.repaint();
            panelRight.revalidate();
            count++;
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


    public GraphGUI(List<Recurso> recursos) {
        graph = new SimpleDirectedGraph<>(DefaultEdge.class);
        panelLeft = new JPanel(new BorderLayout());
        panelRight = new JPanel(new BorderLayout());
        panelP = new JPanel(new GridLayout(1, 10));

        criaRecursos(recursos);
        criaLog();
        criaBotao();

        frame = new JFrame("Janela da Aplicação");
        frame.getContentPane().setBackground(Color.WHITE);

        button.addActionListener(e-> criaProcesso());

//        Adicionando Conexão entre Processo e Recurso
//        graph.addEdge(P1, R1);
//        graph.addEdge(P1, R2);
//        graph.addEdge(P1, R3);

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

        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(panelLeft, BorderLayout.WEST);
        frame.getContentPane().add(panelRight, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    private Point getCenterLocation(Component component) {
        int x = component.getX() + component.getWidth() / 2;
        int y = component.getY() + component.getHeight() / 2;
        return new Point(x, y);
    }
}
