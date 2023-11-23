package application;
import java.sql.*;
import java.util.Scanner;

public class DynamicQuery {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ihl_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        executeDynamicQuery();
    }

    public static void executeDynamicQuery() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {


            System.out.print("Enter the city to filter : ");
            String cityFilter = scanner.nextLine().trim();
            System.out.print("Enter the minimum number of bedrooms: ");
            int minBedrooms = scanner.nextInt();


            String query = buildDynamicQuery(cityFilter, minBedrooms);


            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                ResultSet resultSet = preparedStatement.executeQuery();


                displayResults(resultSet);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String buildDynamicQuery(String cityFilter, int minBedrooms) {

        StringBuilder query = new StringBuilder("SELECT * FROM properties WHERE 1");

        if (!cityFilter.isEmpty()) {
            query.append(" AND city = '").append(cityFilter).append("'");
        }
        query.append(" AND bedrooms >= ").append(minBedrooms);

        return query.toString();
    }

    private static void displayResults(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();


        for (int i = 1; i <= columnCount; i++) {
            System.out.print(metaData.getColumnName(i) + "\t");
        }
        System.out.println();


        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i) + "\t");
            }
            System.out.println();
        }
    }


}

//





