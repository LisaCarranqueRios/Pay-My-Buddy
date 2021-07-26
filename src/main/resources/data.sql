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


/*

update IGNORE account set id='200', email='jeannedupont@mail.com',password='$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2',first_name='Jeanne', last_name='Dupont',count='100';
update IGNORE account set id='201', email='mireilledupont@mail.com',password='$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2',first_name='Mireille', last_name='Dupont',count='100';
update IGNORE account_contact set account_id='200', contact_id='201';
update IGNORE account_contact set account_id='201', contact_id='200';
update IGNORE transaction set id='300', amount='10', rate='0.05', description='js event', date='04/09/2022', creditor_account_id='200', debtor_account_id='201';
update IGNORE account set count='110' where id='200';
update IGNORE account set count='90' where id='201';

update IGNORE account  set id='202', email='mariesmith@mail.com' ,password='$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', first_name='Marie', last_name='Smith', count='100';
update IGNORE account  set id='203', email='louis@mail.com' ,password='$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', first_name='Louis', last_name='Boyd', count='100';
update IGNORE account_contact set account_id='202', contact_id='203';
update IGNORE account_contact set account_id='203', contact_id='202';
update IGNORE transaction set id='301', amount='20', rate='0.05', description='spring event', date='04/09/2022', creditor_account_id='202', debtor_account_id='203';
update IGNORE account set count='80' where id='203';
update IGNORE account set count='120' where id='202';
update IGNORE transaction set id='302', amount='20', rate='0.05', description='spring event', date='04/09/2022', creditor_account_id='203', debtor_account_id='202';
update IGNORE account set count='100' where id='203';
update IGNORE account set count='100' where id='202';

update IGNORE account set id='204', email='luc@mail.com' ,password='$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', first_name='Luc', last_name='Boyd', count='100';

commit;

*/
