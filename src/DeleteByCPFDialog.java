import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteByCPFDialog extends JDialog {

    private JTextField cpfField;

    public DeleteByCPFDialog(Frame owner) {
        super(owner, "Excluir por CPF", true);
        initialize();
    }

    private void initialize() {
        setBounds(100, 100, 300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(2, 2, 10, 10));

        contentPane.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        contentPane.add(cpfField);

        JButton btnDelete = new JButton("Excluir");
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteByCPF();
            }
        });
        contentPane.add(btnDelete);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setBackground(new Color(149, 165, 166));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        contentPane.add(btnCancel);
    }

    private void deleteByCPF() {
        String cpf = cpfField.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um CPF.");
            return;
        }

        int option = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir o registro?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try (Connection conn = DatabaseManager.getConnection()) {
                String deletePatientQuery = "DELETE FROM patients WHERE cpf = ?";
                try (PreparedStatement stmtPatient = conn.prepareStatement(deletePatientQuery)) {
                    stmtPatient.setString(1, cpf);
                    int rowsAffected = stmtPatient.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Registro do paciente excluído com sucesso.");
                    } else {
                        String deletePsychologistQuery = "DELETE FROM psychologists WHERE cpf = ?";
                        try (PreparedStatement stmtPsychologist = conn.prepareStatement(deletePsychologistQuery)) {
                            stmtPsychologist.setString(1, cpf);
                            rowsAffected = stmtPsychologist.executeUpdate();
                            if (rowsAffected > 0) {
                                JOptionPane.showMessageDialog(this, "Registro do psicólogo excluído com sucesso.");
                            } else {
                                JOptionPane.showMessageDialog(this, "Nenhum registro encontrado com o CPF informado.");
                            }
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao excluir registro por CPF.");
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DeleteByCPFDialog dialog = new DeleteByCPFDialog(null);
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
