import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    // Configurações de conexão com o MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/clinic";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // Método para obter uma conexão com o banco de dados
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            initializeDatabase(connection);
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);
        }
    }

    // Método para inicializar o banco de dados
    private static void initializeDatabase(Connection connection) {
        try (Statement stmt = connection.createStatement()) {
            // Criação da tabela de pacientes
            String createPatientsTable = "CREATE TABLE IF NOT EXISTS patients (" +
                    "id INT AUTO_INCREMENT NOT NULL UNIQUE," +
                    "cpf VARCHAR(14) NOT NULL," +
                    "name VARCHAR(50) NOT NULL," +
                    "address VARCHAR(50) NOT NULL," +
                    "phone VARCHAR(16) NOT NULL," +
                    "email VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");";

            // Criação da tabela de psicólogos
            String createPsychologistsTable = "CREATE TABLE IF NOT EXISTS psychologists (" +
                    "id INT AUTO_INCREMENT NOT NULL UNIQUE," +
                    "cpf VARCHAR(14) NOT NULL," +
                    "name VARCHAR(50) NOT NULL," +
                    "address VARCHAR(50) NOT NULL," +
                    "phone VARCHAR(16) NOT NULL," +
                    "email VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (id)" +
                    ");";

            // Criação da tabela de consultas
            String createAppointmentsTable = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INT AUTO_INCREMENT NOT NULL UNIQUE," +
                    "patient_id INT NOT NULL," +
                    "psychologist_id INT NOT NULL," +
                    "appointment_date DATE NOT NULL," +
                    "appointment_time TIME NOT NULL," +
                    "location VARCHAR(50) NOT NULL," +
                    "PRIMARY KEY (id)," +
                    "FOREIGN KEY (patient_id) REFERENCES patients(id)," +
                    "FOREIGN KEY (psychologist_id) REFERENCES psychologists(id)" +
                    ");";

            // Executar as declarações SQL
            stmt.execute(createPatientsTable);
            stmt.execute(createPsychologistsTable);
            stmt.execute(createAppointmentsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
