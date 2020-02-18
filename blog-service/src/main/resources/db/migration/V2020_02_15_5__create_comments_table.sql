create table if not exists comments (
	id bigserial,
	rate varchar(10) not null,
	comment varchar(250) not null,
	news_id int8 not null,
	user_id int8 not null,
	created_at timestamp not null,
	updated_at timestamp not null,
	version int8 not null,
	constraint comments_pk primary key (id),
	constraint comments_fk_news foreign key (news_id) references news (id),
	constraint comments_fk_user foreign key (user_id) references users (id)
);