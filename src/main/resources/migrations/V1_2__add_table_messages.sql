create table "jwtbearer"."messages" (
    "id" SERIAL PRIMARY KEY,
    "user_id" BIGINT not null,
    "message" varchar(100) not null
);

ALTER TABLE "jwtbearer"."messages"
ADD FOREIGN KEY("user_id")
REFERENCES "users" ("id");


