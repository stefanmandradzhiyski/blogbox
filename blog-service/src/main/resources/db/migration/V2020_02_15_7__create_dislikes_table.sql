create table if not exists dislikes (
    id bigserial,
	news_id int8 not null,
	user_id int8 not null,
    constraint dislikes_pk primary key (id),
    constraint dislikes_un unique (news_id,user_id),
    constraint dislikes_fk_news foreign key (news_id) references news (id),
    constraint dislikes_fk_user foreign key (user_id) references users (id)
);