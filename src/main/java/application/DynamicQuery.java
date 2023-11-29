package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

            System.out.print("Enter the city to filter: ");
            String cityFilter = scanner.nextLine().trim();
            System.out.print("Enter the minimum number of bedrooms: ");
            int minBedrooms = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Do you want to sort the results? (yes/no): ");
            String sortChoice = scanner.nextLine().toLowerCase();

            String sortColumn = null;
            String sortOrder = null;

            if ("yes".equals(sortChoice)) {
                System.out.print("Enter the column to sort by: ");
                sortColumn = scanner.nextLine();

                System.out.print("Enter the sorting order (ASC or DESC): ");
                sortOrder = scanner.nextLine();
            }

            // Create a Statement with TYPE_SCROLL_INSENSITIVE type
            try (Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                String query = buildDynamicQuery(cityFilter, minBedrooms, sortColumn, sortOrder);

                try (ResultSet resultSet = statement.executeQuery(query)) {
                    displayResults(resultSet);

                    System.out.print("Would you like to save this search? (yes/no): ");
                    String saveChoice = scanner.nextLine().toLowerCase();

                    if ("yes".equals(saveChoice)) {
                        System.out.print("Please enter a file name to save the search results: ");
                        String fileName = scanner.nextLine().trim();
                        saveAsTextFile(resultSet, fileName);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private static String buildDynamicQuery(String cityFilter, int minBedrooms, String sortColumn, String sortOrder) {
        StringBuilder query = new StringBuilder("SELECT * FROM properties WHERE 1");

        if (!cityFilter.isEmpty()) {
            query.append(" AND city = '").append(cityFilter).append("'");
        }
        query.append(" AND bedrooms >= ").append(minBedrooms);

        if (sortColumn != null && sortOrder != null) {
            query.append(" ORDER BY ").append(sortColumn).append(" ").append(sortOrder);
        }

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

    private static void saveAsTextFile(ResultSet resultSet, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            // Write column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                fileWriter.write(metaData.getColumnName(i) + "\t");
            }
            fileWriter.write("\n");

            // Move cursor to the beginning of the result set
            resultSet.beforeFirst();

            // Write query results
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    fileWriter.write(resultSet.getString(i) + "\t");
                }
                fileWriter.write("\n");
            }
            System.out.println("Results saved to " + fileName + ".");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }




}
