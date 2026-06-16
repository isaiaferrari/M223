delete from Address;
insert into Address (id, street, num, zip, city, nation)
values (NEXT VALUE FOR address_seq, 'Piazza Guisan', '6', '6512', 'Giubiasco', 'Svizzera'),
       (NEXT VALUE FOR address_seq, 'Via Bellinzona', '10', '6743', 'Bodio', 'Svizzera'),
       (NEXT VALUE FOR address_seq, 'Viale Stefano Franscini', '23', '6500', 'Lugano', 'Svizzera');

delete from Customer;
insert into Customer (id, name, surname, age, address_id)
values
    (NEXT VALUE FOR customer_seq, 'Mario', 'Rossi', 24, (
        select distinct id from address where street = 'Piazza Guisan' and num = '6' and zip = '6512' and city = 'Giubiasco' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Guido', 'Bianchi', 34, (
        select distinct id from address where street = 'Via Bellinzona' and num = '10' and zip = '6743' and city = 'Bodio' and nation = 'Svizzera'
    )),
    (NEXT VALUE FOR customer_seq, 'Gino', 'Verdi', 57, (
        select distinct id from address where street = 'Viale Stefano Franscini' and num = '23' and zip = '6500' and city = 'Lugano' and nation = 'Svizzera'
    ));

delete from Reservation;
insert into Reservation (id, room, num, checkin, checkout, customer_id)
values
    (NEXT VALUE FOR reservation_seq, '437', '7', '23.12.2024', '06.01.2025', (
        select distinct id from customer where name = 'Mario' and surname = 'Rossi')),
    (NEXT VALUE FOR reservation_seq, '124', '3', '01.08.2025', '15.08.2025', (
        select distinct id from customer where name = 'Mario' and surname = 'Rossi')),
    (NEXT VALUE FOR reservation_seq, '437', '12', '27.12.2024', '02.01.2025', (
        select distinct id from customer where name = 'Guido' and surname = 'Bianchi'));

delete from meal_group;
insert into meal_group (id, name, description)
values (NEXT VALUE FOR mealgroup_seq, 'Colazione', 'Gruppo della colazione'),
       (NEXT VALUE FOR mealgroup_seq, 'Pranzo', 'Gruppo del pranzo'),
       (NEXT VALUE FOR mealgroup_seq, 'Cena', 'Gruppo della cena');

delete from customer_mealgroup;
insert into customer_mealgroup (customer_id, mealgroup_id)
values
    (select id from customer where name='Mario' and surname='Rossi', select id from meal_group where name='Colazione'),
    (select id from customer where name='Gino' and surname='Verdi', select id from meal_group where name='Colazione'),
    (select id from customer where name='Mario' and surname='Rossi', select id from meal_group where name='Pranzo'),
    (select id from customer where name='Guido' and surname='Bianchi', select id from meal_group where name='Pranzo'),
    (select id from customer where name='Guido' and surname='Bianchi', select id from meal_group where name='Cena'),
    (select id from customer where name='Mario' and surname='Rossi', select id from meal_group where name='Cena'),
    (select id from customer where name='Gino' and surname='Verdi', select id from meal_group where name='Cena');

