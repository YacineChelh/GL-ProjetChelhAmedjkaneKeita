import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fr.ul.miage.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Test pour la classe client ")
public class ClientTest {
    Client client = new Client();

    @Test
    @DisplayName("Le client insère une réponse valide pour son véhicule")
    public void verifReponseTrueOKTest() {
        assertTrue(client.verifReponse("O"));
        assertTrue(client.verifReponse("N"));
        assertTrue(client.verifReponse("o"));
        assertTrue(client.verifReponse("n"));
    }

    @Test
    @DisplayName("Le client insère une réponse non valide pour son véhicule")
    public void verifReponseFalseOKTest() {
        assertFalse(client.verifReponse("A"));
        assertFalse(client.verifReponse("124"));
        assertFalse(client.verifReponse(""));
    }

    @Test
    @DisplayName("Le client insère un nom/prénom valide")
    public void verifInputTrueOKTest() {
        assertTrue(client.verifNomPrenom("Yacine"));
        assertTrue(client.verifNomPrenom("Prenom Compose"));
    }

    @Test
    @DisplayName("Le client insère un nom/prénom invalide")
    public void verifInputFalseOKTest() {
        assertFalse(client.verifNomPrenom("123"));
        assertFalse(client.verifNomPrenom("Jean123"));
        assertFalse(client.verifNomPrenom(""));
    }

    @Test
    @DisplayName("Le client insère une adresse valide")
    public void verifAdresseTrueOKTest() {
        assertTrue(client.verifAdresse("1 Rue du Test"));
        assertTrue(client.verifAdresse("2 Avenue ToujoursDuTest"));
    }

    @Test
    @DisplayName("Le client insère une adresse invalide")
    public void verifAdresseFalseOKTest() {
        assertFalse(client.verifAdresse(""));
        assertFalse(client.verifAdresse("1"));
        assertFalse(client.verifAdresse("@2345"));
    }

    @Test
    @DisplayName("Le client insère un numéro de téléphone valide")
    public void verifTelTrueOKTest() {
        assertTrue(client.verifTel("0123456789"));
    }

    @Test
    @DisplayName("Le client insère un numéro de téléphone invalide")
    public void verifTelFalseOKTest() {
        assertFalse(client.verifTel("1234567890"));
        assertFalse(client.verifTel("012345678"));
        assertFalse(client.verifTel("01234567890"));
        assertFalse(client.verifTel("abcdefghij"));
        assertFalse(client.verifTel("0606060606."));
    }

    @Test
    @DisplayName("Le client insère une adresse mail valide")
    public void verifMailTrueOKTest() {
        assertTrue(client.verifMail("email@test.com"));
        assertTrue(client.verifMail("email.email@test.com"));
    }

    @Test
    @DisplayName("Le client insère une adresse mail invalide")
    public void verifMailFalseOKTest() {
        assertFalse(client.verifMail("email@mail"));
        assertFalse(client.verifMail("email@.com"));
        assertFalse(client.verifMail("email@com"));
    }

    @Test
    @DisplayName("Le client insère un numéro de carte bancaire valide")
    public void verifCarteTrueOKTest() {
        assertTrue(client.verifCarte("1234567812345678"));
    }

    @Test
    @DisplayName("Le client insère un numéro de carte bancaire invalide")
    public void verifCarteFalseOKTest() {
        assertFalse(client.verifCarte("12345678"));
        assertFalse(client.verifCarte("123456781234567"));
        assertFalse(client.verifCarte("12345678123456789"));
        assertFalse(client.verifCarte("abcdefghabcdefgh"));
    }

    @Test
    @DisplayName("Le client insère un numéro d'immatriculation valide")
    public void verifImmaTrueOKTest() {
        assertTrue(client.verifImma("AB-123-CD"));
    }

    @Test
    @DisplayName("Le client insère un numéro d'immatriculation invalide")
    public void verifImmaFalseOKTest() {
        assertFalse(client.verifImma("AB123CD"));
        assertFalse(client.verifImma("123-AB-456"));
        assertFalse(client.verifImma("AB-12-CD"));
        assertFalse(client.verifImma("A-1234-CD"));
    }
}
