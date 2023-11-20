package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Update {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ihl_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        updateProperty();
        archiveProperty();
    }

    public static void updateProperty() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter the ID of the property you wish to update: ");
            int propertyId = scanner.nextInt();
            scanner.nextLine();

            String selectQuery = "SELECT * FROM properties WHERE id=?";
            String updateQuery = "UPDATE properties SET street=?, city=?, listingNum=?, styleId=?, typeId=?, bedrooms=?, " +
                    "bathrooms=?, squarefeet=?, berRating=?, description=?, lotsize=?, garagesize=?, garageId=?, " +
                    "agentId=?, photo=?, price=?, dateAdded=? WHERE id=?";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                 PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

                selectStatement.setInt(1, propertyId);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {

                    System.out.println("Current Street: " + resultSet.getString("street"));
                    System.out.println("Current City: " + resultSet.getString("city"));
                    System.out.println("Current ListingNum: " + resultSet.getInt("listingNum"));
                    System.out.println("Current styleId: " + resultSet.getInt("styleId"));
                    System.out.println("Current typeId: " + resultSet.getInt("typeId"));
                    System.out.println("Current bedroom: " + resultSet.getInt("bedrooms"));
                    System.out.println("Current squarefeet: " + resultSet.getInt("squarefeet"));
                    System.out.println("Current berRating: " + resultSet.getString("berRating"));
                    System.out.println("Current Description: " + resultSet.getString("description"));
                    System.out.println("Current Lotsize: " + resultSet.getString("lotsize"));
                    System.out.println("Current Garagesize: " + resultSet.getInt("garagesize"));
                    System.out.println("Current GarageId: " + resultSet.getInt("garageId"));

                    System.out.println("Current AgentId: " + resultSet.getInt("agentId"));
                    System.out.println("Current Photo: " + resultSet.getString("photo"));
                    System.out.println("Current Prize: " + resultSet.getDouble("price"));
                    System.out.println("Current DateAdded: " + resultSet.getDate("dateAdded"));

                    // Prompt user for updates
                    System.out.print("Would you like to update the ListingNum? (yes/No): ");
                    String updateListingNum = scanner.nextLine();
                    if ("yes".equalsIgnoreCase(updateListingNum)) {
                        System.out.print("Enter new ListingNum: ");
                        int newListingNum = scanner.nextInt();
                        updateStatement.setInt(1, newListingNum);
                    } else {
                        updateStatement.setInt(1, resultSet.getInt("listingNum"));
                    }
                    resultSet.updateInt("styleId", resultSet.getInt("styleId"));
                    resultSet.updateInt("typeId", resultSet.getInt("typeId"));
                    resultSet.updateInt("bedrooms", resultSet.getInt("bedrooms"));
                    resultSet.updateFloat("bathrooms", resultSet.getFloat("bathrooms"));
                    resultSet.updateInt("squarefeet", resultSet.getInt("squarefeet"));
                    resultSet.updateString("berRating", resultSet.getString("berRating"));
                    resultSet.updateString("description", resultSet.getString("description"));
                    resultSet.updateString("lotsize", resultSet.getString("lotsize"));
                    resultSet.updateInt("garagesize", resultSet.getInt("garagesize"));
                    resultSet.updateInt("garageId", resultSet.getInt("garageId"));
                    resultSet.updateInt("agentId", resultSet.getInt("agentId"));
                    resultSet.updateString("photo", resultSet.getString("photo"));
                    resultSet.updateDouble("price", resultSet.getDouble("price"));
                    resultSet.updateDate("dateAdded", resultSet.getDate("dateAdded"));

                    int rowsAffected = updateStatement.executeUpdate();
                    System.out.println(rowsAffected + " row(s) updated.");
                } else {
                    System.out.println("Property not found with ID: " + propertyId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void archiveProperty() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter the ID of the property you want to archive: ");
            int propertyId = scanner.nextInt();

            String query = "UPDATE properties SET description = CONCAT(description, ' [Archived]') WHERE id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, propertyId); // Use the entered property ID

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) archived.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
