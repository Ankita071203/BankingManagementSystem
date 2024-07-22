package ClassQuestions;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Add this line
            String url = "jdbc:mysql://127.0.0.1:3306/BankDB";
            String user = "root"; // your MySQL username
            String password = "Ankita@155"; // your MySQL password
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
