import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.jgrapht.Graph;

import javax.swing.*;

public class Processo extends Thread {
    private int ID, tempoUsoRecurso, tempoSolicitaRecurso, clockS = 0;
    private JTextArea log;
    private final List<Recurso> recursos;
    private List<Recurso> recursosEmUso = new ArrayList<>();

    private Vertex<Processo> vertexRepresentation;

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public Processo (int ID, int tempoUsoRecurso, int tempoSolicitaRecurso, List<Recurso> recursos, JTextArea log){
        this.ID = ID;
        this.tempoUsoRecurso = tempoUsoRecurso;
        this.tempoSolicitaRecurso = tempoSolicitaRecurso;
        this.recursos = recursos;
        this.log = log;
    }

    public void setVertexRepresentation(Vertex<Processo> vertexRepresentation) {
        this.vertexRepresentation = vertexRepresentation;
    }

    public void solicitaRecurso() throws InterruptedException {
        Random random = new Random();
        int index = random.nextInt(recursos.size());
        if(recursosEmUso.stream().anyMatch(recurso -> recurso.getID() == index)){
            log.insert("Recurso em posse do processo " + getID()+"\n",0);
        } else {
            Recurso recurso = recursos.get(index);
            log.insert("processo " + getID()+" tentando pegar " + recurso.getNome()+"\n",0);

            GraphConcurrent graphConcurrent = GraphConcurrent.getInstance();
            Graph<Vertex<?>, Edge> graph = graphConcurrent.getInternalGraph();

            graphConcurrent.acquire();
            graph.addEdge(vertexRepresentation, recurso.getVertexRepresentation(), new Edge(false));
            graphConcurrent.release();

            recurso.getSemaphore().acquire();

            graphConcurrent.acquire();
            graph.removeEdge(vertexRepresentation, recurso.getVertexRepresentation());
            graph.addEdge(vertexRepresentation, recurso.getVertexRepresentation(), new Edge(true));
            graphConcurrent.release();

            recursosEmUso.add(recurso); // TODO: TALVEZ SEJA INTERESSANTE IDENTIFICAR O RECURSO
            log.insert(recurso.getNome() + " foi pegue pelo processo " + getID()+"\n",0);
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

                GraphConcurrent graphConcurrent = GraphConcurrent.getInstance();
                Graph<Vertex<?>, Edge> graph = graphConcurrent.getInternalGraph();
                graphConcurrent.acquire();
                graph.removeEdge(vertexRepresentation, recurso.getVertexRepresentation());
                graphConcurrent.release();
                log.insert(recurso.getNome() + " foi liberado pelo processo " + getID()+"\n",0);
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


