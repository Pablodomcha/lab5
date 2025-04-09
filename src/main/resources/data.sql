insert into users (username, password, enable) values ('alumno', '{noop}alumno1', true);
insert into authorities (username, authority) values ('alumno', 'ROLE_ALUMN');

insert into users (username, password, enable) values ('profesor', '{noop}profesor1', true);
insert into authorities (username, authority) values ('profesor', 'ROLE_PROF');