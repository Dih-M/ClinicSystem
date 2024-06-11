import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditPatient extends JFrame {

    private JTextField nomeField;
    private JTextField emailField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JTextField cpfField;

    public EditPatient() {
        initialize();
    }

    private void initialize() {
        setTitle("Editar Paciente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 2, 10, 10));

        contentPane.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        contentPane.add(nomeField);

        contentPane.add(new JLabel("Email:"));
        emailField = new JTextField();
        contentPane.add(emailField);

        contentPane.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        contentPane.add(telefoneField);

        contentPane.add(new JLabel("Endereço:"));
        enderecoField = new JTextField();
        contentPane.add(enderecoField);

        contentPane.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        contentPane.add(cpfField);

        JButton btnSave = new JButton("Salvar");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePatientData();
            }
        });
        contentPane.add(btnSave);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnCancel);
    }

    public void loadPatientData(String cpf) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "SELECT * FROM patients WHERE cpf = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, cpf);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    nomeField.setText(rs.getString("name"));
                    emailField.setText(rs.getString("email"));
                    telefoneField.setText(rs.getString("phone"));
                    enderecoField.setText(rs.getString("address"));
                    cpfField.setText(rs.getString("cpf"));
                } else {
                    JOptionPane.showMessageDialog(this, "Paciente não encontrado.");
                    dispose();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do paciente.");
        }
    }

    private void updatePatientData() {
        try (Connection conn = DatabaseManager.getConnection()) {
            String query = "UPDATE patients SET name = ?, email = ?, phone = ?, address = ? WHERE cpf = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, nomeField.getText());
                stmt.setString(2, emailField.getText());
                stmt.setString(3, telefoneField.getText());
                stmt.setString(4, enderecoField.getText());
                stmt.setString(5, cpfField.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Dados do paciente atualizados com sucesso.");
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao atualizar dados do paciente.");
        }
    }
}
