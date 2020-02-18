create table if not exists likes (
    id bigserial,
	news_id int8 not null,
	user_id int8 not null,
    constraint likes_pk primary key (id),
    constraint likes_un unique (news_id,user_id),
    constraint likes_fk_news foreign key (news_id) references news (id),
    constraint likes_fk_user foreign key (user_id) references users (id)
);