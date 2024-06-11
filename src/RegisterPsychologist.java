import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterPsychologist extends JFrame {

    private JTextField txtName;
    private JTextField txtEmail;
    private JTextField txtPhone;
    private JTextField txtAddress;
    private JTextField txtCPF;

    public RegisterPsychologist() {
        initialize();
    }

    private void initialize() {
        setTitle("Registrar Psicólogo");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 400);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel lblName = new JLabel("Nome:");
        txtName = new JTextField();
        contentPane.add(lblName);
        contentPane.add(txtName);

        JLabel lblEmail = new JLabel("Email:");
        txtEmail = new JTextField();
        contentPane.add(lblEmail);
        contentPane.add(txtEmail);

        JLabel lblPhone = new JLabel("Telefone:");
        txtPhone = new JTextField();
        contentPane.add(lblPhone);
        contentPane.add(txtPhone);

        JLabel lblAddress = new JLabel("Endereço:");
        txtAddress = new JTextField();
        contentPane.add(lblAddress);
        contentPane.add(txtAddress);

        JLabel lblCPF = new JLabel("CPF:");
        txtCPF = new JTextField();
        contentPane.add(lblCPF);
        contentPane.add(txtCPF);

        JButton btnRegister = new JButton("Registrar");
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setBackground(new Color(52, 152, 219));
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerPsychologist();
            }
        });
        contentPane.add(btnRegister);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(231, 76, 60));
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnCancel);
    }

    private void registerPsychologist() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();
        String address = txtAddress.getText();
        String cpf = txtCPF.getText();

        try (Connection conn = DatabaseManager.getConnection()) {
            String sql = "INSERT INTO psychologists (name, email, phone, address, cpf) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setString(2, email);
                stmt.setString(3, phone);
                stmt.setString(4, address);
                stmt.setString(5, cpf);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Psicólogo registrado com sucesso!");
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao registrar psicólogo: " + e.getMessage());
        }
    }
}
