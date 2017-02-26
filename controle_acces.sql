-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 22 Février 2016 à 18:00
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `controle_acces`
--
CREATE DATABASE IF NOT EXISTS `controle_acces` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `controle_acces`;

-- --------------------------------------------------------

--
-- Structure de la table `acces`
--

CREATE TABLE IF NOT EXISTS `acces` (
  `idBadge` bigint(20) NOT NULL,
  `idZone` bigint(20) NOT NULL,
  PRIMARY KEY (`idBadge`,`idZone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `badge`
--

CREATE TABLE IF NOT EXISTS `badge` (
  `idBadge` bigint(20) NOT NULL AUTO_INCREMENT,
  `numero` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `dateCreation` date NOT NULL,
  `idPersonne` bigint(20) NOT NULL,
  PRIMARY KEY (`idBadge`),
  UNIQUE KEY `idPersonne` (`idPersonne`),
  UNIQUE KEY `numero` (`numero`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `batiment`
--

CREATE TABLE IF NOT EXISTS `batiment` (
  `idBatiment` bigint(20) NOT NULL AUTO_INCREMENT,
  `nomBatiment` varchar(100) NOT NULL,
  PRIMARY KEY (`idBatiment`),
  UNIQUE KEY `nomBatiment` (`nomBatiment`),
  UNIQUE KEY `nomBatiment_2` (`nomBatiment`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `historique`
--

CREATE TABLE IF NOT EXISTS `historique` (
  `datePassage` datetime NOT NULL,
  `idBadge` bigint(20) NOT NULL,
  `idZone` bigint(20) NOT NULL,
  PRIMARY KEY (`datePassage`,`idBadge`,`idZone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `lecteur`
--

CREATE TABLE IF NOT EXISTS `lecteur` (
  `idLecteur` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) NOT NULL,
  `idZone` bigint(20) NOT NULL,
  PRIMARY KEY (`idLecteur`),
  UNIQUE KEY `ip` (`ip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE IF NOT EXISTS `personne` (
  `idPersonne` bigint(20) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  PRIMARY KEY (`idPersonne`),
  KEY `nom` (`nom`),
  KEY `prenom` (`prenom`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `zone`
--

CREATE TABLE IF NOT EXISTS `zone` (
  `idZone` bigint(20) NOT NULL AUTO_INCREMENT,
  `nomZone` varchar(100) NOT NULL,
  `sensible` tinyint(1) NOT NULL,
  `horaireOuverture` time NOT NULL,
  `horaireFermeture` time NOT NULL,
  `idBatiment` bigint(20) NOT NULL,
  PRIMARY KEY (`idZone`),
  UNIQUE KEY `nomZone` (`nomZone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
