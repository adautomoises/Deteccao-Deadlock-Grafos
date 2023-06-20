import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
            recursos.add(new Recurso(i, nomeRecursos.get(i), label));
        }
    }
//            recursos.add(new Recurso(i+1, JOptionPane.showInputDialog("Nome do recurso " + (i+1) +":")));
    public void getRecursos () {
        for (Recurso r : recursos){
            System.out.println(r.getNome() + " : " + r.getID());
        }
    }
    public void programa(){
        new GUI(recursos);
        new SO(tempoDeadlock).start();
    }
}