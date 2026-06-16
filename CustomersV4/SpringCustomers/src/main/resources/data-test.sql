delete from Address;
insert into Address (id, street, num, zip, city, nation)
values
    (NEXT VALUE FOR address_seq, 'Via Giuseppe Motta', '18', '6828', 'Balerna', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Via Monte Boglia', '7', '6962', 'Viganello', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Piazza Grande', '14', '6600', 'Locarno', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Via al Ponte', '29', '6710', 'Biasca', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Via Lavizzari', '5', '6900', 'Lugano', 'Svizzera');

delete from Customer;
insert into Customer (id, name, surname, age, address_id)
values
    (NEXT VALUE FOR customer_seq, 'Matteo', 'Bernasconi', 33, (
        select distinct id from address where street = 'Via Giuseppe Motta' and num = '18' and zip = '6828' and city = 'Balerna' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Chiara', 'Riva', 27, (
        select distinct id from address where street = 'Via Monte Boglia' and num = '7' and zip = '6962' and city = 'Viganello' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Simone', 'Fumagalli', 45, (
        select distinct id from address where street = 'Piazza Grande' and num = '14' and zip = '6600' and city = 'Locarno' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Elena', 'Moretti', 38, (
        select distinct id from address where street = 'Via al Ponte' and num = '29' and zip = '6710' and city = 'Biasca' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Fabio', 'Cattaneo', 50, (
        select distinct id from address where street = 'Via Lavizzari' and num = '5' and zip = '6900' and city = 'Lugano' and nation = 'Svizzera'
    ));

delete from Reservation;
insert into Reservation (id, room, num, checkin, checkout, customer_id)
values
    (NEXT VALUE FOR reservation_seq, '102', '1', '12.06.2025', '18.06.2025', (
        select distinct id from customer where name = 'Matteo' and surname = 'Bernasconi')),
    (NEXT VALUE FOR reservation_seq, '208', '2', '03.07.2025', '11.07.2025', (
        select distinct id from customer where name = 'Chiara' and surname = 'Riva')),
    (NEXT VALUE FOR reservation_seq, '315', '4', '22.08.2025', '28.08.2025', (
        select distinct id from customer where name = 'Simone' and surname = 'Fumagalli')),
    (NEXT VALUE FOR reservation_seq, '420', '6', '08.09.2025', '15.09.2025', (
        select distinct id from customer where name = 'Elena' and surname = 'Moretti')),
    (NEXT VALUE FOR reservation_seq, '525', '3', '18.12.2025', '26.12.2025', (
        select distinct id from customer where name = 'Fabio' and surname = 'Cattaneo')),
    (NEXT VALUE FOR reservation_seq, '102', '2', '28.12.2025', '05.01.2026', (
        select distinct id from customer where name = 'Matteo' and surname = 'Bernasconi'));

delete from meal_group;
insert into meal_group (id, name, description)
values (NEXT VALUE FOR mealgroup_seq, 'Colazione', 'Gruppo della colazione'),
       (NEXT VALUE FOR mealgroup_seq, 'Pranzo', 'Gruppo del pranzo'),
       (NEXT VALUE FOR mealgroup_seq, 'Cena', 'Gruppo della cena');

delete from customer_mealgroup;
insert into customer_mealgroup (customer_id, mealgroup_id)
values
    -- Matteo: Pensione completa
    ((select id from customer where name='Matteo' and surname='Bernasconi'), (select id from meal_group where name='Colazione')),
    ((select id from customer where name='Matteo' and surname='Bernasconi'), (select id from meal_group where name='Pranzo')),
    ((select id from customer where name='Matteo' and surname='Bernasconi'), (select id from meal_group where name='Cena')),

    -- Chiara: Solo colazione
    ((select id from customer where name='Chiara' and surname='Riva'), (select id from meal_group where name='Colazione')),

    -- Simone: Pranzo e Cena
    ((select id from customer where name='Simone' and surname='Fumagalli'), (select id from meal_group where name='Pranzo')),
    ((select id from customer where name='Simone' and surname='Fumagalli'), (select id from meal_group where name='Cena')),

    -- Elena: Mezza pensione
    ((select id from customer where name='Elena' and surname='Moretti'), (select id from meal_group where name='Colazione')),
    ((select id from customer where name='Elena' and surname='Moretti'), (select id from meal_group where name='Cena')),

    -- Fabio: Solo cena
    ((select id from customer where name='Fabio' and surname='Cattaneo'), (select id from meal_group where name='Cena'));