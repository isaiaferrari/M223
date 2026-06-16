delete from Address;
insert into Address (id, street, num, zip, city, nation)
values
    (NEXT VALUE FOR address_seq, 'Via Cantonale', '12', '6900', 'Lugano', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Via San Gottardo', '45', '6500', 'Bellinzona', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Corso Pestalozzi', '8', '6900', 'Lugano', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Via alla Stazione', '3', '6600', 'Locarno', 'Svizzera'),
    (NEXT VALUE FOR address_seq, 'Via Nassa', '22', '6900', 'Lugano', 'Svizzera');

delete from Customer;
insert into Customer (id, name, surname, age, address_id)
values
    (NEXT VALUE FOR customer_seq, 'Luca', 'Ferrari', 29, (
        select distinct id from address where street = 'Via Cantonale' and num = '12' and zip = '6900' and city = 'Lugano' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Anna', 'Russo', 41, (
        select distinct id from address where street = 'Via San Gottardo' and num = '45' and zip = '6500' and city = 'Bellinzona' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Marco', 'Colombo', 35, (
        select distinct id from address where street = 'Corso Pestalozzi' and num = '8' and zip = '6900' and city = 'Lugano' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Sara', 'Galli', 52, (
        select distinct id from address where street = 'Via alla Stazione' and num = '3' and zip = '6600' and city = 'Locarno' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Davide', 'Conti', 47, (
        select distinct id from address where street = 'Via Nassa' and num = '22' and zip = '6900' and city = 'Lugano' and nation = 'Svizzera'
    ));

delete from Reservation;
insert into Reservation (id, room, num, checkin, checkout, customer_id)
values
    (NEXT VALUE FOR reservation_seq, '101', '2', '10.06.2025', '15.06.2025', (
        select distinct id from customer where name = 'Luca' and surname = 'Ferrari')),
    (NEXT VALUE FOR reservation_seq, '205', '5', '01.07.2025', '10.07.2025', (
        select distinct id from customer where name = 'Anna' and surname = 'Russo')),
    (NEXT VALUE FOR reservation_seq, '310', '1', '20.08.2025', '25.08.2025', (
        select distinct id from customer where name = 'Marco' and surname = 'Colombo')),
    (NEXT VALUE FOR reservation_seq, '412', '9', '05.09.2025', '12.09.2025', (
        select distinct id from customer where name = 'Sara' and surname = 'Galli')),
    (NEXT VALUE FOR reservation_seq, '518', '4', '15.12.2025', '22.12.2025', (
        select distinct id from customer where name = 'Davide' and surname = 'Conti')),
    (NEXT VALUE FOR reservation_seq, '101', '3', '23.12.2025', '02.01.2026', (
        select distinct id from customer where name = 'Luca' and surname = 'Ferrari'));

delete from meal_group;
insert into meal_group (id, name, description)
values (NEXT VALUE FOR mealgroup_seq, 'Colazione', 'Gruppo della colazione'),
       (NEXT VALUE FOR mealgroup_seq, 'Pranzo', 'Gruppo del pranzo'),
       (NEXT VALUE FOR mealgroup_seq, 'Cena', 'Gruppo della cena');

delete from customer_mealgroup;
insert into customer_mealgroup (customer_id, mealgroup_id)
values
    -- Luca: Pensione completa (tutti i pasti)
    ((select id from customer where name='Luca' and surname='Ferrari'), (select id from meal_group where name='Colazione')),
    ((select id from customer where name='Luca' and surname='Ferrari'), (select id from meal_group where name='Pranzo')),
    ((select id from customer where name='Luca' and surname='Ferrari'), (select id from meal_group where name='Cena')),

    -- Anna: Mezza pensione (Colazione e Cena)
    ((select id from customer where name='Anna' and surname='Russo'), (select id from meal_group where name='Colazione')),
    ((select id from customer where name='Anna' and surname='Russo'), (select id from meal_group where name='Cena')),

    -- Marco: Solo colazione
    ((select id from customer where name='Marco' and surname='Colombo'), (select id from meal_group where name='Colazione')),

    -- Sara: Solo cena
    ((select id from customer where name='Sara' and surname='Galli'), (select id from meal_group where name='Cena')),

    -- Davide: Pranzo e Cena
    ((select id from customer where name='Davide' and surname='Conti'), (select id from meal_group where name='Pranzo')),
    ((select id from customer where name='Davide' and surname='Conti'), (select id from meal_group where name='Cena'));