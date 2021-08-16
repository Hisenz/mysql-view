create table  if not exists data_source_info (
    id int primary key auto_increment,
    url varchar(255) not null,
    driver varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null
);