import java.awt.*;
public class Main extends Thread {
    public static void main(String[] args) {
        System.out.println("S.O. Projeto (II) - Detecção de Deadlock utilizando Grafos");

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interface janela = new Interface();
                    janela.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}