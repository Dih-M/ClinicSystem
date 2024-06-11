import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainDashboard extends JFrame {

    public MainDashboard() {
        initialize();
    }

    private void initialize() {
        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 400, 200);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(2, 1, 10, 20));

        JButton btnRegister = new JButton("Registrar");
        btnRegister.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(52, 152, 219)); // Cor de fundo azul
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RegisterScreen registerScreen = new RegisterScreen(); // Instancia a tela de registro
                registerScreen.setVisible(true); // Exibe a tela de registro
                dispose(); // Fecha a tela atual (dashboard)
            }
        });
        contentPane.add(btnRegister);

        JButton btnViewAgenda = new JButton("Ver Consultas");
        btnViewAgenda.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnViewAgenda.setForeground(Color.WHITE);
        btnViewAgenda.setBackground(new Color(39, 174, 96)); // Cor de fundo verde
        btnViewAgenda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AppointmentScreen appointmentFrame = new AppointmentScreen(); // Instancia a tela de agendamento
                appointmentFrame.setVisible(true); // Exibe a tela de agendamento
                dispose(); // Fecha a tela atual (dashboard)
            }
        });
        contentPane.add(btnViewAgenda);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainDashboard frame = new MainDashboard();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
