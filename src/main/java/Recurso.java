public class Recurso {
    private String nome;
    private int ID;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
