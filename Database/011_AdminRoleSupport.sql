SET SQL_SAFE_UPDATES = 0;

ALTER TABLE `RadarTypes` ADD COLUMN `CreateDate` TIMESTAMP NOT NULL DEFAULT current_timestamp;

ALTER TABLE `RadarTypes` ADD COLUMN `Version` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarRings` DROP FOREIGN KEY `FK_RadarRings_RadarTypes`;
ALTER TABLE `RadarCategories` DROP FOREIGN KEY `FK_RadarCategories_RadarTypes`;

ALTER TABLE `RadarTypes` DROP INDEX IX_RadarType_Id;

alter table `RadarTypes` drop primary key, add primary key(Id, Version);

ALTER TABLE `RadarTypes` MODIFY COLUMN `Id` NVARCHAR(36) NOT NULL;

UPDATE `RadarTypes` SET Id = 'e9b8779a-3452-47b9-ab49-600162eace3c' WHERE Id = '1';
UPDATE `RadarTypes` SET Id = 'a2330b55-164a-49a0-a883-56d46c34a399' WHERE Id = '2';

CREATE UNIQUE INDEX IX_RadarType_Id_Version ON RadarTypes(Id, Version);

ALTER TABLE `RadarRings` ADD COLUMN `RadarTypeVersion` BIGINT NOT NULL DEFAULT 1;
ALTER TABLE `RadarRings` MODIFY COLUMN `RadarTypeId` NVARCHAR(36) NOT NULL;

UPDATE `RadarRings` SET RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' Where RadarTypeId = '1';
UPDATE `RadarRings` SET RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' Where RadarTypeId = '2';

ALTER TABLE `RadarRings`  ADD  CONSTRAINT `FK_RadarRings_RadarTypes2` FOREIGN KEY(`RadarTypeId`, `RadarTypeVersion`)
REFERENCES `RadarTypes` (`Id`, `Version`);

ALTER TABLE `RadarCategories` ADD COLUMN `RadarTypeVersion` BIGINT NOT NULL DEFAULT 1;
ALTER TABLE `RadarCategories` MODIFY COLUMN `RadarTypeId` NVARCHAR(36) NOT NULL;

UPDATE `RadarCategories` SET RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' Where RadarTypeId = '1';
UPDATE `RadarCategories` SET RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' Where RadarTypeId = '2';

ALTER TABLE `RadarCategories`  ADD  CONSTRAINT `FK_RadarCategories_RadarTypes2` FOREIGN KEY(`RadarTypeId`, `RadarTypeVersion`)
REFERENCES `RadarTypes` (`Id`, `Version`);

ALTER TABLE `RadarUser` ADD COLUMN `UserType` INT NOT NULL DEFAULT 0;

ALTER TABLE `TechnologyAssessments` ADD COLUMN `RadarTypeVersion` BIGINT NOT NULL DEFAULT 1;
ALTER TABLE `TechnologyAssessments` MODIFY COLUMN `RadarTypeId` NVARCHAR(36)  NOT NULL;

UPDATE `TechnologyAssessments` SET RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' Where RadarTypeId = '1';
UPDATE `TechnologyAssessments` SET RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' Where RadarTypeId = '2';

ALTER TABLE `AssociatedRadarTypes` ADD COLUMN `RadarTypeVersion` BIGINT NOT NULL DEFAULT 1;
ALTER TABLE `AssociatedRadarTypes` MODIFY COLUMN `RadarTypeId` NVARCHAR(36)  NOT NULL;

UPDATE `AssociatedRadarTypes` SET RadarTypeId = 'e9b8779a-3452-47b9-ab49-600162eace3c' Where RadarTypeId = '1';
UPDATE `AssociatedRadarTypes` SET RadarTypeId = 'a2330b55-164a-49a0-a883-56d46c34a399' Where RadarTypeId = '2';
