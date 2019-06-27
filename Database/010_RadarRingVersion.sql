ALTER TABLE `RadarTypes` ADD COLUMN `CreateDate` TIMESTAMP NOT NULL DEFAULT current_timestamp;

ALTER TABLE `RadarTypes` ADD COLUMN `Version` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarRings` DROP FOREIGN KEY `FK_RadarRings_RadarTypes`;
ALTER TABLE `RadarCategories` DROP FOREIGN KEY `FK_RadarCategories_RadarTypes`;

ALTER TABLE `RadarTypes` DROP INDEX IX_RadarType_Id;

alter table `RadarTypes` drop primary key, add primary key(Id, Version);

CREATE UNIQUE INDEX IX_RadarType_Id_Version ON RadarTypes(Id, Version);

ALTER TABLE `RadarRings` ADD COLUMN `RadarTypeVersion` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarRings`  ADD  CONSTRAINT `FK_RadarRings_RadarTypes2` FOREIGN KEY(`RadarTypeId`, `RadarTypeVersion`)
REFERENCES `RadarTypes` (`Id`, `Version`);

ALTER TABLE `RadarCategories` ADD COLUMN `RadarTypeVersion` BIGINT NOT NULL DEFAULT 1;

ALTER TABLE `RadarCategories`  ADD  CONSTRAINT `FK_RadarCategories_RadarTypes2` FOREIGN KEY(`RadarTypeId`, `RadarTypeVersion`)
REFERENCES `RadarTypes` (`Id`, `Version`);

ALTER TABLE `RadarUser` ADD COLUMN `Role` BIGINT NOT NULL DEFAULT 0;

ALTER TABLE `RadarUser` ADD COLUMN `UserType` BIGINT NOT NULL DEFAULT 0;