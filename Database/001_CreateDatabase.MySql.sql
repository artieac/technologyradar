/****** Object:  Table [TechnologyRadar].[Technology]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `Technology`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	`CreateDate` datetime NOT NULL,
	`Creator` nvarchar(255) NOT NULL,
	`RadarCategoryId` BIGINT NOT NULL,
	`Description` nvarchar(4096),
	`Url` nvarchar(1024),
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_Technology_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[RadarCategories]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarCategories`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	`Color` nvarchar(8) NOT NULL,
	`QuadrantStart` INTEGER NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarCategories_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[RadarRing]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarRings`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	`DisplayOrder` INTEGER NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarRings_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[AssessmentTeams]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `AssessmentTeams`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_AssessmentTeams_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[Radar]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `TechnologyAssessments`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`AssessmentTeamId` BIGINT NOT NULL,
	`Name` nvarchar(512) NOT NULL,
	`AssessmentDate` datetime NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_TechnologyAssessmenst_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[TechnologyAssessmentItems]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `TechnologyAssessmentItems`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`TechnologyAssessmentId` BIGINT NOT NULL,
	`TechnologyId` BIGINT NOT NULL,
	`Assessor` nvarchar(255) NOT NULL,
	`Details` nvarchar(4096) NOT NULL,
	`RadarRingId` BIGINT NOT NULL,
	`ConfidenceFactor` INTEGER NOT NULL DEFAULT 5,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `ID_TechnologyAssessmentItems_Id` (`Id` ASC));

    /****** Object:  ForeignKey [FK_TechnologyAssessment_AssessmentTeam]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `TechnologyAssessments`  ADD  CONSTRAINT `FK_TechnologyAssessments_AssessmentTeams` FOREIGN KEY(`AssessmentTeamId`)
REFERENCES `AssessmentTeams` (`Id`);

    /****** Object:  ForeignKey [FK_TechnologyAssessmentItems_AssessmentTeam]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `TechnologyAssessmentItems`  ADD  CONSTRAINT `FK_TechnologyAssessmentItems_RadarRings` FOREIGN KEY(`RadarRingId`)
REFERENCES `RadarRings` (`Id`);

    /****** Object:  ForeignKey [FK_Technology_RadarCategories]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `Technology`  ADD  CONSTRAINT `FK_Technology_RadarCategories` FOREIGN KEY(`RadarCategoryId`)
REFERENCES `RadarCategories` (`Id`);
