create user postgres with password 'pass' createdb;
grant select, insert, update, delete on all tables in schema public to postgres;

create database test owner postgres;
select database test;
create table users (username text, password text);
