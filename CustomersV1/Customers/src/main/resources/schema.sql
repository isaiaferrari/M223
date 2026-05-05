create table if not exists Customer(
    id identity,
    name varchar(50) not null,
    surname varchar(50) not null,
    age int not null
);
