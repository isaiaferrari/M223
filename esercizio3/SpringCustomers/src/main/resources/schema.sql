create table if not exists Customer
(
    id              identity,
    name            varchar(50) not null,
    surname         varchar(50) not null,
    age             int         not null,
    city            varchar(20) not null,
    cc_Number       varchar(16) not null,
    cc_Expiration   varchar(5)  not null,
    cc_CVV          int         not null
);
