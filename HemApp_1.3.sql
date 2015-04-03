-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema hemapp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hemapp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hemapp` DEFAULT CHARACTER SET utf8 ;
USE `hemapp` ;

-- -----------------------------------------------------
-- Table `hemapp`.`admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`admin` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`admin` (
  `Felh_nev` VARCHAR(50) NOT NULL,
  `Jelszo` VARCHAR(32) NOT NULL,
  `Vezeteknev` VARCHAR(100) NOT NULL,
  `Keresztnev` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(150) NULL DEFAULT NULL,
  `Telefon` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`Felh_nev`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`orvos`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`orvos` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`orvos` (
  `ID` VARCHAR(32) NOT NULL,
  `Felh_nev` VARCHAR(50) NOT NULL,
  `Jelszo` VARCHAR(32) NOT NULL,
  `Vezeteknev` VARCHAR(100) NOT NULL,
  `Keresztnev` VARCHAR(100) NOT NULL,
  `Email` VARCHAR(150) NULL DEFAULT NULL,
  `Telefon` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`beteg`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`beteg` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`beteg` (
  `ID` VARCHAR(32) NOT NULL,
  `Orvos_ID` VARCHAR(45) NULL DEFAULT NULL,
  `Felh_nev` VARCHAR(50) NOT NULL,
  `Jelszo` VARCHAR(32) NOT NULL,
  `Vezeteknev` VARCHAR(100) NOT NULL,
  `Keresztnev` VARCHAR(100) NOT NULL,
  `TAJ` INT(11) NOT NULL,
  `Email` VARCHAR(150) NULL DEFAULT NULL,
  `Telefon` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Beteg_Orvos_FK_idx` (`Orvos_ID` ASC),
  CONSTRAINT `Beteg_Orvos_FK`
    FOREIGN KEY (`Orvos_ID`)
    REFERENCES `hemapp`.`orvos` (`ID`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`keszitmeny`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`keszitmeny` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`keszitmeny` (
  `ID` VARCHAR(32) NOT NULL,
  `nev` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`kiszereles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`kiszereles` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`kiszereles` (
  `ID` VARCHAR(32) NOT NULL,
  `NE` INT(11) NOT NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`kesz_kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`kesz_kisz` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`kesz_kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Keszitmeny_ID` VARCHAR(32) NOT NULL,
  `Kiszereles_ID` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Kesz_Kisz_Keszitmeny_FK_idx` (`Keszitmeny_ID` ASC),
  INDEX `Kesz_Kisz_Kiszereles_FK_idx` (`Kiszereles_ID` ASC),
  CONSTRAINT `Kesz_Kisz_Keszitmeny_FK`
    FOREIGN KEY (`Keszitmeny_ID`)
    REFERENCES `hemapp`.`keszitmeny` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Kesz_Kisz_Kiszereles_FK`
    FOREIGN KEY (`Kiszereles_ID`)
    REFERENCES `hemapp`.`kiszereles` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`beadott_kesz_kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`beadott_kesz_kisz` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`beadott_kesz_kisz` (
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
    REFERENCES `hemapp`.`beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Beadott_Kesz_Kisz_Kesz_Kisz_FK`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemapp`.`kesz_kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`injekciotortenet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`injekciotortenet` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`injekciotortenet` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Keszitmeny_ID` VARCHAR(32) NOT NULL,
  `Kezdetdatum` DATE NOT NULL,
  `Vegedatum` DATE NULL DEFAULT NULL,
  `Mennyiseg` INT(11) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Injekciotortenet_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Injekciotortenet_Keszitmeny_FK_idx` (`Keszitmeny_ID` ASC),
  CONSTRAINT `Injekciotortenet_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemapp`.`beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Injekciotortenet_Keszitmeny_FK`
    FOREIGN KEY (`Keszitmeny_ID`)
    REFERENCES `hemapp`.`keszitmeny` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`kert_kesz_kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`kert_kesz_kisz` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`kert_kesz_kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Kesz_Kisz_ID` VARCHAR(32) NOT NULL,
  `darab` INT(11) NOT NULL,
  `idopont` DATE NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Kert_Kesz_Kisz_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Kert_Kesz_Kisz_Kesz_Kisz_ID_idx` (`Kesz_Kisz_ID` ASC),
  CONSTRAINT `Kert_Kesz_Kisz_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemapp`.`beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Kert_Kesz_Kisz_Kesz_Kisz_ID`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemapp`.`kesz_kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`otthon_kesz_kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`otthon_kesz_kisz` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`otthon_kesz_kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Kesz_Kisz_ID` VARCHAR(32) NOT NULL,
  `sorozatszam` VARCHAR(60) NOT NULL,
  `darab` INT(11) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Otthon_Kesz_Kisz_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Otthon_Kesz_Kisz_Kesz_Kisz_FK_idx` (`Kesz_Kisz_ID` ASC),
  CONSTRAINT `Otthon_Kesz_Kisz_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemapp`.`beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Otthon_Kesz_Kisz_Kesz_Kisz_FK`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemapp`.`kesz_kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`raktar_kesz_kisz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`raktar_kesz_kisz` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`raktar_kesz_kisz` (
  `ID` VARCHAR(32) NOT NULL,
  `Orvos_ID` VARCHAR(32) NOT NULL,
  `Kesz_Kisz_ID` VARCHAR(32) NOT NULL,
  `Sorozatszam` VARCHAR(60) NOT NULL,
  `darab` INT(11) NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Raktar_Kesz_Kisz_Kesz_Kisz_FK_idx` (`Kesz_Kisz_ID` ASC),
  INDEX `Raktar_Kesz_Kisz_Orvos_FK_idx` (`Orvos_ID` ASC),
  CONSTRAINT `Raktar_Kesz_Kisz_Kesz_Kisz_FK`
    FOREIGN KEY (`Kesz_Kisz_ID`)
    REFERENCES `hemapp`.`kesz_kisz` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Raktar_Kesz_Kisz_Orvos_FK`
    FOREIGN KEY (`Orvos_ID`)
    REFERENCES `hemapp`.`orvos` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `hemapp`.`uzenet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hemapp`.`uzenet` ;

CREATE TABLE IF NOT EXISTS `hemapp`.`uzenet` (
  `ID` VARCHAR(32) NOT NULL,
  `Beteg_ID` VARCHAR(32) NOT NULL,
  `Orvos_ID` VARCHAR(32) NOT NULL,
  `idopont` DATETIME NOT NULL,
  `szoveg` VARCHAR(1000) NOT NULL,
  `kep_link` VARCHAR(300) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  INDEX `Uzenet_Beteg_FK_idx` (`Beteg_ID` ASC),
  INDEX `Uzenet_Orvos_FK_idx` (`Orvos_ID` ASC),
  CONSTRAINT `Uzenet_Beteg_FK`
    FOREIGN KEY (`Beteg_ID`)
    REFERENCES `hemapp`.`beteg` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `Uzenet_Orvos_FK`
    FOREIGN KEY (`Orvos_ID`)
    REFERENCES `hemapp`.`orvos` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
