package org.fr.ul.miage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;

public class DatabaseParam {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    public static void main(String[] args) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Lire le script SQL à partir des ressources
            InputStream inputStream = DatabaseParam.class.getClassLoader().getResourceAsStream("bdd.sql");
            if (inputStream == null) {
                throw new IllegalArgumentException("Fichier SQL introuvable");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line).append("\n");
            }

            // Exécuter le script SQL
            stmt.execute(sql.toString());

            System.out.println("Tables créées avec succès");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
