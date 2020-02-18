create table if not exists users (
    id bigserial,
    first_name varchar(20) not null,
    last_name varchar(25) not null,
    username varchar(25) unique not null,
    password varchar(100) not null,
    favourites_count int8 not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    version int8 not null,
    display boolean not null,
    constraint users_pk primary key (id)
);