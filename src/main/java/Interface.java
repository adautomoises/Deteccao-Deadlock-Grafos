import javax.swing.*;
import java.awt.*;

public class Interface extends JFrame{
    public JFrame frame;
    public JPanel panel;
    public JLabel title;
    public Interface() {
        this.initialize();
    }
    public void initialize() {
            frame = new JFrame("Janela da Aplicação");
            frame.getContentPane().setBackground(Color.WHITE);
            frame.setBounds(0 ,0 , 800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBounds(0 ,0 , 800, 600);
            panel.setBackground(Color.lightGray);

            title = new JLabel("Detecção de Deadlock");
            title.setBackground(Color.LIGHT_GRAY);
            title.setFont(new Font("Tahoma", Font.PLAIN, 30));
            title.setForeground(Color.BLACK);
            title.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(title, BorderLayout.NORTH);
            frame.getContentPane().add(panel);
    }
}
