package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

public class Update {
    public static void main(String[] args) {

        updateProperty();
       // archiveProperty();
    }

    private static final String JDBC_URL = "jdbc:mysql://localhost:8081/ihl_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void updateProperty() {
        try {
            Connection connection  = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            String query1 = "SELECT * FROM properties WHERE id= ?";
            String query = "UPDATE properties SET street=?, city=?, listingNum=?, styleId=?, typeId=?, bedrooms=?, " +
                    "bathrooms=?, squarefeet=?, berRating=?, description=?, lotsize=?, garagesize=?, garageId=?, " +
                    "agentId=?, photo=?, price=?, dateAdded=? WHERE id=?";

            PreparedStatement ps = connection.prepareStatement(query);
            PreparedStatement ps1 = connection.prepareStatement(query1);


            ps.setString(1, "NEW STREET");
            ps.setString(2, "NEW CITY");
            ps.setInt(3, 123);
            ps.setInt(4, 456);
            ps.setInt(5, 789);
            ps.setInt(6, 2);
            ps.setFloat(7, 2);
            ps.setInt(8, 1500);
            ps.setString(9, "NEW BERRATING");
            ps.setString(10, "NEW DESCRIPTION");
            ps.setString(11, "NEW LOTSIZE");
            ps.setInt(12,  1);
            ps.setInt(13, 101);
            ps.setInt(14, 201);
            ps.setString(15, "NEW PHOTO");
            ps.setDouble(16, 35);
            ps.setDate(17, new java.sql.Date(2023, 11,15));
            ps.setInt(18, 1001);



            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void archiveProperty() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "UPDATE properties SET archived=true WHERE id=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, "NEW ID");

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) archived.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
