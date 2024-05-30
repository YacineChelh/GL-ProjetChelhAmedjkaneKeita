package org.fr.ul.miage;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static Scanner scanner = new Scanner (System.in);
    static Database database = new Database();
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
            System.out.println("1. Effectuer une réservation de borne");
            System.out.println("2. Consulter les réservations");
            System.out.println("3. Modifier une réservation");
            System.out.println("4. Prolonger une réservation");
            System.out.println("5. Consulter la disponibilité des bornes");
            System.out.println("6. Quitter");

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
                            while (!Client.verifImma(scanner.next())) {
                                System.out.println("Erreur : Veuillez saisir un numéro d'immatriculation valide. (Exemple : AB-123-CD)");
                                scanner.next();
                            }
                            numImma = scanner.next();
                            System.out.println("Verification du numéro d'immatriculation : " + numImma);
                            if (database.existingImmatriculation(numImma)) {
                                System.out.println("L'immatriculation saisie existe bien dans la base de données.");
                                //TODO : Suite du process de recharge apres imma
                            } else {
                                System.out.println("L'immatriculation saisie n'existe pas. Veuillez entrer un numéro de téléphone :");
                                while (!Client.verifTel(scanner.next())) {
                                    System.out.println("Erreur : Veuillez saisir un numéro de téléphone valide.");
                                    scanner.next();
                                }
                                numTel = scanner.next();
                                System.out.println("Verification du numéro de téléphone : " + numTel);
                                if (database.existingImmatriculation(numTel)) {
                                    System.out.println("Le numéro de téléphone saisie existe bien dans la base de données.");
                                    //TODO : Suite du processus de recharge apres tel
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
                    break;
                case 3:
                    System.out.println("Vous avez choisi de modifier une réservation.");
                    break;
                case 4:
                    System.out.println("Vous avez choisi de prolonger une réservation.");
                    break;
                case 5:
                    System.out.println("Vous avez choisi de consulter la disponibilité des bornes.");
                    break;
                case 6:
                    System.out.println("Vous avez choisi de quitter. Au revoir !");
                    keepGoing=false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez saisir un choix valide.");
            }


        }
    }
}