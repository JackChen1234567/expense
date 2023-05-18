CREATE TABLE users (
    id   BIGINT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    name  VARCHAR(255) NOT NULL,
    group_id BIGINT NOT NULL,
        CONSTRAINT FK_GROUP FOREIGN KEY (group_id) REFERENCES users_group (id)
);

