CREATE USER 'techradaruser'@'%' IDENTIFIED BY 'ILikeTheTechRadar';

GRANT SELECT ON TechRadarDB.* TO 'techradaruser'@'%';
GRANT UPDATE ON TechRadarDB.* TO 'techradaruser'@'%';
GRANT INSERT ON TechRadarDB.* TO 'techradaruser'@'%';
GRANT DELETE ON TechRadarDB.* TO 'techradaruser'@'%';
GRANT EXECUTE ON TechRadarDB.* TO 'techradaruser'@'%';