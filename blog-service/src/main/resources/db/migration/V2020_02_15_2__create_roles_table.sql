create table if not exists roles (
    id bigserial,
    name varchar(15) not null,
    version int8 not null,
    constraint roles_pk primary key (id)
);