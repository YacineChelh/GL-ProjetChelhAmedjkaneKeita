package org.fr.ul.miage;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static Scanner scanner = new Scanner (System.in);
    public static void main(String[] args) {

        Client c = new Client();
        c.inscription();
    }

    public static String verifTel(String verif) {
        boolean keepGoing = true;

        while (keepGoing) {
            if (verif.length() == 0) {
                System.out.println("Veuillez entrer un numéro de téléphone valide !");
                verif = scanner.next();
            } else if (!verif.matches("^(06|07)[0-9]{8}$")) { //Regex qui vérifie la structure de le telephone entré
                System.out.println("Le numéro de téléphone doit commencer par 0 et contenir exactement 10 chiffres ! \n Veuillez entrer à nouveau un numéro valide :");
                verif = scanner.next();
            } else {
                keepGoing = false;
            }
        }
        System.out.println("Ok c bon");
        return verif;
    }
}