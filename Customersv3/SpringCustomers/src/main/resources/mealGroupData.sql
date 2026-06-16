insert into meal_group (id, name, description)
values (NEXT VALUE FOR mealgroup_seq, 'Colazione', 'Gruppo della colazione'),
       (NEXT VALUE FOR mealgroup_seq, 'Pranzo', 'Gruppo del pranzo'),
       (NEXT VALUE FOR mealgroup_seq, 'Cena', 'Gruppo della cena');

insert into customer_mealgroup (customer_id, mealgroup_id)
values
    (select id from customer where name='Mario' and surname='Rossi', select id from meal_group where name='Colazione'),
    (select id from customer where name='Gino' and surname='Verdi', select id from meal_group where name='Colazione'),
    (select id from customer where name='Mario' and surname='Rossi', select id from meal_group where name='Pranzo'),
    (select id from customer where name='Guido' and surname='Bianchi', select id from meal_group where name='Pranzo'),
    (select id from customer where name='Guido' and surname='Bianchi', select id from meal_group where name='Cena'),
    (select id from customer where name='Mario' and surname='Rossi', select id from meal_group where name='Cena'),
    (select id from customer where name='Gino' and surname='Verdi', select id from meal_group where name='Cena');