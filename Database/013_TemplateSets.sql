SET SQL_SAFE_UPDATES = 0;

DROP TABLE `TeamMembers`;

CREATE TABLE `TeamMembers`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`TeamId` BIGINT NOT NULL,
	`MemberId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	INDEX `IX_TeamMember_Id` (`Id` ASC),
	INDEX `IX_Teams_TeamId` (`TeamId` ASC),
	INDEX `IX_Teams_MemberId` (`MemberId` ASC));
    
CREATE TABLE `TeamRadars`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`TeamId` BIGINT NOT NULL,
	`RadarId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	INDEX `IX_TeamRadar_Id` (`Id` ASC),
	INDEX `IX_Teams_TeamId` (`TeamId` ASC),
	INDEX `IX_Teams_RadarId` (`RadarId` ASC));

ALTER TABLE `TechnologyAssessmentItems` ADD COLUMN `CreatorId` BIGINT NOT NULL DEFAULT 1;