SET SQL_SAFE_UPDATES = 0;

ALTER TABLE `RadarRings` DROP FOREIGN KEY `FK_RadarRings_RadarTypes`;
ALTER TABLE `RadarRings` DROP FOREIGN KEY `FK_RadarRings_RadarTypes`;
ALTER TABLE `RadarRings` DROP FOREIGN KEY `FK_RadarRings_RadarTypes`;
ALTER TABLE `RadarRings` DROP FOREIGN KEY `FK_RadarRings_RadarTypes2`;
ALTER TABLE `RadarCategories` DROP FOREIGN KEY `FK_RadarCategories_RadarTypes2`;

ALTER TABLE `RadarTypes` DROP INDEX IX_RadarType_Id_Version;

/****** Object:  Table [TechnologyRadar].[RadarUser]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `NewRadarTypes`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
    `RadarUserId` BIGINT NOT NULL,
	`Name` NVARCHAR(50) NOT NULL,
    `Description` NVARCHAR(1024) NOT NULL,
    `IsPublished` BIT NOT NULL DEFAULT 0,
    `CreateDate` TIMESTAMP NOT NULL DEFAULT current_timestamp,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarType_Id` (`Id` ASC));
    
INSERT INTO `NewRadarTypes` (RadarUserId, Name, Description, IsPublished) 
	SELECT RadarUserId, Name, Description, IsPublished FROM RadarTypes 
    WHERE Id='a2330b55-164a-49a0-a883-56d46c34a399' AND VERSION=1;
    
INSERT INTO `NewRadarTypes` (RadarUserId, Name, Description, IsPublished) 
	SELECT RadarUserId, Name, Description, IsPublished FROM RadarTypes 
    WHERE Id='a2330b55-164a-49a0-a883-56d46c34a399' AND VERSION=2;

INSERT INTO `NewRadarTypes` (RadarUserId, Name, Description, IsPublished) 
	SELECT RadarUserId, Name, Description, IsPublished FROM RadarTypes 
    WHERE Id='e9b8779a-3452-47b9-ab49-600162eace3c' AND VERSION=1;
    
ALTER TABLE `RadarRings` ADD COLUMN `NewRadarTypeId` BIGINT;

UPDATE `RadarRings` SET NewRadarTypeId = 1 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 1;
UPDATE `RadarRings` SET NewRadarTypeId = 2 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 2;
UPDATE `RadarRings` SET NewRadarTypeId = 3 WHERE RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' AND RadarTypeVersion = 1;

ALTER TABLE `RadarRings` DROP COLUMN RadarTypeVersion;
ALTER TABLE `RadarRings` DROP COLUMN RadarTypeId;
ALTER TABLE `RadarRings` CHANGE `NewRadarTypeId` `RadarTypeId` BIGINT NOT NULL;

ALTER TABLE `RadarCategories` ADD COLUMN `NewRadarTypeId` BIGINT;

UPDATE `RadarCategories` SET NewRadarTypeId = 1 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 1;
UPDATE `RadarCategories` SET NewRadarTypeId = 2 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 2;
UPDATE `RadarCategories` SET NewRadarTypeId = 3 WHERE RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' AND RadarTypeVersion = 1;

ALTER TABLE `RadarCategories` DROP COLUMN RadarTypeVersion;
ALTER TABLE `RadarCategories` DROP COLUMN RadarTypeId;
ALTER TABLE `RadarCategories` CHANGE `NewRadarTypeId` `RadarTypeId` BIGINT NOT NULL;

ALTER TABLE `TechnologyAssessments` ADD COLUMN `NewRadarTypeId` BIGINT;

UPDATE `TechnologyAssessments` SET NewRadarTypeId = 1 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 1;
UPDATE `TechnologyAssessments` SET NewRadarTypeId = 2 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 2;
UPDATE `TechnologyAssessments` SET NewRadarTypeId = 3 WHERE RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' AND RadarTypeVersion = 1;

ALTER TABLE `TechnologyAssessments` DROP COLUMN RadarTypeVersion;
ALTER TABLE `TechnologyAssessments` DROP COLUMN RadarTypeId;
ALTER TABLE `TechnologyAssessments` CHANGE `NewRadarTypeId` `RadarTypeId` BIGINT NOT NULL;

ALTER TABLE `AssociatedRadarTypes` ADD COLUMN `NewRadarTypeId` BIGINT;

UPDATE `AssociatedRadarTypes` SET NewRadarTypeId = 1 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 1;
UPDATE `AssociatedRadarTypes` SET NewRadarTypeId = 2 WHERE RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' AND RadarTypeVersion = 2;
UPDATE `AssociatedRadarTypes` SET NewRadarTypeId = 3 WHERE RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' AND RadarTypeVersion = 1;

ALTER TABLE `AssociatedRadarTypes` DROP COLUMN RadarTypeVersion;
ALTER TABLE `AssociatedRadarTypes` DROP COLUMN RadarTypeId;
ALTER TABLE `AssociatedRadarTypes` CHANGE `NewRadarTypeId` `RadarTypeId` BIGINT NOT NULL;

DROP TABLE `RadarTypes`;
RENAME TABLE `NewRadarTypes` TO `RadarTypes`;

ALTER TABLE `RadarTypes` ADD COLUMN `State` INTEGER NOT NULL DEFAULT 1;