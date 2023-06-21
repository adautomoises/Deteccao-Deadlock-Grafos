import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;

public class GUI extends JFrame {
    public JFrame frame;
    public JPanel panelLeft, panelRight, panelR, panelP;
    public JLabel title;
    public JButton button;
    public int count = 1, tempoUsoRecurso, tempoSolicitacaoRecurso;
    private List<Processo> processos = new ArrayList<>();
    private final Graph<Vertex<?>, Edge> graph;

    public void criaRecursos(List<Recurso> recursos) {
        panelR = new JPanel(new GridLayout(1, 10));
        for (Recurso recurso : recursos) {
//            JLabel label = new JLabel(r.getNome());
//            label.setIcon(new ImageIcon("/image/Teclado.png"));
//            panelR.add(label);
            // TODO: Add Graph structure here...
            panelR.add(new JLabel(recurso.getNome()));
        }
        panelRight.add(panelR, BorderLayout.NORTH);
    }
    public void criaProcesso(List<Recurso> recursos) {
        if(count<10){
            guiTempoUso();
            guiTempoSolicitacaoRecurso();
            JLabel processo = new JLabel("Processo "+ count);
            panelP.add(processo);
            Processo processoObjeto = new Processo(
                count, tempoUsoRecurso, tempoSolicitacaoRecurso, recursos,
                graph
            );
            processoObjeto.start();
            processos.add(new Processo(count, tempoUsoRecurso, tempoSolicitacaoRecurso, recursos,
                graph));
            panelRight.add(panelP, BorderLayout.SOUTH);
            panelRight.repaint();
            panelRight.revalidate();
            count++;

            Vertex<Processo> v = new Vertex<>("P" + count, false, processoObjeto);
            graph.addVertex(v);
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
    public GUI(List<Recurso> recursos, Graph<Vertex<?>, Edge> graph) {
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

        this.graph = graph;
    }

    public void updateGraphGUI() {
        panelP.removeAll();
        panelR.removeAll();

        Set<Vertex<?>> vertices = graph.vertexSet();

        List<Recurso> collect = (List<Recurso>) vertices.stream()
            .filter(Vertex::isResource)
            .map(Vertex::getItem)
            .toList();

        for (Recurso recurso : collect) {
            panelR.add(new JLabel(recurso.getNome()));
        }

        List<Processo> proc = (List<Processo>) vertices.stream()
            .filter(v -> !v.isResource())
            .map(Vertex::getItem)
            .toList();

        for (Processo processo : proc) {
            JLabel procs = new JLabel(processo.getName());
            panelP.add(procs);
        }

        panelRight.repaint();
        panelRight.revalidate();
    }

    private Point getCenterLocation(Component component) {
        int x = component.getX() + component.getWidth() / 2;
        int y = component.getY() + component.getHeight() / 2;
        return new Point(x, y);
    }
}
