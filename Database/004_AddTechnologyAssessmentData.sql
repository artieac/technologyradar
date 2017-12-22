INSERT INTO AssessmentTeams (Name) VALUES ("PaCT");

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Chaos Monkey', '2001-01-01', 'Jon Charette');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Test In Prod', '2001-01-01', 'Artie');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Kubernetes', '2001-01-01', 'Jeff Kwan');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Spring Boot', '2001-01-01', 'Jon Hines');

INSERT INTO TechnologyAssessments (AssessmentTeamId, Name, AssessmentDate) VALUES (1, 'PaCT May 2016', '2016-05-01');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 1, 1, 1, 'Jon Charette', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 2, 2, 2, 'Jon Charette', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 3, 3, 3, 'Jon Charette', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 4, 4, 4, 'Jon Charette', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Phoenix Environments', '2016-05-01', 'Jeff Kwan');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Mac Dev Stations', '2016-05-01', 'Jon Hines');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Monitoring of Invariants', '2016-05-01', 'Dave Miller');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('True Scientific Method', '2016-05-01', 'Dave Miller');

UPDATE TechnologyAssessmentItems SET RadarStateId = 3, RadarCategoryId = 2 WHERE Id = 1;
UPDATE TechnologyAssessmentItems SET RadarStateId = 3, RadarCategoryId = 2 WHERE Id = 2;
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 5, 2, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 6, 2, 3, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 7, 2, 3, 'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 8, 2, 3, 'Dave Miller', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Reactive Architecture', '2016-05-01', 'Rajul Raheja');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Domain Driven Design', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Behavior Driven Development', '2016-05-01', 'Chris Bova');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Test Driven Development', '2016-05-01', 'Jon Hines');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Dev Ops', '2016-05-01', 'Jeff Kwan');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Hateos', '2016-05-01', 'James Hart');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Design Thinking', '2016-05-01', 'James Hart');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 9, 2, 2, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 10, 2, 2, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 11, 2, 2, 'Chris Bova', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 12, 2, 2, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 13, 2, 2, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 14, 2, 2, 'James Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 15, 2, 2, 'James Hart', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('API First Design', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 16, 2, 1, 'Jeff Kwan', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Jenkins as Deployment Pipeline', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 17, 1, 4, 'Jeff Kwan', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Eureka', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Central Authentication Service', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('RAML', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Chocolatey for Windows Development Stations', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Prometheus', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Parasoft', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Zipkin', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Ansible', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Scientist', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 18, 1, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 19, 1, 3, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 20, 1, 3, 'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 21, 1, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 22, 1, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 23, 1, 3, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 24, 1, 3, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 25, 1, 3, 'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 26, 1, 3, 'Dave Miller', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Technology Radar', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Slack', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Octopus', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Hipchat', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Gradle', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Jenkins', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Dapper', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Restito', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 27, 1, 2, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 28, 1, 2, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 29, 1, 2, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 30, 1, 2, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 31, 1, 2, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 32, 1, 2, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 33, 1, 2, 'James Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 34, 1, 2, 'James Hart', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Nothing Adopted', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 35, 1, 4, 'Artie Correa', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Elastic Bean Stalk', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Mesos', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Amazon API Gateway', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Azure', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Google Cloud', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Postgres', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Oracle', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Neo4j', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('MySql', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Cassandara', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Drop Wizard', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Heroku', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Open Shift', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Windows 10', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 36, 3, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 37, 3, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 38, 3, 3, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 39, 3, 3, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 40, 3, 3, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 41, 3, 3, 'James Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 42, 3, 3, 'James Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 43, 3, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 44, 3, 3, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 45, 3, 3, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 46, 3, 3, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 47, 3, 3, 'Jamse Hart', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 48, 3, 3, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 49, 3, 3, 'Jeff Kwan', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Open Stack', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Docker', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('AWS EC2', '2016-05-01', 'Artie Correa');

UPDATE TechnologyAssessmentItems SET RadarStateId = 2, RadarCategoryId = 3 WHERE Id = 3;
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 50, 3, 2, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 51, 3, 2, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 52, 3, 2, 'Jeff Kwan', 'Some stuff');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 35, 3, 4, 'Artie Correa', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('NHibernate', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 53, 4, 4, 'Jeff Kwan', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Scala', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('.Net Core', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Go for Microservices', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('F#', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Postman w/Jetpacks', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Auth0', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 54, 4, 3, 'Chris Dufresne', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 55, 4, 3, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 56, 4, 3, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 57, 4, 3, 'Rajul Raheja', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 58, 4, 3, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 59, 4, 3, 'Artie Correa', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Cucumber', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Play', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Akka', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 60, 4, 2, 'Jeff Kwan', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 61, 4, 2, 'Dave Miller', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 62, 4, 2, 'Chris Dufresne', 'Some stuff');

UPDATE TechnologyAssessmentItems SET RadarStateId = 0, RadarCategoryId = 4 WHERE Id = 4;

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Node.js', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Locus.io for performance testing', '2016-05-01', 'Artie Correa');
INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Mockito', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 63, 4, 1, 'Chris Dufresne', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 64, 4, 1, 'Jon Hines', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 65, 4, 1, 'James Hart', 'Some stuff');

INSERT INTO Technology (Name, CreateDate, Creator) VALUES ('Nothing Held', '2016-05-01', 'Artie Correa');

INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 66, 1, 1, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 66, 3, 1, 'Artie Correa', 'Some stuff');
INSERT INTO TechnologyAssessmentItems (TechnologyAssessmentId, TechnologyId, RadarCategoryId, RadarStateId, Assessor, Details) VALUES (1, 35, 2, 4, 'Artie Correa', 'Some stuff');
