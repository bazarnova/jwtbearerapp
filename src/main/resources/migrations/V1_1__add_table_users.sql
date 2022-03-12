create schema if not exists "jwtbearer";

create table "jwtbearer"."users" (
    "id" SERIAL PRIMARY KEY,
    "name" varchar(50) not null,
    "password" varchar(100) not null
);
