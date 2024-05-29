package org.fr.ul.miage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Tests sur la classe Borne")
public class BorneTest {

    @Test
    @DisplayName("Initialisation de la borne")
    void testInitialisationBorne() {
        Borne borne = new Borne(1);
        Assertions.assertEquals(1, borne.getId(), "L'ID de la borne devrait être 1");
        Assertions.assertTrue(borne.estDisponible(), "La borne devrait être disponible après initialisation");
    }

    @Test
    @DisplayName("Commencer la recharge")
    void testCommencerRecharge() {
        Borne borne = new Borne(1);
        borne.commencerRecharge("AB-123-CD", 30);
        Assertions.assertFalse(borne.estDisponible(), "La borne ne devrait pas être disponible après le début de la recharge");
        Assertions.assertEquals("AB-123-CD", borne.immatriculationVehicule, "L'immatriculation du véhicule devrait être AB-123-CD");
        Assertions.assertEquals(30, borne.dureeRecharge, "La durée de recharge devrait être 30 minutes");
    }

    @Test
    @DisplayName("Terminer la recharge")
    void testTerminerRecharge() {
        Borne borne = new Borne(1);
        borne.commencerRecharge("AB-123-CD", 30);
        borne.terminerRecharge();
        Assertions.assertTrue(borne.estDisponible(), "La borne devrait être disponible après la fin de la recharge");
        Assertions.assertNull(borne.immatriculationVehicule, "L'immatriculation du véhicule devrait être null après la fin de la recharge");
        Assertions.assertEquals(0, borne.dureeRecharge, "La durée de recharge devrait être 0 après la fin de la recharge");
    }

    @Test
    @DisplayName("Vérification de la disponibilité de la borne")
    void testDisponibiliteBorne() {
        Borne borne = new Borne(1);
        Assertions.assertTrue(borne.estDisponible(), "La borne devrait être disponible après initialisation");
        borne.commencerRecharge("AB-123-CD", 30);
        Assertions.assertFalse(borne.estDisponible(), "La borne ne devrait pas être disponible pendant la recharge");
        borne.terminerRecharge();
        Assertions.assertTrue(borne.estDisponible(), "La borne devrait être disponible après la fin de la recharge");
    }
}
