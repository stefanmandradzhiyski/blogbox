create table if not exists users_roles (
	user_id int8 not null,
	role_id int8 not null,
	constraint users_roles_fk_user foreign key (user_id) references users (id),
	constraint users_roles_fk_role foreign key (role_id) references roles (id)
);