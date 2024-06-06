
-- Enumérations
CREATE TYPE EEtatReservation AS ENUM ('Prolongée', 'Terminée', 'Modifiée', 'Annulée', 'En cours', 'Programmée');
CREATE TYPE EEtatBorne AS ENUM ('Disponible', 'Indisponible', 'Réservée', 'Occupée');

-- Table Client
CREATE TABLE Client (
    IdClient SERIAL PRIMARY KEY Not NULL,
    Nom VARCHAR(50) Not NULL,
    Prenom VARCHAR(50) Not NULL,
    Adresse VARCHAR(100) Not NULL,
    Tel VARCHAR(10) Not NULL,
    Mail VARCHAR(100) Not NULL,
    NCarteCredit VARCHAR(16) Not NULL,
    Immatriculation VARCHAR(9)
);



-- Table Contrat
CREATE TABLE Contrat (
    IdContrat SERIAL PRIMARY KEY,
    DebutContrat TIMESTAMP,
    FinContrat TIMESTAMP,
    IdClient INT,
    FOREIGN KEY (IdClient) REFERENCES Client(IdClient)
);



-- Table Borne
CREATE TABLE Borne (
    IdBorne SERIAL PRIMARY KEY,
    EtatBorne EEtatBorne,
    DureeTotal INT,
    DureeRestante INT
);


-- Table Réservation
CREATE TABLE Reservation (
    NumReservation SERIAL PRIMARY KEY,
    DebutReserv TIMESTAMP,
    FinReserv TIMESTAMP,
    EtatReservation EEtatReservation,
    IdClient INT,
    IdBorne INT,
    IdFacturation INT,
    FOREIGN KEY (IdClient) REFERENCES Client(IdClient),
    FOREIGN KEY (IdFacturation) REFERENCES Facturation(IdFacturation),
    FOREIGN KEY (IdBorne) REFERENCES Borne(IdBorne)
);


-- Table Recharge
CREATE TABLE Recharge (
    IdRecharge SERIAL PRIMARY KEY,
    DebutRecharge TIMESTAMP,
    DureeRecharge INT,
    IdBorne INT,
    IdClient INT,
    IdFacturation INT,
    FOREIGN KEY (IdBorne) REFERENCES Borne(IdBorne),
    FOREIGN KEY (IdClient) REFERENCES Client(IdClient)

);

-- Table Facturation
CREATE TABLE Facturation (
    IdFacturation SERIAL PRIMARY KEY,
    Montant FLOAT,
    Date TIMESTAMP,
    IdClient INT,
    IdRecharge INT,
    FOREIGN KEY (IdClient) REFERENCES Client(IdClient),
    FOREIGN KEY (IdRecharge) REFERENCES Recharge(IdRecharge)
);


-- Insertion dans la base de données manuelle des bornes
INSERT INTO Borne (EtatBorne, DureeTotal, DureeRestante)
VALUES ('Disponible', 0, 0),
       ('Disponible', 0, 0),
       ('Disponible', 0, 0),
       ('Disponible', 0, 0),
       ('Disponible', 0, 0);

SELECT * FROM Borne;
SELECT * FROM Client;
SELECT * FROM Reservation;

INSERT INTO Reservation (DebutReserv, FinReserv, EtatReservation, IdClient, IdBorne)
VALUES ('2024-06-06 10:00:00', '2024-06-06 12:00:00', 'En cours', 1, 3);
