import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Processo extends Thread {
    private int ID, tempoUsoRecurso, tempoSolicitaRecurso, clockU = 0, clockS = 0;
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
        System.out.println("processo " + getID()+" tenta pegar " + recursos.stream()
                .skip(index)
                .findFirst()
                .map(Recurso::getNome)
                .orElse("Não há recurso"));
        if(recursosEmUso.stream().anyMatch(recurso -> recurso.getID() == index)){
            System.out.println("Recurso em Uso...");
            Recurso recurso = recursos.stream()
                    .findFirst()
                    .orElse(null);
            recursosEmUso.add(recurso);
        }
        if(recursosEmUso.isEmpty()){
            Recurso recurso = recursos.stream()
                    .skip(ID)
                    .findFirst()
                    .orElse(null);
            recursosEmUso.add(recurso);
            recursosEmUso.stream().anyMatch(r -> r.getSemaphore() = 0);
            System.out.println(recurso.getNome() + " foi pegue pelo processo " + getID());
        }
         else {
            System.out.println("Recurso em Uso...");
        }
        clockS = 0;
    }

    public void utilizaRecurso() throws InterruptedException {
        if(!recursosEmUso.isEmpty()){
            recursosEmUso.get(0).getSemaphore().release();
            recursosEmUso.get(0).setClock(0);
            System.out.println(recursosEmUso.stream().findFirst().get().getNome() + " foi liberado pelo processo " + getID());
            recursosEmUso.remove(0);
        }
        clockU = 0;
    }

    @Override
    public void run(){
        while (true) {
            try {
                Thread.sleep(1000);
                clockU++;
                clockS++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Recurso r : recursosEmUso){
                r.setClock(r.getClock() + 1);
                System.out.println("tempo do recurso "+ r.getNome() +" : " + r.getClock());
            }

        //Usar Recurso
            if(tempoUsoRecurso == clockU){
                try {
                    utilizaRecurso();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        //Solicitar Recurso
            if(tempoSolicitaRecurso == clockS){
                try {
                    solicitaRecurso();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


