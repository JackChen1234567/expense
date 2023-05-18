CREATE TABLE users_group (
    id   BIGINT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
   name  VARCHAR(255) NOT NULL
);
insert into users_group (id, name) values (1, 'developer');
insert into users_group (id, name) values (2, 'tester');


