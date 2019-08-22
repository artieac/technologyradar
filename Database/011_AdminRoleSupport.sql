SET SQL_SAFE_UPDATES = 0;

CREATE TABLE `Roles`(
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Name` NVARCHAR(100) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_Role_Id` (`Id` ASC));

INSERT INTO Roles (Name) VALUES ("User");
INSERT INTO Roles (Name) VALUES( "Admin"); 

CREATE TABLE `UserTypes`(
	`Id` INT NOT NULL AUTO_INCREMENT,
	`Name` NVARCHAR(100) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_UserType` (`Id` ASC));

INSERT INTO UserTypes (Name) VALUES ("Free");
INSERT INTO UserTypes (Name) VALUES( "Trial Subscription"); 
INSERT INTO UserTypes (Name) VALUES( "Subscribed"); 
INSERT INTO UserTypes (Name) VALUES( "Team"); 

CREATE TABLE `Teams`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` NVARCHAR(100) NOT NULL,
	`OwnerId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_Teams_Id` (`Id` ASC),
	UNIQUE INDEX `IX_Teams_OwnerId` (`OwnerId` ASC));

CREATE TABLE `TeamMembers`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`TeamId` BIGINT NOT NULL,
	`MemberId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_TeamMember_Id` (`Id` ASC),
	UNIQUE INDEX `IX_Teams_TeamId` (`TeamId` ASC),
	UNIQUE INDEX `IX_Teams_MemberId` (`MemberId` ASC));

ALTER TABLE `RadarUser` CHANGE COLUMN `UserType` `UserTypeId` INT NOT NULL;

ALTER TABLE `RadarTypes` ADD COLUMN `Description` NVARCHAR(1024);

