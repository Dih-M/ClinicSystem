import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchPatientAppointment extends JFrame {

    private JTextField cpfField;

    public SearchPatientAppointment() {
        super("Buscar consultas por CPF");
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(2, 2, 10, 10));

        contentPane.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        contentPane.add(cpfField);

        JButton btnSearch = new JButton("Buscar");
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchByCPF();
            }
        });
        contentPane.add(btnSearch);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setBackground(new Color(231, 76, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnCancel);
    }

    private void searchByCPF() {
        String cpf = cpfField.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um CPF.");
            return;
        }

        List<Appointment> appointments = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM appointments WHERE patient_id = (SELECT id FROM patients WHERE cpf = ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, cpf);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int patientId = rs.getInt("patient_id");
                    int psychologistId = rs.getInt("psychologist_id");
                    Date date = rs.getDate("appointment_date");
                    Time time = rs.getTime("appointment_time");
                    String location = rs.getString("location");
                    appointments.add(new Appointment(id, patientId, psychologistId, date, time, location));
                }

                if (!appointments.isEmpty()) {
                    AppointmentsList appointmentsListFrame = new AppointmentsList(appointments, this);
                    centerWindow(appointmentsListFrame); // Centralizar a janela
                    appointmentsListFrame.setVisible(true);
                    appointmentsListFrame.toFront(); // Trazer a janela para frente
                } else {
                    JOptionPane.showMessageDialog(this, "Nenhuma consulta encontrada para o CPF fornecido.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao buscar consultas.");
        }
    }

    private void centerWindow(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = window.getSize();
        window.setLocation((screenSize.width - windowSize.width) / 2, (screenSize.height - windowSize.height) / 2);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SearchPatientAppointment frame = new SearchPatientAppointment();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
