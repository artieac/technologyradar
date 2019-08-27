SET SQL_SAFE_UPDATES = 0;

RENAME TABLE `RadarTypes` TO `RadarTemplates`;
ALTER TABLE `RadarRings` CHANGE `RadarTypeId` `RadarTemplateId` BIGINT NOT NULL;
ALTER TABLE `RadarCategories` CHANGE `RadarTypeId` `RadarTemplateId` BIGINT NOT NULL;
ALTER TABLE `TechnologyAssessments` CHANGE `RadarTypeId` `RadarTemplateId` BIGINT NOT NULL;

RENAME TABLE `AssociatedRadarTypes` TO `AssociatedRadarTemplates`;
ALTER TABLE `AssociatedRadarTemplates` CHANGE `RadarTypeId` `RadarTemplateId` BIGING NOT NULL;