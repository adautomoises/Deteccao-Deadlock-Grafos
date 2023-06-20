public class Recurso {
    private int ID;
    private int Clock;
    private String nome;
    private Boolean sendoUsado = false;
    public Boolean getSendoUsado() {
        return sendoUsado;
    }
    public void setSendoUsado(Boolean sendoUsado) {
        this.sendoUsado = sendoUsado;
    }
    public String getNome() {
        return nome;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public Recurso (int ID, String nome){
        this.ID = ID;
        this.nome = nome;
    }

    public int getClock() {
        return Clock;
    }

    public void setClock(int clock) {
        Clock = clock;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
