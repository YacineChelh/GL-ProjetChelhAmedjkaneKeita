package org.fr.ul.miage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

@DisplayName("Tests sur la classe StationRecharge")
public class StationRechargeTest {

    @Test
    @DisplayName("Initialisation de la station de recharge")
    void testInitialisationStationRecharge() {
        int nombreDeBornes = 5;
        StationRecharge station = new StationRecharge(nombreDeBornes);

        Assertions.assertNotNull(station, "La station de recharge ne devrait pas être null");
        Assertions.assertEquals(nombreDeBornes, station.bornesRecharge.size(), "Le nombre de bornes de recharge devrait être égal au nombre spécifié");
        for (int i = 0; i < nombreDeBornes; i++) {
            Assertions.assertEquals(i + 1, station.bornesRecharge.get(i).getId(), "L'ID de la borne devrait être " + (i + 1));
        }
    }

    @Test
    @DisplayName("Recharge sans réservation avec durée en minutes")
    void testRechargerSansReservationDuree() {
        StationRecharge station = new StationRecharge(1);
        Borne borne = station.bornesRecharge.get(0);
        Client client = new Client();
        boolean result = station.rechargerSansReservation(client, 30);
        Assertions.assertTrue(result, "La recharge devrait être commencée");
        Assertions.assertEquals(1, borne.getRechargesCommencees(), "Une recharge devrait avoir commencé à la borne");
    }

    @Test
    @DisplayName("Recharge sans réservation avec heure de départ")
    void testRechargerSansReservationHeureDepart() {
        StationRecharge station = new StationRecharge(1);
        Borne borne = station.bornesRecharge.get(0);

        Client client = new Client();
        boolean result = station.rechargerSansReservation(client, "12:00");

        Assertions.assertTrue(result, "La recharge devrait être commencée");
        Assertions.assertEquals(1, borne.getRechargesCommencees(), "Une recharge devrait avoir commencé à la borne");
    }

    @Test
    @DisplayName("Calcul de la durée en minutes")
    void testCalculerDuree() {
        StationRecharge station = new StationRecharge(1);

        int duree = station.calculerDuree("12:00");
        Assertions.assertEquals(120, duree, "La durée calculée devrait être de 120 minutes");

        duree = station.calculerDuree("10:00");
        Assertions.assertEquals(0, duree, "La durée calculée devrait être de 0 minutes car l'heure de départ est passée");
    }

    @Test
    @DisplayName("Recharge impossible quand toutes les bornes sont occupées")
    void testRechargeImpossible() {
        StationRecharge station = new StationRecharge(1);
        Borne borne = station.bornesRecharge.get(0);
        //Borne borne = new Borne(1);
        borne.commencerRecharge("AB-123-CD", 30);
        //station.bornesRecharge.add(borne);

        Client client = new Client();
        boolean result = station.rechargerSansReservation(client, 30);

        Assertions.assertFalse(result, "La recharge ne devrait pas être commencée");
        Assertions.assertEquals(1, borne.getRechargesCommencees(), "Une recharge devrait déjà être en cours à la borne");
    }

    @Test
    @DisplayName("Exception pour durée de recharge négative")
    void testRechargerSansReservationDureeNegative() {
        StationRecharge station = new StationRecharge(1);
        Client client = new Client();

        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            station.rechargerSansReservation(client, -10);
        });

        Assertions.assertEquals("La durée de recharge ne peut pas être négative.", exception.getMessage(), "Le message de l'exception devrait être correct");
    }
}