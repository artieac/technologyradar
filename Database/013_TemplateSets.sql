SET SQL_SAFE_UPDATES = 0;

CREATE TABLE `RadarCategorySets`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
    `RadarUserId` BIGINT NOT NULL,
	`Name` NVARCHAR(50) NOT NULL,
	`Description` NVARCHAR(1024) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarCategorySets_Id` (`Id` ASC));

ALTER TABLE `RadarCategories` ADD COLUMN `RadarCategorySetId` BIGINT;


