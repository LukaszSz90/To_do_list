package org.example.exercise_file;

import java.sql.*;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        String URL = "jdbc:postgresql://localhost:5432/zajavka_v2";
        String user = "postgres";
        String password = "postgres";

        try (
                Connection connection = DriverManager.getConnection(URL, user, password);
                Statement statement = connection.createStatement();
        ) {
            String query1 = "INSERT INTO PRODUCER (ID, PRODUCER_NAME, ADDRESS) " +
                    "VALUES (21, 'Zajavka Group', 'Zajavkowa 15, Warszawa');";

            String query2 = "UPDATE PRODUCER SET ADDRESS = 'Nowy adres naszej siedziby' WHERE ID = 21;";
            String query3 = "DELETE FROM PRODUCER WHERE ID = 21;";


            Optional.of(statement.executeUpdate(query1))
                    .ifPresent(result -> System.out.printf("Inserted %s row(s)%n", result));
//            Optional.of(statement.executeUpdate(query2))
//                    .ifPresent(result -> System.out.printf("Updated %s row(s)%n", result));
//            Optional.of(statement.executeUpdate(query3))
//                    .ifPresent(result -> System.out.printf("Deleted %s row(s)%n", result));

        } catch (Exception e) {
            System.err.printf("Error while working on database: %s%n", e.getMessage());
        }
    }

}