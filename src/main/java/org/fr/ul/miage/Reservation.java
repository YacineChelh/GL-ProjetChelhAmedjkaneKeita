package org.fr.ul.miage;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Reservation {
    private static Map<Integer, Reservation> reservationsParNumero = new HashMap<>();
    private static Map<String, Reservation> reservationsParImmatriculation = new HashMap<>();
    private static int nextNumReservation = 1; // Initialiser à 1 pour éviter les conflits avec des valeurs par défaut

    private String immatriculation;
    private String lastMessage;

    public int numReservation;
    public Timestamp debutReserv;
    public Timestamp finReserv;
    public String etatReservation;
    public int idClient;
    public int idBorne;
    public int idFacturation;

    public enum EEtatReservation {
        Prolongée,
        Terminée,
        Modifiée,
        Annulée,
        EnCours
    }

    public Reservation(int numReservation, Timestamp debutReserv, Timestamp finReserv, String etatReservation, int idClient, int idBorne, int idFacturation) {
        this.numReservation = numReservation;
        this.debutReserv = debutReserv;
        this.finReserv = finReserv;
        this.etatReservation = etatReservation;
        this.idClient = idClient;
        this.idBorne = idBorne;
        this.idFacturation = idFacturation;
    }

    public Reservation(String immatriculation, int idBorne, int idClient, Timestamp debutReserv, Timestamp finReserv) {
        this.numReservation = nextNumReservation++;
        this.immatriculation = immatriculation;
        this.idBorne = idBorne;
        this.idClient = idClient;
        this.debutReserv = debutReserv;
        this.finReserv = finReserv;
        reservationsParNumero.put(numReservation, this);
        reservationsParImmatriculation.put(immatriculation, this);
    }

    public static boolean verifierNumeroReservation(int numReservation) {
        return reservationsParNumero.containsKey(numReservation);
    }

    public static boolean verifierNumeroImmatriculation(String immatriculation) {
        return reservationsParImmatriculation.containsKey(immatriculation);
    }

    public static void reserverBorne(int numReservation, Timestamp debutReserv, Timestamp finReserv) {
        if (reservationsParNumero.containsKey(numReservation)) {
            Reservation reservation = reservationsParNumero.get(numReservation);
            reservation.debutReserv = debutReserv;
            reservation.finReserv = finReserv;
            reservation.lastMessage = "Borne réservée pour la réservation numéro : " + numReservation + " par le client numéro : " + reservation.getIdClient() +
                    " debutReserv " + debutReserv + " et finReserv " + finReserv;
            System.out.println(reservation.lastMessage);
        } else {
            System.out.println("Numéro de réservation invalide.");
        }
    }

    public static void reserverBorne(String immatriculation, Timestamp debutReserv, Timestamp finReserv) {
        if (reservationsParImmatriculation.containsKey(immatriculation)) {
            Reservation reservation = reservationsParImmatriculation.get(immatriculation);
            reservation.debutReserv = debutReserv;
            reservation.finReserv = finReserv;
            reservation.lastMessage = "Borne réservée pour le véhicule immatriculé : " + immatriculation + " par le client numéro : " + reservation.getIdClient() +
                    " debutReserv " + debutReserv + " et finReserv " + finReserv;
            System.out.println(reservation.lastMessage);
        } else {
            System.out.println("Numéro d'immatriculation invalide.");
        }
    }

    public static Timestamp demanderDate(String message) {
        Scanner scanner = new Scanner(System.in);
        Timestamp timestamp = null;
        while (timestamp == null) {
            try {
                System.out.println(message + " (Format: yyyy-mm-dd hh:mm:ss)");
                String dateStr = scanner.nextLine();
                timestamp = Timestamp.valueOf(dateStr);  // Conversion de la chaîne de caractères en Timestamp
            } catch (IllegalArgumentException e) {
                System.out.println("Format de date invalide. Veuillez réessayer.");
            }
        }
        return timestamp;
    }


    public static void demanderReservation() {
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("Voulez-vous effectuer une réservation ? (O/N)");
            String reponse = scanner.nextLine().trim().toUpperCase();

            if (reponse.equals("O")) {
                while (true) {
                    System.out.println("Entrez le numéro d'immatriculation : ");
                    String immatriculation = scanner.nextLine().trim();
                    if (!immatriculation.isEmpty() && estImmatriculationValide(immatriculation)) {
                        Timestamp debutReserv = demanderDate("Entrez la date de début de réservation");
                        Timestamp finReserv = demanderDate("Entrez la date de fin de réservation");
                        Reservation.reserverBorne(immatriculation, debutReserv, finReserv);
                        break;
                    } else {
                        System.out.println("Numéro d'immatriculation invalide. Veuillez entrer un numéro d'immatriculation au format ABC-123.");
                    }
                }

                while (true) {
                    System.out.println("Voulez-vous effectuer une autre réservation ? (O/N)");
                    String autreReservation = scanner.nextLine().trim().toUpperCase();
                    if (autreReservation.equals("O")) {
                        break;
                    } else if (autreReservation.equals("N")) {
                        continuer = false;
                        System.out.println("Merci et à bientôt !");
                        break;
                    } else {
                        System.out.println("Réponse invalide. Veuillez répondre par 'O' pour Oui ou 'N' pour Non.");
                    }
                }
            } else if (reponse.equals("N")) {
                System.out.println("Vous avez choisi de ne pas effectuer de réservation.");
                continuer = false;
            } else {
                System.out.println("Réponse invalide. Veuillez répondre par 'O' pour Oui ou 'N' pour Non.");
            }
        }
    }



    public int getNumReservation() {
        return numReservation;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public int getIdClient() {
        return idClient;
    }

    public Timestamp getDebutReserv() {
        return debutReserv;
    }

    public Timestamp getFinReserv() {
        return finReserv;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }


    public static void clearReservations() {
        reservationsParNumero.clear();
        reservationsParImmatriculation.clear();
        nextNumReservation = 1;
    }

    public static boolean estImmatriculationValide(String immatriculation) {
        String regex = "^[A-Z]{3}-\\d{3}$"; // Exemple de format ABC-123
        return immatriculation.matches(regex);
    }

    public static int getReservationCount() {
        return reservationsParNumero.size();
    }

    public static Reservation getReservationByNum(int numReservation) {
        return reservationsParNumero.get(numReservation);
    }

    public static Reservation getReservationByImmatriculation(String immatriculation) {
        return reservationsParImmatriculation.get(immatriculation);
    }
}
