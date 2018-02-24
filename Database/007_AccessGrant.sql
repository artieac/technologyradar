/****** Object:  Table [TechnologyRadar].[RadarUser]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarAccessGrants`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`UserId` BIGINT NOT NULL,
	`GrantedUserId` BIGINT NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarAccessGrants_Id` (`Id` ASC));

	    /****** Object:  ForeignKey [FK_TechnologyAssessment_AssessmentTeam]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `RadarAccessGrantee`  ADD  CONSTRAINT `FK_RadarAccessGrants_RadarUser_Owner` FOREIGN KEY(`UserId`)
REFERENCES `RadarUser` (`Id`);

ALTER TABLE `RadarAccessGrants`  ADD  CONSTRAINT `FK_RadarAccessGrants_RadarUser_Grant` FOREIGN KEY(`UserId`)
REFERENCES `RadarUser` (`Id`);
