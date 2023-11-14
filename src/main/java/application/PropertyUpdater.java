import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PropertyUpdater {

    private static final String JDBC_URL = "jdbc:mysql://localhost:8081/ihl_db";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void updateProperty(Property property) {
        // Implementation for updating a property
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void archiveProperty(Property property) {
       
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Your archive logic goes here
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
