-- =====================
-- TEAM
-- =====================
delete from player;
delete from game_profile;
delete from team;

insert into team (id, name, headquarters, main_sponsor, annual_budget)
values
    (NEXT VALUE FOR team_seq, 'Neon Wolves',    'Los Angeles, USA',  'RedBull Esports',  2500000.00),
    (NEXT VALUE FOR team_seq, 'Iron Falcons',   'Seoul, South Korea','Samsung Gaming',   3800000.00),
    (NEXT VALUE FOR team_seq, 'Shadow Titans',  'Berlin, Germany',   'Logitech G',       1900000.00);

-- =====================
-- GAME PROFILE
-- =====================
insert into game_profile (id, level, win_rate, kda_ratio, tier)
values
    (NEXT VALUE FOR gameprofile_seq, 42, '73%', '4.2',  'Diamond'),
    (NEXT VALUE FOR gameprofile_seq, 38, '61%', '3.1',  'Platinum'),
    (NEXT VALUE FOR gameprofile_seq, 55, '80%', '6.5',  'Challenger'),
    (NEXT VALUE FOR gameprofile_seq, 29, '48%', '2.4',  'Gold'),
    (NEXT VALUE FOR gameprofile_seq, 33, '55%', '3.7',  'Platinum'),
    (NEXT VALUE FOR gameprofile_seq, 47, '69%', '5.0',  'Diamond');

-- =====================
-- PLAYER
-- =====================
insert into player (id, name, surname, nationality, contract_expiration_date, gameprofile_id, team_id)
values
    (NEXT VALUE FOR player_seq, 'Lucas',  'Müller',   'German',     '31.12.2025',
          (select id from game_profile where level = 42 and tier = 'Diamond'),
          (select id from team where name = 'Neon Wolves')),

    (NEXT VALUE FOR player_seq, 'Ji-ho',  'Park',     'South Korean','30.06.2026',
          (select id from game_profile where level = 38 and tier = 'Platinum'),
          (select id from team where name = 'Neon Wolves')),

    (NEXT VALUE FOR player_seq, 'Carlos', 'Rivera',   'Mexican',    '15.09.2025',
          (select id from game_profile where level = 55 and tier = 'Challenger'),
          (select id from team where name = 'Iron Falcons')),

    (NEXT VALUE FOR player_seq, 'Yuki',   'Tanaka',   'Japanese',   '28.02.2026',
          (select id from game_profile where level = 29 and tier = 'Gold'),
          (select id from team where name = 'Iron Falcons')),

    (NEXT VALUE FOR player_seq, 'Emma',   'Dubois',   'French',     '01.07.2026',
          (select id from game_profile where level = 33 and tier = 'Platinum'),
          (select id from team where name = 'Shadow Titans')),

    (NEXT VALUE FOR player_seq, 'Marco',  'Ferretti', 'Italian',    '31.03.2027',
          (select id from game_profile where level = 47 and tier = 'Diamond'),
          (select id from team where name = 'Shadow Titans'));