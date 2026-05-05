/*
CREATE SEQUENCE IF NOT EXISTS customer_seq START WITH 1 INCREMENT BY 1;
create table if not exists Customer
(
    id              bigint primary key,
    name            varchar(50) not null,
    surname         varchar(50) not null,
    age             int         not null,
    city            varchar(50) not null,
    cc_Number       varchar(16) not null,
    cc_Expiration   varchar(5)  not null,
    ccCVV          int         not null
);

CREATE SEQUENCE IF NOT EXISTS adress_seq START WITH 1 INCREMENT BY 1;
create table if not exists Address
(
    id      bigint primary key,
    street  varchar(30) not null,
    num     varchar(5)  not null,
    zip     varchar(7)  not null,
    city    varchar(30) not null,
    nation  varchar(30) not null,
);
*/
