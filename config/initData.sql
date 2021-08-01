/* Setting up PROD DB */

create database prod;
use prod;

create table account(
id int PRIMARY KEY AUTO_INCREMENT,
email varchar(80) NOT NULL,
password varchar(200) NOT NULL,
first_name varchar(80) NOT NULL,
last_name varchar(80) NOT NULL,
count double);

create table transaction(
id int PRIMARY KEY AUTO_INCREMENT,
date varchar(20) NOT NULL,
amount double NOT NULL,
amount_ttc double,
rate double,
description varchar(20),
debtor_account_id int NOT NULL,
FOREIGN KEY (debtor_account_id) REFERENCES account(ID),
creditor_account_id int NOT NULL,
FOREIGN KEY (creditor_account_id) REFERENCES account(ID));

create table account_contact (
account_id int NOT NULL,
FOREIGN KEY (account_id) REFERENCES account(ID),
contact_id int NOT NULL,
FOREIGN KEY (contact_id) REFERENCES account(ID));


use prod;
insert ignore  into account(id, email,password,first_name, last_name,count) values ('200', 'jeannedupont@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Jeanne', 'Dupont', '100');
insert ignore  into account(id, email,password,first_name, last_name,count) values ('201', 'mireilledupont@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2','Mireille', 'Dupont', '100');
insert ignore  into account_contact(account_id, contact_id) values ('200', '201');
insert ignore  into account_contact(account_id, contact_id) values ('201', '200');
insert ignore  into transaction(id, amount, amount_ttc, rate, description, date, creditor_account_id, debtor_account_id) values ('300', '10', '10.5', '0.05','js event', '04/09/2022', '200', '201');
update IGNORE account set count='110' where id='200';
update IGNORE account set count='90' where id='201';

insert ignore into account(id, email ,password, first_name, last_name, count) values ('202', 'mariesmith@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Marie', 'Smith', '100');
insert ignore  into account(id, email ,password, first_name, last_name, count) values ('203', 'lulu@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Lulu', 'Boyd','100');
insert ignore into account_contact(account_id, contact_id) values ('202', '203');
insert ignore into account_contact(account_id, contact_id) values ('203', '202');
insert ignore into transaction(id, amount, amount_ttc, rate, description, date, creditor_account_id, debtor_account_id) values ('301', '20', '20.1','0.05','spring event', '04/09/2022', '202', '203');
update IGNORE account set count='80' where id='203';
update IGNORE account set count='120' where id='202';
insert ignore into transaction(id, amount, amount_ttc, rate, description, date, creditor_account_id, debtor_account_id) values ('302', '20', '20.1','0.05','spring event', '04/09/2022', '203', '202');
update IGNORE account set count='100' where id='203';
update IGNORE account set count='100' where id='202';

insert ignore  into account(id, email ,password, first_name, last_name, count) values ('204', 'lou@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Lou', 'Boyd','100');

commit;

