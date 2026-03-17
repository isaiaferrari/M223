delete from Customer;
insert into Customer (id, name, surname, age, city, cc_Number, cc_Expiration, ccCVV)
values
    (next value for customer_seq,'Mario', 'Rossi', 40, 'Lugano', '4868719196829038', '11/29', 123),
    (next value for customer_seq,'Giorgio', 'Verdi', 30, 'Bellinzona', '4868719581920723', '01/30', 654),
    (next value for customer_seq,'Ennio', 'Bianchi', 33, 'Chiasso', '4868719158130060', '07/27', 890);

