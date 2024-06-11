import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterScreen extends JFrame {

    public RegisterScreen() {
        initialize();
    }

    private void initialize() {
        setTitle("Registrar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(5, 1, 10, 20));

        JButton btnNew = new JButton("Novo...");
        btnNew.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnNew.setForeground(Color.WHITE);
        btnNew.setBackground(new Color(52, 152, 219)); // Cor de fundo azul
        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistrationOptions();
            }
        });
        contentPane.add(btnNew);

        JButton btnEdit = new JButton("Editar...");
        btnEdit.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setBackground(new Color(46, 204, 113)); // Cor de fundo verde
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showEditOptions();
            }
        });
        contentPane.add(btnEdit);

        JButton btnDelete = new JButton("Excluir...");
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setBackground(new Color(231, 76, 60)); // Cor de fundo vermelha
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDeleteOptions();
            }
        });
        contentPane.add(btnDelete);

        JButton btnViewRecords = new JButton("Ver Registros");
        btnViewRecords.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnViewRecords.setForeground(Color.WHITE);
        btnViewRecords.setBackground(new Color(243, 156, 18)); // Cor de fundo laranja
        btnViewRecords.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showViewRecordsOptions();
            }
        });
        contentPane.add(btnViewRecords);

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

    private void showRegistrationOptions() {
        String[] options = {"Paciente", "Psicólogo"};
        int selectedOption = JOptionPane.showOptionDialog(this,
                "Selecione o tipo de registro:", "Novo Registro",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (selectedOption == 0) {
            RegisterPatient registerPatient = new RegisterPatient();
            registerPatient.setVisible(true);
        } else if (selectedOption == 1) {
            RegisterPsychologist registerPsychologist = new RegisterPsychologist();
            registerPsychologist.setVisible(true);
        }
    }

    private void showEditOptions() {
    	 SearchByCPF searchDialog = new SearchByCPF(this);
         searchDialog.setVisible(true);
    }

    private void showDeleteOptions() {
        DeleteByCPFDialog deleteDialog = new DeleteByCPFDialog(this);
        deleteDialog.setVisible(true);
    }

    private void showViewRecordsOptions() {
        String[] options = {"Paciente", "Psicólogo"};
        int selectedOption = JOptionPane.showOptionDialog(this,
                "Selecione o tipo de registro:", "Ver Registros",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (selectedOption == 0) {
            ViewPatients viewPatients = new ViewPatients();
            viewPatients.setVisible(true);
        } else if (selectedOption == 1) {
            ViewPsychologists viewPsychologists = new ViewPsychologists();
            viewPsychologists.setVisible(true);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RegisterScreen frame = new RegisterScreen();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
