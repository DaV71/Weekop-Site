-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema weekop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema weekop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `weekop` DEFAULT CHARACTER SET utf8 ;
USE `weekop` ;

-- -----------------------------------------------------
-- Table `weekop`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weekop`.`user` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(60) NOT NULL,
  `is_active` TINYINT(1) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 38
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `weekop`.`discovery`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weekop`.`discovery` (
  `discovery_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` VARCHAR(250) NOT NULL,
  `url` VARCHAR(200) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `up_vote` INT(11) NOT NULL,
  `down_vote` INT(11) NOT NULL,
  `num_of_comments` INT(11) NOT NULL,
  PRIMARY KEY (`discovery_id`, `user_id`),
  UNIQUE INDEX `discovery_id_UNIQUE` (`discovery_id` ASC) VISIBLE,
  UNIQUE INDEX `url_UNIQUE` (`url` ASC) VISIBLE,
  INDEX `fk_discovery_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_discovery_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `weekop`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 122
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `weekop`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weekop`.`comment` (
  `comment_id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(250) NOT NULL,
  `com_date` TIMESTAMP NOT NULL,
  `discovery_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`comment_id`, `discovery_id`, `user_id`),
  UNIQUE INDEX `comment_id_UNIQUE` (`comment_id` ASC) VISIBLE,
  INDEX `fk_comment_discovery1_idx` (`discovery_id` ASC) VISIBLE,
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_discovery1`
    FOREIGN KEY (`discovery_id`)
    REFERENCES `weekop`.`discovery` (`discovery_id`),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `weekop`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 19
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `weekop`.`role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weekop`.`role` (
  `role_name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(120) NULL DEFAULT NULL,
  PRIMARY KEY (`role_name`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `weekop`.`user_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weekop`.`user_role` (
  `role_name` VARCHAR(45) NOT NULL DEFAULT 'user',
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_name`, `username`),
  INDEX `fk_role_has_user_role1_idx` (`role_name` ASC) VISIBLE,
  INDEX `fk_user_role_user_username_idx` (`username` ASC) VISIBLE,
  CONSTRAINT `fk_role_has_user_role1`
    FOREIGN KEY (`role_name`)
    REFERENCES `weekop`.`role` (`role_name`),
  CONSTRAINT `fk_user_role_user_username`
    FOREIGN KEY (`username`)
    REFERENCES `weekop`.`user` (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `weekop`.`vote`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `weekop`.`vote` (
  `vote_id` INT(11) NOT NULL AUTO_INCREMENT,
  `discovery_id` INT(11) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `date` TIMESTAMP NOT NULL,
  `type` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`vote_id`, `discovery_id`, `user_id`),
  INDEX `fk_user_has_discovery_discovery1_idx` (`discovery_id` ASC) VISIBLE,
  INDEX `fk_user_has_discovery_user1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_has_discovery_discovery1`
    FOREIGN KEY (`discovery_id`)
    REFERENCES `weekop`.`discovery` (`discovery_id`),
  CONSTRAINT `fk_user_has_discovery_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `weekop`.`user` (`user_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 22
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
