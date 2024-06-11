import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow extends JFrame {

    public WelcomeWindow() {
        initialize();
    }

    private void initialize() {
        setTitle("Bem-Vindo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300); // Define o tamanho da janela
        getContentPane().setBackground(Color.WHITE); // Define o fundo da janela como branco

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(Color.WHITE); // Define o fundo do painel como branco

        JLabel lblWelcome = new JLabel("Bem-vindo");
        lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblWelcome.setForeground(Color.BLACK); // Define a cor do texto como preto
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblWelcome, BorderLayout.CENTER);

        JButton btnEnter = new JButton("Entrar");
        btnEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainDashboard mainDashboard = new MainDashboard();
                mainDashboard.setVisible(true);
                dispose(); // Fecha a janela de boas-vindas
            }
        });
        btnEnter.setBackground(new Color(52, 152, 219)); // Define a cor de fundo do botão
        btnEnter.setForeground(Color.WHITE); // Define a cor do texto do botão
        btnEnter.setFont(new Font("Tahoma", Font.PLAIN, 18));
        contentPane.add(btnEnter, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WelcomeWindow frame = new WelcomeWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
