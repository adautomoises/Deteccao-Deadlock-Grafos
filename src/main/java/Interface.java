import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleDirectedGraph;

public class Interface{
    public List<Recurso> recursos = new ArrayList<>();
    public int qntRecursos, tempoDeadlock;
    List<String> nomeRecursos = List.of(
            "Teclado", "Impressora", "Mouse", "Joystic", "Cd",
            "Disquete", "HD", "Display", "Auto-Falante", "Notebook"
    );
    List<String> imageRecursos = List.of(
            "/image/Teclado.png", "/image/Impressora.png", "/image/Mouse.png", "/image/Joystick.png", "/image/Cd.png",
            "/image/Disquete.png", "/image/HD.png", "/image/Display.png", "/image/Auto-Falante.png", "/image/Notebook.png"
    );

    public Interface () {
    }
    public void guiQntRecursos(String title) {
        qntRecursos = Integer.parseInt(JOptionPane.showInputDialog(title));
    }
    public void guiTempoDeadlock(String title) {
        tempoDeadlock = Integer.parseInt(JOptionPane.showInputDialog(title));
    }
    public ImageIcon getRecursoIcon(int i) {
        String imagePath = imageRecursos.get(i);
        File imageFile = new File(imagePath);

        System.out.println(i + " : " + imagePath);
        System.out.println(imageFile.getAbsolutePath());

        assert imageFile.exists();
        return new ImageIcon(imageFile.getAbsolutePath());
    }
    public void guiNomeRecursos() {
        for(int i = 0; i < this.qntRecursos; i++){
            JLabel label = new JLabel(new ImageIcon("/image/Teclado.png"));
            Recurso recurso = new Recurso(i, nomeRecursos.get(i), label);
            recursos.add(recurso);
        }
    }

    public void programa(){
        for (Recurso recurso : recursos) {
            Vertex<Recurso> vertex = new Vertex<>(recurso.getNome(), true, recurso);
            GraphConcurrent graphConcurrent = GraphConcurrent.getInstance();
            Graph<Vertex<?>, Edge> graph = graphConcurrent.getInternalGraph();

            graphConcurrent.acquire();
            graph.addVertex(vertex);
            graphConcurrent.release();

            recurso.setVertexRepresentation(vertex);
        }

        GUI gui = new GUI(recursos);
        SO so = new SO(tempoDeadlock, gui);
        so.start();
    }
}