import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppointmentScreen extends JFrame {

    public AppointmentScreen() {
        initialize();
    }

    private void initialize() {
        setTitle("Consultas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(4, 1, 10, 20)); // Ajustar para 4 linhas

        JButton btnNew = new JButton("Novo...");
        btnNew.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNew.setForeground(Color.WHITE);
        btnNew.setBackground(new Color(52, 152, 219)); // Cor de fundo azul
        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewAppointment newAppointment = new NewAppointment();
                newAppointment.setVisible(true);
            }
        });
        contentPane.add(btnNew);

        JButton btnManage = new JButton("Gerenciar Consultas...");
        btnManage.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnManage.setForeground(Color.WHITE);
        btnManage.setBackground(new Color(46, 204, 113)); // Cor de fundo verde
        btnManage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchPatientAppointment searchFrame = new SearchPatientAppointment();
                searchFrame.setVisible(true);
            }
        });
        contentPane.add(btnManage);

        JButton btnBackToDashboard = new JButton("Voltar ao Dashboard");
        btnBackToDashboard.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnBackToDashboard.setForeground(Color.WHITE);
        btnBackToDashboard.setBackground(new Color(149, 165, 166)); // Cor de fundo cinza
        btnBackToDashboard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                MainDashboard dashboard = new MainDashboard();
                dashboard.setVisible(true);
            }
        });
        contentPane.add(btnBackToDashboard);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AppointmentScreen frame = new AppointmentScreen();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
