use projet06_test;
insert ignore  into account(id, email,password,first_name, last_name,count) values ('200', 'jeanne@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Jeanne', 'Dupont', '1000');
insert ignore  into account(id, email,password,first_name, last_name,count) values ('201', 'martine@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Martine', 'Dupont', '1000');

insert ignore into account_contact(account_id, contact_id) values ('200', '201');
insert ignore into account_contact(account_id, contact_id) values ('201', '200');
insert ignore into transaction(id, amount, amount_ttc, rate, description, date, creditor_account_id, debtor_account_id) values ('301', '20', '20.1','0.05','spring event', '04/09/2022', '200', '201');
insert ignore into transaction(id, amount, amount_ttc, rate, description, date, creditor_account_id, debtor_account_id) values ('302', '20', '20.1','0.05','spring event', '04/09/2022', '201', '200');

commit;
