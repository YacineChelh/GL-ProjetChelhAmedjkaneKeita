package org.fr.ul.miage;

import java.sql.Timestamp;

public class Reservation {
    public int numReservation;
    public Timestamp debutReserv;
    public Timestamp finReserv;
    public String etatReservation;
    public int idClient;
    public int idBorne;
    public int idFacturation;

    public Reservation(int numReservation, Timestamp debutReserv, Timestamp finReserv, String etatReservation, int idClient, int idBorne, int idFacturation) {
        this.numReservation = numReservation;
        this.debutReserv = debutReserv;
        this.finReserv = finReserv;
        this.etatReservation = etatReservation;
        this.idClient = idClient;
        this.idBorne = idBorne;
        this.idFacturation = idFacturation;
    }

}
