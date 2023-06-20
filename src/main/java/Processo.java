import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import java.time.Duration;
import java.time.Instant;

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

    class Timer {
        private Instant startTime;
        private Duration elapsedTime;
        public Timer() {
            reset();
        }

        public void reset() {
            startTime = Instant.now();
            elapsedTime = Duration.ZERO;
        }

        public void updateElapsedTime() {
            Instant currentTime = Instant.now();
            elapsedTime = elapsedTime.plus(Duration.between(startTime, currentTime));
        }

        public long getElapsedTimeInSeconds() {
            updateElapsedTime();
            return elapsedTime.getSeconds();
        }

        public long getCurrentTimeInSeconds() {
            Instant currentTime = Instant.now();
            return Duration.between(startTime, currentTime).getSeconds();
        }
    }
    Timer timerNowUso = new Timer();
    Timer timerNowSolicita = new Timer();
    @Override
    public void run(){
        while (true) {
            timerNowUso.getElapsedTimeInSeconds();
            timerNowSolicita.getElapsedTimeInSeconds();

        //Usar Recurso
            if(tempoUsoRecurso == timerNowUso.getCurrentTimeInSeconds()){
                System.out.println("tempoUsoRecurso == clock");
                if(!recursosEmUso.isEmpty()){
                    System.out.println(recursosEmUso.stream().findFirst().get().getNome() + " foi liberado pelo processo " + getID());
                    index = recursosIndexEmUso.get(0);
                    semaforoRecursos.get(index).release();
                    recursos.stream().skip(index).findFirst().get().setSendoUsado(false);
                    recursosEmUso.remove(0);
                    recursosIndexEmUso.remove(0);
                }
                timerNowUso.reset();
                System.out.println("--------------semaforo-------------");
                for (Semaphore s : semaforoRecursos){
                    System.out.println(s.availablePermits());
                }
            }
        //Solicitar Recurso
            if(tempoSolicitaRecurso == timerNowSolicita.getCurrentTimeInSeconds()){
                System.out.println("tempoSolicitaRecurso == clockS");
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
                }
                 else {
                    System.out.println("Recurso em Uso...");
                }
                timerNowSolicita.reset();
                for (Semaphore s : semaforoRecursos){
                    System.out.println(s.availablePermits());
                }
            }
        }
    }
}


