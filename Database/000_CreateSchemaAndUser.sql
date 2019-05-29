CREATE DATABASE TechnologyRadar;

CREATE USER 'test'@'%' identified by 'test';

GRANT INSERT, UPDATE, SELECT, DELETE, EXECUTE on TechnologyRadar.* to 'test'@'%' identified by 'test';
