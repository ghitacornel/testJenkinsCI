create table person
(
    id   int primary key,
    name varchar(50) not null unique,
    pass varchar(50) not null
);