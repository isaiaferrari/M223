create table if not exists Customer
(
    id              identity,
    name            varchar(50) not null,
    surname         varchar(50) not null,
    age             int         not null,
    city            varchar(20) not null,
    ccExpiration    varchar(5)  not null,
    ccCVV           int         not null
);
