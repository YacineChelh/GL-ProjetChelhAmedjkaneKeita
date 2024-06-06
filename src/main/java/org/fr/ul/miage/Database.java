package org.fr.ul.miage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {


    // Méthode pour insérer un client dans la base de données
    public void insertClient(String nom, String prenom, String adresse, String telephone, String mail, String numCarteCredit, String immatriculation) {
        String sql = "INSERT INTO Client (Nom, Prenom, Adresse, Tel, Mail, NCarteCredit, Immatriculation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseParam.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
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


    //Méthode pour insérer une réservation
    public void insertReservation(Timestamp debutReserv, Timestamp finReserv, int idClient, int idBorne, int idFacturation) {
        String sql = "INSERT INTO Reservation (DebutReserv, FinReserv, EtatReservation, IdClient, IdBorne, IdFacturation) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseParam.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, debutReserv);
            statement.setTimestamp(2, finReserv);
            statement.setString(3, "Programmée");
            statement.setInt(4, idClient);
            statement.setInt(5, idBorne);
            statement.setInt(6, idFacturation);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Réservation ajoutée avec succès à la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la réservation dans la base de données : " + e.getMessage());
        }
    }


    //Méthode pour insérer une recharge
    public void insertRecharge(Timestamp debutRecharge, int dureeRecharge, Integer idBorne, int idClient, Integer idFacturation) {
        String sql = "INSERT INTO Recharge (DebutRecharge, DureeRecharge, IdBorne, IdClient, IdFacturation) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseParam.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, debutRecharge);
            statement.setInt(2, dureeRecharge);
            if (idBorne != null) {
                statement.setInt(3, idBorne);
            } else {
                statement.setNull(3, java.sql.Types.INTEGER);
            }
            statement.setInt(4, idClient);
            if (idFacturation != null) {
                statement.setInt(5, idFacturation);
            } else {
                statement.setNull(5, java.sql.Types.INTEGER);
            }
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Recharge ajoutée avec succès à la base de données.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la recharge dans la base de données : " + e.getMessage());
        }
    }



    // Méthode pour insérer une réservation dans la base de données
    public void insertReservation(Timestamp debutReserv, Timestamp finReserv, String etatReservation, int idClient, int idFacturation) {
        String sql = "INSERT INTO Reservation (DebutReserv, FinReserv, EtatReservation, IdClient, IdBorne, IdFacturation) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseParam.getConnection()) {
            int idBorne = getBorneDisponible(connection, debutReserv, finReserv);
            if (idBorne == 0) {
                System.out.println("Aucune borne disponible pour le créneau spécifié. La réservation ne peut pas être effectuée.");
                return;
            }

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setTimestamp(1, debutReserv);
                statement.setTimestamp(2, finReserv);
                statement.setString(3, etatReservation);
                statement.setInt(4, idClient);
                statement.setInt(5, idBorne);
                statement.setInt(6, idFacturation);

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Réservation ajoutée avec succès à la base de données.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'insertion de la réservation dans la base de données : " + e.getMessage());
        }
    }

    // Méthode pour récupérer une borne disponible
    private int getBorneDisponible(Connection connection, Timestamp debutreserv, Timestamp finreserv) {
        String sql = "SELECT IdBorne FROM Borne WHERE EtatBorne = 'Disponible' AND IdBorne NOT IN (SELECT IdBorne FROM Reservation WHERE (DebutReserv < ? AND FinReserv > ?) OR (DebutReserv < ? AND FinReserv > ?))";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, finreserv);
            statement.setTimestamp(2, debutreserv);
            statement.setTimestamp(3, debutreserv);
            statement.setTimestamp(4, finreserv);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("IdBorne");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la borne disponible pour le créneau : " + e.getMessage());
        }

        return 0;
    }


    // Méthode pour récupérer toutes les bornes disponibles
    public List<Integer> getToutesLesBornesDisponibles(Timestamp debutreserv, Timestamp finreserv) {
        List<Integer> bornesDisponibles = new ArrayList<>();
        String sql = "SELECT IdBorne FROM Borne WHERE EtatBorne = 'Disponible' AND IdBorne NOT IN (" +
                "SELECT IdBorne FROM Reservation WHERE (DebutReserv < ? AND FinReserv > ?) OR (DebutReserv < ? AND FinReserv > ?) " +
                "UNION " +
                "SELECT IdBorne FROM Recharge WHERE (DebutRecharge < ? AND (DebutRecharge + INTERVAL '1 second' * DureeRecharge) > ?) OR (DebutRecharge < ? AND (DebutRecharge + INTERVAL '1 second' * DureeRecharge) > ?))";

        try (Connection connection = DatabaseParam.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, finreserv);
            statement.setTimestamp(2, debutreserv);
            statement.setTimestamp(3, debutreserv);
            statement.setTimestamp(4, finreserv);
            statement.setTimestamp(5, finreserv);
            statement.setTimestamp(6, debutreserv);
            statement.setTimestamp(7, debutreserv);
            statement.setTimestamp(8, finreserv);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bornesDisponibles.add(resultSet.getInt("IdBorne"));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des bornes disponibles pour le créneau : " + e.getMessage());
        }

        return bornesDisponibles;
    }

    // Méthode  pour récupérer toutes les bornes disponibles

    // Méthode pour vérifier si une immatriculation existe déjà
    public boolean existingImmatriculation(String immatriculation) {
        System.out.println("Vérification de l'immatriculation...");
        String sql = "SELECT COUNT(*) AS count FROM Client WHERE Immatriculation = ?";
        try (Connection connection = DatabaseParam.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, immatriculation);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'immatriculation existante : " + e.getMessage());
        }
        return false;
    }

    // Méthode pour vérifier si un numéro de téléphone existe déjà
    public boolean existingNumTel(String telephone) {
        String sql = "SELECT COUNT(*) AS count FROM Client WHERE Tel = ?";
        try (Connection connection = DatabaseParam.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, telephone);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du numéro de téléphone existant : " + e.getMessage());
        }
        return false;
    }

    // Méthode pour récupérer toutes les réservations liées à un client avec un numéro de téléphone donné
    public List<Reservation> getReservationsAvecNumTel(String phoneNumber) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.NumReservation, r.DebutReserv, r.FinReserv, r.EtatReservation, r.IdClient, r.IdBorne, r.IdFacturation " +
                "FROM Reservation r " +
                "JOIN Client c ON r.IdClient = c.IdClient " +
                "WHERE c.Tel = ?";

        try (Connection connection = DatabaseParam.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, phoneNumber);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Reservation reservation = new Reservation(
                        resultSet.getInt("NumReservation"),
                        resultSet.getTimestamp("DebutReserv"),
                        resultSet.getTimestamp("FinReserv"),
                        resultSet.getString("EtatReservation"),
                        resultSet.getInt("IdClient"),
                        resultSet.getInt("IdBorne"),
                        resultSet.getInt("IdFacturation")
                );
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des réservations : " + e.getMessage());
        }

        return reservations;
    }

}
