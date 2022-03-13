truncate "jwtbearer"."users" CASCADE;
truncate "jwtbearer"."messages" CASCADE;

insert into "jwtbearer"."users"
("name", "password") values
('user1', 'pass'),
('user2', 'pass')
;
--insert into "jwtbearer"."messages"
--("user_id", "message") values
--('1', 'mess1')
--;
