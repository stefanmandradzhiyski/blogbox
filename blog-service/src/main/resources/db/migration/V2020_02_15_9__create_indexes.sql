CREATE INDEX users_first_name_idx ON users (first_name);
CREATE UNIQUE INDEX users_username_idx ON users (username);
CREATE INDEX news_name_idx ON news (name);
CREATE INDEX news_user_idx ON news (user_id);
CREATE INDEX news_created_at_idx ON news (created_at);
CREATE INDEX comments_rate_idx ON comments (rate);
CREATE INDEX comments_news_idx ON comments (news_id);
CREATE INDEX comments_user_idx ON comments (user_id);
CREATE INDEX comments_created_at_idx ON comments (created_at);