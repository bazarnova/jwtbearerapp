--truncate table "jwtbearer"."users" CASCADE;
--truncate table "jwtbearer"."messages" CASCADE;

insert into "jwtbearer"."users"
("name", "password") values
('usr1', 'password1'),
('usr2', 'password2'),
('usr3', 'password3')
;
--insert into "jwtbearer"."messages"
--("user_id", "message") values
--('1', 'mess1')
--;
