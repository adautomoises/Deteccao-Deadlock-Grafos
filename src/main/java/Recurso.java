import javax.swing.*;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import org.jgrapht.Graph;

public class Recurso {
    private int ID;
    private String nome;
    private JLabel jlabel;
    private Semaphore semaphore;
    private long clock;
    private Boolean isUsed = false;
    private Processo process = null;

    private Vertex<Recurso> vertexRepresentation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recurso recurso = (Recurso) o;
        return ID == recurso.ID;
    }
    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

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
    public JLabel getJlabel() {
        return jlabel;
    }

    public Recurso (int ID, String nome, JLabel jlabel){
        this.ID = ID;
        this.nome = nome;
        this.jlabel = jlabel;
        semaphore = new Semaphore(1);
    }

    public void setVertexRepresentation(Vertex<Recurso> vertexRepresentation) {
        this.vertexRepresentation = vertexRepresentation;
    }

    public Vertex<Recurso> getVertexRepresentation() {
        return vertexRepresentation;
    }
}
