-- delete from movie_actor;
-- delete from Movie;
-- delete from Actor;
-- delete from Director;

insert into Director (id, name, surname)
values
    (NEXT VALUE FOR director_seq, 'Christopher', 'Nolan'),
    (NEXT VALUE FOR director_seq, 'Quentin', 'Tarantino');

insert into Actor (id, name, surname)
values
    (NEXT VALUE FOR actor_seq, 'Leonardo', 'DiCaprio'),
    (NEXT VALUE FOR actor_seq, 'Joseph', 'Gordon-Levitt'),
    (NEXT VALUE FOR actor_seq, 'Elliot', 'Page'),
    (NEXT VALUE FOR actor_seq, 'Matthew', 'McConaughey'),
    (NEXT VALUE FOR actor_seq, 'Anne', 'Hathaway'),
    (NEXT VALUE FOR actor_seq, 'John', 'Travolta'),
    (NEXT VALUE FOR actor_seq, 'Samuel', 'Jackson');

insert into Movie (id, title, release_year, director_id)
values
    (
        NEXT VALUE FOR movie_seq,
        'Inception',
        2010,
        (select distinct id from Director where name = 'Christopher' and surname = 'Nolan')
    ),
    (
        NEXT VALUE FOR movie_seq,
        'Interstellar',
        2014,
        (select distinct id from Director where name = 'Christopher' and surname = 'Nolan')
    ),
    (
        NEXT VALUE FOR movie_seq,
        'Pulp Fiction',
        1994,
        (select distinct id from Director where name = 'Quentin' and surname = 'Tarantino')
    );

insert into movie_actor (movie_id, actor_id)
values
    -- Inception
    (
        (select id from Movie where title = 'Inception'),
        (select id from Actor where name = 'Leonardo' and surname = 'DiCaprio')
    ),
    (
        (select id from Movie where title = 'Inception'),
        (select id from Actor where name = 'Joseph' and surname = 'Gordon-Levitt')
    ),
    (
        (select id from Movie where title = 'Inception'),
        (select id from Actor where name = 'Elliot' and surname = 'Page')
    ),

    -- Interstellar
    (
        (select id from Movie where title = 'Interstellar'),
        (select id from Actor where name = 'Matthew' and surname = 'McConaughey')
    ),
    (
        (select id from Movie where title = 'Interstellar'),
        (select id from Actor where name = 'Anne' and surname = 'Hathaway')
    ),

    -- Pulp Fiction
    (
        (select id from Movie where title = 'Pulp Fiction'),
        (select id from Actor where name = 'John' and surname = 'Travolta')
    ),
    (
        (select id from Movie where title = 'Pulp Fiction'),
        (select id from Actor where name = 'Samuel' and surname = 'Jackson')
    );
