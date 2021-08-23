create database if not exists mysql_view default charset utf8;
create table if not exists data_source_info (
    id int primary key auto_increment,
    name varchar (256) unique,
    url varchar(255) not null,
    driver varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null
);