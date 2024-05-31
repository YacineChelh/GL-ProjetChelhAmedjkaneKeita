package org.fr.ul.miage;

import org.fr.ul.miage.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test pour la classe Reservation")
public class ReservationTest {

    @BeforeEach
    void setup() {
        Reservation.clearReservations();
    }

    @Test
    @DisplayName("Vérifier la création d'une réservation")
    void testCreationReservation() {
        Reservation reservation = new Reservation("ABC-123", 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));

        assertEquals(1, Reservation.getReservationCount());
        assertEquals(reservation, Reservation.getReservationByNum(1));
        assertEquals(reservation, Reservation.getReservationByImmatriculation("ABC-123"));
    }

    @Test
    @DisplayName("Vérifier la création de plusieurs réservations")
    void testCreationMultipleReservations() {
        Reservation reservation1 = new Reservation("ABC-123", 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));
        Reservation reservation2 = new Reservation("DEF-456", 2, 2, new Timestamp(System.currentTimeMillis() + 7200000), new Timestamp(System.currentTimeMillis() + 10800000));

        assertEquals(2, Reservation.getReservationCount());
        assertTrue(Reservation.verifierNumeroReservation(reservation1.getNumReservation()));
        assertTrue(Reservation.verifierNumeroReservation(reservation2.getNumReservation()));
        assertTrue(Reservation.verifierNumeroImmatriculation(reservation1.getImmatriculation()));
        assertTrue(Reservation.verifierNumeroImmatriculation(reservation2.getImmatriculation()));
    }

    @Test
    @DisplayName("Vérifier la vérification du numéro de réservation")
    void testVerifierNumeroReservation() {
        Reservation reservation = new Reservation("ABC-123", 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));

        assertTrue(Reservation.verifierNumeroReservation(reservation.getNumReservation()));
        assertFalse(Reservation.verifierNumeroReservation(2));
    }

    @Test
    @DisplayName("Vérifier la vérification du numéro d'immatriculation")
    void testVerifierNumeroImmatriculation() {
        Reservation reservation = new Reservation("ABC-123", 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));

        assertTrue(Reservation.verifierNumeroImmatriculation(reservation.getImmatriculation()));
        assertFalse(Reservation.verifierNumeroImmatriculation("DEF-456"));
    }

    @Test
    @DisplayName("Vérifier la réservation d'une borne avec un numéro de réservation")
    void testReserverBorneAvecNumeroReservation() {
        Reservation reservation = new Reservation("ABC-123", 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));
        Timestamp debutReserv = new Timestamp(System.currentTimeMillis() + 7200000);
        Timestamp finReserv = new Timestamp(System.currentTimeMillis() + 10800000);
        Reservation.reserverBorne(reservation.getNumReservation(), debutReserv, finReserv);

        assertEquals("Borne réservée pour la réservation numéro : " + reservation.getNumReservation() + " par le client numéro : " + reservation.getIdClient() +
                " debutReserv " + debutReserv + " et finReserv " + finReserv, reservation.getLastMessage());
    }

    @Test
    @DisplayName("Vérifier la réservation d'une borne avec un numéro d'immatriculation")
    void testReserverBorneAvecNumeroImmatriculation() {
        Reservation reservation = new Reservation("ABC-123", 1, 1, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));
        Timestamp debutReserv = new Timestamp(System.currentTimeMillis() + 7200000);
        Timestamp finReserv = new Timestamp(System.currentTimeMillis() + 10800000);
        Reservation.reserverBorne(reservation.getImmatriculation(), debutReserv, finReserv);

        assertEquals("Borne réservée pour le véhicule immatriculé : " + reservation.getImmatriculation() + " par le client numéro : " + reservation.getIdClient() +
                " debutReserv " + debutReserv + " et finReserv " + finReserv, reservation.getLastMessage());
    }

    @Test
    @DisplayName("Vérifier l'invalidation d'une réservation avec un numéro invalide")
    void testReserverBorneAvecNumeroReservationInvalide() {
        Reservation.reserverBorne(999, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));

        assertFalse(Reservation.verifierNumeroReservation(999));
    }

    @Test
    @DisplayName("Vérifier l'invalidation d'une réservation avec un numéro d'immatriculation invalide")
    void testReserverBorneAvecNumeroImmatriculationInvalide() {
        Reservation.reserverBorne("XYZ-999", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis() + 3600000));

        assertFalse(Reservation.verifierNumeroImmatriculation("XYZ-999"));
    }
}
