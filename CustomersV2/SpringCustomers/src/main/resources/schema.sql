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

