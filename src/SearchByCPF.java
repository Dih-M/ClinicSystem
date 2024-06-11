import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchByCPF extends JFrame {

    private JTextField cpfField;
    private RegisterScreen parent;

    public SearchByCPF(RegisterScreen owner) {
        super("Buscar por CPF");
        this.parent = owner;
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

        boolean found = false;

        try (Connection conn = DatabaseManager.getConnection()) {
            String queryPatient = "SELECT * FROM patients WHERE cpf = ?";
            try (PreparedStatement stmtPatient = conn.prepareStatement(queryPatient)) {
                stmtPatient.setString(1, cpf);
                ResultSet rsPatient = stmtPatient.executeQuery();
                if (rsPatient.next()) {
                    EditPatient editPatient = new EditPatient();
                    editPatient.loadPatientData(cpf);
                    centerWindow(editPatient); // Centralizar a janela
                    editPatient.setVisible(true);
                    editPatient.toFront(); // Trazer a janela para frente
                    found = true;
                }
            }

            if (!found) {
                String queryPsychologist = "SELECT * FROM psychologists WHERE cpf = ?";
                try (PreparedStatement stmtPsychologist = conn.prepareStatement(queryPsychologist)) {
                    stmtPsychologist.setString(1, cpf);
                    ResultSet rsPsychologist = stmtPsychologist.executeQuery();
                    if (rsPsychologist.next()) {
                        EditPsychologist editPsychologist = new EditPsychologist();
                        editPsychologist.loadPsychologistData(cpf);
                        centerWindow(editPsychologist); // Centralizar a janela
                        editPsychologist.setVisible(true);
                        editPsychologist.toFront(); // Trazer a janela para frente
                        found = true;
                    }
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "CPF não encontrado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao buscar por CPF.");
        }
    }

    private void centerWindow(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = window.getSize();
        window.setLocation((screenSize.width - windowSize.width) / 2, (screenSize.height - windowSize.height) / 2);
    }
}
