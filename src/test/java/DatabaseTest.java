import org.fr.ul.miage.Database;
import org.fr.ul.miage.DatabaseParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseTest {

    private Database database;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    @DisplayName("Mise en place de la connexion et du mock avec de commencer les tests.")
    void setUp() throws SQLException {
        database = new Database();
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        DatabaseParam.setConnection(mockConnection);
    }

    @Test
    @DisplayName("[OK] L'immatriculation existe bien dans la base de données.")
    void existingImmatriculationExistsOKTest() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("count")).thenReturn(1);

        boolean exists = database.existingImmatriculation("AB-123-CD");

        assertTrue(exists);
        verify(mockPreparedStatement).setString(1, "AB-123-CD");
    }

    @Test
    @DisplayName("[OK] L'immatriculation n'existe pas dans la base de données.")
    void existingImmatriculationNotExistsOKTest() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("count")).thenReturn(0);

        boolean exists = database.existingImmatriculation("AB-123-CD");

        assertFalse(exists);
        verify(mockPreparedStatement).setString(1, "AB-123-CD");
    }


    @Test
    @DisplayName("[OK] L'insertion dans la base de données fonctionne.")
    void insertClientOKTest() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        database.insertClient("John", "Doe", "123 Main St", "1234567890", "john.doe@example.com", "1234567812345678", "AB-123-CD");

        verify(mockPreparedStatement).setString(1, "John");
        verify(mockPreparedStatement).setString(2, "Doe");
        verify(mockPreparedStatement).setString(3, "123 Main St");
        verify(mockPreparedStatement).setString(4, "1234567890");
        verify(mockPreparedStatement).setString(5, "john.doe@example.com");
        verify(mockPreparedStatement).setString(6, "1234567812345678");
        verify(mockPreparedStatement).setString(7, "AB-123-CD");
        verify(mockPreparedStatement).executeUpdate();
    }


    @Test
    @DisplayName("La récupération des bornes disponibles fonctionne.")
    void getToutesLesBornesDisponiblesOKTest() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(mockResultSet.getInt("IdBorne")).thenReturn(1).thenReturn(2);

        Timestamp debutreserv = Timestamp.valueOf("2024-06-05 10:00:00");
        Timestamp finreserv = Timestamp.valueOf("2024-06-05 12:00:00");
        List<Integer> bornesDisponibles = database.getToutesLesBornesDisponibles(debutreserv, finreserv);

        assertEquals(2, bornesDisponibles.size());
        assertEquals(1, bornesDisponibles.get(0));
        assertEquals(2, bornesDisponibles.get(1));

        verify(mockPreparedStatement).setTimestamp(1, finreserv);
        verify(mockPreparedStatement).setTimestamp(2, debutreserv);
        verify(mockPreparedStatement).setTimestamp(3, debutreserv);
        verify(mockPreparedStatement).setTimestamp(4, finreserv);
        verify(mockPreparedStatement).setTimestamp(5, finreserv);
        verify(mockPreparedStatement).setTimestamp(6, debutreserv);
        verify(mockPreparedStatement).setTimestamp(7, debutreserv);
        verify(mockPreparedStatement).setTimestamp(8, finreserv);
    }






}
