USE TechnologyRadar;

INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Hold', 0);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Assess', 1);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Trial', 2);
INSERT INTO RadarRings (Name, DisplayOrder) VALUES ('Adopt', 3);

INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Techniques', '#8FA227', 90);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Tools', '#587486', 0);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Platforms', '#DC6F1D', 180);
INSERT INTO RadarCategories (Name, Color, QuadrantStart) VALUES ('Languages and Frameworks', '#B70062', 270);

INSERT INTO AssessmentTeams (Name) VALUES ('PaCT');

INSERT INTO TechnologyAssessments (AssessmentTeamId, Name, AssessmentDate) VALUES (1, 'PaCT May 2016', '2016-05-01');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('API First Design', '2016-05-01', 'Artie Correa', 1, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 1, 4, 9, 'Rajul Raheja', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Reactive Architecture', '2016-05-01', 'Rajul Raheja', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Domain Driven Design', '2016-05-01', 'Artie Correa', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Behavior Driven Development', '2016-05-01', 'Chris Bova', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Test Driven Development', '2016-05-01', 'Jon Hines', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Dev Ops', '2016-05-01', 'Jeff Kwan', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Hateos', '2016-05-01', 'James Hart', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Design Thinking', '2016-05-01', 'James Hart', 1, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 2, 3, 5, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 3, 3, 8, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 4, 3, 5, 'Chris Bova', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 5, 3, 5, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 6, 3, 5, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 7, 3, 5, 'James Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 8, 3, 5, 'James Hart', 'Some stuff');


INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Chaos Monkey', '2001-01-01', 'Jon Charette', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Test In Prod', '2001-01-01', 'Artie', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Phoenix Environments', '2016-05-01', 'Jeff Kwan', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Mac Dev Stations', '2016-05-01', 'Jon Hines', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Monitoring of Invariants', '2016-05-01', 'Dave Miller', 1, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('True Scientific Method', '2016-05-01', 'Dave Miller', 1, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 9, 2,  5,'Jon Charette', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 10, 2,  5,'Jon Charette', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 11, 2,  5,'Jon Charette', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 12, 2,  5,'Jon Charette', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 13, 2,  5,'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 14, 2,  5,'Dave Miller', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Technology Radar', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Slack', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Octopus', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Hipchat', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Gradle', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Jenkins', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Dapper', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Restito', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 15, 3, 5,'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 16, 3, 6, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 17, 3, 2, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 18, 3, 3,'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 19, 3, 2, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 20, 3, 2, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 21, 3, 4, 'James Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 22, 3, 10, 'James Hart', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Eureka', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Central Authentication Service', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('RAML', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Chocolatey for Windows Development Stations', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Prometheus', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Parasoft', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Zipkin', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Ansible', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Scientist', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 23, 2, 5, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 24, 2, 5, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 25, 2, 5, 'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 26, 2, 6, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 27, 2, 2, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 28, 2, 2, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 29, 2, 2, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 30, 2, 5, 'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 31, 2, 6, 'Dave Miller', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Jenkins as Deployment Pipeline', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 32, 1, 5, 'Jeff Kwan', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Open Stack', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Docker', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('AWS EC2', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Kubernetes', '2001-01-01', 'Artie Correa', 3, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 33, 3, 8, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 34, 3, 8,'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 35, 3, 5, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 36, 3, 5, 'Jeff Kwan', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Elastic Bean Stalk', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Mesos', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Azure', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Google Cloud', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Postgres', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Neo4j', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('MySql', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Cassandara', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Drop Wizard', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Heroku', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Open Shift', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Windows 10', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 37, 2, 4, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 38, 2, 2, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 39, 2, 1, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 40, 2, 1, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 41, 2, 1, 'James Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 42, 2, 1, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 43, 2, 6, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 44, 2, 2,'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 45, 2, 2, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 46, 2, 2, 'Jamse Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 47, 2, 5, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 48, 2, 5, 'Jeff Kwan', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Amazon API Gateway', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Oracle', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 49, 1, 10, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 50, 1, 10, 'James Hart', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Spring Boot', '2001-01-01', 'Jon Hines', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Node.js', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Locus.io for performance testing', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Mockito', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 51, 4, 5, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 52, 4, 5, 'Chris Dufresne', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 53, 4, 5, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 54, 4, 5, 'James Hart', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Cucumber', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Play', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Akka', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 55, 3, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 56, 3, 9, 'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 57, 3, 4, 'Chris Dufresne', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Scala', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('.Net Core', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Go for Microservices', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('F#', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Postman w/Jetpacks', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Auth0', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 58, 2, 5, 'Chris Dufresne', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 59, 2, 5, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 60, 2, 1, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 61, 2, 1, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 62, 2, 6, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 63, 2, 1, 'Artie Correa', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('NHibernate', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (1, 64, 1, 8, 'Jeff Kwan', 'Some stuff');

INSERT INTO AssessmentTeams (Name) VALUES ('Data');

INSERT INTO TechnologyAssessments (AssessmentTeamId, Name, AssessmentDate) VALUES (2, 'Data May 2016', '2016-05-01');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('SQL Replication', '2016-05-01', 'Artie Correa', 1, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 65, 4, 9, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('AWS Lambda', '2016-05-01', 'Artie Correa', 1, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 66, 3, 5, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Kyvos', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Kafka', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Hive', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Sqoop', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('SQL Server', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('SSAS', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('SCOM', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Lucid Chart', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('MDS', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 67, 4, 5,'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 68, 4, 5, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 69, 4, 5, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 70, 4, 5,'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 71, 4, 5, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 72, 4, 5, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 73, 4, 5, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 74, 4, 5, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 75, 4, 5, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('DynamoDB', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('CTR', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Bryte', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('NIFI', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Kinesis', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('HBase', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Storm', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Jenkins', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('OpenScoring', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 76, 3, 2,'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 77, 3, 8, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 78, 3, 3, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 79, 3, 4,'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 80, 3, 1, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 81, 3, 10, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 82, 3, 10, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 83, 3, 10, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 84, 3, 10, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Spark', '2016-05-01', 'Artie Correa', 2, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 85, 2, 8, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('APS', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Hortonworks Hadoop', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 85, 4, 8, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 86, 4, 2, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('AWS S3', '2016-05-01', 'Artie Correa', 3, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 87, 3, 8, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('T-SQL Boot', '2001-01-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('HIVEQL', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Java', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Javascript', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 88, 4, 9, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 89, 4, 4, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 90, 4, 5, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 91, 4, 4, 'Nidhi Khetrapal', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator, RadarCategoryId, Description, Url) VALUES ('Python', '2016-05-01', 'Artie Correa', 4, '', '');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 52, 3, 10, 'Nidhi Khetrapal', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarRingId, ConfidenceFactor, Assessor, Details) VALUES (2, 92, 3, 4, 'Dave Miller', 'Some stuff');

