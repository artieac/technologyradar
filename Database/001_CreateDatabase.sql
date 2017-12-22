/****** Object:  Table [TechnologyRadar].[Technology]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `Technology`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	`CreateDate` datetime NOT NULL,
	`Creator` nvarchar(255) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_Technology_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[RadarCategories]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarCategories`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarCategories_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[RadarStates]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarStates`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarStates_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[AssessmentTeams]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `AssessmentTeams`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_AssessmentTeams_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[TechnologyAssessment]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `TechnologyAssessments`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`AssessmentTeamId` BIGINT NOT NULL,
	`Name` nvarchar(512) NOT NULL,
	`AssessmentDate` nvarchar(100) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_TechnologyAssessmenst_Id` (`Id` ASC));

/****** Object:  Table [TechnologyRadar].[TechnologyAssessmentItems]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `TechnologyAssessmentItems`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`TechnologyAssessmentId` BIGINT NOT NULL,
	`TechnologyId` BIGINT NOT NULL,
	`Assessor` nvarchar(255) NOT NULL,
	`Details` nvarchar(4096) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `ID_TechnologyAssessmentItems_Id` (`Id` ASC));

    /****** Object:  ForeignKey [FK_TechnologyAssessment_AssessmentTeam]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `TechnologyAssessments`  ADD  CONSTRAINT `FK_TechnologyAssessments_AssessmentTeams` FOREIGN KEY(`AssessmentTeamId`)
REFERENCES `AssessmentTeams` (`Id`);

ALTER TABLE RadarStates ADD DisplayOrder INTEGER NOT NULL;

ALTER TABLE TechnologyAssessmentItems ADD RadarStateId BIGINT NOT NULL;

    /****** Object:  ForeignKey [FK_TechnologyAssessment_AssessmentTeam]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `TechnologyAssessmentItems`  ADD  CONSTRAINT `FK_TechnologyAssessmentItems_RadarStates` FOREIGN KEY(`RadarStateId`)
REFERENCES `RadarStates` (`Id`);

ALTER TABLE TechnologyAssessmentItems ADD RadarCategoryId BIGINT NOT NULL;

    /****** Object:  ForeignKey [FK_TechnologyAssessment_RadarCategories]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `TechnologyAssessmentItems`  ADD  CONSTRAINT `FK_TechnologyAssessment_RadarCategories` FOREIGN KEY(`RadarCategoryId`)
REFERENCES `RadarCategories` (`Id`);

ALTER TABLE Technology ADD Description nvarchar(4096);
ALTER TABLE Technology ADD Url nvarchar(1024);
