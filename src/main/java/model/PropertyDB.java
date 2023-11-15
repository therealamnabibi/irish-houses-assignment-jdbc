package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PropertyDB {


    private static final String JDBC_URL = "jdbc:mysql://localhost:8081/ihl_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public void updateProperty() {
        try {
            Connection connection  = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            String query = "UPDATE properties SET street=?, city=?, listingNum=?, styleId=?, typeId=?, bedrooms=?, " +
                    "bathrooms=?, squarefeet=?, berRating=?, description=?, lotsize=?, garagesize=?, garageId=?, " +
                    "agentId=?, photo=?, price=?, dateAdded=? WHERE id=?";

            PreparedStatement ps = connection.prepareStatement(query);


            ////
            ps.setString(1, "NEW STREET");

            int rowsAffected = ps.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated.");
        }
     catch (Exception e) {
        e.printStackTrace();
    }
}

    public void archiveProperty() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "UPDATE properties SET archived=true WHERE id=?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, this.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                System.out.println(rowsAffected + " row(s) archived.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
