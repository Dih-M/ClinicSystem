import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class EditAppointment extends JFrame {

    private JTextField txtDate;
    private JTextField txtTime;
    private JTextField txtLocation;
    private Appointment appointment;

    public EditAppointment(Appointment appointment) {
        this.appointment = appointment;
        initialize();
    }

    private void initialize() {
        setTitle("Editar Consulta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblDate = new JLabel("Data (DD/MM/AAAA):");
        contentPane.add(lblDate);

        txtDate = new JTextField(appointment.getAppointmentDate().toString());
        contentPane.add(txtDate);

        JLabel lblTime = new JLabel("Hora (HH:MM):");
        contentPane.add(lblTime);

        txtTime = new JTextField(appointment.getAppointmentTime().toString().substring(0, 5));
        contentPane.add(txtTime);

        JLabel lblLocation = new JLabel("Local:");
        contentPane.add(lblLocation);

        txtLocation = new JTextField(appointment.getLocation());
        contentPane.add(txtLocation);

        JButton btnSave = new JButton("Salvar");
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSave.setForeground(Color.WHITE);
        btnSave.setBackground(new Color(46, 204, 113)); // Cor de fundo verde
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveAppointment();
            }
        });
        contentPane.add(btnSave);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(231, 76, 60)); // Cor de fundo vermelho
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                AppointmentScreen appointmentScreen = new AppointmentScreen();
                appointmentScreen.setVisible(true);
            }
        });
        contentPane.add(btnCancel);
    }

    private void saveAppointment() {
        try (Connection connection = DatabaseManager.getConnection()) {
            String query = "UPDATE appointments SET appointment_date = ?, appointment_time = ?, location = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, Date.valueOf(txtDate.getText()));
            
            // Verifica se a string de tempo está no formato esperado
            String timeString = txtTime.getText();
            if (timeString.matches("\\d{2}:\\d{2}")) {
                timeString += ":00"; // Adiciona os segundos se não estiverem presentes
            } else {
                JOptionPane.showMessageDialog(null, "Formato de hora inválido. Use o formato HH:MM.");
                return;
            }
            
            statement.setTime(2, Time.valueOf(timeString));
            statement.setString(3, txtLocation.getText());
            statement.setInt(4, appointment.getId());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Consulta atualizada com sucesso.");
            dispose(); // Fecha a tela de edição
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar a consulta.");
        }
    }
}
