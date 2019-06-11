/****** Object:  Table [TechnologyRadar].[RadarUser]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarTypes`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
    `RadarUserId` BIGINT NOT NULL,
	`Name` NVARCHAR(100) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarType_Id` (`Id` ASC));

INSERT INTO `RadarTypes` (Name, RadarUserId) VALUES ('Technology Radar',1);

ALTER TABLE `TechnologyAssessments` ADD COLUMN `RadarTypeId` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `TechnologyAssessments` ADD CONSTRAINT `FK_TechnologyAssessments_RadarTypes` FOREIGN KEY(`RadarTypeId`)
REFERENCES `RadarTypes`(`Id`);

ALTER TABLE `RadarRings` ADD COLUMN `RadarTypeId` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarRings`  ADD  CONSTRAINT `FK_RadarRings_RadarTypes` FOREIGN KEY(`RadarTypeId`)
REFERENCES `RadarTypes` (`Id`);

ALTER TABLE `RadarCategories` ADD COLUMN `RadarTypeId` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarCategories`  ADD  CONSTRAINT `FK_RadarCategories_RadarTypes` FOREIGN KEY(`RadarTypeId`)
REFERENCES `RadarTypes` (`Id`);

ALTER TABLE `RadarCategories` DROP COLUMN `QuadrantStart`;

ALTER TABLE `TechnologyAssessmentItems` ADD COLUMN `RadarCategoryId` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `TechnologyAssessmentItems` ADD CONSTRAINT `FK_TechnologyAssessmentItems_RadarCategories` FOREIGN KEY(`RadarCategoryId`)
REFERENCES `RadarCategories`(`Id`);

SET SQL_SAFE_UPDATES = 0;

UPDATE
    TechnologyAssessmentItems TAI,
    Technology T
SET
    TAI.RadarCategoryID = T.RadarCategoryId
WHERE
    TAI.TechnologyId = T.Id;


SET SQL_SAFE_UPDATES = 1;

ALTER TABLE `Technology` DROP FOREIGN KEY `FK_Technology_RadarCategories`;

ALTER TABLE `Technology` DROP COLUMN `RadarCategoryId`;

CREATE TABLE `AssociatedRadarTypes`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
    `RadarUserId` BIGINT NOT NULL,
	`RadarTypeId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_AssociatedRadarTypes_Id` (`Id` ASC));

ALTER TABLE `RadarTypes` ADD COLUMN IsPublished bit NOT NULL DEFAULT 0;
