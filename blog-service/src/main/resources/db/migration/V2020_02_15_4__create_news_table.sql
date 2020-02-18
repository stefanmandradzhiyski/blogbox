create table if not exists news (
	id bigserial,
	name varchar(50) not null,
	short_description varchar(150) not null,
	main_information varchar(5000) not null,
	user_id int8 not null,
	views_count int8 not null,
	likes_count int8 not null,
	dislikes_count int8 not null,
	favourites_count int8 not null,
	status varchar(10) not null,
	created_at timestamp not null,
	updated_at timestamp not null,
	version int8 not null,
	display boolean not null,
	constraint news_pk primary key (id),
	constraint news_fk_user foreign key (user_id) references users (id)
);