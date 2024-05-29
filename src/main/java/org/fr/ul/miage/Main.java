package org.fr.ul.miage;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner (System.in);
    public static void main(String[] args) {

        Client client = new Client();
        StationRecharge stationRecharge = new StationRecharge(5); // Supposons 5 bornes de recharge disponibles
        stationRecharge.interagirAvecUtilisateur(client);
    }


}