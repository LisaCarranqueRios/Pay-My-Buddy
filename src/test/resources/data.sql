
create database   IF NOT EXISTS projet06_test;
use projet06_test;

create table if not exists account(
id int PRIMARY KEY AUTO_INCREMENT,
email varchar(80) NOT NULL,
password varchar(200) NOT NULL,
first_name varchar(80) NOT NULL,
last_name varchar(80) NOT NULL,
count double);


create table if not exists bank_account(
id int PRIMARY KEY AUTO_INCREMENT,
iban varchar(80) NOT NULL,
first_name varchar(80) NOT NULL,
last_name varchar(80) NOT NULL,
bank_account_balance double,
--amount double,
user_account_id int NOT NULL,
FOREIGN KEY (user_account_id) REFERENCES account(ID));


create table  if not exists  transaction(
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

create table  if not exists  account_contact (
account_id int NOT NULL,
FOREIGN KEY (account_id) REFERENCES account(ID),
contact_id int NOT NULL,
FOREIGN KEY (contact_id) REFERENCES account(ID));


use projet06_test;

insert ignore  into account(id, email,password,first_name, last_name,count) values ('200', 'jeanne@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Jeanne', 'Dupont', '1000');
insert ignore  into account(id, email,password,first_name, last_name,count) values ('201', 'martine@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Martine', 'Dupont', '1000');
insert ignore into account_contact(account_id, contact_id) values ('200', '201');
insert ignore into account_contact(account_id, contact_id) values ('201', '200');
commit;
