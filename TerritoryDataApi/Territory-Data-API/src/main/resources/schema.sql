CREATE TABLE gi_comuni_cap (
  `codice_istat` varchar(12) NOT NULL,
  `denominazione_ita_altra` varchar(191) ,
  `denominazione_ita` varchar(191) NOT NULL,
  `denominazione_altra` varchar(191),
  `cap` varchar(10) NOT NULL,
  `sigla_provincia` varchar(4) NOT NULL,
  `denominazione_provincia` varchar(50) NOT NULL,
  `tipologia_provincia` varchar(100) ,
  `codice_regione` varchar(4) ,
  `denominazione_regione` varchar(50) ,
  `tipologia_regione` varchar(30) ,
  `ripartizione_geografica` varchar(20) ,
  `flag_capoluogo` varchar(4) ,
  `codice_belfiore` varchar(8) ,
  `lat` decimal(13,7) ,
  `lon` decimal(13,7) ,
  `superficie_kmq` decimal(10,4) ,
  PRIMARY KEY (`codice_istat`,`cap`));
  
  CREATE TABLE utente (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR (50) NOT NULL,
	cognome VARCHAR(50) NOT NULL,
	dataDiNascita DATE NOT NULL);

  