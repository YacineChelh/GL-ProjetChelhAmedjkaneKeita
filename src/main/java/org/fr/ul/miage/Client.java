package org.fr.ul.miage;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class Client {
    static Scanner scanner = new Scanner(System.in);
    private Database database = new Database();
    String nom;
    String prenom;
    String mail;
    String adresse;
    String telephone;
    String numCarteCredit;
    String immatriculation;

    // Constructeur
    public Client() {
        // Initialisation des champs si nécessaire
    }

    public Client(String nom, String prenom, String mail, String adresse, String telephone, String numCarteCredit) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adresse = adresse;
        this.telephone=telephone;
        this.numCarteCredit=numCarteCredit;

    }

    public Client(String nom, String prenom, String mail, String adresse, String telephone, String numCarteCredit, String immatriculation) {
        if(immatriculation != null) {
            this.nom = nom;
            this.prenom = prenom;
            this.mail = mail;
            this.adresse = adresse;
            this.telephone = telephone;
            this.numCarteCredit = numCarteCredit;
            this.immatriculation = immatriculation;
        }else{
            new Client(nom,prenom,mail,adresse,telephone,numCarteCredit);
        }
    }

    // Méthode d'inscription
    public static void inscription() {
        String nom;
        String prenom;
        String mail;
        String adresse;
        String telephone;
        String numCarteCredit;
        String immatriculation = null;
        System.out.println("Bonjour et bienvenue sur la page inscription. \nQuel est votre nom ?");
        String temp = scanner.next();
        while (!verifNomPrenom(temp)) {
            System.out.println("Nom invalide. Quel est votre nom ?");
            temp = scanner.next();
        }
        nom = temp;

        System.out.println("Ensuite, quel est votre prénom ?");
        temp = scanner.next();
        while (!verifNomPrenom(temp)) {
            System.out.println("Prénom invalide. Quel est votre prénom ?");
            temp = scanner.next();
        }
        prenom = temp;

        System.out.println("Quel est votre adresse ?");
        temp = scanner.next();
        while (!verifAdresse(temp)) {
            System.out.println("Adresse invalide. Quel est votre adresse ?");
            temp = scanner.next();
        }
        adresse = temp;

        System.out.println("Quel est votre numéro de téléphone ?");
        temp = scanner.next();
        while (!verifTel(temp)) {
            System.out.println("Numéro de téléphone invalide. Quel est votre numéro de téléphone ?");
            temp = scanner.next();
        }
        telephone = temp;

        System.out.println("Quelle est votre adresse mail ?");
        temp = scanner.next();
        while (!verifMail(temp)) {
            System.out.println("Adresse mail invalide. Quelle est votre adresse mail ?");
            temp = scanner.next();
        }
        mail = temp;

        System.out.println("Quel est votre numéro de carte de crédit ?");
        temp = scanner.next();
        while (!verifCarte(temp)) {
            System.out.println("Numéro de carte de crédit invalide. Quel est votre numéro de carte de crédit ?");
            temp = scanner.next();
        }
        numCarteCredit = temp;

        System.out.println("Avez-vous un véhicule ? O/N");
        String reponse = scanner.next();
        while (!verifReponse(reponse)) {
            System.out.println("Veuillez faire un choix cohérent (O/N) :");
            reponse = scanner.next();
        }

        if (reponse.equalsIgnoreCase("O")) {
            System.out.println("Veuillez entrer l'immatriculation de votre véhicule : (ex : AB-123-CD)");
            temp = scanner.next();
            while (!verifImma(temp)) {
                System.out.println("Immatriculation invalide. Veuillez entrer l'immatriculation de votre véhicule (ex : AB-123-CD) :");
                temp = scanner.next();
            }
            immatriculation = temp;
        }
        new Client(nom,prenom,mail,adresse,telephone,numCarteCredit,immatriculation);
        // Insertion dans la BDD
        //database.insertClient(nom, prenom, adresse, telephone, mail, numCarteCredit, immatriculation);
        System.out.println("Très bien. Inscription terminée. Bienvenue " + prenom);
    }

    public static boolean verifReponse(String verif) {
        return verif.equalsIgnoreCase("O") || verif.equalsIgnoreCase("N");
    }

    public static boolean verifNomPrenom(String verif) {
        return verif.length() > 0 && verif.matches("[a-zA-Z]+(\\s[a-zA-Z]+)*");
    }

    public static boolean verifAdresse(String verif) {
        return verif.length() >= 5 && verif.length() <= 100 && verif.matches("[\\w\\s\\d.,'-]+");
    }

    public static boolean verifTel(String verif) {
        return verif.matches("^(0)[0-9]{9}$");
    }

    public static boolean verifMail(String verif) {
        return verif.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]+$");
    }

    public static boolean verifCarte(String verif) {
        return verif.length() == 16 && verif.matches("[0-9]+");
    }

    public static boolean verifImma(String verif) {
        return verif.matches("[A-Z]{2}-[0-9]{3}-[A-Z]{2}");
    }


    public void verificationBorneDisponible(Timestamp debut, Timestamp fin){
        List<Integer> listeBorneDisponible = database.getToutesLesBornesDisponibles(debut,fin);
        System.out.println("Liste des bornes disponibles : ");
        for(int i = 0; i<listeBorneDisponible.size();i++){
            System.out.println("- Borne "+(listeBorneDisponible.get(i)));
        }
    }
}
