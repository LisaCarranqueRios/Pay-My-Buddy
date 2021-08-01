use projet06;
insert ignore  into account(id, email,password,first_name, last_name,count) values ('200', 'jeannedupont@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Jeanne', 'Dupont', '100');
insert ignore  into account(id, email,password,first_name, last_name,count) values ('201', 'mireilledupont@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2','Mireille', 'Dupont', '100');
insert ignore  into account_contact(account_id, contact_id) values ('200', '201');
insert ignore  into account_contact(account_id, contact_id) values ('201', '200');
insert ignore  into transaction(id, amount, rate, description, date, creditor_account_id, debtor_account_id) values ('300', '10', '0.05','js event', '04/09/2022', '200', '201');
update IGNORE account set count='110' where id='200';
update IGNORE account set count='90' where id='201';

insert ignore into account(id, email ,password, first_name, last_name, count) values ('202', 'mariesmith@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Marie', 'Smith', '100');
insert ignore  into account(id, email ,password, first_name, last_name, count) values ('203', 'lulu@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Lulu', 'Boyd','100');
insert ignore into account_contact(account_id, contact_id) values ('202', '203');
insert ignore into account_contact(account_id, contact_id) values ('203', '202');
insert ignore into transaction(id, amount, rate, description, date, creditor_account_id, debtor_account_id) values ('301', '20', '0.05','spring event', '04/09/2022', '202', '203');
update IGNORE account set count='80' where id='203';
update IGNORE account set count='120' where id='202';
insert ignore into transaction(id, amount, rate, description, date, creditor_account_id, debtor_account_id) values ('302', '20', '0.05','spring event', '04/09/2022', '203', '202');
update IGNORE account set count='100' where id='203';
update IGNORE account set count='100' where id='202';

insert ignore  into account(id, email ,password, first_name, last_name, count) values ('204', 'lou@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Lou', 'Boyd','100');

commit;
