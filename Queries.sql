create database database_name_test;
use database_name_test;
CREATE TABLE country (id integer, capital VARCHAR(20), country_name VARCHAR(20), PRIMARY KEY (id));
drop table country;
select * from country;
describe country;
insert into country values (1,"Delhi","India");
insert into country values (2,"Washington","USA");
insert into country values (3,"London","UK");

