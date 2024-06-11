import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.List;

public class AppointmentsList extends JFrame {
    private JFrame searchFrame;

    public AppointmentsList(List<Appointment> appointments, JFrame searchFrame) {
        this.searchFrame = searchFrame;
        initialize(appointments);
    }

    private void initialize(List<Appointment> appointments) {
        setTitle("Lista de Consultas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 5));

        JPanel appointmentsPanel = new JPanel();
        appointmentsPanel.setLayout(new GridLayout(0, 1, 0, 3)); // Reduzindo o espaço entre os itens
        JScrollPane scrollPane = new JScrollPane(appointmentsPanel);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        for (Appointment appointment : appointments) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            JLabel label = new JLabel("<html>Consulta em " + appointment.getAppointmentDate() + " às " + appointment.getAppointmentTime() + "</html>");
            panel.add(label, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 0));
            buttonPanel.setBackground(Color.WHITE);

            JButton btnEdit = createButton("Editar", e -> {
                dispose();
                searchFrame.dispose();
                EditAppointment editAppointmentFrame = new EditAppointment(appointment);
                editAppointmentFrame.setVisible(true);
            });
            buttonPanel.add(btnEdit);

            JButton btnDelete = createButton("Excluir", e -> {
                int choice = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esta consulta?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    deleteAppointment(appointment);
                    appointmentsPanel.remove(panel);
                    refreshAppointmentsPanel(appointmentsPanel);
                }
            });
            buttonPanel.add(btnDelete);

            panel.add(buttonPanel, BorderLayout.LINE_END);
            appointmentsPanel.add(panel);
        }

        JButton btnBack = new JButton("Voltar");
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(new Color(46, 204, 113)); // Cor verde
        btnBack.addActionListener(e -> {
            dispose();
            searchFrame.setVisible(true);
        });
        contentPane.add(btnBack, BorderLayout.SOUTH);
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    private void deleteAppointment(Appointment appointment) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "DELETE FROM appointments WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, appointment.getId());
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Consulta excluída com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(null, "Falha ao excluir consulta.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir consulta.");
        }
    }

    private void refreshAppointmentsPanel(JPanel appointmentsPanel) {
        SwingUtilities.invokeLater(() -> {
            appointmentsPanel.revalidate();
            appointmentsPanel.repaint();
        });
    }
}
