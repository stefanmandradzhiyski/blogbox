create table if not exists favourites (
    id bigserial,
	news_id int8 not null,
	user_id int8 not null,
    constraint favourites_pk primary key (id),
    constraint favourites_un unique (news_id,user_id),
    constraint favourites_fk_news foreign key (news_id) references news (id),
    constraint favourites_fk_user foreign key (user_id) references users (id)
);