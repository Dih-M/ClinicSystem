import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewPatients extends JFrame {

    public ViewPatients() {
        initialize();
    }

    private void initialize() {
        setTitle("Registros de Pacientes");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM patients";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            textArea.setText("Registros de Pacientes:\n\n");
            while (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                
                String record = "CPF: " + cpf + ", Nome: " + name + ", Email: " + email +
                                ", Endereço: " + address + ", Telefone: " + phone + "\n";
                textArea.append(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar registros de pacientes.");
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ViewPatients frame = new ViewPatients();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
