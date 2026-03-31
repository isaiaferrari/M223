create sequence if not exists item_seq start with 1 increment by 1;

create table if not exists Blog
(
    id          bigint       not null default next value for item_seq primary key,
    title       varchar(255) not null,
    date        varchar(10)  not null,
    category    varchar(50)  not null,
    author      varchar(50)  not null,
    likes       int          not null default 0,
    content     clob         not null
);