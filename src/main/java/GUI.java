import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

public class GUI extends JFrame {
    public JFrame frame;
    public JPanel panelLeft, panelRight, panelR, panelP;
    public JLabel title;
    public JButton button;
    public int count = 1, tempoUsoRecurso, tempoSolicitacaoRecurso;
    private List<Processo> processos = new ArrayList<>();
    private List<Semaphore> semaforoRecursos = new ArrayList<>();

    public List<JLabel> criaRecursos (List<Recurso> recursos) {
        panelR = new JPanel(new GridLayout(1,10));
        for (Recurso r : recursos){
            JLabel recurso = new JLabel(r.getNome());
            panelR.add(recurso);

            Semaphore semaphore = new Semaphore(1);
            semaforoRecursos.add(semaphore);
        }
        panelRight.add(panelR, BorderLayout.NORTH);
        return recursos.stream().map(e -> new JLabel(e.getNome())).collect(Collectors.toList());
    }

    public void criaProcesso (List<Recurso> recursos) {
        if(count<10){
            guiTempoUso();
            guiTempoSolicitacaoRecurso();
            JLabel processo = new JLabel("Processo "+ count);
            panelP.add(processo);
            new Processo(count, tempoUsoRecurso, tempoSolicitacaoRecurso, recursos, semaforoRecursos).start();
            processos.add(new Processo(count, tempoUsoRecurso, tempoSolicitacaoRecurso, recursos, semaforoRecursos));
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
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}
