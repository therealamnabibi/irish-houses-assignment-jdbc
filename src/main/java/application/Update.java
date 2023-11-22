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
                    int newListingNum;

                    if ("yes".equalsIgnoreCase(updateListingNum)) {
                        System.out.print("Enter new ListingNum: ");
                        newListingNum = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                    } else {
                        newListingNum = resultSet.getInt("listingNum");
                    }

                    updateStatement.setString(1, resultSet.getString("street"));
                    updateStatement.setString(2, resultSet.getString("city"));
                    updateStatement.setInt(3, newListingNum);
                    updateStatement.setInt(4, resultSet.getInt("styleId"));
                    updateStatement.setInt(5, resultSet.getInt("typeId"));
                    updateStatement.setInt(6, resultSet.getInt("bedrooms"));
                    updateStatement.setFloat(7, resultSet.getFloat("bathrooms"));
                    updateStatement.setInt(8, resultSet.getInt("squarefeet"));
                    updateStatement.setString(9, resultSet.getString("berRating"));
                    updateStatement.setString(10, resultSet.getString("description"));
                    updateStatement.setString(11, resultSet.getString("lotsize"));
                    updateStatement.setInt(12, resultSet.getInt("garagesize"));
                    updateStatement.setInt(13, resultSet.getInt("garageId"));
                    updateStatement.setInt(14, resultSet.getInt("agentId"));
                    updateStatement.setString(15, resultSet.getString("photo"));
                    updateStatement.setDouble(16, resultSet.getDouble("price"));
                    updateStatement.setDate(17, resultSet.getDate("dateAdded"));
                    updateStatement.setInt(18, propertyId);

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
            scanner.nextLine(); // Consume newline character

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
