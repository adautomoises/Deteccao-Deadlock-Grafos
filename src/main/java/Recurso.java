import javax.swing.*;
import java.util.concurrent.Semaphore;

public class Recurso {
    private int ID;
    private String nome;
    private JLabel jlabel;
    private Semaphore semaphore;
    private long clock;

    public void setJlabel(JLabel jlabel) {
        this.jlabel = jlabel;
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public long getClock() {
        return clock;
    }

    public void setClock(long clock) {
        this.clock = clock;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Semaphore getS() {
        return semaphore;
    }

    public void setS(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public Recurso (int ID, String nome, JLabel jlabel){
        this.ID = ID;
        this.nome = nome;
        this.jlabel = jlabel;
        semaphore = new Semaphore(1);
    }

    public JLabel getJlabel() {
        return jlabel;
    }
}
