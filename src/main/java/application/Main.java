package application;

import model.Property;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    // Modify the connection details based on your database setup
    private static final String JDBC_URL = "jdbc:mysql://localhost:8081/ihl_db";
    private static final String USERNAME = "your_username";
    private static final String PASSWORD = "your_password";

    public static void updateProperty(Property property) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "UPDATE properties SET street=?, city=?, listingNum=?, styleId=?, typeId=?, bedrooms=?, " +
                    "bathrooms=?, squarefeet=?, berRating=?, description=?, lotsize=?, garagesize=?, garageId=?, " +
                    "agentId=?, photo=?, price=?, dateAdded=? WHERE id=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set parameters for the update query
                preparedStatement.setString(1, property.getStreet());
                preparedStatement.setString(2, property.getCity());
                preparedStatement.setInt(3, property.getListingNum());
                preparedStatement.setInt(4, property.getStyleId());
                preparedStatement.setInt(5, property.getTypeId());
                preparedStatement.setInt(6, property.getBedrooms());
                preparedStatement.setFloat(7, property.getBathrooms());
                preparedStatement.setInt(8, property.getSquarefeet());
                preparedStatement.setString(9, property.getBerRating());
                preparedStatement.setString(10, property.getDescription());
                preparedStatement.setString(11, property.getLotsize());
                preparedStatement.setByte(12, property.getGaragesize());
                preparedStatement.setInt(13, property.getGarageId());
                preparedStatement.setInt(14, property.getAgentId());
                preparedStatement.setString(15, property.getPhoto());
                preparedStatement.setDouble(16, property.getPrice());
                preparedStatement.setDate(17, new java.sql.Date(property.getDateAdded().getTime()));
                preparedStatement.setInt(18, property.getId());

                // Execute the update query
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) updated.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void archiveProperty(Property property) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "UPDATE properties SET archived=true WHERE id=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Set parameters for the update query
                preparedStatement.setInt(1, property.getId());

                // Execute the update query
                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) archived.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Create an instance of Property for testing
        Property property = new Property(/* initialize property details */);

        // Call the update and archive methods
        updateProperty(property);
        archiveProperty(property);
    }
}
//nnn