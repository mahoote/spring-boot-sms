create table questions
(
    id       serial primary key,
    key_word varchar(255) not null,
    question varchar(500) not null

);

create table users
(
    user_id  serial primary key,
    phone_nr varchar(15) not null
)
