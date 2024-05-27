package org.fr.ul.miage;

import java.util.Scanner;

public class Client {
    Scanner scanner = new Scanner(System.in);
    String nom;
    String prenom;
    String mail;
    String adresse;
    String telephone;
    String numCarteCredit;
    String immatriculation;

    public Client() {
        System.out.println("Bonjour et bienvenue sur la page inscription. \nQuel est votre nom ?");
        String temp = scanner.next();
        this.nom = verifInput(temp);

        System.out.println("Ensuite, quel est votre prénom ?");
        temp = scanner.next();
        this.prenom = verifInput(temp);

        System.out.println("Quel est votre adresse ?");
        temp= scanner.next();
        this.adresse = verifAdresse(temp);

        System.out.println("Quel est votre numéro de téléphone ?");
        temp= scanner.next();
        this.telephone = verifTel(temp);

        System.out.println("Quelle est votre adresse mail ?");
        temp=scanner.next();
        this.mail = verifMail(temp);

        System.out.println("Quel est votre numéro de carte de crédit ?");
        temp=scanner.next();
        this.numCarteCredit=verifCarte(temp);

        System.out.println("Avez-vous un véhicule ? O/N");
        if(verifReponse(scanner.next())=="O"){
            System.out.println("Veuillez l'immatriculation de votre véhicule :");
            temp=scanner.next();
            this.immatriculation=verifImma(temp);
        }
        System.out.println("Très bien. Inscription terminée. Bienvenue "+this.prenom);
    }

    public String verifReponse(String verif) {
        while (true) {
            if (verif.equalsIgnoreCase("O")) {
                return "O";
            } else if (verif.equalsIgnoreCase("N")) {
                return "N";
            } else {
                System.out.println("Veuillez entrer une réponse valide (O/N) :");
                verif = scanner.nextLine();
            }
        }
    }

    public String verifInput(String verif) {
        boolean keepGoing = true;

        while (keepGoing) {
            if (verif.length() == 0) {
                System.out.println("Veuillez entrer un champ valide !");
                verif = scanner.next();
            } else if (!verif.matches("[a-zA-Z]+")) {
                System.out.println("Le nom ne doit contenir que des lettres !\nVeuillez la saisir à nouveau :");
                verif = scanner.next();
            } else {
                keepGoing = false;
            }
        }
        return verif;
    }

    public String verifAdresse(String verif) {
        boolean keepGoing = true;

        while (keepGoing) {
            if (verif.length() == 0) {
                System.out.println("Veuillez entrer une adresse valide !");
                verif = scanner.nextLine();
            } else if (verif.length() < 5 || verif.length() > 100) {
                System.out.println("L'adresse doit contenir entre 5 et 100 caractères ! Veuillez la saisir à nouveau :");
                verif = scanner.nextLine();
            } else if (!verif.matches("[\\w\\s\\d.,'-]+")) {
                System.out.println("L'adresse contient des caractères invalides !\n Veuillez la saisir à nouveau :");
                verif = scanner.nextLine();
            } else {
                keepGoing = false;
            }
        }
        return verif;
    }

    public String verifTel(String verif) {
        boolean keepGoing = true;

        while (keepGoing) {
            if (verif.length() == 0) {
                System.out.println("Veuillez entrer un numéro de téléphone valide !\n Veuillez le saisir à nouveau :");
                verif = scanner.next();
            } else if (!verif.matches("^(06|07)[0-9]{8}$")) { //Regex qui vérifie la structure de le telephone entré
                System.out.println("Le numéro de téléphone doit commencer par 0 et contenir exactement 10 chiffres ! \n Veuillez entrer à nouveau un numéro valide :");
                verif = scanner.next();
            } else {
                keepGoing = false;
            }
        }
        return verif;
    }

    public String verifMail(String verif) {
        boolean keepGoing = true;

        while (keepGoing) {
            if (verif.length() == 0) {
                System.out.println("Veuillez entrer une adresse e-mail valide ! \nVeuillez entrer une adresse valide :");
                verif = scanner.next();
            } else if (!verif.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) { //Regex qui vérifie la structure de l'adresse mail entré
                System.out.println("L'adresse e-mail n'est pas valide ! \nVeuillez entrer une adresse valide :");
                verif = scanner.next();
            } else {
                keepGoing = false;
            }
        }
        return verif;
    }

    public String verifCarte(String verif) {
        boolean keepGoing = true;

        while(keepGoing) {
            if (verif.length() != 16) {
                System.out.println("Le numéro de carte de crédit doit contenir exactement 16 chiffres. \nVeuillez le saisir à nouveau : ");
                verif = scanner.nextLine();
            } else if (!verif.matches("[0-9]+")) {
                System.out.println("Le numéro de carte de crédit ne doit contenir que des chiffres. \nVeuillez le saisir à nouveau :");
                verif = scanner.nextLine();
            } else {
                keepGoing = false;
            }
        }
        return verif;
    }


    public String verifImma(String verif){
        boolean keepGoing = true;

        while(keepGoing) {
            if (verif.matches("[A-Z]{2}-[0-9]{3}-[A-Z]{2}")) {
                return verif;
            } else {
                System.out.println("Veuillez entrer une immatriculation valide (ex: AB-123-CD) :");
                verif = scanner.nextLine();
            }
        }

        return verif;
    }

}

