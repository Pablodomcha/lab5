CREATE TABLE users (
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(200) not null,
    enable boolean not null,
);

CREATE TABLE authorities (
    username varchar_ignorecase(50) not null primary key,
    authority varchar_ignorecase(200) not null,
    constraint fk_authorities_users foreign key(username) references users(username),
);