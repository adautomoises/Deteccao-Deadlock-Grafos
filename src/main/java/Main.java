import java.awt.*;
public class Main extends Thread {
    public static void main(String[] args) {
        System.out.println("S.O. Projeto (II) - Detecção de Deadlock utilizando Grafos");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface interfaces = new Interface();
                    interfaces.guiQntRecursos("Quantidade de Recursos: ");
                    interfaces.guiTempoDeadlock("ΔT do Sistema Operacional: ");
                    interfaces.guiNomeRecursos();
                    interfaces.programa();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}