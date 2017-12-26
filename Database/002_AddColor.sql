ALTER TABLE RadarCategories ADD Color nvarchar(8);

ALTER TABLE `TechnologyAssessmentItems`  DROP FOREIGN KEY `FK_TechnologyAssessmentItems_RadarStates` ;

DROP TABLE `RadarStates`;

/****** Object:  Table [TechnologyRadar].[RadarRings]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarRings`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`Name` nvarchar(512) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_RadarRings_Id` (`Id` ASC));
    
ALTER TABLE `TechnologyAssessmentItems` CHANGE RadarStateId RadarRingId BIGINT NOT NULL;
	
	    /****** Object:  ForeignKey [FK_TechnologyAssessment_RadarRings]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE `TechnologyAssessmentItems`  ADD  CONSTRAINT `FK_TechnologyAssessmentItems_RadarRings` FOREIGN KEY(`RadarRingId`)
REFERENCES `RadarRings` (`Id`);

ALTER TABLE `RadarRings` ADD DisplayOrder INT NOT NULL;

ALTER TABLE `TechnologyAssessmentItems` ADD ConfidenceFactor INT NOT NULL;

INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Hold', 0);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Assess', 1);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Trial', 2);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Adopt', 3);

ALTER TABLE `RadarCategories` ADD QuadrantStart INT NOT NULL;

INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Techniques', '#8FA227', 90);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Tools', '#587486', 0);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Platforms', '#DC6F1D', 180);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Languages and Frameworks', '#B70062', 270);

ALTER TABLE `Technology` ADD RadarCategoryId INT NOT NULL;
