package application;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class DatabaseSeeder {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ihl_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        seedProperties();

    }

    public static void seedProperties() {

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database.");

            seedProperties(connection);

            // After seeding properties, provide the option to delete
            deleteProperty(connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getMaxStyleId(Connection connection) throws SQLException {
        String query = "SELECT MAX(styleId) FROM styles";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        throw new SQLException("Failed to get max styleId");
    }

    private static void seedProperties(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        Faker faker = new Faker();

        System.out.print("Enter the number of properties to generate: ");
        int numberOfProperties = scanner.nextInt();
        scanner.nextLine();

        try {
            int maxStyleId = getMaxStyleId(connection);

            // Check if there are styles available in the styles table
            if (maxStyleId == 0) {
                System.out.println("No styles available in the styles table. Cannot seed properties.");
                return;
            }

            String insertPropertyQuery = "INSERT INTO properties " +
                    "(street, city, listingNum, styleId, typeId, bedrooms, bathrooms, squarefeet, " +
                    "berRating, description, lotsize, garagesize, garageId, agentId, photo, price, dateAdded) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement insertPropertyStatement = connection.prepareStatement(insertPropertyQuery)) {
                for (int i = 0; i < numberOfProperties; i++) {
                    int styleId = faker.number().numberBetween(1, maxStyleId + 1);

                    insertPropertyStatement.setString(1, faker.address().streetAddress()); //street
                    insertPropertyStatement.setString(2, faker.address().city()); //city
                    insertPropertyStatement.setInt(3, faker.number().randomDigitNotZero());//listingnum
                    insertPropertyStatement.setInt(4, faker.number().numberBetween(1, 11));//styleid
                    insertPropertyStatement.setInt(5, faker.number().numberBetween(1, 3));//typeid
                    insertPropertyStatement.setInt(6, faker.number().numberBetween(1, 10));//bedrooms
                    insertPropertyStatement.setInt(7, faker.number().numberBetween(1, 10));//bathrooms
                    insertPropertyStatement.setInt(8, faker.number().numberBetween(1000, 5000));//squarefeet
                    insertPropertyStatement.setString(9, faker.lorem().characters(2));//berRating
                    insertPropertyStatement.setString(10, faker.lorem().sentence());//description
                    insertPropertyStatement.setInt(11, faker.number().numberBetween(1, 10));//lotsize
                    insertPropertyStatement.setInt(12, faker.number().numberBetween(1, 5));//garagesize
                    insertPropertyStatement.setInt(13, faker.number().numberBetween(1, 3));//garageid
                    insertPropertyStatement.setInt(14, faker.number().numberBetween(1, 6));//agentid
                    insertPropertyStatement.setString(15, faker.lorem().word() + ".jpg");//photo
                    insertPropertyStatement.setDouble(16, faker.number().randomDouble(2, 50000, 300000));//price
                    insertPropertyStatement.setDate(17, new java.sql.Date(new Date().getTime()));//dateAdded

                    insertPropertyStatement.executeUpdate();
                }
            }
            System.out.println("Properties seeded successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteProperty(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you want to delete a property? (yes/no): ");
        String choice = scanner.nextLine().toLowerCase();

        if (choice.equals("yes")) {
            System.out.print("Enter the ID of the property to delete: ");
            int propertyId = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            String deletePropertyQuery = "DELETE FROM properties WHERE id = ?";

            try (PreparedStatement deletePropertyStatement = connection.prepareStatement(deletePropertyQuery)) {
                deletePropertyStatement.setInt(1, propertyId);
                int affectedRows = deletePropertyStatement.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Property with ID " + propertyId + " deleted successfully.");
                } else {
                    System.out.println("No property found with ID " + propertyId + ". Deletion failed.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

