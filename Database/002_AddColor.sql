	    /****** Object:  ForeignKey [FK_TechnologyAssessment_RadarRings]    Script Date: 03/12/2015 12:16:42 ******/
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Hold', 0);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Assess', 1);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Trial', 2);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Adopt', 3);

INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Techniques', '#8FA227', 90);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Tools', '#587486', 0);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Platforms', '#DC6F1D', 180);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Languages and Frameworks', '#B70062', 270);
