-- MySQL dump 10.11
--
-- Host: localhost    Database: astro
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `friday_alert`
--

DROP TABLE IF EXISTS `friday_alert`;
CREATE TABLE `friday_alert` (
  `id` int(11) NOT NULL,
  `message` varchar(160) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `friday_alert`
--

LOCK TABLES `friday_alert` WRITE;
/*!40000 ALTER TABLE `friday_alert` DISABLE KEYS */;
INSERT INTO `friday_alert` VALUES (1,'ada sikurada(Friday) maru sitina dishawa ginikona(SouthEast),','pending'),(2,' ada wurshaba, kataka, kanya, danu, makara, kumbha lagna himiyanta jaya dinayaki(2012 wasara sadaha) ','pending'),(3,' kona masa - janawari (january) 1 - 15 dakwa danu rawiya, kona masawala wiwaha, niwasa katayutu ashubai','pending'),(4,'heta senasurada(Saturday) rahu kalaya 9.00-10.30 ','pending');
/*!40000 ALTER TABLE `friday_alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `monday_alert`
--

DROP TABLE IF EXISTS `monday_alert`;
CREATE TABLE `monday_alert` (
  `id` int(11) NOT NULL,
  `message` varchar(160) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `monday_alert`
--

LOCK TABLES `monday_alert` WRITE;
/*!40000 ALTER TABLE `monday_alert` DISABLE KEYS */;
INSERT INTO `monday_alert` VALUES (1,'ada saduda(Monday) maru sitina dishawa wayamba(NorthWest),','completed'),(2,' ada kataka, thula, wurshika lagna himiyanta jaya dinayaki(2012 wasara sadaha) ','pending'),(3,' kona masa - janawari (january) 1 - 15 dakwa danu rawiya','pending'),(4,'heta agaharuwada(Tuesday) rahu kalaya 3.00-4.30 ','pending');
/*!40000 ALTER TABLE `monday_alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saturday_alert`
--

DROP TABLE IF EXISTS `saturday_alert`;
CREATE TABLE `saturday_alert` (
  `id` int(11) NOT NULL,
  `message` varchar(160) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `saturday_alert`
--

LOCK TABLES `saturday_alert` WRITE;
/*!40000 ALTER TABLE `saturday_alert` DISABLE KEYS */;
INSERT INTO `saturday_alert` VALUES (1,'ada sensurada(Saturday) maru sitina dishawa nagenahira(East),','pending'),(2,' ada mesha lagna himiyanta jaya dinayaki(2012 wasara sadaha) ','pending'),(3,' kona masa - janawari (january) 1 - 15 dakwa danu rawiya, kona masawala wiwaha, niwasa katayutu ashubai','pending'),(4,'heta irida(Saturday) rahu kalaya 4.30-6.00 ','pending');
/*!40000 ALTER TABLE `saturday_alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscribers`
--

DROP TABLE IF EXISTS `subscribers`;
CREATE TABLE `subscribers` (
  `msisdn` varchar(200) NOT NULL,
  `inserttime` datetime NOT NULL,
  `astro_sign` varchar(100) default NULL,
  `extra` varchar(100) default NULL,
  PRIMARY KEY  (`msisdn`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subscribers`
--

LOCK TABLES `subscribers` WRITE;
/*!40000 ALTER TABLE `subscribers` DISABLE KEYS */;
/*!40000 ALTER TABLE `subscribers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sunday_alert`
--

DROP TABLE IF EXISTS `sunday_alert`;
CREATE TABLE `sunday_alert` (
  `id` int(11) NOT NULL,
  `message` varchar(160) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sunday_alert`
--

LOCK TABLES `sunday_alert` WRITE;
/*!40000 ALTER TABLE `sunday_alert` DISABLE KEYS */;
INSERT INTO `sunday_alert` VALUES (1,'ada irida(Sunday) maru sitina dishawa utura(North),','pending'),(2,' ada sinha, thula, wurshika, meena lagna himiyanta jaya dinayaki(2012 wasara sadaha) ','pending'),(3,' kona masa - janawari (january) 1 - 15 dakwa danu rawiya','pending'),(4,'heta saduda(Monday) rahu kalaya 7.30-9.00 ','pending');
/*!40000 ALTER TABLE `sunday_alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thursday_alert`
--

DROP TABLE IF EXISTS `thursday_alert`;
CREATE TABLE `thursday_alert` (
  `id` int(11) NOT NULL,
  `message` varchar(160) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `thursday_alert`
--

LOCK TABLES `thursday_alert` WRITE;
/*!40000 ALTER TABLE `thursday_alert` DISABLE KEYS */;
INSERT INTO `thursday_alert` VALUES (1,'ada brahaspatinda(Thursday) maru sitina dishawa dakuna(South),','pending'),(2,' ada wurshaba, mithuna, kanya, danu lagna himiyanta jaya dinayaki(2012 wasara sadaha) ','pending'),(3,' kona masa - janawari (january) 1 - 15 dakwa danu rawiya, kona masawala wiwaha, niwasa katayutu ashubai','pending'),(4,'heta sikurada(Friday) rahu kalaya 10.30-12.00 ','pending');
/*!40000 ALTER TABLE `thursday_alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tuesday_alert`
--

DROP TABLE IF EXISTS `tuesday_alert`;
CREATE TABLE `tuesday_alert` (
  `id` int(11) NOT NULL,
  `message` varchar(160) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tuesday_alert`
--

LOCK TABLES `tuesday_alert` WRITE;
/*!40000 ALTER TABLE `tuesday_alert` DISABLE KEYS */;
INSERT INTO `tuesday_alert` VALUES (1,'ada agaharuwada(Tuesday) maru sitina dishawa batahira(West),','pending'),(2,' ada kataka, thula, wurshika lagna himiyanta jaya dinayaki(2012 wasara sadaha) ','pending'),(3,' kona masa - janawari (january) 1 - 15 dakwa danu rawiya, kona masawala wiwaha, niwasa katayutu ashubai','pending'),(4,'heta badada(Wednesday) rahu kalaya 12.00-1.30 ','pending');
/*!40000 ALTER TABLE `tuesday_alert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wednesday_alert`
--

DROP TABLE IF EXISTS `wednesday_alert`;
CREATE TABLE `wednesday_alert` (
  `id` int(11) NOT NULL,
  `message` varchar(160) NOT NULL,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `wednesday_alert`
--

LOCK TABLES `wednesday_alert` WRITE;
/*!40000 ALTER TABLE `wednesday_alert` DISABLE KEYS */;
INSERT INTO `wednesday_alert` VALUES (1,'ada badada(Wednesday) maru sitina dishawa niritha(SouthWest),','pending'),(2,' ada wurshaba, mithuna, kanya, danu lagna himiyanta jaya dinayaki(2012 wasara sadaha) ','pending'),(3,' kona masa - janawari (january) 1 - 15 dakwa danu rawiya, kona masawala wiwaha, niwasa katayutu ashubai','pending'),(4,'heta brahaspatinda(Thursday) rahu kalaya 1.30-3.00 ','pending');
/*!40000 ALTER TABLE `wednesday_alert` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-01-02 12:46:31
