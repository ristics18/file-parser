-- -----------------------------------------------------
-- Create database `accesslog`
-- -----------------------------------------------------

CREATE DATABASE IF NOT EXISTS accesslog;

USE accesslog;

-- -----------------------------------------------------
-- Create table `log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DateTime` datetime(3) NOT NULL,
  `IP` varchar(45) NOT NULL,
  `Request` varchar(45) NOT NULL,
  `Status` int(11) NOT NULL,
  `UserAgent` longtext NOT NULL,
   primary key (`ID`)
);

-- -----------------------------------------------------
-- Create table `blocked`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blocked`;
CREATE TABLE `blocked` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IP` varchar(45) NOT NULL,
  `Comment` longtext NOT NULL,
   primary key (`ID`)
);

-- -----------------------------------------------------
-- Create stored procedure `truncate_tables`
-- -----------------------------------------------------
DROP PROCEDURE IF EXISTS `truncate_tables`;
DELIMITER $$

CREATE PROCEDURE `truncate_tables`()
BEGIN
    TRUNCATE TABLE accesslog.log;
    TRUNCATE TABLE accesslog.blocked;
END
