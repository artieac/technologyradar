/****** Object:  Table [TechnologyRadar].[RadarUser]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarTypes`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
    `RadarUserId` BIGINT NOT NULL,
	`Name` NVARCHAR(100) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarType_Id` (`Id` ASC));

ALTER TABLE `RadarTypes`  ADD  CONSTRAINT `FK_RadarTypes_RadarUser` FOREIGN KEY(`RadarUserId`)
REFERENCES `RadarUser` (`Id`);

INSERT INTO `RadarTypes` (Name, RadarUserId) VALUES ('Technology Radar',1);

ALTER TABLE `RadarRings` ADD COLUMN `RadarTypeId` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarRings`  ADD  CONSTRAINT `FK_RadarRings_RadarTypes` FOREIGN KEY(`RadarTypeId`)
REFERENCES `RadarTypes` (`Id`);

ALTER TABLE `RadarCategories` ADD COLUMN `RadarTypeId` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarCategories`  ADD  CONSTRAINT `FK_RadarCategories_RadarTypes` FOREIGN KEY(`RadarTypeId`)
REFERENCES `RadarTypes` (`Id`);
