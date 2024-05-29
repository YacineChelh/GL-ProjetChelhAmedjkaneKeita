package org.fr.ul.miage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StationRecharge {

    //Database database = new Database();
    public List<Borne> bornesRecharge;

    public StationRecharge(int nombreDeBornes) {
        this.bornesRecharge = new ArrayList<>();
        for (int i = 0; i < nombreDeBornes; i++) {
            bornesRecharge.add(new Borne(i + 1));
        }
    }

    public boolean rechargerSansReservation(Client client, int dureeMinutes) {
        if (dureeMinutes < 0) {
            throw new IllegalArgumentException("La durée de recharge ne peut pas être négative.");
        }
        for (Borne borne : bornesRecharge) {
            if (borne.estDisponible()) {
                borne.commencerRecharge(client.immatriculation, dureeMinutes);
                //int borneChoisi = database.getBorneDisponible()

                System.out.println("Recharge commencée à la borne : " + borne.getId());
                //database.changesStatutsOccupé(borneChoisi);
                return true;
            }
        }
        System.out.println("Aucune borne de recharge disponible.");
        return false;
    }

    public boolean rechargerSansReservation(Client client, String heureDepart) {
        int dureeMinutes = calculerDuree(heureDepart);
        return rechargerSansReservation(client, dureeMinutes);
    }

    public int calculerDuree(String heureDepart) {
        // Supposons que l'heure de départ est donnée au format "HH:mm"
        String[] parties = heureDepart.split(":");
        int heures = Integer.parseInt(parties[0]);
        int minutes = Integer.parseInt(parties[1]);

        // Simulation de l'heure actuelle
        int heureActuelle = 10; // Exemple d'heure actuelle
        int minuteActuelle = 0; // Exemple de minute actuelle

        int duree = (heures - heureActuelle) * 60 + (minutes - minuteActuelle);
        return Math.max(duree, 0);
    }

    public void interagirAvecUtilisateur(Client client) {
        Scanner scanner = new Scanner(System.in);

        boolean choixValide = false;
        while (!choixValide) {
            System.out.println("Souhaitez-vous spécifier la durée de recharge prévue (D) ou l'heure de départ (H) ?");
            String choix = scanner.nextLine();

            if (choix.equalsIgnoreCase("D")) {
                boolean dureeValide = false;
                while (!dureeValide) {
                    System.out.println("Veuillez entrer la durée de recharge prévue en minutes :");
                    if (scanner.hasNextInt()) {
                        int dureeMinutes = scanner.nextInt();
                        scanner.nextLine();
                        if (dureeMinutes > 0) {
                            rechargerSansReservation(client, dureeMinutes);
                            choixValide = true;
                            dureeValide = true;
                        } else {
                            System.out.println("Veuillez entrer un nombre positif."); 
                        }
                    } else {
                        System.out.println("Veuillez entrer un nombre valide.");
                        scanner.next(); // Clear le buffer
                    }
                }
            } else if (choix.equalsIgnoreCase("H")) {
                System.out.println("Veuillez entrer votre heure de départ (format HH:mm) :");
                String heureDepart = scanner.next();
                rechargerSansReservation(client, heureDepart);
                choixValide = true;
            } else {
                System.out.println("Format invalide. Veuillez réessayer.");
            }
        }
    }

}
