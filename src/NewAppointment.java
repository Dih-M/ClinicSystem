import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class NewAppointment extends JFrame {

    private JTextField txtPatientCPF;
    private JTextField txtPsychologistCPF;
    private JTextField txtDate;
    private JTextField txtTime;
    private JTextField txtLocation;

    public NewAppointment() {
        initialize();
    }

    private void initialize() {
        setTitle("Nova Consulta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 2, 10, 10));

        contentPane.add(new JLabel("CPF do Paciente:"));
        txtPatientCPF = new JTextField();
        contentPane.add(txtPatientCPF);

        contentPane.add(new JLabel("CPF do Psicólogo:"));
        txtPsychologistCPF = new JTextField();
        contentPane.add(txtPsychologistCPF);

        contentPane.add(new JLabel("Data (DD/MM/AAAA):"));
        txtDate = new JTextField();
        contentPane.add(txtDate);

        contentPane.add(new JLabel("Hora (HH:MM):"));
        txtTime = new JTextField();
        contentPane.add(txtTime);

        contentPane.add(new JLabel("Local:"));
        txtLocation = new JTextField();
        contentPane.add(txtLocation);

        JButton btnSave = new JButton("Salvar");
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(new Color(52, 152, 219)); // Cor de fundo azul
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAppointment();
            }
        });
        contentPane.add(btnSave);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(231, 76, 60)); // Cor de fundo vermelha
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnCancel);
    }

    private void saveAppointment() {
        String patientCPF = txtPatientCPF.getText();
        String psychologistCPF = txtPsychologistCPF.getText();
        String dateStr = txtDate.getText();
        String timeStr = txtTime.getText();
        String location = txtLocation.getText();

        int patientId = getIdByCPF(patientCPF, "patients");
        int psychologistId = getIdByCPF(psychologistCPF, "psychologists");

        if (patientId == -1 || psychologistId == -1) {
            JOptionPane.showMessageDialog(this, "CPF inválido.");
            return;
        }

        try {
            // Converte a data de DD/MM/AAAA para AAAA-MM-DD
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate date = LocalDate.parse(dateStr, inputFormatter);
            String formattedDate = date.format(outputFormatter);

            LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ISO_LOCAL_TIME);

            try (Connection conn = DatabaseManager.getConnection()) {
                String query = "INSERT INTO appointments (patient_id, psychologist_id, appointment_date, appointment_time, location) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(query);
                statement.setInt(1, patientId);
                statement.setInt(2, psychologistId);
                statement.setDate(3, Date.valueOf(formattedDate));
                statement.setTime(4, Time.valueOf(time));
                statement.setString(5, location);
                statement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Consulta salva com sucesso.");
                dispose();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar consulta.");
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data ou hora inválida. Use os formatos DD/MM/AAAA e HH:MM.");
        }
    }

    private int getIdByCPF(String cpf, String tableName) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT id FROM " + tableName + " WHERE cpf = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NewAppointment frame = new NewAppointment();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
