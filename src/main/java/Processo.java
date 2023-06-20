import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Processo extends Thread {
    private int ID, tempoUsoRecurso, tempoSolicitaRecurso, clock = 0, clockS = 0, index;
    private final List<Recurso> recursos;
    private final List<Semaphore> semaforoRecursos;
    private List<Recurso> recursosEmUso = new ArrayList<>();
    private List<Integer> recursosIndexEmUso = new ArrayList<>();
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public Processo (int ID, int tempoUsoRecurso, int tempoSolicitaRecurso, List<Recurso> recursos, List<Semaphore> semaforoRecursos){
        this.ID = ID;
        this.tempoUsoRecurso = tempoUsoRecurso;
        this.tempoSolicitaRecurso = tempoSolicitaRecurso;
        this.recursos = recursos;
        this.semaforoRecursos = semaforoRecursos;
    }
    @Override
    public void run(){
        while (true) {
        // Tempo de ciclo (contagem de 1s)
        try {
            sleep(1000);

            clock++;
            clockS++;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Usar Recurso
            if(tempoUsoRecurso == clock){
                if(!recursosEmUso.isEmpty()){
                    System.out.println(recursosEmUso.stream().findFirst().get().getNome() + " foi liberado pelo processo " + getID());
                    semaforoRecursos.get(recursosIndexEmUso.get(0)).release();
                    recursosEmUso.remove(0);
                    recursosIndexEmUso.remove(0);
                }
                clock = 0;
            }
        //Solicitar Recurso
            if(tempoSolicitaRecurso == clockS){
                Random random = new Random();
                index = random.nextInt(recursos.size());
                System.out.println("processo " + getID()+" tenta pegar " + recursos.stream().skip(index).findFirst().get().getNome());

                if(!(recursos.stream().skip(index).findFirst().get().getSendoUsado())){
                    Recurso recurso = recursos.stream()
                            .skip(index)
                            .findFirst()
                            .orElse(null);
                    recursos.stream().skip(index).findFirst().get().setSendoUsado(true);
                    recursosEmUso.add(recurso);
                    recursosIndexEmUso.add(index);
                    try {

                        semaforoRecursos.get(index).acquire();
                        System.out.println(recurso.getNome() + " foi pegue pelo processo " + getID());
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    for (Semaphore s : semaforoRecursos){
                        System.out.println(s.availablePermits());
                    }
                    clockS = 0;
                }
                 else {
                    System.out.println("Recurso em Uso...");
                }
            }
        }
    }
}
