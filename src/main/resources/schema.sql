    CREATE TABLE Lager(
        LID INTEGER AUTO_INCREMENT NOT NULL,
        Navn VARCHAR(40) NOT NULL ,
        Adresse VARCHAR(40) NOT NULL ,
        PRIMARY KEY (LID)
    );
CREATE TABLE Bruker(
    BID INTEGER AUTO_INCREMENT NOT NULL ,
    Navn VARCHAR(40) NOT NULL ,
    Passord VARCHAR(40) NOT NULL ,
    PRIMARY KEY (BID)
);
CREATE TABLE Pakke(
    PID INTEGER AUTO_INCREMENT NOT NULL,
    LID INTEGER NOT NULL ,
    Eier VARCHAR(40) NOT NULL ,
    Vekt DOUBLE PRECISION NOT NULL ,
    Volum DOUBLE PRECISION NOT NULL ,
    PRIMARY KEY (PID),
    FOREIGN KEY (LID) REFERENCES Lager(LID)
);