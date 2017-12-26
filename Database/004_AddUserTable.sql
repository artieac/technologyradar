/****** Object:  Table [RadarUser].[Technology]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `RadarUser`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`AuthenticationId` nvarchar(512) NOT NULL,
	`RoleId` int NOT NULL,
	`Authority` nvarchar(256) NOT NULL,
	`Issuer` nvarchar(1024) NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_User_Id` (`Id` ASC));

