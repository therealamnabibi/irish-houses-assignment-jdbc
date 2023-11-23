
package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import static application.Update.displayPropertyDetails;


public class ArchiveProperty {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ihl_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {


        archiveProperty();

    }

    public static void archiveProperty() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            int propertyId = readIntInput(scanner);

            String selectQuery = "SELECT * FROM properties WHERE id=?";
            String archiveQuery = "INSERT INTO archiveproperty (propertyID, city, description) " +
                    "SELECT id, city, description FROM properties WHERE id=?";

            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                 PreparedStatement archiveStatement = connection.prepareStatement(archiveQuery)) {

                selectStatement.setInt(1, propertyId);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    displayPropertyDetails(resultSet);

                    // Confirm archiving
                    System.out.print("Do you want to archive this property? (yes/No): ");
                    String archiveDecision = scanner.nextLine();

                    if ("yes".equalsIgnoreCase(archiveDecision)) {
                        // Archive property
                        archiveStatement.setInt(1, propertyId);
                        int rowsArchived = archiveStatement.executeUpdate();

                        System.out.println(rowsArchived + " row(s) archived.");
                    } else {
                        System.out.println("Archiving canceled.");
                    }
                } else {
                    System.out.println("Property not found with ID: " + propertyId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static int readIntInput(Scanner scanner) {
        int input = 0;
        while (true) {
            try {
                System.out.print("Enter a numeric value: ");
                input = Integer.parseInt(scanner.nextLine());
                break; // Exit the loop if input is successfully parsed
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid numeric value.");
            }
        }
        return input;
    }


}
