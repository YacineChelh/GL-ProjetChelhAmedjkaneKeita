package org.fr.ul.miage;

import java.sql.*;
public class Database {
    //TEST A FAIRE SUR DATABASE
    private Connection connection;
    private static final String URL = "jdbc:postgresql://localhost:5432/plg-broker.ad.univ-lorraine.fr"; //Adresse de la base dans pgadmin
    private static final String USER = "m1user1_07";
    private static final String PASSWORD = USER;

    public Database() {
        connect();
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données établie.");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Déconnexion de la base de données.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la déconnexion de la base de données : " + e.getMessage());
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }


    //Client
    public void insertClient(String nom, String prenom, String adresse, String telephone, String mail, String numCarteCredit, String immatriculation) {
        String sql = "INSERT INTO Client (Nom, Prenom, Adresse, Tel, Mail, NCarteCredit, Immatriculation) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, adresse);
            statement.setString(4, telephone);
            statement.setString(5, mail);
            statement.setString(6, numCarteCredit);
            statement.setString(7, immatriculation);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Client ajouté avec succès à la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion du client dans la base de données : " + e.getMessage());
        }
    }

    //Reservation

    public void insertReservation(int numReservation, Timestamp debutReserv, Timestamp finReserv, String etatReservation, int idClient, int idFacturation) {
        int idBorne = getBorneDisponible(debutReserv, finReserv); // Récupérer une borne disponible pour le créneau spécifié
        if (idBorne == 0) {
            System.out.println("Aucune borne disponible pour le créneau spécifié. La réservation ne peut pas être effectuée.");
            return; // Sortir de la méthode si aucune borne disponible n'est trouvée
        }

        String sql = "INSERT INTO Reservation (NumReservation, DebutReserv, FinReserv, EtatReservation, IdClient, IdBorne, IdFacturation) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, numReservation);
            statement.setTimestamp(2, debutReserv);
            statement.setTimestamp(3, finReserv);
            statement.setString(4, etatReservation);
            statement.setInt(5, idClient);
            statement.setInt(6, idBorne); // Assigner l'ID de la borne disponible pour le créneau spécifié
            statement.setInt(7, idFacturation);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Réservation ajoutée avec succès à la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la réservation dans la base de données : " + e.getMessage());
        }
    }



    //Borne

    // La fonction suivante est pour la fonction de creation de reservation à un créneau donnée pour pouvoir inserer une nouvelle ligne dans réservation
    // Elle permet d'assigner automatiquement une borne à une réservation si une est dispo sinon ca renvoi 0
    public int getBorneDisponible(Timestamp debutreserv, Timestamp finreserv) {
        String sql = "SELECT IdBorne FROM Borne WHERE EtatBorne = 'Disponible' AND IdBorne NOT IN (SELECT IdBorne FROM Reservation WHERE (DebutReserv < ? AND FinReserv > ?) OR (DebutReserv < ? AND FinReserv > ?))";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, finreserv);
            statement.setTimestamp(2, debutreserv);
            statement.setTimestamp(3, debutreserv);
            statement.setTimestamp(4, finreserv);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("IdBorne"); // Retourne l'ID de la première borne disponible pour le créneau spécifié
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la borne disponible pour le créneau : " + e.getMessage());
        }

        return 0; // Retourne 0 s'il n'y a pas de borne disponible
    }



}
