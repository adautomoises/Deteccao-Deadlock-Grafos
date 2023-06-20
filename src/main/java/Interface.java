import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Interface{
    public List<Recurso> recursos = new ArrayList<>();
    public int qntRecursos, tempoDeadlock;
    public Interface () {
    }
    public void guiQntRecursos(String title) {
        qntRecursos = Integer.parseInt(JOptionPane.showInputDialog(title));
    }
    public void guiTempoDeadlock(String title) {
        tempoDeadlock = Integer.parseInt(JOptionPane.showInputDialog(title));
    }
    public void guiNomeRecursos() {
        for(int i = 0; i < this.qntRecursos; i++){
            recursos.add(new Recurso(i+1, JOptionPane.showInputDialog("Nome do recurso " + (i+1) +":")));
        }
    }
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