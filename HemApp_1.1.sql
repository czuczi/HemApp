SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hemApp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hemApp` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `hemApp` ;

-- -----------------------------------------------------
-- Table `hemApp`.`Orvos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Orvos` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Orvos` (
  `ID` VARCHAR(32) NOT NULL,
  `Felh_nev` VARCHAR(50) NOT NULL,
  `Jelszo` VARCHAR(32) NOT NULL,
  `Vezeteknev` VARCHAR(100) NOT NULL,
  `Keresztnev` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(150) NULL,
  `Telefon` VARCHAR(20) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Beteg`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Beteg` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Beteg` (
  `ID` VARCHAR(32) NOT NULL,
  `Orvos_ID` VARCHAR(45) NULL,
  `Felh_nev` VARCHAR(50) NOT NULL,
  `Jelszo` VARCHAR(32) NOT NULL,
  `Vezeteknev` VARCHAR(100) NOT NULL,
  `Keresztnev` VARCHAR(100) NOT NULL,
  `TAJ` INT NOT NULL,
  `Email` VARCHAR(150) NULL,
  `Telefon` VARCHAR(20) NULL,
  PRIMARY KEY (`ID`),
  INDEX `Beteg_Orvos_FK_idx` (`Orvos_ID` ASC),
  CONSTRAINT `Beteg_Orvos_FK`
    FOREIGN KEY (`Orvos_ID`)
    REFERENCES `hemApp`.`Orvos` (`ID`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Uzenet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Uzenet` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Uzenet` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Orvos_ID` VARCHAR(32) NOT NULL,
  `idopont` DATETIME NOT NULL,
  `szoveg` VARCHAR(1000) NOT NULL,
  `kep_link` VARCHAR(300) NULL,
  PRIMARY KEY (`ID`),
  INDEX `Uzenet_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Uzenet_Orvos_FK_idx` (`Orvos_ID` ASC),
  CONSTRAINT `Uzenet_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemApp`.`Beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Uzenet_Orvos_FK`
    FOREIGN KEY (`Orvos_ID`)
    REFERENCES `hemApp`.`Orvos` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Keszitmeny`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Keszitmeny` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Keszitmeny` (
  `ID` VARCHAR(32) NOT NULL,
  `nev` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Kiszereles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Kiszereles` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Kiszereles` (
  `ID` VARCHAR(32) NOT NULL,
  `NE` INT NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Kesz_Kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Kesz_Kisz` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Kesz_Kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Keszitmeny_ID` VARCHAR(32) NOT NULL,
  `Kiszereles_ID` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Kesz_Kisz_Keszitmeny_FK_idx` (`Keszitmeny_ID` ASC),
  INDEX `Kesz_Kisz_Kiszereles_FK_idx` (`Kiszereles_ID` ASC),
  CONSTRAINT `Kesz_Kisz_Keszitmeny_FK`
    FOREIGN KEY (`Keszitmeny_ID`)
    REFERENCES `hemApp`.`Keszitmeny` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Kesz_Kisz_Kiszereles_FK`
    FOREIGN KEY (`Kiszereles_ID`)
    REFERENCES `hemApp`.`Kiszereles` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Beadott_Kesz_Kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Beadott_Kesz_Kisz` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Beadott_Kesz_Kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Kesz_Kisz_ID` VARCHAR(32) NOT NULL,
  `Datum` DATETIME NOT NULL,
  `Sorozatszam` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Beadot_Kesz_Kisz_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Beadott_Kesz_Kisz_Kesz_Kisz_FK_idx` (`Kesz_Kisz_ID` ASC),
  CONSTRAINT `Beadot_Kesz_Kisz_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemApp`.`Beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Beadott_Kesz_Kisz_Kesz_Kisz_FK`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemApp`.`Kesz_Kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Kert_Kesz_Kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Kert_Kesz_Kisz` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Kert_Kesz_Kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Kesz_Kisz_ID` VARCHAR(32) NOT NULL,
  `darab` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Kert_Kesz_Kisz_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Kert_Kesz_Kisz_Kesz_Kisz_ID_idx` (`Kesz_Kisz_ID` ASC),
  CONSTRAINT `Kert_Kesz_Kisz_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemApp`.`Beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Kert_Kesz_Kisz_Kesz_Kisz_ID`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemApp`.`Kesz_Kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Otthon_Kesz_Kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Otthon_Kesz_Kisz` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Otthon_Kesz_Kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Kesz_Kisz_ID` VARCHAR(32) NOT NULL,
  `sorozatszam` VARCHAR(60) NOT NULL,
  `darab` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Otthon_Kesz_Kisz_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Otthon_Kesz_Kisz_Kesz_Kisz_FK_idx` (`Kesz_Kisz_ID` ASC),
  CONSTRAINT `Otthon_Kesz_Kisz_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemApp`.`Beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Otthon_Kesz_Kisz_Kesz_Kisz_FK`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemApp`.`Kesz_Kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Injekciotortenet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Injekciotortenet` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Injekciotortenet` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Keszitmeny_ID` VARCHAR(32) NOT NULL,
  `Kezdetdatum` DATE NOT NULL,
  `Vegedatum` DATE NULL,
  `Mennyiseg` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Injekciotortenet_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Injekciotortenet_Keszitmeny_FK_idx` (`Keszitmeny_ID` ASC),
  CONSTRAINT `Injekciotortenet_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemApp`.`Beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Injekciotortenet_Keszitmeny_FK`
    FOREIGN KEY (`Keszitmeny_ID`)
    REFERENCES `hemApp`.`Keszitmeny` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Raktar_Kesz_Kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Raktar_Kesz_Kisz` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Raktar_Kesz_Kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Kesz_Kisz_ID` VARCHAR(32) NOT NULL,
  `Sorozatszam` VARCHAR(60) NOT NULL,
  `darab` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Raktar_Kesz_Kisz_Kesz_Kisz_FK_idx` (`Kesz_Kisz_ID` ASC),
  CONSTRAINT `Raktar_Kesz_Kisz_Kesz_Kisz_FK`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemApp`.`Kesz_Kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hemApp`.`Admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemApp`.`Admin` ;

CREATE TABLE IF NOT EXISTS `hemApp`.`Admin` (
  `Felh_nev` VARCHAR(50) NOT NULL,
  `Jelszo` VARCHAR(32) NOT NULL,
  `Vezeteknev` VARCHAR(100) NOT NULL,
  `Keresztnev` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(150) NULL,
  `Telefon` VARCHAR(20) NULL,
  PRIMARY KEY (`Felh_nev`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
