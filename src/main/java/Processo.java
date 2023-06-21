import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jgrapht.Graph;

public class Processo extends Thread {
    private int ID, tempoUsoRecurso, tempoSolicitaRecurso, clockS = 0;
    private final List<Recurso> recursos;
    private List<Recurso> recursosEmUso = new ArrayList<>();

    private Vertex<Processo> vertexRepresentation;
    private final org.jgrapht.Graph<Vertex<?>, Edge> graph;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public Processo (int ID, int tempoUsoRecurso, int tempoSolicitaRecurso, List<Recurso> recursos,
        Graph<Vertex<?>, Edge> graph){
        this.ID = ID;
        this.tempoUsoRecurso = tempoUsoRecurso;
        this.tempoSolicitaRecurso = tempoSolicitaRecurso;
        this.recursos = recursos;
        this.graph = graph;
    }

    public void setVertexRepresentation(Vertex<Processo> vertexRepresentation) {
        this.vertexRepresentation = vertexRepresentation;
    }

    public void solicitaRecurso() throws InterruptedException {
        Random random = new Random();
        int index = random.nextInt(recursos.size());
        if(recursosEmUso.stream().anyMatch(recurso -> recurso.getID() == index)){
            System.out.println("Recurso em posse do processo " + getID());
        } else {
            Recurso recurso = recursos.get(index);
            System.out.println("processo " + getID()+" tentando pegar " + recurso.getNome());

            // Here we will be playing with the graphs
            graph.addEdge(vertexRepresentation, recurso.getVertexRepresentation(), new Edge(false));
            recurso.getSemaphore().acquire();
            graph.removeEdge(vertexRepresentation, recurso.getVertexRepresentation());
            graph.addEdge(vertexRepresentation, recurso.getVertexRepresentation(), new Edge(true));

            recursosEmUso.add(recurso); // TODO: TALVEZ SEJA INTERESSANTE IDENTIFICAR O RECURSO
            System.out.println(recurso.getNome() + " foi pegue pelo processo " + getID());
            clockS = 0;
        }

    }

    public void liberaRecurso(){
        List<Recurso> lixeira = new ArrayList<>();
        for (Recurso recurso : recursosEmUso) {
            if(recurso.getClock() == tempoUsoRecurso){
                recurso.setClock(0);
                lixeira.add(recurso);

                recurso.getSemaphore().release();
                graph.removeEdge(vertexRepresentation, recurso.getVertexRepresentation());

                System.out.println(recurso.getNome() + " foi liberado pelo processo " + getID());
            }
        }
        recursosEmUso.removeAll(lixeira);
    }

    @Override
    public void run(){
        while (true) {
            try {
                Thread.sleep(1000);
                clockS++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Recurso r : recursosEmUso){
                r.setClock(r.getClock() + 1);
                System.out.println("processo " + getID()+ " tempo do recurso "+ r.getNome() +" : " + r.getClock());
            }

        //Liberar Recurso
            liberaRecurso();

        //Solicitar Recurso
            if(clockS >= tempoSolicitaRecurso){
                try {
                    solicitaRecurso();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


