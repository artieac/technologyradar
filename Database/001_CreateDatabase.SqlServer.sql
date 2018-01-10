USE [TechnologyRadar]
/****** Object:  Table [TechnologyRadar].[Technology]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE [dbo].[Technology](
	[Id] BIGINT IDENTITY(1,1) NOT NULL,
	[Name] nvarchar(512) NOT NULL,
	[CreateDate] datetime NOT NULL,
	[Creator] nvarchar(255) NOT NULL,
	[RadarCategoryId] BIGINT NOT NULL,
	[Description] nvarchar(MAX),
	[Url] nvarchar(1024),
	CONSTRAINT [PK_Technology_Id] PRIMARY KEY CLUSTERED
	(
		[Id] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [TechnologyRadar].[RadarCategories]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE [RadarCategories](
	[Id] BIGINT IDENTITY(1,1) NOT NULL,
	[Name] nvarchar(512) NOT NULL,
	[Color] nvarchar(8) NOT NULL,
	[QuadrantStart] Integer NOT NULL,
	CONSTRAINT [PK_RadarCategories_Id] PRIMARY KEY CLUSTERED
	(
		[Id] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [TechnologyRadar].[RadarRing]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE [RadarRings](
	[Id] BIGINT IDENTITY(1,1) NOT NULL,
	[Name] nvarchar(512) NOT NULL,
	[DisplayOrder] INTEGER NOT NULL,
	CONSTRAINT [PK_RadarRings_Id] PRIMARY KEY CLUSTERED
	(
		[Id] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [TechnologyRadar].[AssessmentTeams]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE [AssessmentTeams](
	[Id] BIGINT IDENTITY(1,1) NOT NULL,
	[Name] nvarchar(512) NOT NULL,
	CONSTRAINT [PK_AssessmentTeams_Id] PRIMARY KEY CLUSTERED
	(
		[Id] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [TechnologyRadar].[Radar]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE [TechnologyAssessments](
	[Id] BIGINT IDENTITY(1,1) NOT NULL,
	[AssessmentTeamId] BIGINT NOT NULL,
	[Name] nvarchar(512) NOT NULL,
	[AssessmentDate] datetime NOT NULL,
	CONSTRAINT [PK_TechnologyAssessments_Id] PRIMARY KEY CLUSTERED
	(
		[Id] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

/****** Object:  Table [TechnologyRadar].[TechnologyAssessmentItems]    Script Date: 03/12/2015 12:16:42 ******/
CREATE TABLE [TechnologyAssessmentItems](
	[Id] BIGINT IDENTITY(1,1) NOT NULL,
	[TechnologyAssessmentId] BIGINT NOT NULL,
	[TechnologyId] BIGINT NOT NULL,
	[Assessor] nvarchar(255) NOT NULL,
	[Details] nvarchar(MAX) NOT NULL,
	[RadarRingId] BIGINT NOT NULL,
	[ConfidenceFactor] INTEGER NOT NULL DEFAULT 5,
	CONSTRAINT [PK_TechnologyAssessmentItems_Id] PRIMARY KEY CLUSTERED
	(
		[Id] ASC
	)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

    /****** Object:  ForeignKey [FK_TechnologyAssessment_AssessmentTeam]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE [TechnologyAssessments]  ADD  CONSTRAINT [FK_TechnologyAssessments_AssessmentTeams] FOREIGN KEY(AssessmentTeamId)
REFERENCES AssessmentTeams(Id);

    /****** Object:  ForeignKey [FK_TechnologyAssessmentItems_AssessmentTeam]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE [TechnologyAssessmentItems]  ADD  CONSTRAINT [FK_TechnologyAssessmentItems_RadarRings] FOREIGN KEY(RadarRingId)
REFERENCES RadarRings(Id);

    /****** Object:  ForeignKey [FK_Technology_RadarCategories]    Script Date: 03/12/2015 12:16:42 ******/
ALTER TABLE [Technology]  ADD  CONSTRAINT [FK_Technology_RadarCategories] FOREIGN KEY(RadarCategoryId)
REFERENCES RadarCategories(Id);
