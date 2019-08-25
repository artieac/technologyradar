SET SQL_SAFE_UPDATES = 0;

CREATE TABLE `RadarCategorySets`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` NVARCHAR(50) NOT NULL,
	`Description` NVARCHAR(1025) NULL,
	`RadarUserId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarCategorySets_Id` (`Id` ASC));

INSERT INTO RadarCategorySets (Name, Description, RadarUserId) VALUES ('Technology Radar Quadrants', 'The four quadrants from the Thoughtworks Technology Radar practice', 1);
INSERT INTO RadarCategorySets (Name, Description, RadarUserId) VALUES ('Walt Disney World Parks', 'A breakdown of the four theme parks at Walt Disney World', 1);

CREATE TABLE `RadarRingSets`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` NVARCHAR(50) NOT NULL,
	`Description` NVARCHAR(1025) NULL,
	`RadarUserId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarRingSets_Id` (`Id` ASC));

INSERT INTO RadarRingSets (Name, Description, RadarUserId) VALUES ('Technology Radar Rings', 'The four Radar Rings from the Thoughtworks Technology Radar practice.', 1);
INSERT INTO RadarRingSets (Name, Description, RadarUserId) VALUES ('WDW Must Dos', 'Ranking if something is worth waiting for or not.', 1);
INSERT INTO RadarRingSets (Name, Description, RadarUserId) VALUES ('Park Ride Wait Times', 'Ranking how long I would be willing to wait in line to do something at an amusement or Theme Park.', 1);

CREATE TABLE `RadarTemplates`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` NVARCHAR(50) NOT NULL,
	`Description` NVARCHAR(1025) NULL,
	`RadarUserId` BIGINT NOT NULL,
	`RadarCategorySetId` BIGINT NOT NULL,
	`RadarRingSetId` BIGINT NOT NULL, 
	`State` INT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarTemplates_Id` (`Id` ASC));

INSERT INTO RadarTemplates (Name, Description, RadarUserId, RadarCategorySetId, RadarRingSetId, State) VALUES ('Technology Radar', 'TBD', 1, 1, 1, 1);
INSERT INTO RadarTemplates (Name, Description, RadarUserId, RadarCategorySetId, RadarRingSetId, State) VALUES ('Walt Disney World Must Dos', 'TBD', 1, 2, 2, 1);
INSERT INTO RadarTemplates (Name, Description, RadarUserId, RadarCategorySetId, RadarRingSetId, State) VALUES ('Walt Disney World Wait Times', 'TBD', 1, 2, 3, 1);

ALTER TABLE `RadarRings` ADD COLUMN `RadarRingSetId` BIGINT NULL;

UPDATE RadarRings SET RadarRingSetId = 1 WHERE Id IN (1,2,3,4);
UPDATE RadarRings SET RadarRingSetId = 2 WHERE Id IN (5,6,7,8);
UPDATE RadarRings SET RadarRingSetId = 3 WHERE Id IN (9,10,11,12,13,14,15,16);

ALTER TABLE `RadarCategories` ADD COLUMN `RadarCategorySetId` BIGINT NULL;

UPDATE RadarCategories SET RadarCategorySetId = 1 WHERE Id IN (1,2,3,4);
UPDATE RadarCategories SET RadarCategorySetId = 2 WHERE Id IN (5,6,7,8);

ALTER TABLE `RadarRings` MODIFY COLUMN `RadarRingSetId` BIGINT NOT NULL;
ALTER TABLE `RadarCategories` MODIFY COLUMN `RadarCategorySetId` BIGINT NOT NULL;

ALTER TABLE `RadarRings` DROP INDEX `FK_RadarRings_RadarTypes`;
ALTER TABLE `RadarRings` DROP FOREIGN KEY `FK_RadarRings_RadarTypes2`;
ALTER TABLE `RadarRings` DROP INDEX `FK_RadarRings_RadarTypes2`;
ALTER TABLE `RadarRings` DROP COLUMN `RadarTypeId`;

ALTER TABLE `RadarCategories` DROP INDEX `FK_RadarCategories_RadarTypes`;
ALTER TABLE `RadarCategories` DROP FOREIGN KEY `FK_RadarCategories_RadarTypes2`;
ALTER TABLE `RadarCategories` DROP INDEX `FK_RadarCategories_RadarTypes2`;
ALTER TABLE `RadarCategories` DROP COLUMN `RadarTypeId`;

ALTER TABLE `TechnologyAssessments` ADD COLUMN RadarRingSetId BIGINT NULL;
ALTER TABLE `TechnologyAssessments` ADD COLUMN RadarCategorySetId BIGINT NULL;

UPDATE `TechnologyAssessments` SET `RadarRingSetId` = 1 WHERE ID IN (1,3,4,9);
UPDATE `TechnologyAssessments` SET `RadarRingSetId` = 2 WHERE ID IN (8);
UPDATE `TechnologyAssessments` SET `RadarRingSetId` = 3 WHERE ID IN (9);

UPDATE `TechnologyAssessments` SET `RadarCategorySetId` = 1 WHERE ID IN (1,3,4,9);
UPDATE `TechnologyAssessments` SET `RadarCategorySetId` = 2 WHERE ID IN (8, 9);

CREATE TABLE `RadarTemplates`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
    `RadarUserId` BIGINT NOT NULL,
	`Name` NVARCHAR(50) NOT NULL,
	`Description` NVARCHAR(100) NOT NULL,
	`RadarCategorySetId` BIGINT NOT NULL,
	`RadarRingSetId` BIGINT NOT NULL,
	`State` INT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarTemplate_Id` (`Id` ASC));

INSERT INTO `RadarTemplates` (Name, RadarUserId, Description, RadarCategorySetId, RadarRingSetId, State) VALUES ('Technology Radar',1, "TechnologyRadar", 1, 1, 1);
INSERT INTO `RadarTemplates` (Name, RadarUserId, Description, RadarCategorySetId, RadarRingSetId, State) VALUES ('Walt Disney World Must Dos',1, "TechnologyRadar", 2, 2, 1);
INSERT INTO `RadarTemplates` (Name, RadarUserId, Description, RadarCategorySetId, RadarRingSetId, State) VALUES ('Walt Disney World Wait Times',1, "TechnologyRadar", 2, 3, 1);


