package org.fr.ul.miage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner (System.in);
    static Database database = new Database();
    static Client client = new Client();
    public static void main(String[] args) {
        menuPrincipal();
    }

    public static void menuPrincipal() {
        int choix = 0;
        boolean keepGoing = true;
        int numRes;
        String numImma;
        String numTel;

        while(keepGoing) {

            System.out.println("Bienvenue sur le menu principal de CarBorne. Que souhaitez-vous faire ?");
            System.out.println("1. Effectuer une recharge/Se présenter à une réservation");
            System.out.println("2. Consulter les réservations");
            System.out.println("3. Modifier une réservation");
            System.out.println("4. Prolonger une réservation");
            System.out.println("5. Consulter la disponibilité des bornes");
            System.out.println("6. Faire une réservation");
            System.out.println("7. Quitter");

            System.out.print("Entrez votre choix : ");
            while (!scanner.hasNextInt()) {
                System.out.println("Erreur : Veuillez saisir un choix valide.");
                scanner.next();
            }
            choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    System.out.println("Vous avez choisi d'effectuer une réservation.");
                    System.out.println("Souhaitez-vous entrer votre numéro de réservation (1) ou votre numéro d'immatriculation (2) ?");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Erreur : Veuillez saisir un choix valide.");
                        scanner.next();
                    }

                    int choixReservation = scanner.nextInt();

                    switch (choixReservation) {
                        case 1:
                            System.out.println("Vous avez choisi de saisir votre numéro de réservation. Veuillez saisir votre numéro de réservation : ");
                            while (!scanner.hasNextInt()) {
                                System.out.println("Erreur : Veuillez saisir un numéro de réservation valide.");
                                scanner.next();
                            }
                            numRes = scanner.nextInt();
                            System.out.println("Verification du numéro de réservation : " + numRes);
                            //Appel de la fonction qui vérifie si la réservation existe et qui correspond à ce créneau (10min avant le rendez-vous au moins) Database.existeReservation()
                            break;
                        case 2:
                            System.out.println("Vous avez choisi de saisir votre numéro d'immatriculation. (Exemple : AB-123-CD)");
                            String numImmaVerif;
                            do {
                                numImmaVerif = scanner.next();
                                if (!Client.verifImma(numImmaVerif)) {
                                    System.out.println("Erreur : Veuillez saisir un numéro d'immatriculation valide. (Exemple : AB-123-CD)");
                                }
                            } while (!Client.verifImma(numImmaVerif));

                            System.out.println("Verification du numéro d'immatriculation : " + numImmaVerif);
                            if (database.existingImmatriculation(numImmaVerif)) {
                                System.out.println("L'immatriculation saisie existe bien dans la base de données.");
                                //TODO : Suite du process de recharge (insertion dans la table Recharge) apres imma
                                // Demande de détails supplémentaires pour la recharge
                                System.out.println("Veuillez saisir la date et l'heure de début de la recharge (format: YYYY-MM-DD HH:MM:SS) :");
                                String debutRechargeStr = scanner.next() + " " + scanner.next();
                                Timestamp debutRecharge = Timestamp.valueOf(debutRechargeStr);
                                System.out.println("Veuillez saisir la durée de la recharge (en minutes) :");
                                int dureeRecharge = scanner.nextInt();
                                System.out.println("Veuillez saisir l'ID du client :");
                                int idClient = scanner.nextInt();
                                // Insertion des données de la recharge dans la base de données
                                database.insertRecharge(debutRecharge, dureeRecharge, null, idClient, null);
                            }else {
                                System.out.println("L'immatriculation saisie n'existe pas. Veuillez entrer un numéro de téléphone :");
                                do {
                                    numTel = scanner.next();
                                    if (!Client.verifTel(numTel)) {
                                        System.out.println("Erreur : Veuillez saisir un numéro de téléphone valide.");
                                    }
                                } while (!Client.verifTel(numTel));

                                System.out.println("Verification du numéro de téléphone : " + numTel);
                                if (database.existingNumTel(numTel)) {
                                    System.out.println("Le numéro de téléphone saisie existe bien dans la base de données.");
                                    // TODO : Suite du processus de recharge (insertion dans la table Recharge) apres tel
                                    System.out.println("Veuillez saisir la date et l'heure de début de la recharge (format: YYYY-MM-DD HH:MM:SS) :");
                                    String debutRechargeStr = scanner.next() + " " + scanner.next();
                                    Timestamp debutRecharge = Timestamp.valueOf(debutRechargeStr);
                                    System.out.println("Veuillez saisir la durée de la recharge (en minutes) :");
                                    int dureeRecharge = scanner.nextInt();
                                    System.out.println("Veuillez saisir l'ID du client :");
                                    int idClient = scanner.nextInt();
                                    // Insertion des données de la recharge dans la base de données
                                    database.insertRecharge(debutRecharge, dureeRecharge, null, idClient, null);
                                } else {
                                    System.out.println("Le numéro de téléphone saisie n'existe pas. Voulez-vous inscrire pour pouvoir continuer ?\n 1. OUI\n 2. NON");

                                    while (!scanner.hasNextInt()) {
                                        System.out.println("Erreur : Veuillez saisir un choix valide.");
                                        scanner.next();
                                    }

                                    int choixInscription = scanner.nextInt();
                                    switch (choixInscription) {
                                        case 1:
                                            Client.inscription();
                                            break;
                                        case 2:
                                            System.out.println("Vous avez choisi de ne pas vous inscrire...");
                                            break;
                                        default:
                                            System.out.println("Veuillez faire un choix valide");
                                    }
                                }
                            }
                            break;
                        default:
                            System.out.println("Choix invalide. Veuillez saisir 1 ou 2.");
                    }
                    break;
                case 2:
                    System.out.println("Vous avez choisi de consulter les réservations.");

                    System.out.println("Veuillez entrer votre numéro de téléphone : ");

                    do {
                        numTel = scanner.next();
                        if (!Client.verifTel(numTel)) {
                            System.out.println("Erreur : Veuillez saisir un numéro de téléphone valide.");
                        }
                    } while (!Client.verifTel(numTel));

                    System.out.println("Verification du numéro de téléphone : " + numTel);
                    if (database.existingNumTel(numTel)) {
                        System.out.println("Le numéro de téléphone saisie existe bien dans la base de données.");
                        List<Reservation> listRes = database.getReservationsAvecNumTel(numTel);
                        if (listRes.size() > 0) {
                            for (int i = 0; i < listRes.size(); i++) {
                                System.out.println((i + 1) + ". Réservation qui commence " + listRes.get(i).debutReserv + " et qui finit : " + listRes.get(i).finReserv);
                            }
                        } else {
                            System.out.println("Vous n'avez aucune réservation enregistré à ce numéro...");
                        }
                    }else{
                        System.out.println("Votre numéro de téléphone est inconnue de notre système...");
                    }
                        break;
                case 3:
                    System.out.println("Vous avez choisi de modifier une réservation.");
                    //Sprint 3
                    break;
                case 4:
                    System.out.println("Vous avez choisi de prolonger une réservation.");
                    //Sprint 3
                    break;
                case 5:
                    System.out.println("Vous avez choisi de consulter la disponibilité des bornes.");

 /*                   // Boucle pour forcer l'utilisateur à entrer un timestamp avec le bon format
                    Timestamp debutres = null;
                    while (debutres == null) {
                        System.out.println("Quel est l'heure et la date de départ ? (Format : yyyy-MM-dd HH:mm:ss) ");
                        String debutresString = scanner.next();
                        if (Borne.validateTimestampFormat(debutresString)) {
                            debutres = Timestamp.valueOf(debutresString);
                        } else {
                            System.out.println("Format de date et heure invalide. Assurez-vous d'utiliser le format : yyyy-MM-dd HH:mm:ss");
                        }
                    }

                    Timestamp finres = null;
                    while (finres == null) {
                        System.out.println("Très bien! Quel est l'heure et la date de fin ? (Format : yyyy-MM-dd HH:mm:ss) ");
                        String finresString = scanner.next();
                        if (Borne.validateTimestampFormat(finresString)) {
                            finres = Timestamp.valueOf(finresString);
                        } else {
                            System.out.println("Format de date et heure invalide. Assurez-vous d'utiliser le format : yyyy-MM-dd HH:mm:ss");
                        }
                    }
*/
                    //Nous allons considérer que la vérification des bornes se fait seulement maintenant
                    List<Integer> listeBorne = database.getToutesLesBornesDisponibles(Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now()));

                    if (listeBorne.size() > 0) {
                        System.out.println("Pour cette intervalle de date, il y a ces bornes de libres :");
                        for (int i = 0; i < listeBorne.size(); i++) {
                            System.out.println("Borne n°"+(i+1));
                        }
                    } else {
                        System.out.println("Aucune borne n'est disponible à cette intervalle donnée.");
                    }
                    break;

                case 6:
                    System.out.println("Vous avez choisi de faire une réservation.");
                    //Appel de la fonction pour créer une réservation
                    break;


                case 7:
                    System.out.println("Vous avez choisi de quitter. Au revoir !");
                    keepGoing=false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez saisir un choix valide.");
            }


        }
    }
}