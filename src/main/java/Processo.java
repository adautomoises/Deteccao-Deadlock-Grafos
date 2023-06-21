import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Processo extends Thread {
    private int ID, tempoUsoRecurso, tempoSolicitaRecurso, clockS = 0;
    private final List<Recurso> recursos;
    private List<Recurso> recursosEmUso = new ArrayList<>();
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public Processo (int ID, int tempoUsoRecurso, int tempoSolicitaRecurso, List<Recurso> recursos){
        this.ID = ID;
        this.tempoUsoRecurso = tempoUsoRecurso;
        this.tempoSolicitaRecurso = tempoSolicitaRecurso;
        this.recursos = recursos;
    }

    public void solicitaRecurso() throws InterruptedException {
        Random random = new Random();
        int index = random.nextInt(recursos.size());
        if(recursosEmUso.stream().anyMatch(recurso -> recurso.getID() == index)){
            System.out.println("Recurso em posse do processo " + getID());
        } else {
            Recurso recurso = recursos.get(index);
            System.out.println("processo " + getID()+" tentando pegar " + recurso.getNome());
            recurso.getSemaphore().acquire();
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


