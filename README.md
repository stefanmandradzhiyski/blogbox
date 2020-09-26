# blogbox

Tech-stack: Java 11, Spring Boot, Spring Data, Spring Security, Spring WEB, Hibernate, PostgreSQL, Flyway, Gradle.

BlogBox is a news application that can be used by anyone. The application can be used without being registered, but its functionality will be limited in this way. Once registered, you are given full control over your personal profile, your news, favorites, likes and comments. The application offers the ability to build an administrative and user interface. There are 3 types of implemented roles: 
User, Writer and Administrator. 
The administrative portal can be accessed on port 8086 and the user portal on 8084.

User-related features in the user panel:
-	POST(api/v1/auth/login) - login;
-	POST(api/v1/users/register) - register;
-	GET(api/v1/users/{userId}) - anyone can view a particular active user by his id, the returned data number is determined by the role of the applicant;
-	GET(api/v1/users/un/{username}) - anyone can view a particular active user by his username, the returned data number is determined by the role of the applicant;
-	GET(api/v1/users/favourites/{userId}) - only a user can view their own favorite list;
-	GET(api/v1/users/news/{userId}) - only a user can view a list of news created by a particular user. If the user views his list of created news, then he will see all irrespective of their status, while if the user views a foreign list of created news, he will see only those with the status "APPROVED";
-	POST(api/v1/users/search) - anyone can search users in the system by specific criteria;
-	PUT(api/v1/users) - only a user can update his own account.

User-related features in the admin panel:
-	POST(api/v1/auth/login) - login;
-	GET(api/v1/users/{userId}) - can view any user by his id, always getting all the data;
-	GET(api/v1/users/un/{username}) - can view any user by his username, always getting all the data;
-	GET(api/v1/users/favourites/{userId}) - can view all users' favorite lists;
-	GET(api/v1/users/news/{userId}) - can view all users’ created news;
-	GET(api/v1/users) - may require a request for all users in the system;
-	POST(api/v1/users/search) - can search for users;
-	PUT(api/v1/users) - can change the profile of any user, which involves changing only names;
-	DELETE(api/v1/users/{userId}) - can delete / block / disable any user;
-	POST(api/v1/users/manage-user-roles) - can control the roles of each user.

User-related features in the archive admin panel:
-	All features of the admin panel, but they will only be applied to deleted / inactive users;
-	PUT(api/v1/users/inactive/restore/{userId}) - can recover / activate any deleted user.

News-related features in the user panel:
-	POST(api/v1/news) - only a writer can create news, news automatically accepting a "WAITING" status;
-	GET(api/v1/news/{newsId}) - anyone can view a specific news with an "APPROVED" status by id (does not apply if the news is yours), the returned data number is determined by the role of the applicant. Only a user can view their created news regardless of status;
-	POST(api/v1/news/search) - anyone can search for news with a status APPROVED by specific criteria;
-	GET(api/v1/news/likes/{newsId}), GET(api/v1/news/dislikes/{newsId}), GET(api/v1/news/favourites/{newsId}) - anyone can view the three types of statistics on a particular news with an APPROVED status;
-	PUT(api/v1/news) - only a writer can correct their own news regardless of status;
-	POST(api/v1/news/statistics) - only a user can create new statistics for a certain news with the status APPROVED (likes, dislikes, favorites);
-	DELETE(api/v1/news/{newsId}) - only a writer can delete their own news regardless of status.

News-related features in the admin panel:
-	POST(api/v1/news) - can create news;
-	GET(api/v1/news/{newsId}) - can view a specific news by id regardless of its status;
-	GET(api/v1/news/user/{userId}) - can view the news created by a specific user regardless of status;
-	GET(api/v1/news) - can request all news in the system regardless of status;
-	POST(api/v1/news/search) - can search for news by a certain criteria and choose the status;
-	GET(api/v1/news/likes/{newsId}), GET(api/v1/news/dislikes/{newsId}), GET(api/v1/news/favourites/{newsId}) - can view statistics of a particular news regardless of status;
-	PUT(api/v1/news) - can edit any news;
-	POST(api/v1/news/statistics) - can create new statistics for any news regardless of status;
-	PATCH(api/v1/news/status) - can change the status of the news;
-	DELETE(api/v1/news/{newsId}) - can delete specific news.

News-related features in the archive admin panel:
-	Оffers some admin panel actions but only applies to deleted ones.

Comment-related features in the user panel:
-	POST(api/v1/comments) - only a user can comment on a specific news;
-	GET(api/v1/comments/{commentId}) - Anyone can read comments;
-	PUT(api/v1/comments) - only a user can update their own comment;
-	DELETE(api/v1/comments/{commentId}) - only a user can delete their own comment;

