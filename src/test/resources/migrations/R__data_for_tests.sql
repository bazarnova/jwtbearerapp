--truncate table "jwtbearer"."users" CASCADE;
--truncate table "jwtbearer"."messages" CASCADE;

insert into "jwtbearer"."users"
("name", "password") values
('user1', 'password1'),
('user2', 'password2'),
('user3', 'password3')
;
--insert into "jwtbearer"."messages"
--("user_id", "message") values
--('1', 'mess1')
--;
