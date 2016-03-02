/*
SQLyog Ultimate v8.71 
MySQL - 5.5.18.1-log : Database - dbg0flj3fz9u2145
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dbg0flj3fz9u2145` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `dbg0flj3fz9u2145`;

/*Table structure for table `admin_sessions` */

DROP TABLE IF EXISTS `admin_sessions`;

CREATE TABLE `admin_sessions` (
  `session_id` varchar(40) CHARACTER SET latin1 NOT NULL DEFAULT '0',
  `ip_address` varchar(45) CHARACTER SET latin1 NOT NULL DEFAULT '0',
  `user_agent` varchar(120) CHARACTER SET latin1 NOT NULL,
  `last_activity` int(10) unsigned NOT NULL DEFAULT '0',
  `user_data` text CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`session_id`),
  KEY `last_activity_idx` (`last_activity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `admin_sessions` */

insert  into `admin_sessions`(`session_id`,`ip_address`,`user_agent`,`last_activity`,`user_data`) values ('7e5397fee74bf70f346c7e434dbfdac8','127.0.0.1','Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.103 Safari/537.36',1419768034,'');

/*Table structure for table `admins` */

DROP TABLE IF EXISTS `admins`;

CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hashed_password` varchar(128) NOT NULL DEFAULT '',
  `email` varchar(128) NOT NULL DEFAULT '',
  `firstname` varchar(32) NOT NULL DEFAULT '',
  `lastname` varchar(32) NOT NULL DEFAULT '',
  `username` varchar(128) DEFAULT NULL,
  `key_set` varchar(128) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `role` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `admins` */

insert  into `admins`(`id`,`hashed_password`,`email`,`firstname`,`lastname`,`username`,`key_set`,`status`,`role`) values (1,'f51ae22708636db0d55cf14609646636c8ad0b357b2a882f18da6753b80f98a7708a853a03531b177f882b240fe0a4e32f747acaf72dddfa5789c790781a7528','bobhao@gmail.com','haoz','zhenyu','admin','8fbb6f8911d02bf0c7b7525241161461b811ad186d6ed1b2ee57afa9bb5021670d74349ea8e697dfe2d82da96078e9fcc6e61fb903258b41c2fcaff3dc27ff44','1','administrator'),(3,'cc5f060e4f84990132700a74f185c018ced10e864cfb1b107f8ac66829e0bb8dfb4c70e5c1a4af6e86c1b4bcecdf45a66d85a87de3a9c1d7c7f21f3ec5bfd5fd','chinaboxbxl@gmail.com','wu','xiaoyong',NULL,'eb102d3ad1203931acdb566800301d48730cf38c5349e30ef3fd19546604626c4bdfa5c76989da1e73ba2e5f52c382694d3348b579626fb9cca26aae2fb6fd7b','1','administrator');

/*Table structure for table `attributelang` */

DROP TABLE IF EXISTS `attributelang`;

CREATE TABLE `attributelang` (
  `id` int(11) DEFAULT NULL,
  `attribution_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `attributelang` */

/*Table structure for table `attribution` */

DROP TABLE IF EXISTS `attribution`;

CREATE TABLE `attribution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attribution_price` int(11) DEFAULT '0',
  `attribution_thumb` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `attribution` */

insert  into `attribution`(`id`,`attribution_price`,`attribution_thumb`) values (1,1,'thumb_1'),(2,2,'thumb_2'),(3,3,'thumb_3');

/*Table structure for table `attributiongroup` */

DROP TABLE IF EXISTS `attributiongroup`;

CREATE TABLE `attributiongroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attribution_group_type` tinyint(4) DEFAULT NULL,
  `attribution_group_status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `attributiongroup` */

insert  into `attributiongroup`(`id`,`attribution_group_type`,`attribution_group_status`) values (1,1,1),(2,NULL,NULL);

/*Table structure for table `attributiongroupattribution` */

DROP TABLE IF EXISTS `attributiongroupattribution`;

CREATE TABLE `attributiongroupattribution` (
  `attribution_group_id` int(11) NOT NULL,
  `attribution_id` int(11) NOT NULL,
  `attribution_group_attribution_status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`attribution_group_id`,`attribution_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `attributiongroupattribution` */

insert  into `attributiongroupattribution`(`attribution_group_id`,`attribution_id`,`attribution_group_attribution_status`) values (1,1,1),(1,2,1),(2,3,1);

/*Table structure for table `c3p0testtable` */

DROP TABLE IF EXISTS `c3p0testtable`;

CREATE TABLE `c3p0testtable` (
  `a` char(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `c3p0testtable` */

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) NOT NULL DEFAULT '0',
  `name` varchar(128) DEFAULT NULL,
  `level_depth` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `active` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `position` int(10) NOT NULL DEFAULT '0',
  `description` text NOT NULL,
  `thumb` varchar(128) DEFAULT NULL,
  `view` int(11) DEFAULT '0',
  `bar_name` varchar(100) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '0' COMMENT '0:冷菜；1：热菜；2：套餐',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`id`,`parent_id`,`name`,`level_depth`,`active`,`position`,`description`,`thumb`,`view`,`bar_name`,`type`) values (1,0,'La Carte',0,1,1,'',NULL,0,'1',0),(2,1,NULL,1,1,6,'','thumb_2014_12_14_12_26_57.png',0,'1',0),(3,1,NULL,1,1,5,'','thumb_2014_12_14_12_41_13.png',0,'1',1),(4,1,NULL,1,1,12,'','thumb_2014_12_14_12_43_58.jpg',0,'1',0),(5,1,NULL,1,0,10,'','thumb_2014_12_14_12_46_55.jpg',0,'1',0),(6,1,NULL,1,1,7,'','thumb_2014_12_14_13_57_54.png',0,'2',0),(7,1,NULL,1,1,8,'','thumb_2014_12_14_13_58_55.png',0,'2',0),(8,1,NULL,1,1,9,'','thumb_2014_12_17_12_53_23.png',0,'2',0),(9,1,NULL,1,1,11,'','thumb_2014_12_14_14_03_57.jpg',0,'2',0),(10,1,NULL,1,1,13,'','thumb_2014_12_14_14_05_04.png',0,'2',0),(11,1,NULL,1,1,2,'',NULL,0,'3',0),(12,1,NULL,1,1,3,'','thumb_2014_12_17_17_58_22.jpg',0,'3',0),(13,1,NULL,1,1,4,'','thumb_2014_12_17_17_58_55.png',0,'2',0),(14,1,NULL,1,1,17,'','thumb_2014_12_17_18_23_15.jpg',0,'3',0),(15,1,NULL,1,1,16,'','thumb_2014_12_17_18_30_58.jpg',0,'3',0),(16,1,NULL,1,1,14,'','thumb_2014_12_17_18_18_06.jpg',0,'4',0),(17,1,NULL,1,1,18,'','thumb_2014_12_27_19_22_34.jpg',0,'4',0),(18,1,NULL,1,1,15,'','thumb_2014_12_27_19_28_44.jpg',0,'4',0),(19,1,NULL,1,1,20,'','thumb_2014_12_27_19_23_40.jpg',0,'4',0),(20,1,NULL,1,1,19,'','thumb_2014_12_27_19_25_14.jpg',0,'4',0),(21,1,NULL,1,0,1,'',NULL,0,'4',0);

/*Table structure for table `diningtable` */

DROP TABLE IF EXISTS `diningtable`;

CREATE TABLE `diningtable` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT 'id=0的记录删除，就不显示外卖了；或者设置available=0，外卖按钮不可点',
  `available` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可用（1:可用，0:桌椅坏了不可使用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='id=0的记录删除，就不显示外卖了；或者设置available=0，外卖按钮不可点';

/*Data for the table `diningtable` */

insert  into `diningtable`(`id`,`available`) values (0,0),(1,1),(2,1),(3,1),(4,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(11,1),(12,1),(13,1),(14,1),(15,1),(16,1),(17,1),(18,1),(19,1),(20,1),(21,1),(22,1),(23,1),(24,1),(25,1),(26,1),(27,1),(28,1);

/*Table structure for table `multilang` */

DROP TABLE IF EXISTS `multilang`;

CREATE TABLE `multilang` (
  `lid` int(11) NOT NULL AUTO_INCREMENT,
  `id` varchar(12) NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `desc` text,
  `locale` varchar(2) NOT NULL,
  `cat_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `ag_id` int(11) DEFAULT NULL,
  `a_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=977 DEFAULT CHARSET=utf8;

/*Data for the table `multilang` */

insert  into `multilang`(`lid`,`id`,`name`,`desc`,`locale`,`cat_id`,`product_id`,`ag_id`,`a_id`) values (1,'c1','La Carte',NULL,'fr',1,NULL,NULL,NULL),(2,'c1','La Carte',NULL,'nl',1,NULL,NULL,NULL),(3,'c1','La Carte',NULL,'en',1,NULL,NULL,NULL),(4,'c1','菜单',NULL,'cn',1,NULL,NULL,NULL),(5,'c2','Sashimi',NULL,'fr',2,NULL,NULL,NULL),(6,'c2','Sashimi',NULL,'nl',2,NULL,NULL,NULL),(7,'c2','Sashimi',NULL,'en',2,NULL,NULL,NULL),(8,'c2','刺身类',NULL,'cn',2,NULL,NULL,NULL),(9,'c3','Entrées',NULL,'fr',3,NULL,NULL,NULL),(10,'c3','Voorgerechten',NULL,'nl',3,NULL,NULL,NULL),(11,'c3','Starters',NULL,'en',3,NULL,NULL,NULL),(12,'c3','头盘类',NULL,'cn',3,NULL,NULL,NULL),(13,'c4','Teppan Yaki',NULL,'fr',4,NULL,NULL,NULL),(14,'c4','Teppan Yaki',NULL,'nl',4,NULL,NULL,NULL),(15,'c4','Teppan Yaki',NULL,'en',4,NULL,NULL,NULL),(16,'c4','铁板类',NULL,'cn',4,NULL,NULL,NULL),(17,'c5','Barbecue de style japonais','','fr',5,NULL,NULL,NULL),(18,'c5','Japanese stijl barbecue','','nl',5,NULL,NULL,NULL),(19,'c5','Japanese style barbecue','','en',5,NULL,NULL,NULL),(20,'c5','烧烤类','','cn',5,NULL,NULL,NULL),(21,'c6','Sushi',NULL,'fr',6,NULL,NULL,NULL),(22,'c6','Sushi',NULL,'nl',6,NULL,NULL,NULL),(23,'c6','Sushi',NULL,'en',6,NULL,NULL,NULL),(24,'c6','寿司类',NULL,'cn',6,NULL,NULL,NULL),(25,'c7','Maki',NULL,'fr',7,NULL,NULL,NULL),(26,'c7','Maki',NULL,'nl',7,NULL,NULL,NULL),(27,'c7','Maki',NULL,'en',7,NULL,NULL,NULL),(28,'c7','小卷',NULL,'cn',7,NULL,NULL,NULL),(29,'c8','California',NULL,'fr',8,NULL,NULL,NULL),(30,'c8','California',NULL,'nl',8,NULL,NULL,NULL),(31,'c8','California',NULL,'en',8,NULL,NULL,NULL),(32,'c8','大卷',NULL,'cn',8,NULL,NULL,NULL),(33,'c9','Tempura','','fr',9,NULL,NULL,NULL),(34,'c9','Tempura','','nl',9,NULL,NULL,NULL),(35,'c9','Tempura','','en',9,NULL,NULL,NULL),(36,'c9','油炸类','','cn',9,NULL,NULL,NULL),(37,'c10','Donburi','','fr',10,NULL,NULL,NULL),(38,'c10','Donburi','','nl',10,NULL,NULL,NULL),(39,'c10','Donburi','','en',10,NULL,NULL,NULL),(40,'c10','盖饭类','','cn',10,NULL,NULL,NULL),(41,'c11','Menu','','fr',11,NULL,NULL,NULL),(42,'c11','Menu','','nl',11,NULL,NULL,NULL),(43,'c11','Menu','','en',11,NULL,NULL,NULL),(44,'c11','套餐','','cn',11,NULL,NULL,NULL),(45,'p1','Saumon',NULL,'fr',NULL,1,NULL,NULL),(46,'p1','Zalm',NULL,'nl',NULL,1,NULL,NULL),(47,'p1','Salmon',NULL,'en',NULL,1,NULL,NULL),(48,'p1','三文鱼',NULL,'cn',NULL,1,NULL,NULL),(49,'p2','Thon','','fr',NULL,2,NULL,NULL),(50,'p2','Tonijn','','nl',NULL,2,NULL,NULL),(51,'p2','Tuna','','en',NULL,2,NULL,NULL),(52,'p2','金枪鱼','','cn',NULL,2,NULL,NULL),(53,'p3','Maquereau','','fr',NULL,3,NULL,NULL),(54,'p3','Makreel','','nl',NULL,3,NULL,NULL),(55,'p3','Mackerel','','en',NULL,3,NULL,NULL),(56,'p3','鲭鱼','','cn',NULL,3,NULL,NULL),(57,'p4','Poulpe','','fr',NULL,4,NULL,NULL),(58,'p4','Octopus','','nl',NULL,4,NULL,NULL),(59,'p4','Octopus','','en',NULL,4,NULL,NULL),(60,'p4','八爪鱼','','cn',NULL,4,NULL,NULL),(61,'p5','Coquille St-Jacques','','fr',NULL,5,NULL,NULL),(62,'p5','St-Jacobsschelp','','nl',NULL,5,NULL,NULL),(63,'p5','Couilique saint jacque','','en',NULL,5,NULL,NULL),(64,'p5','带子','','cn',NULL,5,NULL,NULL),(65,'p6','Scampis',NULL,'fr',NULL,6,NULL,NULL),(66,'p6','Scampis',NULL,'nl',NULL,6,NULL,NULL),(67,'p6','Prawn',NULL,'en',NULL,6,NULL,NULL),(68,'p6','虾',NULL,'cn',NULL,6,NULL,NULL),(69,'p7','Corail',NULL,'fr',NULL,7,NULL,NULL),(70,'p7','Koraal',NULL,'nl',NULL,7,NULL,NULL),(71,'p7','Coral',NULL,'en',NULL,7,NULL,NULL),(72,'p7','北极贝',NULL,'cn',NULL,7,NULL,NULL),(73,'p8','Sushi + Sashimi ( S )',NULL,'fr',NULL,8,NULL,NULL),(74,'p8','Sushi + Sashimi ( S )',NULL,'nl',NULL,8,NULL,NULL),(75,'p8','Sushi + Sashimi ( S )',NULL,'en',NULL,8,NULL,NULL),(76,'p8','寿司刺身拼盘 ( S )',NULL,'cn',NULL,8,NULL,NULL),(77,'p9','Sushi + Sashimi ( M )',NULL,'fr',NULL,9,NULL,NULL),(78,'p9','Sushi + Sashimi ( M )',NULL,'nl',NULL,9,NULL,NULL),(79,'p9','Sushi + Sashimi ( M )',NULL,'en',NULL,9,NULL,NULL),(80,'p9','寿司刺身拼盘 ( M )',NULL,'cn',NULL,9,NULL,NULL),(81,'p10','Sushi + Sashimi ( L )',NULL,'fr',NULL,10,NULL,NULL),(82,'p10','Sushi + Sashimi ( L )',NULL,'nl',NULL,10,NULL,NULL),(83,'p10','Sushi + Sashimi ( L )',NULL,'en',NULL,10,NULL,NULL),(84,'p10','寿司刺身拼盘 ( L )',NULL,'cn',NULL,10,NULL,NULL),(85,'p11','Raviolis',NULL,'fr',NULL,11,NULL,NULL),(86,'p11','Ravaioli',NULL,'nl',NULL,11,NULL,NULL),(87,'p11','Gyoza',NULL,'en',NULL,11,NULL,NULL),(88,'p11','饺子',NULL,'cn',NULL,11,NULL,NULL),(89,'p12','Salade de boeuf facon japonais','','fr',NULL,12,NULL,NULL),(90,'p12','Rundvlees salade in Japanse wijze','','nl',NULL,12,NULL,NULL),(91,'p12','Japanese carpaccio salad','','en',NULL,12,NULL,NULL),(92,'p12','生牛肉沙拉','','cn',NULL,12,NULL,NULL),(93,'p13','Wakame japonais',NULL,'fr',NULL,13,NULL,NULL),(94,'p13','Japanse zeewier',NULL,'nl',NULL,13,NULL,NULL),(95,'p13','Japanese seaweed',NULL,'en',NULL,13,NULL,NULL),(96,'p13','海带沙拉',NULL,'cn',NULL,13,NULL,NULL),(97,'p14','Saumon avocat',NULL,'fr',NULL,14,NULL,NULL),(98,'p14','Zalm avocado',NULL,'nl',NULL,14,NULL,NULL),(99,'p14','Salmon avocado',NULL,'en',NULL,14,NULL,NULL),(100,'p14','三文鱼+牛油果',NULL,'cn',NULL,14,NULL,NULL),(101,'p15','Salade de calamar facon japonais',NULL,'fr',NULL,15,NULL,NULL),(102,'p15','Inktvis salade in Janpanse wijze',NULL,'nl',NULL,15,NULL,NULL),(103,'p15','Japanese squid salad',NULL,'en',NULL,15,NULL,NULL),(104,'p15','鱿鱼沙拉',NULL,'cn',NULL,15,NULL,NULL),(105,'p16','Salade de poisson facon japonais',NULL,'fr',NULL,16,NULL,NULL),(106,'p16','Vis salade in Japanse wijze',NULL,'nl',NULL,16,NULL,NULL),(107,'p16','Japanese fish salad',NULL,'en',NULL,16,NULL,NULL),(108,'p16','生鱼沙拉',NULL,'cn',NULL,16,NULL,NULL),(109,'p17','Soupe miso',NULL,'fr',NULL,17,NULL,NULL),(110,'p17','Miso soep',NULL,'nl',NULL,17,NULL,NULL),(111,'p17','Miso soup',NULL,'en',NULL,17,NULL,NULL),(112,'p17','味增汤',NULL,'cn',NULL,17,NULL,NULL),(113,'p18','Soupe de poisson','','fr',NULL,18,NULL,NULL),(114,'p18','Vis soep','','nl',NULL,18,NULL,NULL),(115,'p18','Fish soup','','en',NULL,18,NULL,NULL),(116,'p18','鱼汤','','cn',NULL,18,NULL,NULL),(117,'p19','Rouleau de boeuf',NULL,'fr',NULL,19,NULL,NULL),(118,'p19','Rundvlees roll',NULL,'nl',NULL,19,NULL,NULL),(119,'p19','Beef roll',NULL,'en',NULL,19,NULL,NULL),(120,'p19','牛肉卷',NULL,'cn',NULL,19,NULL,NULL),(121,'p20','Filet de cuisse du poulet',NULL,'fr',NULL,20,NULL,NULL),(122,'p20','Kip billen',NULL,'nl',NULL,20,NULL,NULL),(123,'p20','Chicken thigh',NULL,'en',NULL,20,NULL,NULL),(124,'p20','鸡腿肉',NULL,'cn',NULL,20,NULL,NULL),(125,'p21','Filet pur de boeuf',NULL,'fr',NULL,21,NULL,NULL),(126,'p21','Ossenhaas',NULL,'nl',NULL,21,NULL,NULL),(127,'p21','Beef filet',NULL,'en',NULL,21,NULL,NULL),(128,'p21','牛柳',NULL,'cn',NULL,21,NULL,NULL),(129,'p22','Couronne d\'agneau',NULL,'fr',NULL,22,NULL,NULL),(130,'p22','Lamb kroon',NULL,'nl',NULL,22,NULL,NULL),(131,'p22','Lamb crown',NULL,'en',NULL,22,NULL,NULL),(132,'p22','小羊排',NULL,'cn',NULL,22,NULL,NULL),(133,'p23','scampis','','fr',NULL,23,NULL,NULL),(134,'p23','scampi\'s','','nl',NULL,23,NULL,NULL),(135,'p23','srimps','','en',NULL,23,NULL,NULL),(136,'p23','虎皮虾16-20','','cn',NULL,23,NULL,NULL),(137,'p24','Saumon',NULL,'fr',NULL,24,NULL,NULL),(138,'p24','Zalm',NULL,'nl',NULL,24,NULL,NULL),(139,'p24','Salmon',NULL,'en',NULL,24,NULL,NULL),(140,'p24','三文鱼',NULL,'cn',NULL,24,NULL,NULL),(141,'p25','cabillaud','','fr',NULL,25,NULL,NULL),(142,'p25','Kabeljauw','','nl',NULL,25,NULL,NULL),(143,'p25','Cod','','en',NULL,25,NULL,NULL),(144,'p25','鳕鱼','','cn',NULL,25,NULL,NULL),(145,'p26','Homard','700 gr','fr',NULL,26,NULL,NULL),(146,'p26','Kreeft','700 gr','nl',NULL,26,NULL,NULL),(147,'p26','Lobster','700 gr','en',NULL,26,NULL,NULL),(148,'p26','龙虾','700 gr','cn',NULL,26,NULL,NULL),(149,'p27','Canard','','fr',NULL,27,NULL,NULL),(150,'p27','Eend','','nl',NULL,27,NULL,NULL),(151,'p27','Duck','','en',NULL,27,NULL,NULL),(152,'p27','鸭子','','cn',NULL,27,NULL,NULL),(153,'p28','Coquille st-jaques',NULL,'fr',NULL,28,NULL,NULL),(154,'p28','St.-Jacobsschelp',NULL,'nl',NULL,28,NULL,NULL),(155,'p28','Coquille st-jaques',NULL,'en',NULL,28,NULL,NULL),(156,'p28','带子',NULL,'cn',NULL,28,NULL,NULL),(157,'p29','Calamar',NULL,'fr',NULL,29,NULL,NULL),(158,'p29','Inktvis',NULL,'nl',NULL,29,NULL,NULL),(159,'p29','Squid',NULL,'en',NULL,29,NULL,NULL),(160,'p29','鱿鱼',NULL,'cn',NULL,29,NULL,NULL),(161,'p30','King-crabe',NULL,'fr',NULL,30,NULL,NULL),(162,'p30','Koningskrab',NULL,'nl',NULL,30,NULL,NULL),(163,'p30','King crab',NULL,'en',NULL,30,NULL,NULL),(164,'p30','帝王蟹',NULL,'cn',NULL,30,NULL,NULL),(165,'p31','Poulpe','','fr',NULL,31,NULL,NULL),(166,'p31','Octopus','','nl',NULL,31,NULL,NULL),(167,'p31','Octopus','','en',NULL,31,NULL,NULL),(168,'p31','八爪鱼','','cn',NULL,31,NULL,NULL),(169,'p32','Saumon',NULL,'fr',NULL,32,NULL,NULL),(170,'p32','Zalm',NULL,'nl',NULL,32,NULL,NULL),(171,'p32','Salmon',NULL,'en',NULL,32,NULL,NULL),(172,'p32','三文鱼',NULL,'cn',NULL,32,NULL,NULL),(173,'p33','Calamar',NULL,'fr',NULL,33,NULL,NULL),(174,'p33','Inktvis',NULL,'nl',NULL,33,NULL,NULL),(175,'p33','Squid',NULL,'en',NULL,33,NULL,NULL),(176,'p33','鱿鱼',NULL,'cn',NULL,33,NULL,NULL),(177,'p34','Boeuf fromage',NULL,'fr',NULL,34,NULL,NULL),(178,'p34','Rundvlees Kaas',NULL,'nl',NULL,34,NULL,NULL),(179,'p34','Beef Cheese',NULL,'en',NULL,34,NULL,NULL),(180,'p34','芝士牛肉',NULL,'cn',NULL,34,NULL,NULL),(181,'p35','Cuisse de poulet','','fr',NULL,35,NULL,NULL),(182,'p35','Kip billen','','nl',NULL,35,NULL,NULL),(183,'p35','Chicken thigh','','en',NULL,35,NULL,NULL),(184,'p35','鸡腿肉','','cn',NULL,35,NULL,NULL),(185,'p36','Morue',NULL,'fr',NULL,36,NULL,NULL),(186,'p36','Kabeljauw',NULL,'nl',NULL,36,NULL,NULL),(187,'p36','Cod',NULL,'en',NULL,36,NULL,NULL),(188,'p36','鳕鱼',NULL,'cn',NULL,36,NULL,NULL),(189,'p37','Gambas sauvage',NULL,'fr',NULL,37,NULL,NULL),(190,'p37','Wilde gambas',NULL,'nl',NULL,37,NULL,NULL),(191,'p37','Wild prawn',NULL,'en',NULL,37,NULL,NULL),(192,'p37','大头虾',NULL,'cn',NULL,37,NULL,NULL),(193,'p38','Homard','','fr',NULL,38,NULL,NULL),(194,'p38','Kreeft','','nl',NULL,38,NULL,NULL),(195,'p38','Lobster','','en',NULL,38,NULL,NULL),(196,'p38','龙虾','','cn',NULL,38,NULL,NULL),(197,'p39','Poulpe',NULL,'fr',NULL,39,NULL,NULL),(198,'p39','Octopus',NULL,'nl',NULL,39,NULL,NULL),(199,'p39','Octopus',NULL,'en',NULL,39,NULL,NULL),(200,'p39','八爪鱼',NULL,'cn',NULL,39,NULL,NULL),(201,'p40','Maquereau',NULL,'fr',NULL,40,NULL,NULL),(202,'p40','Makreel',NULL,'nl',NULL,40,NULL,NULL),(203,'p40','Mackerel',NULL,'en',NULL,40,NULL,NULL),(204,'p40','盐烧鲭鱼',NULL,'cn',NULL,40,NULL,NULL),(205,'p41','Anguilles',NULL,'fr',NULL,41,NULL,NULL),(206,'p41','Paling',NULL,'nl',NULL,41,NULL,NULL),(207,'p41','Eels',NULL,'en',NULL,41,NULL,NULL),(208,'p41','烤鳗鱼',NULL,'cn',NULL,41,NULL,NULL),(209,'p42','Saumon','','fr',NULL,42,NULL,NULL),(210,'p42','Zalm','','nl',NULL,42,NULL,NULL),(211,'p42','Salmon','','en',NULL,42,NULL,NULL),(212,'p42','三文鱼','','cn',NULL,42,NULL,NULL),(213,'p43','Thon','','fr',NULL,43,NULL,NULL),(214,'p43','Tonijn','','nl',NULL,43,NULL,NULL),(215,'p43','Tuna','','en',NULL,43,NULL,NULL),(216,'p43','金枪鱼','','cn',NULL,43,NULL,NULL),(217,'p44','Dorade','','fr',NULL,44,NULL,NULL),(218,'p44','Goudbrasem','','nl',NULL,44,NULL,NULL),(219,'p44','Gilt-head  bream','','en',NULL,44,NULL,NULL),(220,'p44','鲷鱼','','cn',NULL,44,NULL,NULL),(221,'p45','Poulpe','','fr',NULL,45,NULL,NULL),(222,'p45','Octopus','','nl',NULL,45,NULL,NULL),(223,'p45','Octopus','','en',NULL,45,NULL,NULL),(224,'p45','八爪鱼','','cn',NULL,45,NULL,NULL),(225,'p46','Coquille St-Jacques','','fr',NULL,46,NULL,NULL),(226,'p46','St.-Jacobsschelp','','nl',NULL,46,NULL,NULL),(227,'p46','Coquille st-jaques','','en',NULL,46,NULL,NULL),(228,'p46','带子','','cn',NULL,46,NULL,NULL),(229,'p47','Scampis',NULL,'fr',NULL,47,NULL,NULL),(230,'p47','Scampis',NULL,'nl',NULL,47,NULL,NULL),(231,'p47','Prawn',NULL,'en',NULL,47,NULL,NULL),(232,'p47','虾',NULL,'cn',NULL,47,NULL,NULL),(233,'p48','Tamago',NULL,'fr',NULL,48,NULL,NULL),(234,'p48','Tamago',NULL,'nl',NULL,48,NULL,NULL),(235,'p48','Tamago',NULL,'en',NULL,48,NULL,NULL),(236,'p48','鸡蛋',NULL,'cn',NULL,48,NULL,NULL),(237,'p49','Oeuf de saumon','','fr',NULL,49,NULL,NULL),(238,'p49','Zalm eien','','nl',NULL,49,NULL,NULL),(239,'p49','Salmon eggs','','en',NULL,49,NULL,NULL),(240,'p49','三文鱼蛋','','cn',NULL,49,NULL,NULL),(241,'p50','Scampis frits','','fr',NULL,50,NULL,NULL),(242,'p50','Gefrituurde scampi','','nl',NULL,50,NULL,NULL),(243,'p50','Fried prawn','','en',NULL,50,NULL,NULL),(244,'p50','炸虾寿司','','cn',NULL,50,NULL,NULL),(245,'p51','Poisson de saison',NULL,'fr',NULL,51,NULL,NULL),(246,'p51','Seizoensgebonden vis',NULL,'nl',NULL,51,NULL,NULL),(247,'p51','Fish of the season',NULL,'en',NULL,51,NULL,NULL),(248,'p51','时令鱼种',NULL,'cn',NULL,51,NULL,NULL),(249,'p52','Saumon','','fr',NULL,52,NULL,NULL),(250,'p52','Zalm','','nl',NULL,52,NULL,NULL),(251,'p52','Salmon','','en',NULL,52,NULL,NULL),(252,'p52','三文鱼','','cn',NULL,52,NULL,NULL),(253,'p53','Thon',NULL,'fr',NULL,53,NULL,NULL),(254,'p53','Tonijn',NULL,'nl',NULL,53,NULL,NULL),(255,'p53','Tuna',NULL,'en',NULL,53,NULL,NULL),(256,'p53','金枪鱼',NULL,'cn',NULL,53,NULL,NULL),(257,'p54','Comcombre',NULL,'fr',NULL,54,NULL,NULL),(258,'p54','Komkommer',NULL,'nl',NULL,54,NULL,NULL),(259,'p54','Cucumber',NULL,'en',NULL,54,NULL,NULL),(260,'p54','青瓜',NULL,'cn',NULL,54,NULL,NULL),(261,'p55','Scampis',NULL,'fr',NULL,55,NULL,NULL),(262,'p55','Scampis',NULL,'nl',NULL,55,NULL,NULL),(263,'p55','Prawn',NULL,'en',NULL,55,NULL,NULL),(264,'p55','虾',NULL,'cn',NULL,55,NULL,NULL),(265,'p56','Saumon mango',NULL,'fr',NULL,56,NULL,NULL),(266,'p56','Zalm mango',NULL,'nl',NULL,56,NULL,NULL),(267,'p56','Salmon mango',NULL,'en',NULL,56,NULL,NULL),(268,'p56','芒果三文鱼',NULL,'cn',NULL,56,NULL,NULL),(269,'p57','Saumon massago',NULL,'fr',NULL,57,NULL,NULL),(270,'p57','Zalm massago',NULL,'nl',NULL,57,NULL,NULL),(271,'p57','Salmon massago',NULL,'en',NULL,57,NULL,NULL),(272,'p57','蟹耔三文鱼',NULL,'cn',NULL,57,NULL,NULL),(273,'p58','Scampis frits',NULL,'fr',NULL,58,NULL,NULL),(274,'p58','Gefrituurde scampi',NULL,'nl',NULL,58,NULL,NULL),(275,'p58','Fried prawn',NULL,'en',NULL,58,NULL,NULL),(276,'p58','炸虾卷',NULL,'cn',NULL,58,NULL,NULL),(277,'p59','Poulet frits',NULL,'fr',NULL,59,NULL,NULL),(278,'p59','Gefrituurde kip',NULL,'nl',NULL,59,NULL,NULL),(279,'p59','Fried chicken',NULL,'en',NULL,59,NULL,NULL),(280,'p59','炸鸡卷',NULL,'cn',NULL,59,NULL,NULL),(281,'p60','Avocat comcombre et sesame',NULL,'fr',NULL,60,NULL,NULL),(282,'p60','Avocado komkommer en sesam',NULL,'nl',NULL,60,NULL,NULL),(283,'p60','Avocado cucumber and sesame',NULL,'en',NULL,60,NULL,NULL),(284,'p60','牛油果+青瓜+芝麻',NULL,'cn',NULL,60,NULL,NULL),(285,'p61','Sesame thon',NULL,'fr',NULL,61,NULL,NULL),(286,'p61','Tonijn sesam',NULL,'nl',NULL,61,NULL,NULL),(287,'p61','Tuna sesame',NULL,'en',NULL,61,NULL,NULL),(288,'p61','芝麻金枪鱼',NULL,'cn',NULL,61,NULL,NULL),(289,'p62','Maison',NULL,'fr',NULL,62,NULL,NULL),(290,'p62','Van chef',NULL,'nl',NULL,62,NULL,NULL),(291,'p62','Signature roll',NULL,'en',NULL,62,NULL,NULL),(292,'p62','师傅招牌卷',NULL,'cn',NULL,62,NULL,NULL),(293,'p63','Legumes mixte',NULL,'fr',NULL,63,NULL,NULL),(294,'p63','Gemengde groenten',NULL,'nl',NULL,63,NULL,NULL),(295,'p63','Mixed vegetables',NULL,'en',NULL,63,NULL,NULL),(296,'p63','蔬菜',NULL,'cn',NULL,63,NULL,NULL),(297,'p64','Tempura mix','','fr',NULL,64,NULL,NULL),(298,'p64','Tempura mix','','nl',NULL,64,NULL,NULL),(299,'p64','Tempura mix','','en',NULL,64,NULL,NULL),(300,'p64','天妇罗','','cn',NULL,64,NULL,NULL),(301,'p65','Scampis',NULL,'fr',NULL,65,NULL,NULL),(302,'p65','Scampis',NULL,'nl',NULL,65,NULL,NULL),(303,'p65','Prawn',NULL,'en',NULL,65,NULL,NULL),(304,'p65','虾',NULL,'cn',NULL,65,NULL,NULL),(305,'p66','Pangasius',NULL,'fr',NULL,66,NULL,NULL),(306,'p66','Pangasius',NULL,'nl',NULL,66,NULL,NULL),(307,'p66','Pangasius',NULL,'en',NULL,66,NULL,NULL),(308,'p66','白鱼',NULL,'cn',NULL,66,NULL,NULL),(309,'p67','Calamar',NULL,'fr',NULL,67,NULL,NULL),(310,'p67','Inktvis',NULL,'nl',NULL,67,NULL,NULL),(311,'p67','Squid',NULL,'en',NULL,67,NULL,NULL),(312,'p67','鱿鱼',NULL,'cn',NULL,67,NULL,NULL),(313,'p68','Poulpe',NULL,'fr',NULL,68,NULL,NULL),(314,'p68','Octopus',NULL,'nl',NULL,68,NULL,NULL),(315,'p68','Octopus',NULL,'en',NULL,68,NULL,NULL),(316,'p68','八爪鱼',NULL,'cn',NULL,68,NULL,NULL),(317,'p69','Anguilles','accompagné de soupe et salade','fr',NULL,69,NULL,NULL),(318,'p69','Paling','vergezeld van soep en salade','nl',NULL,69,NULL,NULL),(319,'p69','Eels','accompanied by soup and salad','en',NULL,69,NULL,NULL),(320,'p69','鳗鱼','','cn',NULL,69,NULL,NULL),(321,'p70','Côtelettes de porc','accompagné de soupe et salade','fr',NULL,70,NULL,NULL),(322,'p70','Varkens koteletten','','nl',NULL,70,NULL,NULL),(323,'p70','Pork chops','accompanied by soup and salad','en',NULL,70,NULL,NULL),(324,'p70','炸猪排','','cn',NULL,70,NULL,NULL),(325,'p71','Scampis frits','accompagné de soupe et salade','fr',NULL,71,NULL,NULL),(326,'p71','Gefrituurde scampi','vergezeld van soep en salade','nl',NULL,71,NULL,NULL),(327,'p71','Fried prawn','accompanied by soup and salad','en',NULL,71,NULL,NULL),(328,'p71','炸虾','','cn',NULL,71,NULL,NULL),(329,'p72','boeuf','accompagné de soupe et salade','fr',NULL,72,NULL,NULL),(330,'p72','boeuf','vergezeld van soep en salade','nl',NULL,72,NULL,NULL),(331,'p72','boeuf','accompanied by soup and salad','en',NULL,72,NULL,NULL),(332,'p72','肥牛','','cn',NULL,72,NULL,NULL),(333,'p73','Poulet frits','accompagné de soupe et salade','fr',NULL,73,NULL,NULL),(334,'p73','Gebakken kip','vergezeld van soep en salade','nl',NULL,73,NULL,NULL),(335,'p73','Fried chicken','accompanied by soup and salad','en',NULL,73,NULL,NULL),(336,'p73','炸鸡排','','cn',NULL,73,NULL,NULL),(337,'p74','Niku',NULL,'fr',NULL,74,NULL,NULL),(338,'p74','Vlees menu',NULL,'nl',NULL,74,NULL,NULL),(339,'p74','Meat menu',NULL,'en',NULL,74,NULL,NULL),(340,'p74','肉类套餐',NULL,'cn',NULL,74,NULL,NULL),(341,'p75','Menu Teppan Yaki','Dodoro\'s starters\r\n1/2 languste\r\ncabiaud\r\nFilet pur de boeuf\r\n japanese dessert','fr',NULL,75,NULL,NULL),(342,'p75','Menu Teppan Yaki','Dodoro\'s starters\r\n1/2 languste\r\nkabeljauw\r\nossenhaas\r\n japanese dessert','nl',NULL,75,NULL,NULL),(343,'p75','Menu Teppan Yaki','Dodoro\'s starters\r\n1/2 languste\r\ncod\r\nbeef filet\r\n japanese dessert','en',NULL,75,NULL,NULL),(344,'p75','铁板套餐','','cn',NULL,75,NULL,NULL),(345,'c12','Apéritifs','','fr',12,NULL,NULL,NULL),(346,'c12','Apéritifs','','nl',12,NULL,NULL,NULL),(347,'c12','Apéritifs','','en',12,NULL,NULL,NULL),(348,'c12','Apéritifs','','cn',12,NULL,NULL,NULL),(349,'p76','Apéritifs japonais','','fr',NULL,76,NULL,NULL),(350,'p76','Japanse Apéritifs ','','nl',NULL,76,NULL,NULL),(351,'p76','japanese Apéritifs','','en',NULL,76,NULL,NULL),(352,'p76','Apéritifs japan','','cn',NULL,76,NULL,NULL),(353,'p77','martini blanc','','fr',NULL,77,NULL,NULL),(354,'p77','wit martini','','nl',NULL,77,NULL,NULL),(355,'p77','white martini','','en',NULL,77,NULL,NULL),(356,'p77','martini bl','','cn',NULL,77,NULL,NULL),(357,'p78','martini rouge','','fr',NULL,78,NULL,NULL),(358,'p78','rot martini','','nl',NULL,78,NULL,NULL),(359,'p78','red martini','','en',NULL,78,NULL,NULL),(360,'p78','martini rouge','','cn',NULL,78,NULL,NULL),(361,'p79','porto rouge','','fr',NULL,79,NULL,NULL),(362,'p79','rot porto','','nl',NULL,79,NULL,NULL),(363,'p79','red porto','','en',NULL,79,NULL,NULL),(364,'p79','porto rouge','','cn',NULL,79,NULL,NULL),(365,'p80','pisang orange','','fr',NULL,80,NULL,NULL),(366,'p80','pisang orange','','nl',NULL,80,NULL,NULL),(367,'p80','pisang orange','','en',NULL,80,NULL,NULL),(368,'p80','pisang orange','','cn',NULL,80,NULL,NULL),(369,'p81','kirr','','fr',NULL,81,NULL,NULL),(370,'p81','kirr','','nl',NULL,81,NULL,NULL),(371,'p81','kirr','','en',NULL,81,NULL,NULL),(372,'p81','kirr','','cn',NULL,81,NULL,NULL),(373,'p82','kirr royale','','fr',NULL,82,NULL,NULL),(374,'p82','kirr royale','','nl',NULL,82,NULL,NULL),(375,'p82','kirr royale','','en',NULL,82,NULL,NULL),(376,'p82','kirr royale','','cn',NULL,82,NULL,NULL),(377,'p83','glass champagne','','fr',NULL,83,NULL,NULL),(378,'p83','glass champagne','','nl',NULL,83,NULL,NULL),(379,'p83','glass champagne','','en',NULL,83,NULL,NULL),(380,'p83','glass champagne','','cn',NULL,83,NULL,NULL),(381,'c13','boissons','','fr',13,NULL,NULL,NULL),(382,'c13','dranken','','nl',13,NULL,NULL,NULL),(383,'c13','drinks','','en',13,NULL,NULL,NULL),(384,'c13','boissons','','cn',13,NULL,NULL,NULL),(385,'p84','coca cola','','fr',NULL,84,NULL,NULL),(386,'p84','coca cola','','nl',NULL,84,NULL,NULL),(387,'p84','coca cola','','en',NULL,84,NULL,NULL),(388,'p84','coca cola','','cn',NULL,84,NULL,NULL),(389,'p85','coca cola light','','fr',NULL,85,NULL,NULL),(390,'p85','coca cola light','','nl',NULL,85,NULL,NULL),(391,'p85','coca cola light','','en',NULL,85,NULL,NULL),(392,'p85','coca cola light','','cn',NULL,85,NULL,NULL),(393,'p86','coca cola zero','','fr',NULL,86,NULL,NULL),(394,'p86','coca cola zero','','nl',NULL,86,NULL,NULL),(395,'p86','coca cola zero','','en',NULL,86,NULL,NULL),(396,'p86','coca cola zero','','cn',NULL,86,NULL,NULL),(397,'p87','fanta','','fr',NULL,87,NULL,NULL),(398,'p87','fanta','','nl',NULL,87,NULL,NULL),(399,'p87','fanta','','en',NULL,87,NULL,NULL),(400,'p87','fanta','','cn',NULL,87,NULL,NULL),(401,'p88','sprite','','fr',NULL,88,NULL,NULL),(402,'p88','sprite','','nl',NULL,88,NULL,NULL),(403,'p88','sprite','','en',NULL,88,NULL,NULL),(404,'p88','sprite','','cn',NULL,88,NULL,NULL),(405,'p89','tonic','','fr',NULL,89,NULL,NULL),(406,'p89','tonic','','nl',NULL,89,NULL,NULL),(407,'p89','tonic','','en',NULL,89,NULL,NULL),(408,'p89','tonic','','cn',NULL,89,NULL,NULL),(409,'p90','jus d\'orange','','fr',NULL,90,NULL,NULL),(410,'p90','sinaasappelsap','','nl',NULL,90,NULL,NULL),(411,'p90','orange juice','','en',NULL,90,NULL,NULL),(412,'p90','jus d\'orange','','cn',NULL,90,NULL,NULL),(413,'p91','jus de pomme','','fr',NULL,91,NULL,NULL),(414,'p91','appelsap','','nl',NULL,91,NULL,NULL),(415,'p91','apple juice','','en',NULL,91,NULL,NULL),(416,'p91','jus de pomme','','cn',NULL,91,NULL,NULL),(417,'p92','jupiler','','fr',NULL,92,NULL,NULL),(418,'p92','jupiler','','nl',NULL,92,NULL,NULL),(419,'p92','jupiler','','en',NULL,92,NULL,NULL),(420,'p92','jupiler','','cn',NULL,92,NULL,NULL),(421,'p93','asahi','','fr',NULL,93,NULL,NULL),(422,'p93','asahi','','nl',NULL,93,NULL,NULL),(423,'p93','asahi','','en',NULL,93,NULL,NULL),(424,'p93','asahi','','cn',NULL,93,NULL,NULL),(425,'p94','kirrin','','fr',NULL,94,NULL,NULL),(426,'p94','kirrin','','nl',NULL,94,NULL,NULL),(427,'p94','kirrin','','en',NULL,94,NULL,NULL),(428,'p94','kirrin','','cn',NULL,94,NULL,NULL),(429,'p95','sapporo','','fr',NULL,95,NULL,NULL),(430,'p95','sapporo','','nl',NULL,95,NULL,NULL),(431,'p95','sapporo','','en',NULL,95,NULL,NULL),(432,'p95','sapporo','','cn',NULL,95,NULL,NULL),(433,'c14','digestifs','','fr',14,NULL,NULL,NULL),(434,'c14','digestieven','','nl',14,NULL,NULL,NULL),(435,'c14','Digestive','','en',14,NULL,NULL,NULL),(436,'c14','Digestive','','cn',14,NULL,NULL,NULL),(437,'c15','boissons chaudes','','fr',15,NULL,NULL,NULL),(438,'c15','warme dranken','','nl',15,NULL,NULL,NULL),(439,'c15','warm drinks','','en',15,NULL,NULL,NULL),(440,'c15','boissons chaudes','','cn',15,NULL,NULL,NULL),(441,'c16','desserts','','fr',16,NULL,NULL,NULL),(442,'c16','desserts','','nl',16,NULL,NULL,NULL),(443,'c16','desserts','','en',16,NULL,NULL,NULL),(444,'c16','desserts','','cn',16,NULL,NULL,NULL),(445,'p96','glace aux thé vert','','fr',NULL,96,NULL,NULL),(446,'p96','ice groene thee','','nl',NULL,96,NULL,NULL),(447,'p96','ice green tea','','en',NULL,96,NULL,NULL),(448,'p96','glace aux thé vert','','cn',NULL,96,NULL,NULL),(449,'p97','mini cheese cake yuzu','','fr',NULL,97,NULL,NULL),(450,'p97','mini cheese cake yuzu','','nl',NULL,97,NULL,NULL),(451,'p97','mini cheese cake yuzu','','en',NULL,97,NULL,NULL),(452,'p97','mini cheese cake yuzu','','cn',NULL,97,NULL,NULL),(453,'p98','mini cheese cake au thé ','','fr',NULL,98,NULL,NULL),(454,'p98','mini cheese cake au thé ','','nl',NULL,98,NULL,NULL),(455,'p98','mini cheese cake au thé ','','en',NULL,98,NULL,NULL),(456,'p98','mini cheese cake au thé ','','cn',NULL,98,NULL,NULL),(457,'p99','dame blanche','','fr',NULL,99,NULL,NULL),(458,'p99','dame blanche','','nl',NULL,99,NULL,NULL),(459,'p99','dame blanche','','en',NULL,99,NULL,NULL),(460,'p99','dame blanche','','cn',NULL,99,NULL,NULL),(461,'p100','dame noir','','fr',NULL,100,NULL,NULL),(462,'p100','dame noir','','nl',NULL,100,NULL,NULL),(463,'p100','dame noir','','en',NULL,100,NULL,NULL),(464,'p100','dame noir','','cn',NULL,100,NULL,NULL),(465,'p101','café','','fr',NULL,101,NULL,NULL),(466,'p101','koffie','','nl',NULL,101,NULL,NULL),(467,'p101','Coffee','','en',NULL,101,NULL,NULL),(468,'p101','café','','cn',NULL,101,NULL,NULL),(469,'p102','café au lait','','fr',NULL,102,NULL,NULL),(470,'p102','koffie met melk','','nl',NULL,102,NULL,NULL),(471,'p102','coffee with milk','','en',NULL,102,NULL,NULL),(472,'p102','coffee with milk','','cn',NULL,102,NULL,NULL),(473,'p103','cappuccino','','fr',NULL,103,NULL,NULL),(474,'p103','cappuccino','','nl',NULL,103,NULL,NULL),(475,'p103','cappuccino','','en',NULL,103,NULL,NULL),(476,'p103','cappuccino','','cn',NULL,103,NULL,NULL),(477,'p104','thé vert japonais','','fr',NULL,104,NULL,NULL),(478,'p104','Japanse groene thee','','nl',NULL,104,NULL,NULL),(479,'p104','Japanese green tea','','en',NULL,104,NULL,NULL),(480,'p104','Japanese green tea','','cn',NULL,104,NULL,NULL),(481,'p105','decafe','','fr',NULL,105,NULL,NULL),(482,'p105','decafe','','nl',NULL,105,NULL,NULL),(483,'p105','decafe','','en',NULL,105,NULL,NULL),(484,'p105','decafe','','cn',NULL,105,NULL,NULL),(485,'p106','sake','','fr',NULL,106,NULL,NULL),(486,'p106','sake','','nl',NULL,106,NULL,NULL),(487,'p106','sake','','en',NULL,106,NULL,NULL),(488,'p106','sake','','cn',NULL,106,NULL,NULL),(489,'p107','sake chaud','','fr',NULL,107,NULL,NULL),(490,'p107','hete sake','','nl',NULL,107,NULL,NULL),(491,'p107','warm sake','','en',NULL,107,NULL,NULL),(492,'p107','hete sake','','cn',NULL,107,NULL,NULL),(493,'p108','sake froid','','fr',NULL,108,NULL,NULL),(494,'p108','koude sake','','nl',NULL,108,NULL,NULL),(495,'p108','cold sake','','en',NULL,108,NULL,NULL),(496,'p108','koude sake','','cn',NULL,108,NULL,NULL),(497,'p109','whisky japonais','','fr',NULL,109,NULL,NULL),(498,'p109','Japanse whisky','','nl',NULL,109,NULL,NULL),(499,'p109','Japanese whisky','','en',NULL,109,NULL,NULL),(500,'p109','Japanse whisky','','cn',NULL,109,NULL,NULL),(501,'p110','JB whisky','','fr',NULL,110,NULL,NULL),(502,'p110','JB whisky','','nl',NULL,110,NULL,NULL),(503,'p110','JB whisky','','en',NULL,110,NULL,NULL),(504,'p110','JB whisky','','cn',NULL,110,NULL,NULL),(505,'p111','calvados','','fr',NULL,111,NULL,NULL),(506,'p111','calvados','','nl',NULL,111,NULL,NULL),(507,'p111','calvados','','en',NULL,111,NULL,NULL),(508,'p111','calvados','','cn',NULL,111,NULL,NULL),(509,'p112','cognac','','fr',NULL,112,NULL,NULL),(510,'p112','cognac','','nl',NULL,112,NULL,NULL),(511,'p112','cognac','','en',NULL,112,NULL,NULL),(512,'p112','cognac','','cn',NULL,112,NULL,NULL),(513,'p113','amarreto','','fr',NULL,113,NULL,NULL),(514,'p113','amarreto','','nl',NULL,113,NULL,NULL),(515,'p113','amarreto','','en',NULL,113,NULL,NULL),(516,'p113','amarreto','','cn',NULL,113,NULL,NULL),(517,'p114','cointreau','','fr',NULL,114,NULL,NULL),(518,'p114','cointreau','','nl',NULL,114,NULL,NULL),(519,'p114','cointreau','','en',NULL,114,NULL,NULL),(520,'p114','cointreau','','cn',NULL,114,NULL,NULL),(521,'c17','vin blanc','','fr',17,NULL,NULL,NULL),(522,'c17','witte wijn','','nl',17,NULL,NULL,NULL),(523,'c17','white wine','','en',17,NULL,NULL,NULL),(524,'c17','vin blanc','','cn',17,NULL,NULL,NULL),(525,'p115','1/2 eau plat','','fr',NULL,115,NULL,NULL),(526,'p115','1/2 vlak water','','nl',NULL,115,NULL,NULL),(527,'p115','1/2 flat water','','en',NULL,115,NULL,NULL),(528,'p115','1/2 eau plat','','cn',NULL,115,NULL,NULL),(529,'p116','eau plat 1 L','','fr',NULL,116,NULL,NULL),(530,'p116','vlak water 1 L','','nl',NULL,116,NULL,NULL),(531,'p116','water 1 L','','en',NULL,116,NULL,NULL),(532,'p116','water 1 L','','cn',NULL,116,NULL,NULL),(533,'p117','vin blanc maison','','fr',NULL,117,NULL,NULL),(534,'p117','wit wijn huis','','nl',NULL,117,NULL,NULL),(535,'p117','white wine of house','','en',NULL,117,NULL,NULL),(536,'p117','vin blanc maison','','cn',NULL,117,NULL,NULL),(537,'p118','bouteille de vin','','fr',NULL,118,NULL,NULL),(538,'p118','fles wijn','','nl',NULL,118,NULL,NULL),(539,'p118','bottle of wine','','en',NULL,118,NULL,NULL),(540,'p118','bottle of wine','','cn',NULL,118,NULL,NULL),(541,'p119','1/2 eau petillante','','fr',NULL,119,NULL,NULL),(542,'p119','1/2 bruisend water','','nl',NULL,119,NULL,NULL),(543,'p119','1/2 sparkling water','','en',NULL,119,NULL,NULL),(544,'p119','1/2 bruisend water','','cn',NULL,119,NULL,NULL),(545,'p120','1L eau pétillante','','fr',NULL,120,NULL,NULL),(546,'p120','1L bruisend water','','nl',NULL,120,NULL,NULL),(547,'p120','1L sparkling water','','en',NULL,120,NULL,NULL),(548,'p120','1L eau pétillante','','cn',NULL,120,NULL,NULL),(549,'p121','1/2 vin rouge maison','','fr',NULL,121,NULL,NULL),(550,'p121','1/2 rode huiswijn','','nl',NULL,121,NULL,NULL),(551,'p121','1/2 red house wine','','en',NULL,121,NULL,NULL),(552,'p121','1/2 red house wine','','cn',NULL,121,NULL,NULL),(553,'p122','1/2 vin rose','','fr',NULL,122,NULL,NULL),(554,'p122','1/2 roze huiswijn','','nl',NULL,122,NULL,NULL),(555,'p122','1/2 pink house wine','','en',NULL,122,NULL,NULL),(556,'p122','1/2 pink house wine','','cn',NULL,122,NULL,NULL),(557,'p123','1/2 vin blanc maison','','fr',NULL,123,NULL,NULL),(558,'p123','1/2 witte huiswijn','','nl',NULL,123,NULL,NULL),(559,'p123','1/2 white house wine','','en',NULL,123,NULL,NULL),(560,'p123','1/2 witte huiswijn','','cn',NULL,123,NULL,NULL),(561,'p124','1/4 vin rouge maison','','fr',NULL,124,NULL,NULL),(562,'p124','1/4 rode huiswijn','','nl',NULL,124,NULL,NULL),(563,'p124','1/4 red house wine','','en',NULL,124,NULL,NULL),(564,'p124','1/4 red house wine','','cn',NULL,124,NULL,NULL),(565,'p125','1/4 vin rose maison','','fr',NULL,125,NULL,NULL),(566,'p125','1/4 roze huiswijn','','nl',NULL,125,NULL,NULL),(567,'p125','1/4 rose house wine','','en',NULL,125,NULL,NULL),(568,'p125','1/4 rose house wine','','cn',NULL,125,NULL,NULL),(569,'p126','1/4 vin blanc maison','','fr',NULL,126,NULL,NULL),(570,'p126','1/4 witte huiswijn','','nl',NULL,126,NULL,NULL),(571,'p126','1/4 white house wine ','','en',NULL,126,NULL,NULL),(572,'p126','1/4 vin blanc maison','','cn',NULL,126,NULL,NULL),(573,'p127','un verre de vin rouge','','fr',NULL,127,NULL,NULL),(574,'p127','een glas rode wijn','','nl',NULL,127,NULL,NULL),(575,'p127','a glass of red wine','','en',NULL,127,NULL,NULL),(576,'p127','a glass of red wine','','cn',NULL,127,NULL,NULL),(577,'p128','un verre de vin rose','','fr',NULL,128,NULL,NULL),(578,'p128','een glas roze wijn','','nl',NULL,128,NULL,NULL),(579,'p128','a glass of pink wine','','en',NULL,128,NULL,NULL),(580,'p128','a glass of pink wine','','cn',NULL,128,NULL,NULL),(581,'p129','un verre de vin blanc','','fr',NULL,129,NULL,NULL),(582,'p129','een glas witte wijn','','nl',NULL,129,NULL,NULL),(583,'p129','a glass of white wine','','en',NULL,129,NULL,NULL),(584,'p129','a glass of white wine','','cn',NULL,129,NULL,NULL),(585,'p130','chablis','','fr',NULL,130,NULL,NULL),(586,'p130','chablis','','nl',NULL,130,NULL,NULL),(587,'p130','chablis','','en',NULL,130,NULL,NULL),(588,'p130','chablis','','cn',NULL,130,NULL,NULL),(589,'p131','muscat sec ','','fr',NULL,131,NULL,NULL),(590,'p131','muscat sec ','','nl',NULL,131,NULL,NULL),(591,'p131','muscat sec ','','en',NULL,131,NULL,NULL),(592,'p131','muscat sec ','','cn',NULL,131,NULL,NULL),(593,'p132','picpoul de pinet','','fr',NULL,132,NULL,NULL),(594,'p132','picpoul de pinet','','nl',NULL,132,NULL,NULL),(595,'p132','picpoul de pinet','','en',NULL,132,NULL,NULL),(596,'p132','picpoul de pinet','','cn',NULL,132,NULL,NULL),(597,'p133','Le Blanc des Filles (Vin de France du Languedoc)','','fr',NULL,133,NULL,NULL),(598,'p133','Le Blanc des Filles (Vin de France du Languedoc)','','nl',NULL,133,NULL,NULL),(599,'p133','Le Blanc des Filles (Vin de France du Languedoc)','','en',NULL,133,NULL,NULL),(600,'p133','Le Blanc des Filles (Vin de France du Languedoc)','','cn',NULL,133,NULL,NULL),(601,'p134','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','fr',NULL,134,NULL,NULL),(602,'p134','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','nl',NULL,134,NULL,NULL),(603,'p134','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','en',NULL,134,NULL,NULL),(604,'p134','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','cn',NULL,134,NULL,NULL),(605,'p135','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','fr',NULL,135,NULL,NULL),(606,'p135','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','nl',NULL,135,NULL,NULL),(607,'p135','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','en',NULL,135,NULL,NULL),(608,'p135','Mademoiselle Lily (Vin de France  Côte de Brian Clos du Gravillas)','','cn',NULL,135,NULL,NULL),(609,'p136','Constellations ( Vin de France sur le terroir de Fitou Domaine Mamaruta)','','fr',NULL,136,NULL,NULL),(610,'p136','Constellations ( Vin de France sur le terroir de Fitou Domaine Mamaruta)','','nl',NULL,136,NULL,NULL),(611,'p136','Constellations ( Vin de France sur le terroir de Fitou Domaine Mamaruta)','','en',NULL,136,NULL,NULL),(612,'p136','Constellations ( Vin de France sur le terroir de Fitou Domaine Mamaruta)','','cn',NULL,136,NULL,NULL),(613,'p137','Marche Avant Blanc Domaine Mas Du Chêne','','fr',NULL,137,NULL,NULL),(614,'p137','Marche Avant Blanc Domaine Mas Du Chêne','','nl',NULL,137,NULL,NULL),(615,'p137','Marche Avant Blanc Domaine Mas Du Chêne','','en',NULL,137,NULL,NULL),(616,'p137','Marche Avant Blanc Domaine Mas Du Chêne','','cn',NULL,137,NULL,NULL),(617,'p138',' Orléans blanc (Domaine Clos St Fiacre)','','fr',NULL,138,NULL,NULL),(618,'p138',' Orléans blanc (Domaine Clos St Fiacre)','','nl',NULL,138,NULL,NULL),(619,'p138',' Orléans blanc (Domaine Clos St Fiacre)','','en',NULL,138,NULL,NULL),(620,'p138',' Orléans blanc (Domaine Clos St Fiacre)','','cn',NULL,138,NULL,NULL),(621,'p139','AOC Touraine blanc  <Le Blanc de la Mariée>','','fr',NULL,139,NULL,NULL),(622,'p139','AOC Touraine blanc  <Le Blanc de la Mariée>','','nl',NULL,139,NULL,NULL),(623,'p139','AOC Touraine blanc  <Le Blanc de la Mariée>','','en',NULL,139,NULL,NULL),(624,'p139','AOC Touraine blanc  <Le Blanc de la Mariée>','','cn',NULL,139,NULL,NULL),(625,'p140','Sancerre blanc <Clos les Chasseignes>','','fr',NULL,140,NULL,NULL),(626,'p140','Sancerre blanc <Clos les Chasseignes>','','nl',NULL,140,NULL,NULL),(627,'p140','Sancerre blanc <Clos les Chasseignes>','','en',NULL,140,NULL,NULL),(628,'p140','Sancerre blanc <Clos les Chasseignes>','','cn',NULL,140,NULL,NULL),(629,'p141','Montlouis Sec <Eole et Bacchus> ','','fr',NULL,141,NULL,NULL),(630,'p141','Montlouis Sec <Eole et Bacchus> ','','nl',NULL,141,NULL,NULL),(631,'p141','Montlouis Sec <Eole et Bacchus> ','','en',NULL,141,NULL,NULL),(632,'p141','Montlouis Sec <Eole et Bacchus> ','','cn',NULL,141,NULL,NULL),(633,'p142','Pouilly  Fumé ','','fr',NULL,142,NULL,NULL),(634,'p142','Pouilly  Fumé ','','nl',NULL,142,NULL,NULL),(635,'p142','Pouilly  Fumé ','','en',NULL,142,NULL,NULL),(636,'p142','Pouilly  Fumé ','','cn',NULL,142,NULL,NULL),(637,'p143','Coteaux Du Layon ','','fr',NULL,143,NULL,NULL),(638,'p143','Coteaux Du Layon ','','nl',NULL,143,NULL,NULL),(639,'p143','Coteaux Du Layon ','','en',NULL,143,NULL,NULL),(640,'p143','Coteaux Du Layon ','','cn',NULL,143,NULL,NULL),(641,'c18','vins en pichet','','fr',18,NULL,NULL,NULL),(642,'c18','wijn pitcher','','nl',18,NULL,NULL,NULL),(643,'c18','wine pitcher','','en',18,NULL,NULL,NULL),(644,'c18','wine pitcher','','cn',18,NULL,NULL,NULL),(645,'p144','champagne Mannoury','','fr',NULL,144,NULL,NULL),(646,'p144','champagne Mannoury','','nl',NULL,144,NULL,NULL),(647,'p144','champagne Mannoury','','en',NULL,144,NULL,NULL),(648,'p144','champagne Mannoury','','cn',NULL,144,NULL,NULL),(649,'p145','Menu dodoro','sushi+sashimi\r\ncoquille st-jacques+scampis\r\nyakitori\r\nboeuf ou saumon teppan yaki','fr',NULL,145,NULL,NULL),(650,'p145','Menu dodoro','sushi+sashimi\r\nSt-Jacques shell + scampi\r\nyakitori\r\nrundvlees of zalm','nl',NULL,145,NULL,NULL),(651,'p145','Menu dodoro','sushi+sashimi\r\nSt-Jacques shell + scampi\r\nyakitori\r\nbeef or salmon','en',NULL,145,NULL,NULL),(652,'p145','Menu dodoro','sushi+sashimi\r\ncoquille st-jacques+scampis\r\nyakitori\r\nboeuf ou saumon teppan yaki','cn',NULL,145,NULL,NULL),(653,'c19','vin rouge','','fr',19,NULL,NULL,NULL),(654,'c19','rote wijn','','nl',19,NULL,NULL,NULL),(655,'c19','red wine','','en',19,NULL,NULL,NULL),(656,'c19','vin rouge','','cn',19,NULL,NULL,NULL),(657,'p146','Bourgogne pinot noir','','fr',NULL,146,NULL,NULL),(658,'p146','Bourgogne pinot noir','','nl',NULL,146,NULL,NULL),(659,'p146','Bourgogne pinot noir','','en',NULL,146,NULL,NULL),(660,'p146','Bourgogne pinot noir','','cn',NULL,146,NULL,NULL),(661,'p147','chenas','','fr',NULL,147,NULL,NULL),(662,'p147','chenas','','nl',NULL,147,NULL,NULL),(663,'p147','chenas','','en',NULL,147,NULL,NULL),(664,'p147','chenas','','cn',NULL,147,NULL,NULL),(665,'c20','vin rose','','fr',20,NULL,NULL,NULL),(666,'c20','roze wijn ','','nl',20,NULL,NULL,NULL),(667,'c20','rose wine','','en',20,NULL,NULL,NULL),(668,'c20','vin rose','','cn',20,NULL,NULL,NULL),(669,'p148','Tavel','','fr',NULL,148,NULL,NULL),(670,'p148','Tavel','','nl',NULL,148,NULL,NULL),(671,'p148','Tavel','','en',NULL,148,NULL,NULL),(672,'p148','Tavel','','cn',NULL,148,NULL,NULL),(673,'p149','pinot noir rose','','fr',NULL,149,NULL,NULL),(674,'p149','pinot noir rose','','nl',NULL,149,NULL,NULL),(675,'p149','pinot noir rose','','en',NULL,149,NULL,NULL),(676,'p149','pinot noir rose','','cn',NULL,149,NULL,NULL),(677,'p150','accompagnement des nouilles ','','fr',NULL,150,NULL,NULL),(678,'p150','begeleidend noedels','','nl',NULL,150,NULL,NULL),(679,'p150','accompanying noodles','','en',NULL,150,NULL,NULL),(680,'p150','伴菜面条','','cn',NULL,150,NULL,NULL),(681,'p151','Irish coffee','','fr',NULL,151,NULL,NULL),(682,'p151','Irish coffee','','nl',NULL,151,NULL,NULL),(683,'p151','Irish coffee','','en',NULL,151,NULL,NULL),(684,'p151','Irish coffee','','cn',NULL,151,NULL,NULL),(685,'c21','DODORO\'S LUNCH ','','fr',21,NULL,NULL,NULL),(686,'c21','DODORO\'S LUNCH ','','nl',21,NULL,NULL,NULL),(687,'c21','DODORO\'S LUNCH ','','en',21,NULL,NULL,NULL),(688,'c21','DODORO\'S LUNCH ','','cn',21,NULL,NULL,NULL),(689,'p152','Poulet frit ','Poulet frit ，riz，maki，salade \r\nsoupe ou un verre boisson au choix','fr',NULL,152,NULL,NULL),(690,'p152','Gebakken kip','Gebakken kip, rijst, maki, salade soep of glas drank keuze','nl',NULL,152,NULL,NULL),(691,'p152','Fried chicken','Fried chicken, rice, maki, salad soup or drink glass choice','en',NULL,152,NULL,NULL),(692,'p152','鸡排饭','炸鸡，米饭，真希，沙拉 汤或饮料玻璃的选择','cn',NULL,152,NULL,NULL),(693,'p153','Côtelettes de porc','porc frit ，riz，maki，salade \r\nsoupe ou un verre boisson au choix','fr',NULL,153,NULL,NULL),(694,'p153','Pork Chops','Gebakken varkensvlees, rijst, maki, salade soep of drank glas keuze','nl',NULL,153,NULL,NULL),(695,'p153','Pork Chops','fried pork, rice, maki, salad soup or drink glass choice','en',NULL,153,NULL,NULL),(696,'p153','猪排饭','','cn',NULL,153,NULL,NULL),(697,'p154','Crozes Hermitage <Sens>','','fr',NULL,154,NULL,NULL),(698,'p154','Crozes Hermitage <Sens>','','nl',NULL,154,NULL,NULL),(699,'p154','Crozes Hermitage <Sens>','','en',NULL,154,NULL,NULL),(700,'p154','Crozes Hermitage <Sens>','','cn',NULL,154,NULL,NULL),(701,'p155','Fleur de Brian Côté de bordeaux à castillon 1/2','','fr',NULL,155,NULL,NULL),(702,'p155','Fleur de Brian Côté de bordeaux à castillon 1/2','','nl',NULL,155,NULL,NULL),(703,'p155','Fleur de Brian Côté de bordeaux à castillon 1/2','','en',NULL,155,NULL,NULL),(704,'p155','Fleur de Brian Côté de bordeaux à castillon 1/2','','cn',NULL,155,NULL,NULL),(705,'p156','Bourgueil 《Les Grands Rangs》 Maison Audebert','','fr',NULL,156,NULL,NULL),(706,'p156','Bourgueil 《Les Grands Rangs》 Maison Audebert','','nl',NULL,156,NULL,NULL),(707,'p156','Bourgueil 《Les Grands Rangs》 Maison Audebert','','en',NULL,156,NULL,NULL),(708,'p156','Bourgueil 《Les Grands Rangs》 Maison Audebert','','cn',NULL,156,NULL,NULL),(709,'p157','Sainte Foy de Bordeaux 《Les Temps Modernes》','','fr',NULL,157,NULL,NULL),(710,'p157','Sainte Foy de Bordeaux 《Les Temps Modernes》','','nl',NULL,157,NULL,NULL),(711,'p157','Sainte Foy de Bordeaux 《Les Temps Modernes》','','en',NULL,157,NULL,NULL),(712,'p157','Sainte Foy de Bordeaux 《Les Temps Modernes》','','cn',NULL,157,NULL,NULL),(713,'p158','《Vain de Ru》 Vin des Cotes de Gascogne','','fr',NULL,158,NULL,NULL),(714,'p158','《Vain de Ru》 Vin des Cotes de Gascogne','','nl',NULL,158,NULL,NULL),(715,'p158','《Vain de Ru》 Vin des Cotes de Gascogne','','en',NULL,158,NULL,NULL),(716,'p158','《Vain de Ru》 Vin des Cotes de Gascogne','','cn',NULL,158,NULL,NULL),(717,'p159','Ste Foy Bordeaux 《La Découverte》','','fr',NULL,159,NULL,NULL),(718,'p159','Ste Foy Bordeaux 《La Découverte》','','nl',NULL,159,NULL,NULL),(719,'p159','Ste Foy Bordeaux 《La Découverte》','','en',NULL,159,NULL,NULL),(720,'p159','Ste Foy Bordeaux 《La Découverte》','','cn',NULL,159,NULL,NULL),(721,'p160','Ste Foy Bordeaux 《La Découverte》','','fr',NULL,160,NULL,NULL),(722,'p160','Ste Foy Bordeaux 《La Découverte》','','nl',NULL,160,NULL,NULL),(723,'p160','Ste Foy Bordeaux 《La Découverte》','','en',NULL,160,NULL,NULL),(724,'p160','Ste Foy Bordeaux 《La Découverte》','','cn',NULL,160,NULL,NULL),(725,'p161','Pinot Gris','','fr',NULL,161,NULL,NULL),(726,'p161','Pinot Gris','','nl',NULL,161,NULL,NULL),(727,'p161','Pinot Gris','','en',NULL,161,NULL,NULL),(728,'p161','Pinot Gris','','cn',NULL,161,NULL,NULL),(729,'p162','Pinot Gris 1/2','','fr',NULL,162,NULL,NULL),(730,'p162','Pinot Gris 1/2','','nl',NULL,162,NULL,NULL),(731,'p162','Pinot Gris 1/2','','en',NULL,162,NULL,NULL),(732,'p162','Pinot Gris 1/2','','cn',NULL,162,NULL,NULL),(733,'p163','Gewürztraminer','','fr',NULL,163,NULL,NULL),(734,'p163','Gewürztraminer','','nl',NULL,163,NULL,NULL),(735,'p163','Gewürztraminer','','en',NULL,163,NULL,NULL),(736,'p163','Gewürztraminer','','cn',NULL,163,NULL,NULL),(737,'p164','Gewürztraminer 1/2','','fr',NULL,164,NULL,NULL),(738,'p164','Gewürztraminer 1/2','','nl',NULL,164,NULL,NULL),(739,'p164','Gewürztraminer 1/2','','en',NULL,164,NULL,NULL),(740,'p164','Gewürztraminer 1/2','','cn',NULL,164,NULL,NULL),(741,'p165','chablis','','fr',NULL,165,NULL,NULL),(742,'p165','chablis','','nl',NULL,165,NULL,NULL),(743,'p165','chablis','','en',NULL,165,NULL,NULL),(744,'p165','chablis','','cn',NULL,165,NULL,NULL),(745,'p166','Pernand Vergelesses Blanc 《Domaine Rollin》','','fr',NULL,166,NULL,NULL),(746,'p166','Pernand Vergelesses Blanc 《Domaine Rollin》','','nl',NULL,166,NULL,NULL),(747,'p166','Pernand Vergelesses Blanc 《Domaine Rollin》','','en',NULL,166,NULL,NULL),(748,'p166','Pernand Vergelesses Blanc 《Domaine Rollin》','','cn',NULL,166,NULL,NULL),(749,'p167','《Les Fruités》 de Félines Rosé côtes de bésilles','','fr',NULL,167,NULL,NULL),(750,'p167','《Les Fruités》 de Félines Rosé côtes de bésilles','','nl',NULL,167,NULL,NULL),(751,'p167','《Les Fruités》 de Félines Rosé côtes de bésilles','','en',NULL,167,NULL,NULL),(752,'p167','《Les Fruités》 de Félines Rosé côtes de bésilles','','cn',NULL,167,NULL,NULL),(753,'p168','Côtes de provence','','fr',NULL,168,NULL,NULL),(754,'p168','Côtes de provence','','nl',NULL,168,NULL,NULL),(755,'p168','Côtes de provence','','en',NULL,168,NULL,NULL),(756,'p168','Côtes de provence','','cn',NULL,168,NULL,NULL),(757,'p169','Côtes de provence 1/2','','fr',NULL,169,NULL,NULL),(758,'p169','Côtes de provence 1/2','','nl',NULL,169,NULL,NULL),(759,'p169','Côtes de provence 1/2','','en',NULL,169,NULL,NULL),(760,'p169','Côtes de provence 1/2','','cn',NULL,169,NULL,NULL),(761,'p170','Bordeaux Clairet de Barre','','fr',NULL,170,NULL,NULL),(762,'p170','Bordeaux Clairet de Barre','','nl',NULL,170,NULL,NULL),(763,'p170','Bordeaux Clairet de Barre','','en',NULL,170,NULL,NULL),(764,'p170','Bordeaux Clairet de Barre','','cn',NULL,170,NULL,NULL),(765,'p171','Rosé d\'anjou- Demi-sec','','fr',NULL,171,NULL,NULL),(766,'p171','Rosé d\'anjou- Demi-sec','','nl',NULL,171,NULL,NULL),(767,'p171','Rosé d\'anjou- Demi-sec','','en',NULL,171,NULL,NULL),(768,'p171','Rosé d\'anjou- Demi-sec','','cn',NULL,171,NULL,NULL),(769,'p172','Orléans rosé','','fr',NULL,172,NULL,NULL),(770,'p172','Orléans rosé','','nl',NULL,172,NULL,NULL),(771,'p172','Orléans rosé','','en',NULL,172,NULL,NULL),(772,'p172','Orléans rosé','','cn',NULL,172,NULL,NULL),(773,'p173','Marche Arriere  rosé Vin de France en Costières de Nimes','','fr',NULL,173,NULL,NULL),(774,'p173','Marche Arriere  rosé Vin de France en Costières de Nimes','','nl',NULL,173,NULL,NULL),(775,'p173','Marche Arriere  rosé Vin de France en Costières de Nimes','','en',NULL,173,NULL,NULL),(776,'p173','Marche Arriere  rosé Vin de France en Costières de Nimes','','cn',NULL,173,NULL,NULL),(777,'p174','Tavel','','fr',NULL,174,NULL,NULL),(778,'p174','Tavel','','nl',NULL,174,NULL,NULL),(779,'p174','Tavel','','en',NULL,174,NULL,NULL),(780,'p174','Tavel','','cn',NULL,174,NULL,NULL),(781,'p175',' Pinot Noir Rosé','','fr',NULL,175,NULL,NULL),(782,'p175',' Pinot Noir Rosé','','nl',NULL,175,NULL,NULL),(783,'p175',' Pinot Noir Rosé','','en',NULL,175,NULL,NULL),(784,'p175',' Pinot Noir Rosé','','cn',NULL,175,NULL,NULL),(785,'p176',' Pinot Noir Rosé 1/2 ','','fr',NULL,176,NULL,NULL),(786,'p176',' Pinot Noir Rosé 1/2','','nl',NULL,176,NULL,NULL),(787,'p176',' Pinot Noir Rosé 1/2','','en',NULL,176,NULL,NULL),(788,'p176',' Pinot Noir Rosé 1/2','','cn',NULL,176,NULL,NULL),(789,'p177','Beaujolais Pérreon','','fr',NULL,177,NULL,NULL),(790,'p177','Beaujolais Pérreon','','nl',NULL,177,NULL,NULL),(791,'p177','Beaujolais Pérreon','','en',NULL,177,NULL,NULL),(792,'p177','Beaujolais Pérreon','','cn',NULL,177,NULL,NULL),(793,'p178','Beaujolais Pérreon 1/2','','fr',NULL,178,NULL,NULL),(794,'p178','Beaujolais Pérreon 1/2','','nl',NULL,178,NULL,NULL),(795,'p178','Beaujolais Pérreon 1/2','','en',NULL,178,NULL,NULL),(796,'p178','Beaujolais Pérreon 1/2','','cn',NULL,178,NULL,NULL),(797,'p179','Macon Pierreclos rouge','','fr',NULL,179,NULL,NULL),(798,'p179','Macon Pierreclos rouge','','nl',NULL,179,NULL,NULL),(799,'p179','Macon Pierreclos rouge','','en',NULL,179,NULL,NULL),(800,'p179','Macon Pierreclos rouge','','cn',NULL,179,NULL,NULL),(801,'p180','Orléans Rouge','','fr',NULL,180,NULL,NULL),(802,'p180','Orléans Rouge','','nl',NULL,180,NULL,NULL),(803,'p180','Orléans Rouge','','en',NULL,180,NULL,NULL),(804,'p180','Orléans Rouge','','cn',NULL,180,NULL,NULL),(805,'p181','Bourgueil 《Les Grands Rangs》 Maison Audebert','','fr',NULL,181,NULL,NULL),(806,'p181','Bourgueil 《Les Grands Rangs》 Maison Audebert','','nl',NULL,181,NULL,NULL),(807,'p181','Bourgueil 《Les Grands Rangs》 Maison Audebert','','en',NULL,181,NULL,NULL),(808,'p181','Bourgueil 《Les Grands Rangs》 Maison Audebert','','cn',NULL,181,NULL,NULL),(809,'p182','Saumur Champigny 《Vieilles Vignes》','','fr',NULL,182,NULL,NULL),(810,'p182','Saumur Champigny 《Vieilles Vignes》','','nl',NULL,182,NULL,NULL),(811,'p182','Saumur Champigny 《Vieilles Vignes》','','en',NULL,182,NULL,NULL),(812,'p182','Saumur Champigny 《Vieilles Vignes》','','cn',NULL,182,NULL,NULL),(813,'p183','Sancerre rouge <Clos les Chasseignes>','','fr',NULL,183,NULL,NULL),(814,'p183','Sancerre rouge <Clos les Chasseignes>','','nl',NULL,183,NULL,NULL),(815,'p183','Sancerre rouge <Clos les Chasseignes>','','en',NULL,183,NULL,NULL),(816,'p183','Sancerre rouge <Clos les Chasseignes>','','cn',NULL,183,NULL,NULL),(817,'p184','Bourgogne pinot noir','','fr',NULL,184,NULL,NULL),(818,'p184','Bourgogne pinot noir','','nl',NULL,184,NULL,NULL),(819,'p184','Bourgogne pinot noir','','en',NULL,184,NULL,NULL),(820,'p184','Bourgogne pinot noir','','cn',NULL,184,NULL,NULL),(821,'p185','Bourgogne pinot noir 1/2','','fr',NULL,185,NULL,NULL),(822,'p185','Bourgogne pinot noir 1/2','','nl',NULL,185,NULL,NULL),(823,'p185','Bourgogne pinot noir 1/2','','en',NULL,185,NULL,NULL),(824,'p185','Bourgogne pinot noir 1/2','','cn',NULL,185,NULL,NULL),(825,'p186','Marsannay Rouge Domaine du Vieux College','','fr',NULL,186,NULL,NULL),(826,'p186','Marsannay Rouge Domaine du Vieux College','','nl',NULL,186,NULL,NULL),(827,'p186','Marsannay Rouge Domaine du Vieux College','','en',NULL,186,NULL,NULL),(828,'p186','Marsannay Rouge Domaine du Vieux College','','cn',NULL,186,NULL,NULL),(829,'p187','Hautes Côtes de Beaune','','fr',NULL,187,NULL,NULL),(830,'p187','Hautes Côtes de Beaune','','nl',NULL,187,NULL,NULL),(831,'p187','Hautes Côtes de Beaune','','en',NULL,187,NULL,NULL),(832,'p187','Hautes Côtes de Beaune','','cn',NULL,187,NULL,NULL),(833,'p188','Aloxe Corton Domaine Rollin','','fr',NULL,188,NULL,NULL),(834,'p188','Aloxe Corton Domaine Rollin','','nl',NULL,188,NULL,NULL),(835,'p188','Aloxe Corton Domaine Rollin','','en',NULL,188,NULL,NULL),(836,'p188','Aloxe Corton Domaine Rollin','','cn',NULL,188,NULL,NULL),(837,'p189','Merlot - Côtes de Prouihe','','fr',NULL,189,NULL,NULL),(838,'p189','Merlot - Côtes de Prouihe','','nl',NULL,189,NULL,NULL),(839,'p189','Merlot - Côtes de Prouihe','','en',NULL,189,NULL,NULL),(840,'p189','Merlot - Côtes de Prouihe','','cn',NULL,189,NULL,NULL),(841,'p190','Côte de Thongue 《Mariage》','','fr',NULL,190,NULL,NULL),(842,'p190','Côte de Thongue 《Mariage》','','nl',NULL,190,NULL,NULL),(843,'p190','Côte de Thongue 《Mariage》','','en',NULL,190,NULL,NULL),(844,'p190','Côte de Thongue 《Mariage》','','cn',NULL,190,NULL,NULL),(845,'p191','Igp Côtes Catalanes 《Toine》','','fr',NULL,191,NULL,NULL),(846,'p191','Igp Côtes Catalanes 《Toine》','','nl',NULL,191,NULL,NULL),(847,'p191','Igp Côtes Catalanes 《Toine》','','en',NULL,191,NULL,NULL),(848,'p191','Igp Côtes Catalanes 《Toine》','','cn',NULL,191,NULL,NULL),(849,'p192','Corbières 《Le Fournas 》','','fr',NULL,192,NULL,NULL),(850,'p192','Corbières 《Le Fournas 》','','nl',NULL,192,NULL,NULL),(851,'p192','Corbières 《Le Fournas 》','','en',NULL,192,NULL,NULL),(852,'p192','Corbières 《Le Fournas 》','','cn',NULL,192,NULL,NULL),(853,'p193','《Lou Maset 》 Rouge Aop Languedoc dur Montpeyroux','','fr',NULL,193,NULL,NULL),(854,'p193','《Lou Maset 》 Rouge Aop Languedoc dur Montpeyroux','','nl',NULL,193,NULL,NULL),(855,'p193','《Lou Maset 》 Rouge Aop Languedoc dur Montpeyroux','','en',NULL,193,NULL,NULL),(856,'p193','《Lou Maset 》 Rouge Aop Languedoc dur Montpeyroux','','cn',NULL,193,NULL,NULL),(857,'p194','《Marie Delmas》 Côtes du Roussillon Villages','','fr',NULL,194,NULL,NULL),(858,'p194','《Marie Delmas》 Côtes du Roussillon Villages','','nl',NULL,194,NULL,NULL),(859,'p194','《Marie Delmas》 Côtes du Roussillon Villages','','en',NULL,194,NULL,NULL),(860,'p194','《Marie Delmas》 Côtes du Roussillon Villages','','cn',NULL,194,NULL,NULL),(861,'p195','《Sous les Cailloux, des Grillons》','','fr',NULL,195,NULL,NULL),(862,'p195','《Sous les Cailloux, des Grillons》','','nl',NULL,195,NULL,NULL),(863,'p195','《Sous les Cailloux, des Grillons》','','en',NULL,195,NULL,NULL),(864,'p195','《Sous les Cailloux, des Grillons》','','cn',NULL,195,NULL,NULL),(865,'p196','AOP Fitou 《Le Vin de Ma Cocotte》','','fr',NULL,196,NULL,NULL),(866,'p196','AOP Fitou 《Le Vin de Ma Cocotte》','','nl',NULL,196,NULL,NULL),(867,'p196','AOP Fitou 《Le Vin de Ma Cocotte》','','en',NULL,196,NULL,NULL),(868,'p196','AOP Fitou 《Le Vin de Ma Cocotte》','','cn',NULL,196,NULL,NULL),(869,'p197','Côtes du Rhône Vieux Moulin','','fr',NULL,197,NULL,NULL),(870,'p197','Côtes du Rhône Vieux Moulin','','nl',NULL,197,NULL,NULL),(871,'p197','Côtes du Rhône Vieux Moulin','','en',NULL,197,NULL,NULL),(872,'p197','Côtes du Rhône Vieux Moulin','','cn',NULL,197,NULL,NULL),(873,'p198','Côtes du Rhône Vieux Moulin 1/2','','fr',NULL,198,NULL,NULL),(874,'p198','Côtes du Rhône Vieux Moulin 1/2','','nl',NULL,198,NULL,NULL),(875,'p198','Côtes du Rhône Vieux Moulin 1/2','','en',NULL,198,NULL,NULL),(876,'p198','Côtes du Rhône Vieux Moulin 1/2','','cn',NULL,198,NULL,NULL),(877,'p199','Côtes du Rhône villages 《Chuslan》','','fr',NULL,199,NULL,NULL),(878,'p199','Côtes du Rhône villages 《Chuslan》','','nl',NULL,199,NULL,NULL),(879,'p199','Côtes du Rhône villages 《Chuslan》','','en',NULL,199,NULL,NULL),(880,'p199','Côtes du Rhône villages 《Chuslan》','','cn',NULL,199,NULL,NULL),(881,'p200','Marche Avant rouge Domaine Mas Du Chêne','','fr',NULL,200,NULL,NULL),(882,'p200','Marche Avant rouge Domaine Mas Du Chêne','','nl',NULL,200,NULL,NULL),(883,'p200','Marche Avant rouge Domaine Mas Du Chêne','','en',NULL,200,NULL,NULL),(884,'p200','Marche Avant rouge Domaine Mas Du Chêne','','cn',NULL,200,NULL,NULL),(885,'p201','《La Petite Syrah》Vin de France en Costières de Nismes','','fr',NULL,201,NULL,NULL),(886,'p201','《La Petite Syrah》Vin de France en Costières de Nismes','','nl',NULL,201,NULL,NULL),(887,'p201','《La Petite Syrah》Vin de France en Costières de Nismes','','en',NULL,201,NULL,NULL),(888,'p201','《La Petite Syrah》Vin de France en Costières de Nismes','','cn',NULL,201,NULL,NULL),(889,'p202','Gigondas Domaine du Gour Chaulé','','fr',NULL,202,NULL,NULL),(890,'p202','Gigondas Domaine du Gour Chaulé','','nl',NULL,202,NULL,NULL),(891,'p202','Gigondas Domaine du Gour Chaulé','','en',NULL,202,NULL,NULL),(892,'p202','Gigondas Domaine du Gour Chaulé','','cn',NULL,202,NULL,NULL),(893,'p203','Crozes Hermitage 《sens》','','fr',NULL,203,NULL,NULL),(894,'p203','Crozes Hermitage 《sens》','','nl',NULL,203,NULL,NULL),(895,'p203','Crozes Hermitage 《sens》','','en',NULL,203,NULL,NULL),(896,'p203','Crozes Hermitage 《sens》','','cn',NULL,203,NULL,NULL),(897,'p204','Châteauneuf du Pape Rouge','','fr',NULL,204,NULL,NULL),(898,'p204','Châteauneuf du Pape Rouge','','nl',NULL,204,NULL,NULL),(899,'p204','Châteauneuf du Pape Rouge','','en',NULL,204,NULL,NULL),(900,'p204','Châteauneuf du Pape Rouge','','cn',NULL,204,NULL,NULL),(901,'p205','Magnus Vin des Côtes de Gascogne','','fr',NULL,205,NULL,NULL),(902,'p205','Magnus Vin des Côtes de Gascogne','','nl',NULL,205,NULL,NULL),(903,'p205','Magnus Vin des Côtes de Gascogne','','en',NULL,205,NULL,NULL),(904,'p205','Magnus Vin des Côtes de Gascogne','','cn',NULL,205,NULL,NULL),(905,'p206','Bordeaux Supérieur  -  château Croix de Barre','','fr',NULL,206,NULL,NULL),(906,'p206','Bordeaux Supérieur  -  château Croix de Barre','','nl',NULL,206,NULL,NULL),(907,'p206','Bordeaux Supérieur  -  château Croix de Barre','','en',NULL,206,NULL,NULL),(908,'p206','Bordeaux Supérieur  -  château Croix de Barre','','cn',NULL,206,NULL,NULL),(909,'p207','Fleur de Brian Côté de bordeaux à castillon ','','fr',NULL,207,NULL,NULL),(910,'p207','Fleur de Brian Côté de bordeaux à castillon','','nl',NULL,207,NULL,NULL),(911,'p207','Fleur de Brian Côté de bordeaux à castillon','','en',NULL,207,NULL,NULL),(912,'p207','Fleur de Brian Côté de bordeaux à castillon','','cn',NULL,207,NULL,NULL),(913,'p208','Fleur de Brian Côté de bordeaux à castillon 1/2','','fr',NULL,208,NULL,NULL),(914,'p208','Fleur de Brian Côté de bordeaux à castillon 1/2','','nl',NULL,208,NULL,NULL),(915,'p208','Fleur de Brian Côté de bordeaux à castillon 1/2','','en',NULL,208,NULL,NULL),(916,'p208','Fleur de Brian Côté de bordeaux à castillon 1/2','','cn',NULL,208,NULL,NULL),(917,'p209','Premières Côtes de Blaye 《Les Cèdres》','','fr',NULL,209,NULL,NULL),(918,'p209','Premières Côtes de Blaye 《Les Cèdres》','','nl',NULL,209,NULL,NULL),(919,'p209','Premières Côtes de Blaye 《Les Cèdres》','','en',NULL,209,NULL,NULL),(920,'p209','Premières Côtes de Blaye 《Les Cèdres》','','cn',NULL,209,NULL,NULL),(921,'p210','Graves -- Château la Gravelière','','fr',NULL,210,NULL,NULL),(922,'p210','Graves -- Château la Gravelière','','nl',NULL,210,NULL,NULL),(923,'p210','Graves -- Château la Gravelière','','en',NULL,210,NULL,NULL),(924,'p210','Graves -- Château la Gravelière','','cn',NULL,210,NULL,NULL),(925,'p211','Grave de Vayres - Château De Barre Gentillot','','fr',NULL,211,NULL,NULL),(926,'p211','Grave de Vayres - Château De Barre Gentillot','','nl',NULL,211,NULL,NULL),(927,'p211','Grave de Vayres - Château De Barre Gentillot','','en',NULL,211,NULL,NULL),(928,'p211','Grave de Vayres - Château De Barre Gentillot','','cn',NULL,211,NULL,NULL),(929,'p212','Mèdoc - Château partage de l\'Argenteyre','','fr',NULL,212,NULL,NULL),(930,'p212','Mèdoc - Château partage de l\'Argenteyre','','nl',NULL,212,NULL,NULL),(931,'p212','Mèdoc - Château partage de l\'Argenteyre','','en',NULL,212,NULL,NULL),(932,'p212','Mèdoc - Château partage de l\'Argenteyre','','cn',NULL,212,NULL,NULL),(933,'p213','Sainte Foy de Bordeaux 《Les Temps Modernes》','','fr',NULL,213,NULL,NULL),(934,'p213','Sainte Foy de Bordeaux 《Les Temps Modernes》','','nl',NULL,213,NULL,NULL),(935,'p213','Sainte Foy de Bordeaux 《Les Temps Modernes》','','en',NULL,213,NULL,NULL),(936,'p213','Sainte Foy de Bordeaux 《Les Temps Modernes》','','cn',NULL,213,NULL,NULL),(937,'p214','Saint - Emilion - Château Tertre de Lisse','','fr',NULL,214,NULL,NULL),(938,'p214','Saint - Emilion - Château Tertre de Lisse','','nl',NULL,214,NULL,NULL),(939,'p214','Saint - Emilion - Château Tertre de Lisse','','en',NULL,214,NULL,NULL),(940,'p214','Saint - Emilion - Château Tertre de Lisse','','cn',NULL,214,NULL,NULL),(941,'p215','Saint - Emilion Grand Cru - Château Bernateau','','fr',NULL,215,NULL,NULL),(942,'p215','Saint - Emilion Grand Cru - Château Bernateau','','nl',NULL,215,NULL,NULL),(943,'p215','Saint - Emilion Grand Cru - Château Bernateau','','en',NULL,215,NULL,NULL),(944,'p215','Saint - Emilion Grand Cru - Château Bernateau','','cn',NULL,215,NULL,NULL),(945,'p216','Lalande de Pomerol - Château La croix blanche ','','fr',NULL,216,NULL,NULL),(946,'p216','Lalande de Pomerol - Château La croix blanche ','','nl',NULL,216,NULL,NULL),(947,'p216','Lalande de Pomerol - Château La croix blanche ','','en',NULL,216,NULL,NULL),(948,'p216','Lalande de Pomerol - Château La croix blanche ','','cn',NULL,216,NULL,NULL),(949,'p217','Château Rêve d\'Or','','fr',NULL,217,NULL,NULL),(950,'p217','Château Rêve d\'Or','','nl',NULL,217,NULL,NULL),(951,'p217','Château Rêve d\'Or','','en',NULL,217,NULL,NULL),(952,'p217','Château Rêve d\'Or','','cn',NULL,217,NULL,NULL),(953,'p218','Château La Fleur Peyrabon    Pauillac','','fr',NULL,218,NULL,NULL),(954,'p218','Château La Fleur Peyrabon    Pauillac','','nl',NULL,218,NULL,NULL),(955,'p218','Château La Fleur Peyrabon    Pauillac','','en',NULL,218,NULL,NULL),(956,'p218','Château La Fleur Peyrabon    Pauillac','','cn',NULL,218,NULL,NULL),(957,'p219','Château Lalande Borie Saint Julien','','fr',NULL,219,NULL,NULL),(958,'p219','Château Lalande Borie Saint Julien','','nl',NULL,219,NULL,NULL),(959,'p219','Château Lalande Borie Saint Julien','','en',NULL,219,NULL,NULL),(960,'p219','Château Lalande Borie Saint Julien','','cn',NULL,219,NULL,NULL),(961,'p220','Château La Lagune -- Haut Médoc','','fr',NULL,220,NULL,NULL),(962,'p220','Château La Lagune -- Haut Médoc','','nl',NULL,220,NULL,NULL),(963,'p220','Château La Lagune -- Haut Médoc','','en',NULL,220,NULL,NULL),(964,'p220','Château La Lagune -- Haut Médoc','','cn',NULL,220,NULL,NULL),(965,'p221','un verre d\'eau','','fr',NULL,221,NULL,NULL),(966,'p221','een glas water','','nl',NULL,221,NULL,NULL),(967,'p221','a glass of water','','en',NULL,221,NULL,NULL),(968,'p221','un eau','','cn',NULL,221,NULL,NULL),(969,'a1','pepsi',NULL,'fr',NULL,NULL,NULL,NULL),(970,'a2','cola',NULL,'fr',NULL,NULL,NULL,NULL),(971,'a3','nuts',NULL,'fr',NULL,NULL,NULL,NULL),(972,'ag1','drinks',NULL,'fr',NULL,NULL,NULL,NULL),(973,'ag2','snack',NULL,'fr',NULL,NULL,NULL,NULL),(974,'a1','百事可乐',NULL,'cn',NULL,NULL,NULL,NULL),(975,'a2','可口可乐',NULL,'cn',NULL,NULL,NULL,NULL),(976,'a3','坚果',NULL,'cn',NULL,NULL,NULL,NULL);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `turnover_id` int(11) NOT NULL COMMENT '翻台号',
  `product_id` int(11) NOT NULL COMMENT '菜品',
  `count` tinyint(4) NOT NULL DEFAULT '1' COMMENT '个数',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '是否上菜，催菜加一。0：上菜；1：未上菜',
  `modified` tinyint(4) DEFAULT '0' COMMENT '0:未修改数量；1：加菜；2：减菜；3：消菜',
  `served` timestamp NULL DEFAULT NULL COMMENT '上菜时间戳',
  `printed` tinyint(1) NOT NULL COMMENT '0：未打印；1：已打印',
  PRIMARY KEY (`id`),
  KEY `idx-turnover_id` (`turnover_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`id`,`turnover_id`,`product_id`,`count`,`created`,`updated`,`status`,`modified`,`served`,`printed`) values (1,1,1,3,'2016-02-17 14:00:33',NULL,1,0,NULL,1),(2,2,2,3,'2016-02-17 14:05:42',NULL,1,0,NULL,1),(3,3,3,3,'2016-02-17 14:05:46',NULL,1,0,NULL,1),(4,2,3,3,'2016-02-17 14:59:31',NULL,1,0,NULL,1);

/*Table structure for table `orderattribution` */

DROP TABLE IF EXISTS `orderattribution`;

CREATE TABLE `orderattribution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `attribution_id` int(11) NOT NULL,
  `count` tinyint(4) NOT NULL DEFAULT '1',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `modified` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx-order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `orderattribution` */

insert  into `orderattribution`(`id`,`order_id`,`attribution_id`,`count`,`created`,`updated`,`status`,`modified`) values (1,1,1,1,'2016-02-17 14:00:33',NULL,0,0),(2,1,2,2,'2016-02-17 14:00:33',NULL,0,0),(3,2,1,1,'2016-02-17 14:05:42',NULL,0,0),(4,2,2,2,'2016-02-17 14:05:42',NULL,0,0),(5,3,1,1,'2016-02-17 14:05:46',NULL,0,0),(6,3,2,2,'2016-02-17 14:05:46',NULL,0,0),(7,4,1,1,'2016-02-17 14:59:31',NULL,0,0),(8,4,2,2,'2016-02-17 14:59:31',NULL,0,0);

/*Table structure for table `orderproductgroup` */

DROP TABLE IF EXISTS `orderproductgroup`;

CREATE TABLE `orderproductgroup` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `status` tinyint(1) DEFAULT '1' COMMENT '0:上菜；1：未上菜，套餐order表status',
  `updated` timestamp NULL DEFAULT NULL,
  `served` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`order_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orderproductgroup` */

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category_id` int(11) NOT NULL DEFAULT '0',
  `product_num` varchar(32) NOT NULL DEFAULT '',
  `product_name` varchar(128) NOT NULL DEFAULT '',
  `description` text,
  `position` int(11) DEFAULT '0',
  `active` int(10) NOT NULL DEFAULT '0',
  `taxgroup_id` int(10) DEFAULT NULL,
  `num` int(128) DEFAULT NULL,
  `min_num` int(11) DEFAULT '1',
  `thumb` varchar(128) DEFAULT NULL,
  `product_price` int(11) NOT NULL DEFAULT '0',
  `bar_name` varchar(100) DEFAULT NULL COMMENT '同category的bar_name',
  `type` tinyint(1) DEFAULT NULL COMMENT '同category的type',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`id`,`category_id`,`product_num`,`product_name`,`description`,`position`,`active`,`taxgroup_id`,`num`,`min_num`,`thumb`,`product_price`,`bar_name`,`type`) values (1,2,'1','',NULL,1,1,1,5,1,'thumb_2014_12_15_11_23_26.png',480,NULL,2),(2,2,'2','',NULL,2,1,1,5,1,'thumb_2014_12_15_11_24_01.png',980,NULL,1),(3,2,'3','',NULL,3,1,1,5,1,NULL,580,NULL,0),(4,2,'4','',NULL,4,1,1,5,1,'thumb_2014_12_26_15_03_04.jpg',580,NULL,NULL),(5,2,'5','',NULL,5,1,1,5,1,'thumb_2014_12_26_15_04_43.jpg',580,NULL,NULL),(6,2,'6','',NULL,6,1,1,5,1,NULL,580,NULL,NULL),(7,2,'7','',NULL,7,1,1,5,1,'thumb_2014_12_26_15_05_18.jpg',580,NULL,NULL),(8,2,'8-s','',NULL,8,1,1,NULL,1,'thumb_2014_12_26_15_05_46.jpg',1800,NULL,NULL),(9,2,'8-m','',NULL,9,1,1,NULL,1,'thumb_2014_12_26_15_06_10.jpg',3200,NULL,NULL),(10,2,'8-L','',NULL,10,1,1,NULL,1,'thumb_2014_12_26_15_06_58.jpg',5800,NULL,NULL),(11,3,'10','',NULL,1,1,1,5,1,NULL,700,NULL,NULL),(12,3,'11','',NULL,2,1,1,NULL,1,'thumb_2014_12_26_15_09_58.jpg',880,NULL,NULL),(13,3,'12','',NULL,3,1,1,NULL,1,NULL,600,NULL,NULL),(14,3,'13','',NULL,4,1,1,NULL,1,NULL,800,NULL,NULL),(15,3,'14','',NULL,5,1,1,NULL,1,'thumb_2014_12_26_14_58_40.jpg',700,NULL,NULL),(16,3,'15','',NULL,6,1,1,NULL,1,'thumb_2014_12_26_14_59_35.jpg',800,NULL,NULL),(17,3,'16','',NULL,7,1,1,NULL,1,NULL,320,NULL,NULL),(18,3,'17','',NULL,8,1,1,NULL,1,NULL,450,NULL,NULL),(19,3,'18','',NULL,9,1,1,NULL,1,'thumb_2014_12_26_15_33_55.jpg',750,NULL,NULL),(20,4,'20','',NULL,1,1,1,NULL,1,'thumb_2014_12_26_15_19_36.jpg',1450,NULL,NULL),(21,4,'21','',NULL,2,1,1,NULL,1,'thumb_2014_12_26_15_20_28.jpg',2150,NULL,NULL),(22,4,'22','',NULL,3,1,1,NULL,1,NULL,2150,NULL,NULL),(23,4,'23','',NULL,4,1,1,NULL,1,NULL,2150,NULL,NULL),(24,4,'24','',NULL,5,1,1,NULL,1,NULL,1850,NULL,NULL),(25,4,'25','',NULL,6,0,1,NULL,1,NULL,2150,NULL,NULL),(26,4,'26','',NULL,7,1,1,NULL,1,NULL,5800,NULL,NULL),(27,4,'27','',NULL,8,0,1,NULL,1,NULL,1850,NULL,NULL),(28,4,'28','',NULL,9,1,1,NULL,1,NULL,2150,NULL,NULL),(29,4,'29','',NULL,10,1,1,NULL,1,NULL,1850,NULL,NULL),(30,4,'30','',NULL,11,0,1,NULL,1,NULL,2500,NULL,NULL),(31,4,'31','',NULL,12,1,1,NULL,1,NULL,2800,NULL,NULL),(32,5,'40','',NULL,1,1,1,NULL,1,NULL,0,NULL,NULL),(33,5,'41','',NULL,2,1,1,NULL,1,NULL,0,NULL,NULL),(34,5,'42','',NULL,3,1,1,NULL,1,NULL,0,NULL,NULL),(35,5,'43','',NULL,4,0,1,NULL,1,NULL,0,NULL,NULL),(36,5,'44','',NULL,5,1,1,NULL,1,NULL,0,NULL,NULL),(37,5,'45','',NULL,6,1,1,NULL,1,NULL,0,NULL,NULL),(38,5,'46','',NULL,7,0,1,NULL,1,NULL,0,NULL,NULL),(39,5,'47','',NULL,8,1,1,NULL,1,NULL,0,NULL,NULL),(40,5,'48','',NULL,9,1,1,NULL,1,NULL,0,NULL,NULL),(41,5,'49','',NULL,10,1,1,NULL,1,NULL,0,NULL,NULL),(42,6,'50','',NULL,1,1,1,2,1,'thumb_2014_12_15_11_24_35.png',380,NULL,NULL),(43,6,'51','',NULL,2,1,1,2,1,'thumb_2014_12_15_11_25_05.png',450,NULL,NULL),(44,6,'52','',NULL,3,1,1,2,1,'thumb_2014_12_26_15_07_53.jpg',380,NULL,NULL),(45,6,'53','',NULL,4,1,1,2,1,'thumb_2014_12_26_15_08_19.jpg',420,NULL,NULL),(46,6,'54','',NULL,5,1,1,2,1,'thumb_2014_12_15_11_25_55.png',420,NULL,NULL),(47,6,'55','',NULL,6,1,1,2,1,'thumb_2014_12_15_11_26_23.png',380,NULL,NULL),(48,6,'56','',NULL,7,1,1,2,1,'thumb_2014_12_15_11_26_49.png',320,NULL,NULL),(49,6,'57','',NULL,8,1,1,2,1,'thumb_2014_12_26_15_09_13.jpg',420,NULL,NULL),(50,6,'58','',NULL,9,1,1,2,1,'thumb_2014_12_15_11_43_44.png',580,NULL,NULL),(51,6,'59','',NULL,10,0,1,2,1,NULL,480,NULL,NULL),(52,7,'60','',NULL,4,1,1,6,1,'thumb_2014_12_15_11_28_48.png',500,NULL,NULL),(53,7,'61','',NULL,1,1,1,6,1,'thumb_2014_12_15_11_29_09.png',600,NULL,NULL),(54,7,'62','',NULL,2,1,1,6,1,'thumb_2014_12_15_11_28_22.png',500,NULL,NULL),(55,7,'63','',NULL,3,1,1,6,1,'thumb_2014_12_26_15_12_02.jpg',600,NULL,NULL),(56,8,'70','',NULL,1,1,1,8,1,'thumb_2014_12_26_15_12_53.jpg',820,NULL,NULL),(57,8,'71','',NULL,2,1,1,8,1,'thumb_2014_12_26_15_13_35.jpg',820,NULL,NULL),(58,8,'72','',NULL,3,1,1,8,1,'thumb_2014_12_26_15_14_34.jpg',920,NULL,NULL),(59,8,'73','',NULL,4,1,1,8,1,'thumb_2014_12_26_15_15_13.jpg',820,NULL,NULL),(60,8,'74','',NULL,5,1,1,8,1,'thumb_2014_12_26_15_15_35.jpg',820,NULL,NULL),(61,8,'75','',NULL,6,1,1,8,1,'thumb_2014_12_26_15_18_38.jpg',820,NULL,NULL),(62,8,'76','',NULL,7,1,1,8,1,'thumb_2014_12_26_15_33_23.jpg',1250,NULL,NULL),(63,9,'80','',NULL,1,1,1,NULL,1,NULL,750,NULL,NULL),(64,9,'81','',NULL,2,1,1,NULL,1,'thumb_2014_12_26_15_22_12.jpg',980,NULL,NULL),(65,9,'82','',NULL,3,1,1,NULL,1,'thumb_2014_12_26_15_23_10.jpg',1350,NULL,NULL),(66,9,'83','',NULL,4,1,1,NULL,1,NULL,980,NULL,NULL),(67,9,'84','',NULL,5,1,1,NULL,1,NULL,980,NULL,NULL),(68,9,'85','',NULL,6,1,1,NULL,1,NULL,980,NULL,NULL),(69,10,'90','',NULL,1,1,1,NULL,1,'thumb_2014_12_15_11_45_34.jpg',2350,NULL,NULL),(70,10,'91','',NULL,2,1,1,NULL,1,NULL,1450,NULL,NULL),(71,10,'92','',NULL,3,1,1,NULL,1,NULL,2350,NULL,NULL),(72,10,'93','',NULL,4,1,1,NULL,1,NULL,1650,NULL,NULL),(73,10,'94','',NULL,5,1,1,NULL,1,NULL,1450,NULL,NULL),(74,11,'M-1','',NULL,1,0,1,NULL,1,NULL,0,NULL,NULL),(75,11,'M-2','',NULL,2,1,1,NULL,1,NULL,6800,NULL,NULL),(76,12,'101','',NULL,1,1,2,NULL,1,'thumb_2014_12_17_17_18_18.jpg',500,NULL,NULL),(77,12,'102','',NULL,2,1,1,NULL,1,'thumb_2014_12_17_17_23_13.jpg',420,NULL,NULL),(78,12,'103','',NULL,3,1,1,NULL,1,'thumb_2014_12_17_17_24_01.jpg',420,NULL,NULL),(79,12,'104','',NULL,4,1,1,NULL,1,NULL,420,NULL,NULL),(80,12,'105','',NULL,5,1,2,NULL,1,'thumb_2014_12_17_17_25_07.jpg',480,NULL,NULL),(81,12,'106','',NULL,6,1,2,NULL,1,'thumb_2014_12_17_17_25_34.jpg',500,NULL,NULL),(82,12,'107','',NULL,7,1,2,NULL,1,'thumb_2014_12_17_17_26_39.jpg',980,NULL,NULL),(83,12,'108','',NULL,8,1,1,NULL,1,'thumb_2014_12_17_17_27_04.jpg',880,NULL,NULL),(84,13,'201','',NULL,5,1,1,NULL,1,'thumb_2014_12_17_17_57_23.jpg',320,NULL,NULL),(85,13,'202','',NULL,6,1,1,NULL,1,'thumb_2014_12_17_17_30_50.jpg',320,NULL,NULL),(86,13,'203','',NULL,7,1,1,NULL,1,'thumb_2014_12_17_17_33_05.jpg',320,NULL,NULL),(87,13,'204','',NULL,8,1,1,NULL,1,'thumb_2014_12_17_17_33_29.jpg',320,NULL,NULL),(88,13,'205','',NULL,9,1,1,NULL,1,'thumb_2014_12_17_17_33_55.png',320,NULL,NULL),(89,13,'206','',NULL,10,1,1,NULL,1,'thumb_2014_12_17_17_34_20.jpg',320,NULL,NULL),(90,13,'207','',NULL,11,1,1,NULL,1,'thumb_2014_12_17_17_34_46.jpg',350,NULL,NULL),(91,13,'208','',NULL,12,1,1,NULL,1,'thumb_2014_12_17_17_35_18.jpg',350,NULL,NULL),(92,13,'200 ','',NULL,4,1,1,NULL,1,'thumb_2014_12_17_17_28_52.jpg',320,NULL,NULL),(93,13,'209','',NULL,1,1,1,NULL,1,'thumb_2014_12_17_17_27_36.png',450,NULL,NULL),(94,13,'210','',NULL,2,1,1,NULL,1,'thumb_2014_12_17_17_28_03.jpg',450,NULL,NULL),(95,13,'211','',NULL,3,1,1,NULL,1,'thumb_2014_12_17_17_28_27.jpg',500,NULL,NULL),(96,16,'301','',NULL,1,1,1,NULL,1,'thumb_2014_12_17_17_41_36.JPG',650,NULL,NULL),(97,16,'302','',NULL,2,0,1,NULL,1,NULL,450,NULL,NULL),(98,16,'303','',NULL,3,1,1,NULL,1,'thumb_2014_12_17_17_42_38.jpg',550,NULL,NULL),(99,16,'304','',NULL,4,1,1,NULL,1,'thumb_2014_12_17_17_43_10.jpg',550,NULL,NULL),(100,16,'305','',NULL,5,1,1,NULL,1,'thumb_2014_12_17_17_43_34.jpg',550,NULL,NULL),(101,15,'401','',NULL,1,1,1,NULL,1,'thumb_2014_12_17_17_44_06.jpg',280,NULL,NULL),(102,15,'402','',NULL,2,1,1,NULL,1,'thumb_2014_12_17_17_44_35.jpg',320,NULL,NULL),(103,15,'403','',NULL,3,1,1,NULL,1,'thumb_2014_12_17_17_45_02.jpg',320,NULL,NULL),(104,15,'404','',NULL,4,1,1,NULL,1,'thumb_2014_12_17_17_45_33.jpg',280,NULL,NULL),(105,15,'400','',NULL,5,1,1,NULL,1,'thumb_2014_12_17_17_45_57.jpg',280,NULL,NULL),(106,15,'405','',NULL,6,1,1,NULL,1,'thumb_2014_12_17_17_46_20.jpg',600,NULL,NULL),(107,13,'212','',NULL,13,1,1,NULL,1,'thumb_2014_12_17_17_35_43.jpg',600,NULL,NULL),(108,13,'213','',NULL,14,1,1,NULL,1,'thumb_2014_12_17_17_36_07.jpg',600,NULL,NULL),(109,14,'501','',NULL,1,1,1,NULL,1,'thumb_2014_12_17_17_46_45.jpg',800,NULL,NULL),(110,14,'502','',NULL,2,1,1,NULL,1,'thumb_2014_12_17_17_47_18.jpg',550,NULL,NULL),(111,14,'503','',NULL,3,1,1,NULL,1,'thumb_2014_12_17_17_47_43.jpg',600,NULL,NULL),(112,14,'504','',NULL,4,1,1,NULL,1,'thumb_2014_12_17_17_48_11.jpg',600,NULL,NULL),(113,14,'505','',NULL,5,1,1,NULL,1,'thumb_2014_12_17_17_50_01.jpg',500,NULL,NULL),(114,14,'506','',NULL,6,1,1,NULL,1,'thumb_2014_12_17_17_50_27.jpg',550,NULL,NULL),(115,13,'214','',NULL,15,1,1,NULL,1,'thumb_2014_12_17_17_36_30.jpg',400,NULL,NULL),(116,13,'215','',NULL,16,1,1,NULL,1,'thumb_2014_12_17_17_37_08.jpg',750,NULL,NULL),(117,17,'601','',NULL,2,1,1,NULL,1,NULL,1800,NULL,NULL),(118,17,'591','',NULL,1,0,1,NULL,1,NULL,100,NULL,NULL),(119,13,'216','',NULL,17,1,1,NULL,1,'thumb_2014_12_17_17_40_15.jpg',400,NULL,NULL),(120,13,'217','',NULL,18,1,1,NULL,1,'thumb_2014_12_17_17_40_45.jpg',750,NULL,NULL),(121,18,'600','',NULL,3,1,1,NULL,1,NULL,980,NULL,NULL),(122,18,'599','',NULL,6,1,1,NULL,1,NULL,980,NULL,NULL),(123,18,'598','',NULL,9,1,1,NULL,1,NULL,980,NULL,NULL),(124,18,'597','',NULL,2,1,1,NULL,1,NULL,550,NULL,NULL),(125,18,'596','',NULL,5,1,1,NULL,1,NULL,550,NULL,NULL),(126,18,'595','',NULL,8,1,1,NULL,1,NULL,550,NULL,NULL),(127,18,'594','',NULL,1,1,1,NULL,1,NULL,300,NULL,NULL),(128,18,'593','',NULL,4,1,1,NULL,1,NULL,300,NULL,NULL),(129,18,'592','',NULL,7,1,1,NULL,1,NULL,300,NULL,NULL),(130,17,'618','',NULL,3,0,1,NULL,1,NULL,2900,NULL,NULL),(131,17,'602','',NULL,4,1,1,NULL,1,NULL,2300,NULL,NULL),(132,17,'603','',NULL,5,1,1,NULL,1,NULL,2400,NULL,NULL),(133,17,'604','',NULL,6,1,1,NULL,1,NULL,2500,NULL,NULL),(134,17,'605','',NULL,7,1,1,NULL,1,NULL,3100,NULL,NULL),(135,17,'605','',NULL,8,1,1,NULL,1,NULL,3100,NULL,NULL),(136,17,'606','',NULL,9,1,1,NULL,1,NULL,3200,NULL,NULL),(137,17,'607','',NULL,10,1,1,NULL,1,NULL,2800,NULL,NULL),(138,17,'608','',NULL,11,1,1,NULL,1,NULL,2600,NULL,NULL),(139,17,'609','',NULL,12,1,1,NULL,1,NULL,2900,NULL,NULL),(140,17,'610','',NULL,13,1,1,NULL,1,NULL,3200,NULL,NULL),(141,17,'611','',NULL,14,1,1,NULL,1,NULL,3200,NULL,NULL),(142,17,'612','',NULL,15,1,1,NULL,1,NULL,3300,NULL,NULL),(143,17,'613','',NULL,16,1,1,NULL,1,NULL,2600,NULL,NULL),(144,17,'99','',NULL,17,1,1,NULL,1,NULL,5500,NULL,NULL),(145,11,'M-3','',NULL,3,1,1,NULL,1,NULL,3500,NULL,NULL),(146,19,'734','',NULL,1,0,1,NULL,1,NULL,2600,NULL,NULL),(147,19,'930','',NULL,2,0,1,NULL,1,NULL,2500,NULL,NULL),(148,20,'826','',NULL,1,0,1,NULL,1,NULL,2400,NULL,NULL),(149,20,'827','',NULL,2,0,1,NULL,1,NULL,2300,NULL,NULL),(150,4,'32','',NULL,13,1,1,NULL,1,NULL,480,NULL,NULL),(151,15,'406','',NULL,7,1,1,NULL,1,'thumb_2014_12_20_19_48_52.jpg',600,NULL,NULL),(152,21,'151','',NULL,1,1,1,NULL,1,NULL,1190,NULL,NULL),(153,21,'152','',NULL,2,1,1,NULL,1,NULL,1190,NULL,NULL),(154,19,'951','',NULL,3,0,1,NULL,1,NULL,3400,NULL,NULL),(155,19,'655a','',NULL,14,0,1,NULL,1,NULL,1200,NULL,NULL),(156,19,'631','',NULL,4,0,1,NULL,1,NULL,2400,NULL,NULL),(157,19,'660','',NULL,5,0,1,NULL,1,NULL,2600,NULL,NULL),(158,17,'614','',NULL,18,1,1,NULL,1,NULL,2300,NULL,NULL),(159,17,'615','',NULL,19,1,1,NULL,1,NULL,2700,NULL,NULL),(160,17,'615','',NULL,20,1,1,NULL,1,NULL,2700,NULL,NULL),(161,17,'616','',NULL,21,1,1,NULL,1,NULL,2500,NULL,NULL),(162,17,'616a','',NULL,22,1,1,NULL,1,NULL,1500,NULL,NULL),(163,17,'617','',NULL,23,1,1,NULL,1,NULL,2600,NULL,NULL),(164,17,'617a','',NULL,24,1,1,NULL,1,NULL,1700,NULL,NULL),(165,17,'618','',NULL,25,1,1,NULL,1,NULL,3200,NULL,NULL),(166,17,'619','',NULL,26,1,1,NULL,1,NULL,4600,NULL,NULL),(167,20,'620','',NULL,3,1,1,NULL,1,NULL,1700,NULL,NULL),(168,20,'621','',NULL,4,1,1,NULL,1,NULL,1800,NULL,NULL),(169,20,'621a','',NULL,5,1,1,NULL,1,NULL,1200,NULL,NULL),(170,20,'622','',NULL,6,1,1,NULL,1,NULL,2100,NULL,NULL),(171,20,'623','',NULL,7,1,1,NULL,1,NULL,1800,NULL,NULL),(172,20,'624','',NULL,8,1,1,NULL,1,NULL,2600,NULL,NULL),(173,20,'625','',NULL,9,1,1,NULL,1,NULL,2700,NULL,NULL),(174,20,'626','',NULL,10,1,1,NULL,1,NULL,2600,NULL,NULL),(175,20,'627','',NULL,11,1,1,NULL,1,NULL,2400,NULL,NULL),(176,20,'627a','',NULL,12,1,1,NULL,1,NULL,1400,NULL,NULL),(177,19,'628','',NULL,6,1,1,NULL,1,NULL,2200,NULL,NULL),(178,19,'628a','',NULL,7,1,1,NULL,1,NULL,1200,NULL,NULL),(179,19,'629','',NULL,8,1,1,NULL,1,NULL,2200,NULL,NULL),(180,19,'630','',NULL,10,1,1,NULL,1,NULL,2600,NULL,NULL),(181,19,'631','',NULL,10,1,1,NULL,1,NULL,2400,NULL,NULL),(182,19,'632','',NULL,11,1,1,NULL,1,NULL,2500,NULL,NULL),(183,19,'633','',NULL,12,1,1,NULL,1,NULL,3100,NULL,NULL),(184,19,'634','',NULL,13,1,1,NULL,1,NULL,2700,NULL,NULL),(185,19,'634a','',NULL,15,1,1,NULL,1,NULL,1500,NULL,NULL),(186,19,'635','',NULL,16,1,1,NULL,1,NULL,3400,NULL,NULL),(187,19,'636','',NULL,17,1,1,NULL,1,NULL,3300,NULL,NULL),(188,19,'637','',NULL,18,1,1,NULL,1,NULL,5500,NULL,NULL),(189,19,'638','',NULL,19,1,1,NULL,1,NULL,1700,NULL,NULL),(190,19,'639','',NULL,20,1,1,NULL,1,NULL,2200,NULL,NULL),(191,19,'640','',NULL,21,1,1,NULL,1,NULL,2400,NULL,NULL),(192,19,'641','',NULL,22,1,1,NULL,1,NULL,2700,NULL,NULL),(193,19,'642','',NULL,23,1,1,NULL,1,NULL,2600,NULL,NULL),(194,19,'643','',NULL,24,1,1,NULL,1,NULL,2900,NULL,NULL),(195,19,'644','',NULL,25,1,1,NULL,1,NULL,2800,NULL,NULL),(196,19,'645','',NULL,26,1,1,NULL,1,NULL,4500,NULL,NULL),(197,19,'646','',NULL,27,1,1,NULL,1,NULL,2000,NULL,NULL),(198,19,'646a','',NULL,28,1,1,NULL,1,NULL,1200,NULL,NULL),(199,19,'647 ','',NULL,29,1,1,NULL,1,NULL,2400,NULL,NULL),(200,19,'648','',NULL,30,1,1,NULL,1,NULL,2800,NULL,NULL),(201,19,'649','',NULL,31,1,1,NULL,1,NULL,3300,NULL,NULL),(202,19,'650','',NULL,32,1,1,NULL,1,NULL,3400,NULL,NULL),(203,19,'651','',NULL,33,1,1,NULL,1,NULL,3100,NULL,NULL),(204,19,'652','',NULL,34,1,1,NULL,1,NULL,4600,NULL,NULL),(205,19,'653','',NULL,35,1,1,NULL,1,NULL,2800,NULL,NULL),(206,19,'654','',NULL,36,1,1,NULL,1,NULL,2100,NULL,NULL),(207,19,'655','',NULL,37,1,1,NULL,1,NULL,2200,NULL,NULL),(208,19,'655a','',NULL,38,1,1,NULL,1,NULL,1200,NULL,NULL),(209,19,'656','',NULL,39,1,1,NULL,1,NULL,2300,NULL,NULL),(210,19,'657','',NULL,40,1,1,NULL,1,NULL,2600,NULL,NULL),(211,19,'658','',NULL,41,1,1,NULL,1,NULL,2600,NULL,NULL),(212,19,'659','',NULL,42,1,1,NULL,1,NULL,2700,NULL,NULL),(213,19,'660','',NULL,43,1,1,NULL,1,NULL,2600,NULL,NULL),(214,19,'661','',NULL,44,1,1,NULL,1,NULL,3300,NULL,NULL),(215,19,'662','',NULL,45,1,1,NULL,1,NULL,4800,NULL,NULL),(216,19,'663','',NULL,46,1,1,NULL,1,NULL,4000,NULL,NULL),(217,19,'664','',NULL,47,1,1,NULL,1,NULL,5900,NULL,NULL),(218,19,'665','',NULL,48,1,1,NULL,1,NULL,5400,NULL,NULL),(219,19,'666','',NULL,49,1,1,NULL,1,NULL,6100,NULL,NULL),(220,19,'667','',NULL,50,1,1,NULL,1,NULL,12200,NULL,NULL),(221,13,'218','',NULL,19,1,1,NULL,1,NULL,200,NULL,NULL);

/*Table structure for table `productattributiongroup` */

DROP TABLE IF EXISTS `productattributiongroup`;

CREATE TABLE `productattributiongroup` (
  `product_id` int(11) NOT NULL,
  `attribution_group_id` int(11) NOT NULL,
  `product_attribution_group_status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`product_id`,`attribution_group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `productattributiongroup` */

insert  into `productattributiongroup`(`product_id`,`attribution_group_id`,`product_attribution_group_status`) values (1,1,1),(1,2,2),(2,1,1);

/*Table structure for table `productgroup` */

DROP TABLE IF EXISTS `productgroup`;

CREATE TABLE `productgroup` (
  `product_id` int(11) NOT NULL,
  `member_product_id` int(11) NOT NULL,
  PRIMARY KEY (`product_id`,`member_product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `productgroup` */

insert  into `productgroup`(`product_id`,`member_product_id`) values (1,2),(1,3);

/*Table structure for table `quickquery` */

DROP TABLE IF EXISTS `quickquery`;

CREATE TABLE `quickquery` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL,
  `key_short` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `key_short` (`key_short`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='quick search by key_short ';

/*Data for the table `quickquery` */

/*Table structure for table `settings` */

DROP TABLE IF EXISTS `settings`;

CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_theme` varchar(128) DEFAULT NULL,
  `main_theme` varchar(128) DEFAULT NULL,
  `meta` varchar(128) DEFAULT NULL,
  `contact` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `category_url` varchar(128) DEFAULT NULL,
  `product_url` varchar(128) DEFAULT NULL,
  `rounds` tinyint(2) DEFAULT NULL,
  `round_interval` tinyint(2) DEFAULT NULL COMMENT '单位：分钟',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `settings` */

insert  into `settings`(`id`,`admin_theme`,`main_theme`,`meta`,`contact`,`email`,`category_url`,`product_url`,`rounds`,`round_interval`) values (1,'default',NULL,NULL,NULL,NULL,'http://www.zhen.be/dodoro/catThumbs/','http://www.zhen.be/dodoro/productThumbs/',3,2);

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_thumb` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tag` */

/*Table structure for table `tagproduct` */

DROP TABLE IF EXISTS `tagproduct`;

CREATE TABLE `tagproduct` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL DEFAULT '0',
  `product_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`tag_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tagproduct` */

/*Table structure for table `takeaway` */

DROP TABLE IF EXISTS `takeaway`;

CREATE TABLE `takeaway` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  `memo` varchar(500) DEFAULT NULL,
  `takeaway` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否取走（1:已取走，0:未取走）',
  `vaild` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否确认有效（1：有效，0：未确认）',
  `printed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '厨房是否打印（1：已打印，0：未打印）',
  `source` tinyint(1) DEFAULT NULL COMMENT '1：外卖网站，2：电话下单，3：店内下单',
  `delivery` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0:自取，1：外送',
  `delivery_timestamp` timestamp NULL DEFAULT NULL COMMENT '自取或送货时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `takeaway` */

/*Table structure for table `taxgroups` */

DROP TABLE IF EXISTS `taxgroups`;

CREATE TABLE `taxgroups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `value` float DEFAULT '0',
  `takeaway` float DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `taxgroups` */

insert  into `taxgroups`(`id`,`name`,`value`,`takeaway`) values (1,'food',0.12,0.06),(2,'alcool',0.21,0.21);

/*Table structure for table `turnover` */

DROP TABLE IF EXISTS `turnover`;

CREATE TABLE `turnover` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` tinyint(4) NOT NULL COMMENT '桌号',
  `first_table_id` tinyint(4) NOT NULL COMMENT '进店时第一次桌号',
  `checkout` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否已结帐（1: 已结帐，0：未结帐）',
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` timestamp NULL DEFAULT NULL,
  `discount` tinyint(2) DEFAULT NULL,
  `takeaway_id` int(11) DEFAULT NULL,
  `round` tinyint(2) DEFAULT '1',
  `round_time` timestamp NULL DEFAULT NULL,
  `round_order_count` tinyint(2) DEFAULT NULL COMMENT '每轮点单个数',
  `payment` tinyint(1) DEFAULT NULL COMMENT '0:cash;1:credit card',
  PRIMARY KEY (`id`),
  KEY `idx-check` (`checkout`),
  KEY `FK_turnover` (`table_id`),
  CONSTRAINT `FK_turnover` FOREIGN KEY (`table_id`) REFERENCES `diningtable` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `turnover` */

insert  into `turnover`(`id`,`table_id`,`first_table_id`,`checkout`,`created`,`updated`,`discount`,`takeaway_id`,`round`,`round_time`,`round_order_count`,`payment`) values (1,1,1,0,'2016-02-10 01:05:06',NULL,NULL,NULL,1,NULL,NULL,NULL),(2,3,3,0,'2016-02-17 14:00:04',NULL,NULL,NULL,1,NULL,NULL,NULL),(3,3,3,0,'2016-02-17 14:00:04',NULL,NULL,NULL,1,NULL,NULL,NULL);

/*Table structure for table `turnoverattribute` */

DROP TABLE IF EXISTS `turnoverattribute`;

CREATE TABLE `turnoverattribute` (
  `turnover_id` int(11) NOT NULL,
  `attribute_name` varchar(30) NOT NULL,
  `attribute_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`turnover_id`,`attribute_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `turnoverattribute` */

insert  into `turnoverattribute`(`turnover_id`,`attribute_name`,`attribute_value`) values (1,'mark','false'),(2,'mark','true'),(3,'mark','true');

/*Table structure for table `colddish` */

DROP TABLE IF EXISTS `colddish`;

/*!50001 DROP VIEW IF EXISTS `colddish` */;
/*!50001 DROP TABLE IF EXISTS `colddish` */;

/*!50001 CREATE TABLE  `colddish`(
 `count` tinyint(4) ,
 `order_id` int(11) ,
 `product_id` int(11) ,
 `status` tinyint(3) ,
 `created` timestamp ,
 `category_id` int(11) ,
 `product_name` varchar(128) ,
 `category_name` varchar(128) ,
 `type` tinyint(1) ,
 `product_num` varchar(32) ,
 `takeaway_id` int(11) ,
 `turnover_id` int(11) ,
 `table_id` tinyint(4) 
)*/;

/*Table structure for table `combo` */

DROP TABLE IF EXISTS `combo`;

/*!50001 DROP VIEW IF EXISTS `combo` */;
/*!50001 DROP TABLE IF EXISTS `combo` */;

/*!50001 CREATE TABLE  `combo`(
 `count` tinyint(4) ,
 `order_id` int(11) ,
 `product_id` int(11) ,
 `status` tinyint(3) ,
 `created` timestamp ,
 `category_id` int(11) ,
 `product_name` varchar(128) ,
 `category_name` varchar(128) ,
 `type` tinyint(1) ,
 `product_num` varchar(32) ,
 `takeaway_id` int(11) ,
 `turnover_id` int(11) ,
 `table_id` tinyint(4) 
)*/;

/*Table structure for table `hotdish` */

DROP TABLE IF EXISTS `hotdish`;

/*!50001 DROP VIEW IF EXISTS `hotdish` */;
/*!50001 DROP TABLE IF EXISTS `hotdish` */;

/*!50001 CREATE TABLE  `hotdish`(
 `order_id` int(11) ,
 `count` tinyint(4) ,
 `product_id` int(11) ,
 `status` tinyint(3) ,
 `created` timestamp ,
 `category_id` int(11) ,
 `product_name` varchar(128) ,
 `category_name` varchar(128) ,
 `type` tinyint(1) ,
 `product_num` varchar(32) ,
 `takeaway_id` int(11) ,
 `turnover_id` int(11) ,
 `table_id` tinyint(4) 
)*/;

/*Table structure for table `kitchenorder` */

DROP TABLE IF EXISTS `kitchenorder`;

/*!50001 DROP VIEW IF EXISTS `kitchenorder` */;
/*!50001 DROP TABLE IF EXISTS `kitchenorder` */;

/*!50001 CREATE TABLE  `kitchenorder`(
 `order_id` int(11) ,
 `count` tinyint(4) ,
 `product_id` int(11) ,
 `status` tinyint(3) ,
 `created` timestamp ,
 `served` timestamp ,
 `category_id` int(11) ,
 `product_name` varchar(128) ,
 `category_name` varchar(128) ,
 `type` tinyint(1) ,
 `product_num` varchar(32) ,
 `takeaway_id` int(11) ,
 `turnover_id` int(11) ,
 `table_id` tinyint(4) 
)*/;

/*Table structure for table `mergedorder` */

DROP TABLE IF EXISTS `mergedorder`;

/*!50001 DROP VIEW IF EXISTS `mergedorder` */;
/*!50001 DROP TABLE IF EXISTS `mergedorder` */;

/*!50001 CREATE TABLE  `mergedorder`(
 `count` decimal(25,0) ,
 `turnover_id` int(11) ,
 `product_id` int(11) ,
 `status` tinyint(3) ,
 `created` timestamp 
)*/;

/*Table structure for table `servedorder` */

DROP TABLE IF EXISTS `servedorder`;

/*!50001 DROP VIEW IF EXISTS `servedorder` */;
/*!50001 DROP TABLE IF EXISTS `servedorder` */;

/*!50001 CREATE TABLE  `servedorder`(
 `order_id` int(11) ,
 `count` tinyint(4) ,
 `product_id` int(11) ,
 `product_name` varchar(128) ,
 `category_name` varchar(128) ,
 `updated` timestamp ,
 `created` timestamp ,
 `category_id` int(11) ,
 `type` tinyint(1) ,
 `product_num` varchar(32) ,
 `takeaway_id` int(11) ,
 `turnover_id` int(11) ,
 `table_id` tinyint(4) ,
 `served` timestamp 
)*/;

/*View structure for view colddish */

/*!50001 DROP TABLE IF EXISTS `colddish` */;
/*!50001 DROP VIEW IF EXISTS `colddish` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`dbg0flj3fz9u2145`@`%` SQL SECURITY DEFINER VIEW `colddish` AS (select `kitchenorder`.`count` AS `count`,`kitchenorder`.`order_id` AS `order_id`,`kitchenorder`.`product_id` AS `product_id`,`kitchenorder`.`status` AS `status`,`kitchenorder`.`created` AS `created`,`kitchenorder`.`category_id` AS `category_id`,`kitchenorder`.`product_name` AS `product_name`,`kitchenorder`.`category_name` AS `category_name`,`kitchenorder`.`type` AS `type`,`kitchenorder`.`product_num` AS `product_num`,`kitchenorder`.`takeaway_id` AS `takeaway_id`,`kitchenorder`.`turnover_id` AS `turnover_id`,`kitchenorder`.`table_id` AS `table_id` from `kitchenorder` where (`kitchenorder`.`type` = 0)) */;

/*View structure for view combo */

/*!50001 DROP TABLE IF EXISTS `combo` */;
/*!50001 DROP VIEW IF EXISTS `combo` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`dbg0flj3fz9u2145`@`%` SQL SECURITY DEFINER VIEW `combo` AS (select `kitchenorder`.`count` AS `count`,`kitchenorder`.`order_id` AS `order_id`,`kitchenorder`.`product_id` AS `product_id`,`kitchenorder`.`status` AS `status`,`kitchenorder`.`created` AS `created`,`kitchenorder`.`category_id` AS `category_id`,`kitchenorder`.`product_name` AS `product_name`,`kitchenorder`.`category_name` AS `category_name`,`kitchenorder`.`type` AS `type`,`kitchenorder`.`product_num` AS `product_num`,`kitchenorder`.`takeaway_id` AS `takeaway_id`,`kitchenorder`.`turnover_id` AS `turnover_id`,`kitchenorder`.`table_id` AS `table_id` from `kitchenorder` where (`kitchenorder`.`type` = 2)) */;

/*View structure for view hotdish */

/*!50001 DROP TABLE IF EXISTS `hotdish` */;
/*!50001 DROP VIEW IF EXISTS `hotdish` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`dbg0flj3fz9u2145`@`%` SQL SECURITY DEFINER VIEW `hotdish` AS (select `kitchenorder`.`order_id` AS `order_id`,`kitchenorder`.`count` AS `count`,`kitchenorder`.`product_id` AS `product_id`,`kitchenorder`.`status` AS `status`,`kitchenorder`.`created` AS `created`,`kitchenorder`.`category_id` AS `category_id`,`kitchenorder`.`product_name` AS `product_name`,`kitchenorder`.`category_name` AS `category_name`,`kitchenorder`.`type` AS `type`,`kitchenorder`.`product_num` AS `product_num`,`kitchenorder`.`takeaway_id` AS `takeaway_id`,`kitchenorder`.`turnover_id` AS `turnover_id`,`kitchenorder`.`table_id` AS `table_id` from `kitchenorder` where (`kitchenorder`.`type` = 1)) */;

/*View structure for view kitchenorder */

/*!50001 DROP TABLE IF EXISTS `kitchenorder` */;
/*!50001 DROP VIEW IF EXISTS `kitchenorder` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`dbg0flj3fz9u2145`@`%` SQL SECURITY DEFINER VIEW `kitchenorder` AS (select `g`.`id` AS `order_id`,`g`.`count` AS `count`,`g`.`product_id` AS `product_id`,`g`.`status` AS `status`,`g`.`created` AS `created`,`g`.`served` AS `served`,`p`.`category_id` AS `category_id`,`pm`.`name` AS `product_name`,`cm`.`name` AS `category_name`,`p`.`type` AS `type`,`p`.`product_num` AS `product_num`,`t`.`takeaway_id` AS `takeaway_id`,`t`.`id` AS `turnover_id`,`t`.`table_id` AS `table_id` from (((`order` `g` join (`product` `p` left join `multilang` `pm` on(((`pm`.`id` = concat('p',`p`.`id`)) and (`pm`.`locale` = 'cn'))))) join (`category` `c` left join `multilang` `cm` on(((`cm`.`id` = concat('c',`c`.`id`)) and (`cm`.`locale` = 'cn'))))) join `turnover` `t`) where ((`g`.`turnover_id` = `t`.`id`) and (`g`.`product_id` = `p`.`id`) and (`p`.`category_id` = `c`.`id`) and (`g`.`status` > 0) and (`t`.`table_id` > 0)) order by `g`.`status` desc,`g`.`created`) */;

/*View structure for view mergedorder */

/*!50001 DROP TABLE IF EXISTS `mergedorder` */;
/*!50001 DROP VIEW IF EXISTS `mergedorder` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`dbg0flj3fz9u2145`@`%` SQL SECURITY DEFINER VIEW `mergedorder` AS (select sum(`o`.`count`) AS `count`,`o`.`turnover_id` AS `turnover_id`,`o`.`product_id` AS `product_id`,max(`o`.`status`) AS `status`,min(`o`.`created`) AS `created` from (`order` `o` join `turnover` `t`) where ((`o`.`status` > 0) and (`t`.`id` = `o`.`turnover_id`) and (`t`.`table_id` > 0)) group by `o`.`turnover_id`,`o`.`product_id`) */;

/*View structure for view servedorder */

/*!50001 DROP TABLE IF EXISTS `servedorder` */;
/*!50001 DROP VIEW IF EXISTS `servedorder` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`dbg0flj3fz9u2145`@`%` SQL SECURITY DEFINER VIEW `servedorder` AS (select `o`.`id` AS `order_id`,`o`.`count` AS `count`,`o`.`product_id` AS `product_id`,`pm`.`name` AS `product_name`,`cm`.`name` AS `category_name`,`o`.`updated` AS `updated`,`o`.`created` AS `created`,`p`.`category_id` AS `category_id`,`p`.`type` AS `type`,`p`.`product_num` AS `product_num`,`t`.`takeaway_id` AS `takeaway_id`,`t`.`id` AS `turnover_id`,`t`.`table_id` AS `table_id`,`o`.`served` AS `served` from ((((`product` `p` left join `multilang` `pm` on(((`pm`.`id` = concat('p',`p`.`id`)) and (`pm`.`locale` = 'cn')))) join (`category` `c` left join `multilang` `cm` on(((`cm`.`id` = concat('c',`c`.`id`)) and (`cm`.`locale` = 'cn'))))) join `order` `o`) join `turnover` `t`) where ((`o`.`turnover_id` = `t`.`id`) and (`o`.`product_id` = `p`.`id`) and (`p`.`category_id` = `c`.`id`) and (`o`.`status` = 0) and (`t`.`table_id` > 0)) order by `o`.`served` desc limit 0,20) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
