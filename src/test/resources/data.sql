
use projet06_test;
insert ignore  into account(id, email,password,first_name, last_name,count) values ('200', 'jeanne@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Jeanne', 'Dupont', '1000');
insert ignore  into account(id, email,password,first_name, last_name,count) values ('201', 'martine@mail.com', '$2a$10$MiAcjZQu0fAjWtoCc6NpSO.4.1yteMsb6mhmJloqoAcM0d7Z5tAB2', 'Martine', 'Dupont', '1000');
commit;
