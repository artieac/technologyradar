ALTER TABLE TechnologyAssessments DROP FOREIGN KEY FK_TechnologyAssessments_AssessmentTeams;

ALTER TABLE TechnologyAssessments DROP AssessmentTeamId;
ALTER TABLE TechnologyAssessments ADD RadarUserId BIGINT NOT NULL;

ALTER TABLE TechnologyAssessments  ADD  CONSTRAINT `FK_TechnologyAssessments_RadarUsers` FOREIGN KEY(`RadarUserId`)
REFERENCES `RadarUser` (`Id`);
