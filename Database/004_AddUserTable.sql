/****** Object:  Table [User].[Technology]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE `User`(
	`Id` BIGINT NOT NULL AUTO_INCREMENT,
	`AuthenticationId` nvarchar(512) NOT NULL,
	`RoleId` int NOT NULL,
	PRIMARY KEY (`Id`),
	UNIQUE INDEX `IX_User_Id` (`Id` ASC));
